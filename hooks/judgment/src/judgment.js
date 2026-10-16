(function () {
    const P = Phi;
    let flushScheduled = false;
    // Avoid Java calls, socket work, and logging on Unity's judgment thread.
    P.enqueue = function (event) {
        if (P.queue.length >= 256) { P.stats.dropped++; return; }
        P.queue.push(event);
        if (!flushScheduled) {
            flushScheduled = true;
            setImmediate(function () {
                flushScheduled = false;
                const events = P.queue.splice(0);
                if (P.disposed) return;
                Java.perform(function () {
                    events.forEach(function (e) {
                        try {
                            if (!P.config.enabled || !P.config[e.grade]) {
                                P.stats.dropped++; return;
                            }
                            if (P.transport) {
                                if (!P.transport.sendJudgment(e)) { P.stats.dropped++; return; }
                            } else {
                                if (typeof wsState !== 'function' || wsState() !== 'OPEN') { P.stats.dropped++; return; }
                                wsSend(JSON.stringify(e));
                            }
                            P.stats.handedOff++;
                        } catch (err) { P.stats.errors++; P.log('Send error: ' + err); }
                    });
                });
            });
        }
    };

    function install() {
        const r = P.initRuntime();
        const klass = r.game('ScoreControl');
        const offsets = {};
        ['perfect', 'good', 'bad', 'miss', '_combo'].forEach(n => offsets[n] = r.offset(klass, n));
        ['Perfect', 'Good', 'Bad', 'Miss'].forEach(function (name) {
            const grade = name.toLowerCase();
            const method = r.method(klass, name, name === 'Miss' ? 1 : name === 'Bad' ? 2 : 4);
            const rva = r.hook(method, {
                onEnter: function (args) {
                    this.self = args[0];
                    this.time = Date.now();
                    this.before = this.self.add(offsets[grade]).readS32();
                    // AAPCS64: float/HFA parameters use SIMD registers; bool uses x1.
                    // Do NOT interpret args[1]/args[2] as noteCode/judgeTime.
                    this.isHold = (name === 'Perfect' || name === 'Good') ? args[1].toInt32() !== 0 : null;
                },
                onLeave: function () {
                    try {
                        if (this.self.add(offsets[grade]).readS32() !== this.before + 1) return;
                        P.stats.observed[grade]++;
                        const seq = ++P.sequence;
                        if (!P.config.enabled || !P.config[grade]) return;
                        const counts = {};
                        ['perfect', 'good', 'bad', 'miss'].forEach(n => counts[n] = this.self.add(offsets[n]).readS32());
                        P.enqueue({type: 'judgment', version: 1, source: 'phigros',
                            session: P.session, seq: seq, timestamp: this.time, grade: grade,
                            isHold: this.isHold, combo: this.self.add(offsets._combo).readS32(),
                            counts: counts});
                    } catch (err) { P.stats.errors++; P.log('Hook read error: ' + err); }
                }
            });
            P.log('ScoreControl.' + name + ' resolved at RVA ' + rva);
        });
        P.ready = true;
        if (P.installSettingsEntry) P.installSettingsEntry();
    }
    // Attach after IL2CPP loads; never fall back to guessed RVAs.
    const deadline = Date.now() + 120000;
    let lastInitError = '';
    const wait = setInterval(function () {
        if (P.disposed) { clearInterval(wait); return; }
        if (Date.now() >= deadline) {
            clearInterval(wait);
            P.initializationError = lastInitError || 'libil2cpp.so did not load';
            P.log('Initialization timed out: ' + P.initializationError);
            return;
        }
        if (!Process.findModuleByName('libil2cpp.so')) return;
        try { install(); P.initializationError = null; clearInterval(wait); }
        catch (e) {
            P.ready = false;
            P.listeners.forEach(h => h.detach()); P.listeners = [];
            const error = String(e);
            P.initializationError = error;
            if (error !== lastInitError) P.log('Waiting for game initialization: ' + error);
            lastInitError = error;
        }
    }, 250);
    P.waitTimer = wait;
    globalThis.phiStatus = function () {
        return {ready: P.ready, initializationError: P.initializationError || null, config: P.config, stats: P.stats,
                connection: P.transport ? P.transport.state() : typeof wsState === 'function' ? wsState() : 'NOT_READY',
                embedded: P.transport ? P.transport.status() : undefined};
    };
    let disposal = null;
    globalThis.phiDispose = function () {
        if (disposal) return disposal;
        disposal = (async function () {
            P.disposed = true; clearInterval(P.waitTimer);
            P.queue = [];
            if (P.closeSettings) await P.closeSettings();
            if (P.transport) await P.transport.dispose();
            if (P.removeSettingsEntry) await P.removeSettingsEntry();
            P.listeners.forEach(h => h.detach()); P.listeners = [];
            if (typeof wsClose === 'function') wsClose();
            P.ready = false;
            return {disposed: true};
        })().catch(function (error) { disposal = null; throw error; });
        return disposal;
    };
    rpc.exports.status = globalThis.phiStatus;
    rpc.exports.dispose = globalThis.phiDispose;
})();
