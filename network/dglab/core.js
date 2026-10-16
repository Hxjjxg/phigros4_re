/* Embedded single-controller DG-LAB V4 API. Platform-independent; see README. */
(function (root) {
    'use strict';
    const record = x => x !== null && typeof x === 'object' && !Array.isArray(x);
    const copy = x => JSON.parse(JSON.stringify(x));
    function merge(a, b) {
        if (!record(a) || !record(b)) return copy(b);
        const out = Object.assign({}, a);
        Object.keys(b).forEach(k => {
            if (['__proto__', 'constructor', 'prototype'].includes(k)) return;
            out[k] = merge(a[k], b[k]);
        });
        return out;
    }
    function integer(x, min, max, name) {
        if (!Number.isInteger(x) || x < min || x > max) throw new Error(name + ': expected ' + min + '..' + max);
        return x;
    }
    function frames(value, version) {
        if (!Array.isArray(value) || !value.length || value.length > 300) throw new Error('Expected 1..300 pulse frames');
        const size = version === 2 ? 3 : 8;
        return value.map(frame => {
            if (typeof frame === 'string' && new RegExp('^[0-9a-fA-F]{' + size * 2 + '}$').test(frame)) return frame.toUpperCase();
            if (Array.isArray(frame) && frame.length === size) return frame.map(v => integer(v, 0, 255, 'pulse byte'));
            throw new Error('Invalid V' + version + ' waveform frame');
        });
    }
    class DglabCore {
        constructor(io, options) {
            this.io = io;
            this.targetId = options.targetId;
            this.peer = null;
            this.devices = new Map(); this.pending = new Map(); this.counter = 0;
            this.closed = false; this.history = []; this.lastError = null;
            this.stats = {requests: 0, responses: 0, timeouts: 0, judgments: 0, triggered: 0, skipped: 0};
            this.lastTrigger = -Infinity;
            this.heartbeatAt = 0;
            this.clock = options.clock || Date.now;
            this.timeout = options.timeout || 8000;
            this.emit = options.emit || (() => {});
        }
        log(type, data) {
            const event = {type, time: this.clock(), data};
            this.history.push(event); if (this.history.length > 40) this.history.shift();
            try { this.emit(event); } catch (_) { /* consumers cannot corrupt protocol state */ }
        }
        send(id, value) {
            if (!this.io.send(id, JSON.stringify(value))) throw new Error('Connection unavailable or backpressure');
        }
        open(id, path) {
            if (this.closed) { this.io.close(id, 4000, 'controller_disconnected'); return; }
            try {
                this.send(id, {type: 'hello', clientId: id});
                const match = /^\/\?(.+)$/.exec(path);
                const params = new Map();
                if (match) match[1].split('&').forEach(p => {
                    const i = p.indexOf('=');
                    const k = decodeURIComponent(i < 0 ? p : p.slice(0, i));
                    if (!params.has(k)) params.set(k, decodeURIComponent(i < 0 ? '' : p.slice(i + 1)));
                });
                if ((params.get('targetId') || params.get('tid')) !== this.targetId) {
                    this.send(id, {type: 'error', code: 'controller_not_found'});
                    this.io.close(id, 4001, 'controller_not_found'); return;
                }
                if (this.peer) {
                    this.send(id, {type: 'error', code: 'controller_busy'});
                    this.io.close(id, 4003, 'controller_busy'); return;
                }
                this.peer = id; this.devices.clear(); this.lastTrigger = -Infinity;
                this.send(id, {type: 'controller_attached', clientId: this.targetId});
                this.log('client_attached', {clientId: id});
                this.requestDevices().catch(e => this.log('discovery_error', String(e)));
            } catch (e) {
                this.disconnect(id); this.io.close(id, 1008, 'invalid_connection');
                this.lastError = String(e);
            }
        }
        disconnect(id) {
            if (id !== this.peer) return;
            this.peer = null; this.devices.clear();
            for (const p of this.pending.values()) p.reject(new Error('DG-LAB App disconnected'));
            this.pending.clear(); this.log('client_disconnected', {clientId: id});
        }
        receive(id, text) {
            if (id !== this.peer || this.closed) return;
            try {
                if (text.length > 65536) throw new Error('Frame too large');
                const frame = JSON.parse(text);
                if (!record(frame)) return;
                if (frame.type === 'ping') { this.send(id, {type: 'pong', ts: this.clock()}); return; }
                if (frame.type === 'error') { this.lastError = String(frame.code); this.log('error', frame); return; }
                if (frame.type !== 'message' || !record(frame.data)) return;
                const data = frame.data;
                if (data.t === 'resp') {
                    const reqId = data.reqId || data.requestId;
                    const p = this.pending.get(reqId);
                    if (!p || p.clientId !== id) return;
                    this.pending.delete(reqId); this.stats.responses++;
                    if (data.error) { this.lastError = String(data.error); p.reject(new Error(String(data.error))); }
                    else {
                        // A delayed devices.get must not overwrite a newer snapshot/patch.
                        if (p.method === 'devices.get' && p.revision === this.revision && record(data.result)) this.replace(data.result.devices);
                        p.resolve(data.result);
                    }
                    this.log('response', {reqId, method: p.method, result: data.result, error: data.error});
                } else if (data.t === 'ev') {
                    if (data.ev === 'devices.snapshot') this.replace(data.devices);
                    else if (data.ev === 'devices.patch') {
                        (Array.isArray(data.added) ? data.added : []).forEach(d => this.upsert(d));
                        (Array.isArray(data.removed) ? data.removed : []).forEach(s => this.devices.delete(s));
                    } else if (data.ev === 'slots.patch') {
                        (Array.isArray(data.slots) ? data.slots : []).forEach(p => {
                            if (!record(p)) return;
                            const d = this.devices.get(p.slotId); if (!d) return;
                            ['props', 'slotState'].forEach(k => { if (record(p[k])) d[k] = merge(d[k] || {}, p[k]); });
                        });
                    }
                    this.revision = (this.revision || 0) + 1;
                    this.log('event', data);
                }
            } catch (e) { this.lastError = String(e); this.log('protocol_error', String(e)); }
        }
        upsert(d) {
            if (record(d) && typeof d.slotId === 'string' && d.slotId.length <= 256 && this.devices.size < 32)
                this.devices.set(d.slotId, copy(d));
        }
        replace(devices) {
            if (!Array.isArray(devices)) return;
            this.devices.clear(); devices.forEach(d => this.upsert(d));
        }
        request(method, data, timeout) {
            if (!this.peer || this.closed) return Promise.reject(new Error('No paired DG-LAB App'));
            if (this.pending.size >= 128) return Promise.reject(new Error('Too many pending requests'));
            const reqId = this.targetId + '-' + (++this.counter);
            const clientId = this.peer;
            const promise = new Promise((resolve, reject) => {
                this.pending.set(reqId, {clientId, resolve, reject, method, revision: this.revision,
                    deadline: this.clock() + integer(timeout || this.timeout, 1, 120000, 'timeout')});
                try {
                    // App-bound envelope intentionally has no clientId, matching upstream relay.
                    this.send(clientId, {type: 'message', data: {t: 'req', reqId, m: method, data}});
                    this.stats.requests++;
                } catch (e) { this.pending.delete(reqId); reject(e); }
            });
            promise.requestId = reqId;
            return promise;
        }
        requestDevices() { return this.request('devices.get'); }
        operate(data) {
            try {
                if (!record(data) || !this.devices.has(data.s)) throw new Error('Unknown slotId');
                integer(data.c, 0, 1, 'channel');
                if (![0, 3, 4, 7].includes(data.t)) throw new Error('Unsupported operation');
                const d = Object.assign({}, data);
                if (d.p !== undefined) integer(d.p, 0, 2, 'priority');
                if (d.im !== undefined && typeof d.im !== 'boolean') throw new Error('im must be boolean');
                if (d.t === 0 || d.t === 4) integer(d.d, 100, 30000, 'duration ms');
                if (d.t === 0) {
                    d.ver = d.ver === undefined ? 3 : d.ver;
                    if (![2, 3].includes(d.ver)) throw new Error('Unsupported pulse version');
                    d.v = frames(d.v, d.ver);
                } else {
                    integer(d.v, d.t === 3 ? -200 : 0, d.t === 7 ? 0 : 200, 'intensity');
                }
                return this.request('device.op', d, Math.max(this.timeout, (d.d || 0) + 2000));
            } catch (e) { return Promise.reject(e); }
        }
        clear(slotId, channel) {
            const data = {};
            if (slotId !== undefined && slotId !== '') {
                data.s = slotId;
                if (channel !== undefined) data.c = integer(channel, 0, 1, 'channel');
            } else if (channel !== undefined) return Promise.reject(new Error('channel requires slotId'));
            return this.request('device.op.clear', data);
        }
        stopOutput() { return this.clear(); }
        tick() {
            const now = this.clock();
            if (this.peer && !this.io.connected(this.peer)) this.disconnect(this.peer);
            for (const [id, p] of this.pending) if (p.deadline <= now) {
                this.pending.delete(id); this.stats.timeouts++;
                this.lastError = 'RPC timeout: ' + p.method;
                p.reject(new Error(this.lastError));
            }
            if (this.peer && now >= this.heartbeatAt) {
                this.heartbeatAt = now + 30000;
                try { this.send(this.peer, {type: 'heartbeat'}); }
                catch (e) { this.lastError = String(e); }
            }
        }
        judgment(event, config, waveforms) {
            this.stats.judgments++;
            const rule = config.rules[event.grade];
            const skip = () => { this.stats.skipped++; return false; };
            if (!rule || !rule.intensity || !this.peer || this.closed) return skip();
            // Do not replay game events after Android/network scheduling stalls.
            const now = this.clock();
            if (now - event.timestamp > 500 || now - this.lastTrigger < config.cooldownMs) return skip();
            const candidates = [...this.devices.values()].filter(d => d.type === 'COYOTE_030' && d.slotState?.hasDevice !== false);
            const device = config.slotId ? candidates.find(d => d.slotId === config.slotId) : candidates.length === 1 ? candidates[0] : null;
            if (!device) { this.lastError = 'Select one connected COYOTE_030 slot'; return skip(); }
            const base = waveforms[rule.waveform];
            if (!base) return skip();
            this.lastTrigger = now;
            const triggerPeer = this.peer, triggerTime = now;
            const pulse = Array.from({length: Math.ceil(rule.durationMs / 100)}, (_, i) => base[i % base.length]);
            const common = {s: device.slotId, c: config.channel, p: 1, d: rule.durationMs, im: true};
            // Tasks of different types run together. Each has a finite duration;
            // im=true replaces prior same-type tasks instead of building a backlog.
            const intensity = this.operate(Object.assign({}, common, {t: 4, v: rule.intensity}));
            const waveform = this.operate(Object.assign({}, common, {t: 0, v: pulse, ver: 3}));
            this.stats.triggered++;
            this.log('judgment', {grade: event.grade, seq: event.seq, slotId: device.slotId,
                intensityRequest: intensity.requestId, waveformRequest: waveform.requestId});
            Promise.all([intensity, waveform]).catch(e => {
                this.lastError = String(e); this.log('operation_error', String(e));
                // A partial failure must not leave the other task running unattended.
                if (this.peer === triggerPeer && this.lastTrigger === triggerTime)
                    this.clear(device.slotId, config.channel).catch(() => {});
            });
            return true;
        }
        status() {
            return copy({targetId: this.targetId, clientId: this.peer, devices: [...this.devices.values()],
                pending: this.pending.size, stats: this.stats, lastError: this.lastError, history: this.history});
        }
        shutdown() {
            if (this.closed) return;
            this.closed = true;
            if (this.peer) {
                const id = this.peer;
                try { this.send(id, {type: 'controller_disconnected', clientId: this.targetId}); } catch (_) {}
                this.io.close(id, 4000, 'controller_disconnected'); this.disconnect(id);
            }
        }
    }
    root.DglabCore = DglabCore;
    if (typeof module !== 'undefined') module.exports = {DglabCore, frames};
})(globalThis);
