(function () {
    const P = Phi;
    let prefs, UI, clickClass, dismissClass, callbacks = {}, callbackId = 0;
    let dialog = null;
    let panelButtons = [];
    let uiScale = 1;
    const classSuffix = Date.now().toString();
    function connection() { return P.transport ? P.transport.state() : wsState(); }
    function stateText() {
        if (!P.transport) return connection() + '   Sent: ' + P.stats.handedOff + '   Dropped: ' + P.stats.dropped;
        const s = P.transport.status();
        return s.state + ' | Triggered: ' + (s.stats ? s.stats.triggered : 0) + '\n' +
            s.devices.map(d => d.name + ' [' + d.slotId + '] ' + d.type).join('\n') +
            ((s.error || s.lastError) ? '\n' + (s.error || s.lastError) : '');
    }

    function validate(config) {
        const url = String(config.url).trim();
        let uri;
        try { uri = Java.use('java.net.URI').$new(url); }
        catch (_) { throw new Error('Enter a ws:// or wss:// URL with a host and valid port.'); }
        const scheme = uri.getScheme();
        if ((String(scheme) !== 'ws' && String(scheme) !== 'wss') || uri.getHost() === null ||
            uri.getFragment() !== null || uri.getPort() === 0 || uri.getPort() > 65535)
            throw new Error('Enter a ws:// or wss:// URL with a host and valid port.');
        if (String(config.qrText).length > 1200) throw new Error('QR text is too long (maximum 1200 characters).');
        config.url = url;
        ['enabled', 'perfect', 'good', 'bad', 'miss'].forEach(k => {
            if (typeof config[k] !== 'boolean') throw new Error(k + ' must be true or false');
        });
        config.qrText = String(config.qrText);
        return P.transport ? P.transport.validate(config) : config;
    }
    function apply(config) {
        const next = validate(Object.assign({}, P.config, config));
        const previous = P.config;
        prefs.edit().putString('config', JSON.stringify(next)).apply();
        P.config = next;
        // Drop pending events if the destination changes; never send old events to a new peer.
        if (previous.url !== next.url || !next.enabled) {
            P.stats.dropped += P.queue.length; P.queue = [];
        }
        if (P.transport) P.transport.configure(next, previous);
        else if (!next.enabled) wsClose();
        else if (!previous.enabled || previous.url !== next.url || wsState() !== 'OPEN') wsConnect(next.url);
        return next;
    }
    globalThis.phiConfigure = function (config) {
        return new Promise((resolve, reject) => Java.perform(function () {
            try {
                if (P.disposed) throw new Error('Script is disposed');
                if (!prefs) throw new Error('Settings are not ready'); resolve(apply(config));
            }
            catch (e) { reject(e); }
        }));
    };
    rpc.exports.configure = globalThis.phiConfigure;

    function listen(fn) {
        const id = String(++callbackId);
        callbacks[id] = fn;
        const listener = clickClass.$new(); listener.key.value = id;
        return listener;
    }
    function text(ctx, value, size) {
        const view = UI.TextView.$new(ctx);
        view.setText(Java.use('java.lang.String').$new(value));
        view.setTextSize((size || 14) * uiScale); view.setTextColor(-1);
        return view;
    }
    function editValue(view) {
        return Java.cast(view.getText(), Java.use('java.lang.CharSequence')).toString();
    }
    // Always called on Android's UI thread. Remove JS-backed listeners before
    // dismissing; otherwise Android may dispatch them after the script unloads.
    function closeOnMain() {
        panelButtons.forEach(button => { button.setOnClickListener(null); button.$dispose(); });
        panelButtons = [];
        if (dialog) {
            const old = dialog; dialog = null;
            old.setOnDismissListener(null);
            old.dismiss(); old.$dispose();
        }
        callbacks = {};
    }
    function show() {
        if (P.disposed || !prefs || !UI) return;
        Java.scheduleOnMainThread(function () {
            if (P.disposed) return;
            try {
                closeOnMain();
                const activity = Java.use('com.unity3d.player.UnityPlayer').currentActivity.value;
                if (!activity || activity.isFinishing()) throw new Error('No active Unity activity');
                const metrics = activity.getResources().getDisplayMetrics();
                const density = metrics.density.value;
                uiScale = Math.max(0.65, Math.min(1, metrics.heightPixels.value / density / 430));
                if (P.embedded) uiScale *= 0.85;
                const dp = n => Math.round(n * density * uiScale);
                const outer = UI.LinearLayout.$new(activity); outer.setOrientation(1);
                outer.setBackgroundColor(-15130332);
                const heading = text(activity, P.embedded ? 'Phigros × DG-LAB' : 'Judgment link', 20);
                heading.setPadding(dp(16), dp(10), dp(16), dp(6)); outer.addView(heading);
                const root = UI.LinearLayout.$new(activity);
                root.setOrientation(0); root.setPadding(dp(16), dp(8), dp(16), dp(8));
                root.setBackgroundColor(-15130332);
                outer.addView(root, UI.LayoutParams.$new(-1, 0, 1));
                const scroll = UI.ScrollView.$new(activity);
                const form = UI.LinearLayout.$new(activity); form.setOrientation(1);
                form.setPadding(0, 0, dp(20), 0); scroll.addView(form);
                root.addView(scroll, UI.LayoutParams.$new(0, -1, 1.3));
                const rightScroll = UI.ScrollView.$new(activity);
                const right = UI.LinearLayout.$new(activity); right.setOrientation(1); right.setGravity(1);
                rightScroll.addView(right);
                root.addView(rightScroll, UI.LayoutParams.$new(0, -1, 1));
                const state = text(activity, stateText(), 13); form.addView(state);
                const checks = {};
                function check(key, label, parent) {
                    const v = UI.CheckBox.$new(activity); v.setText(Java.use('java.lang.String').$new(label));
                    v.setTextColor(-1); v.setTextSize(13 * uiScale); v.setMinHeight(dp(32));
                    v.setContentDescription(Java.use('java.lang.String').$new('judgment.' + key));
                    v.setChecked(P.config[key]); checks[key] = v;
                    if (parent) parent.addView(v, UI.LayoutParams.$new(0, dp(36), 1));
                    else form.addView(v, UI.LayoutParams.$new(-1, dp(36)));
                }
                check('enabled', P.embedded ? 'Enable DG-LAB link' : 'Enable judgment link');
                const urlLabel = text(activity, P.embedded ? 'Same-phone server address' : 'WebSocket server', 13);
                form.addView(urlLabel);
                const url = UI.EditText.$new(activity);
                url.setContentDescription(Java.use('java.lang.String').$new('judgment.url'));
                UI.TextView.setText.overload('java.lang.CharSequence').call(url, Java.use('java.lang.String').$new(P.config.url)); url.setTextColor(-1);
                url.setTextSize(14 * uiScale); url.setSingleLine(true); url.setInputType(17);
                form.addView(url, UI.LayoutParams.$new(-1, dp(40)));
                const grades1 = UI.LinearLayout.$new(activity), grades2 = UI.LinearLayout.$new(activity);
                form.addView(grades1); form.addView(grades2);
                check('good', 'Good', grades1); check('bad', 'Bad', grades1);
                check('miss', 'Miss', P.embedded ? grades1 : grades2);
                if (!P.embedded) check('perfect', 'Perfect (debug)', grades2);
                const extraButtons = [];
                let outputFields = null;
                if (P.embedded) {
                    form.removeView(urlLabel); form.removeView(url);
                    outputFields = {rules: {}, channel: P.config.channel};
                    function entry(parent, value, key, width, numeric) {
                        const v = UI.EditText.$new(activity);
                        UI.TextView.setText.overload('java.lang.CharSequence').call(v, Java.use('java.lang.String').$new(String(value)));
                        v.setContentDescription(Java.use('java.lang.String').$new('dglab.' + key));
                        v.setTextColor(-1); v.setTextSize(12 * uiScale); v.setSingleLine(true);
                        v.setPadding(dp(4), 0, dp(4), 0);
                        v.setInputType(numeric ? 2 : 1);
                        parent.addView(v, width ? UI.LayoutParams.$new(0, dp(40), width) : UI.LayoutParams.$new(-1, dp(40)));
                        return v;
                    }
                    const channel = UI.Button.$new(activity);
                    function channelTitle() { channel.setText(Java.use('java.lang.String').$new('Channel ' + (outputFields.channel ? 'B' : 'A') + ' (tap to switch)')); }
                    channelTitle(); channel.setTextSize(12 * uiScale);
                    channel.setPadding(0, 0, 0, 0);
                    channel.setContentDescription(Java.use('java.lang.String').$new('dglab.channel'));
                    channel.setOnClickListener(listen(() => { outputFields.channel = 1 - outputFields.channel; channelTitle(); }));
                    channel.setMinHeight(0); form.addView(channel, UI.LayoutParams.$new(-1, dp(36))); extraButtons.push(channel);
                    form.addView(text(activity, 'Grade / intensity (0 = off) / duration ms / waveform', 12));
                    ['good', 'bad', 'miss'].forEach(g => {
                        const row = UI.LinearLayout.$new(activity); form.addView(row);
                        row.addView(text(activity, g, 12), UI.LayoutParams.$new(dp(40), dp(40)));
                        const r = P.config.rules[g], f = {waveform: r.waveform}; outputFields.rules[g] = f;
                        f.intensity = entry(row, r.intensity, g + '.intensity', 0.8, true);
                        f.durationMs = entry(row, r.durationMs, g + '.durationMs', 1.1, true);
                        const wave = UI.Button.$new(activity);
                        wave.setTextSize(10 * uiScale); wave.setMinWidth(0); wave.setPadding(0, 0, 0, 0);
                        wave.setContentDescription(Java.use('java.lang.String').$new('dglab.' + g + '.waveform'));
                        const label = () => wave.setText(Java.use('java.lang.String').$new(f.waveform)); label();
                        wave.setOnClickListener(listen(() => {
                            const keys = Object.keys(DGLAB_WAVES); f.waveform = keys[(keys.indexOf(f.waveform) + 1) % keys.length]; label();
                        }));
                        row.addView(wave, UI.LayoutParams.$new(0, dp(40), 1.4)); extraButtons.push(wave);
                    });
                    form.addView(text(activity, 'Minimum trigger interval (ms); new hits replace active tasks', 12));
                    outputFields.cooldownMs = entry(form, P.config.cooldownMs, 'cooldownMs', 0, true);
                    form.addView(text(activity, 'Coyote 3 slot ID (blank: only connected device)', 12));
                    outputFields.slotId = entry(form, P.config.slotId, 'slotId');
                    form.addView(urlLabel); form.addView(url, UI.LayoutParams.$new(-1, dp(40)));
                }
                if (!P.embedded) form.addView(text(activity, 'QR text (blank uses server URL)', 13));
                const qrText = UI.EditText.$new(activity);
                qrText.setContentDescription(Java.use('java.lang.String').$new('judgment.qrText'));
                qrText.setHint(Java.use('java.lang.String').$new('Use server URL'));
                qrText.setHintTextColor(-5197648);
                UI.TextView.setText.overload('java.lang.CharSequence').call(qrText, Java.use('java.lang.String').$new(P.config.qrText)); qrText.setTextColor(-1);
                qrText.setTextSize(13 * uiScale); qrText.setSingleLine(false);
                form.addView(qrText, UI.LayoutParams.$new(-1, dp(44)));
                if (P.embedded) qrText.setVisibility(8);
                const error = text(activity, '', 13); error.setTextColor(-28571); form.addView(error);
                const save = UI.Button.$new(activity); save.setText(Java.use('java.lang.String').$new(P.embedded ? 'Save' : 'Save and connect'));
                const footer = UI.LinearLayout.$new(activity); footer.setPadding(dp(16), 0, dp(16), dp(6));
                footer.addView(save, UI.LayoutParams.$new(0, dp(44), 1));
                const close = UI.Button.$new(activity); close.setText(Java.use('java.lang.String').$new('Close'));
                footer.addView(close, UI.LayoutParams.$new(0, dp(44), 1)); outer.addView(footer);
                save.setContentDescription(Java.use('java.lang.String').$new('judgment.save'));
                close.setContentDescription(Java.use('java.lang.String').$new('judgment.close'));
                right.addView(text(activity, P.embedded ? 'DG-LAB 4 pairing QR' : 'Connection QR', 18));
                const image = UI.ImageView.$new(activity); image.setBackgroundColor(-1);
                const qrSide = Math.max(dp(80), Math.min(dp(150), Math.floor(metrics.heightPixels.value * 0.32)));
                right.addView(image, UI.LayoutParams.$new(qrSide, qrSide));
                const previewText = text(activity, '', 11); previewText.setGravity(1); right.addView(previewText);
                const preview = UI.Button.$new(activity); preview.setText(Java.use('java.lang.String').$new('Refresh QR'));
                const qrActions = UI.LinearLayout.$new(activity); right.addView(qrActions);
                qrActions.addView(preview, UI.LayoutParams.$new(0, dp(40), 1));
                const refresh = UI.Button.$new(activity); refresh.setText(Java.use('java.lang.String').$new('Status'));
                qrActions.addView(refresh, UI.LayoutParams.$new(0, dp(40), 1));
                [save, preview, refresh, close].forEach(b => { b.setTextSize(13 * uiScale); b.setMinHeight(0); if (P.embedded) b.setPadding(0, 0, 0, 0); });
                if (P.embedded) {
                    const copy = UI.Button.$new(activity); copy.setText(Java.use('java.lang.String').$new('Copy pairing link'));
                    copy.setTextSize(12 * uiScale); right.addView(copy);
                    copy.setOnClickListener(listen(() => {
                        const clipboard = Java.cast(activity.getSystemService('clipboard'), Java.use('android.content.ClipboardManager'));
                        clipboard.setPrimaryClip(Java.use('android.content.ClipData').newPlainText(
                            Java.use('java.lang.String').$new('DG-LAB'), Java.use('java.lang.String').$new(P.transport.pairing(P.config.url))));
                    })); extraButtons.push(copy);
                    const stop = UI.Button.$new(activity); stop.setText(Java.use('java.lang.String').$new('STOP'));
                    stop.setTextSize(12 * uiScale); stop.setContentDescription(Java.use('java.lang.String').$new('dglab.stop'));
                    stop.setPadding(0, 0, 0, 0);
                    stop.setOnClickListener(listen(() => {
                        apply({enabled: false}); checks.enabled.setChecked(false);
                        state.setText(Java.use('java.lang.String').$new('Stopping and clearing tasks…'));
                    })); stop.setMinHeight(0); footer.addView(stop, UI.LayoutParams.$new(0, dp(44), 1)); extraButtons.push(stop);
                    right.addView(text(activity, 'Same phone: copy link, or save a screenshot and import it in DG-LAB 4 Socket Control.', 11));
                }
                panelButtons = [save, preview, refresh, close].concat(extraButtons).map(button => Java.retain(button));
                function drawQR() {
                    const payload = P.transport ? P.transport.pairing(P.config.url) : editValue(qrText).trim() || editValue(url).trim();
                    if (!payload || payload.length > 1200) throw new Error('Enter QR text of 1–1200 characters.');
                    const qr = qrcode(0, 'M'); qr.addData(payload, 'Byte'); qr.make();
                    const modules = qr.getModuleCount(), side = modules + 8;
                    const pixels = [];
                    for (let y = 0; y < side; y++) for (let x = 0; x < side; x++)
                        pixels.push(x >= 4 && y >= 4 && x < modules + 4 && y < modules + 4 &&
                            qr.isDark(y - 4, x - 4) ? -16777216 : -1);
                    const bitmap = UI.Bitmap.createBitmap.overload('[I', 'int', 'int', 'android.graphics.Bitmap$Config').call(
                        UI.Bitmap, Java.array('int', pixels), side, side, UI.BitmapConfig.ARGB_8888.value);
                    // Integer scaling preserves modules and the four-module quiet zone.
                    const scale = Math.max(1, Math.floor(qrSide / side));
                    const enlarged = UI.Bitmap.createScaledBitmap(bitmap, side * scale, side * scale, false);
                    image.setImageBitmap(enlarged);
                    image.setScaleType(UI.ScaleType.CENTER_INSIDE.value);
                    previewText.setText(Java.use('java.lang.String').$new(P.embedded ? P.config.url : payload));
                }
                function guarded(fn) { return listen(function () {
                    try { fn(); error.setText(Java.use('java.lang.String').$new('')); }
                    catch (e) { error.setText(Java.use('java.lang.String').$new(String(e.message || e))); }
                }); }
                preview.setOnClickListener(guarded(drawQR));
                refresh.setOnClickListener(guarded(function () {
                    state.setText(Java.use('java.lang.String').$new(stateText()));
                }));
                save.setOnClickListener(guarded(function () {
                    const next = {url: editValue(url), qrText: editValue(qrText)};
                    Object.keys(checks).forEach(k => next[k] = checks[k].isChecked());
                    if (outputFields) {
                        next.slotId = editValue(outputFields.slotId); next.channel = outputFields.channel;
                        next.cooldownMs = Number(editValue(outputFields.cooldownMs)); next.rules = {};
                        Object.keys(outputFields.rules).forEach(g => {
                            const r = outputFields.rules[g];
                            next.rules[g] = {intensity: Number(editValue(r.intensity)), durationMs: Number(editValue(r.durationMs)), waveform: r.waveform};
                        });
                    }
                    // Validate the QR before committing any settings.
                    validate(Object.assign({}, P.config, next));
                    if (!P.embedded) drawQR();
                    apply(next); drawQR();
                    state.setText(Java.use('java.lang.String').$new(next.enabled ? 'Saved. Connection: ' + connection() : 'Saved. Link disabled.'));
                }));
                close.setOnClickListener(listen(closeOnMain));
                drawQR();
                dialog = Java.retain(UI.Dialog.$new(activity));
                dialog.requestWindowFeature(1); dialog.setContentView(outer);
                dialog.setOnDismissListener(dismissClass.$new()); dialog.show();
                dialog.getWindow().setSoftInputMode(16); // Resize for the keyboard.
                dialog.getWindow().setLayout(Math.floor(metrics.widthPixels.value * 0.9), Math.floor(metrics.heightPixels.value * 0.88));
            } catch (e) { closeOnMain(); P.log('Settings UI: ' + e.stack); }
        });
    }
    globalThis.phiSettings = function () { Java.perform(show); };
    rpc.exports.settings = globalThis.phiSettings;
    P.closeSettings = function () { return new Promise((resolve, reject) => Java.perform(function () {
        Java.scheduleOnMainThread(function () {
            try {
                closeOnMain();
                // Drain previously posted UI callbacks, then resolve off the UI
                // callback stack so RPC callers may safely unload the script.
                Java.scheduleOnMainThread(function () { setImmediate(resolve); });
            } catch (e) { setImmediate(() => reject(e)); }
        });
    })); };
    rpc.exports.closesettings = P.closeSettings;
    globalThis.phiCloseSettings = P.closeSettings;

    Java.perform(function () {
        const app = Java.use('android.app.ActivityThread').currentApplication();
        prefs = app.getSharedPreferences(P.embedded ? 'phigros_dglab_v4' : 'phigros_judgment_link', 0);
        try {
            const saved = prefs.getString('config', null);
            if (saved) P.config = validate(Object.assign({}, P.config, JSON.parse(String(saved))));
        } catch (e) { P.log('Ignoring invalid saved settings: ' + e); }
        UI = {};
        const names = {
            TextView: 'android.widget.TextView', LinearLayout: 'android.widget.LinearLayout',
            LayoutParams: 'android.widget.LinearLayout$LayoutParams', ScrollView: 'android.widget.ScrollView',
            EditText: 'android.widget.EditText', CheckBox: 'android.widget.CheckBox',
            Button: 'android.widget.Button', ImageView: 'android.widget.ImageView',
            ScaleType: 'android.widget.ImageView$ScaleType', Dialog: 'android.app.Dialog',
            Bitmap: 'android.graphics.Bitmap', BitmapConfig: 'android.graphics.Bitmap$Config'
        };
        Object.keys(names).forEach(k => UI[k] = Java.use(names[k]));
        clickClass = Java.registerClass({name: 're.phigros.JudgmentClick' + classSuffix,
            implements: [Java.use('android.view.View$OnClickListener')], fields: {key: 'java.lang.String'},
            methods: {onClick: function () { const f = callbacks[String(this.key.value)]; if (f && !P.disposed) f(); }}});
        dismissClass = Java.registerClass({name: 're.phigros.JudgmentDismiss' + classSuffix,
            implements: [Java.use('android.content.DialogInterface$OnDismissListener')],
            methods: {onDismiss: function () { closeOnMain(); }}});
        if (P.transport) P.transport.configure(P.config);
        else if (P.config.enabled) wsConnect(P.config.url);
        P.log('Settings ready; phiSettings() opens the panel.');
    });

    P.installSettingsEntry = function () {
        const r = P.runtime, api = r.api;
        const setting = r.game('Setting'), managerClass = r.game('SettingItemsManager');
        const ml = r.klass('Assembly-CSharp.dll', 'Phigros2.Utils.MultiLanguageText', 'MultiLanguageTextString');
        const action = r.klass('UnityEngine.CoreModule.dll', 'UnityEngine.Events', 'UnityAction');
        const component = r.klass('UnityEngine.CoreModule.dll', 'UnityEngine', 'Component');
        const object = r.klass('UnityEngine.CoreModule.dll', 'UnityEngine', 'Object');
        const transform = r.klass('UnityEngine.CoreModule.dll', 'UnityEngine', 'Transform');
        const managerOffset = r.offset(setting, 'settingItemsManager');
        const holderOffset = r.offset(managerClass, 'itemsHolder');
        const add = r.method(managerClass, 'AddButton', 2);
        const refresh = r.method(managerClass, 'RefreshItems', 0);
        const getGameObject = r.method(component, 'get_gameObject', 0);
        const getTransform = r.method(component, 'get_transform', 0);
        const find = r.method(transform, 'Find', 1);
        const rename = r.method(object, 'set_name', 1);
        const destroy = r.method(object, 'DestroyImmediate', 1);
        let current = NULL, button = NULL, failed = NULL;
        let cleanup = null;
        const marker = 'PhigrosJudgmentLink';
        P.removeSettingsEntry = function () {
            if (current.isNull()) return Promise.resolve();
            return new Promise((resolve, reject) => {
                // Keep the script loaded if Unity isn't ticking; don't claim
                // cleanup succeeded while a live entry still owns callbacks.
                const timer = setTimeout(() => { cleanup = null; reject(new Error('Unity settings update is paused; return to Gameplay settings and retry dispose')); }, 3000);
                cleanup = {resolve: () => { clearTimeout(timer); setImmediate(resolve); }, reject: e => { clearTimeout(timer); setImmediate(() => reject(e)); }};
            });
        };
        r.hook(r.method(setting, 'Update', 0), {
            onEnter: function (args) {
                if (cleanup) {
                    const pending = cleanup; cleanup = null;
                    try {
                        const manager = args[0].add(managerOffset).readPointer();
                        const holder = manager.add(holderOffset).readPointer();
                        const name = r.str(marker), handle = api.gchandle_new(name, 0);
                        try {
                            const existing = r.invoke(find, holder, [name]);
                            if (!existing.isNull()) r.invoke(destroy, NULL, [r.invoke(getGameObject, existing)]);
                            r.invoke(refresh, manager);
                        } finally { api.gchandle_free(handle); }
                        current = NULL; button = NULL; failed = NULL; pending.resolve();
                    } catch (e) { pending.reject(e); }
                    return;
                }
                if (P.disposed || args[0].equals(current) || args[0].equals(failed)) return;
                const handles = [];
                const pin = p => { handles.push(api.gchandle_new(p, 0)); return p; };
                try {
                    const manager = args[0].add(managerOffset).readPointer();
                    if (manager.isNull()) return;
                    const holder = manager.add(holderOffset).readPointer();
                    if (holder.isNull()) return;
                    // Script reload: remove only our prior empty-listener entry.
                    const existing = r.invoke(find, holder, [pin(r.str(marker))]);
                    if (!existing.isNull()) r.invoke(destroy, NULL, [r.invoke(getGameObject, existing)]);
                    const title = pin(api.object_new(ml));
                    r.invoke(r.method(ml, '.ctor', 0), title);
                    r.invoke(r.method(ml, 'set_NewString', 1), title, [pin(r.str(P.embedded ? 'DG-LAB link' : 'Judgment link'))]);
                    const listeners = pin(api.array_new(action, 0)); // null would throw in AddButton
                    button = r.invoke(add, manager, [title, listeners]);
                    const go = r.invoke(getGameObject, button);
                    r.invoke(rename, go, [pin(r.str(marker))]);
                    // Put the entry first, where it is visible without scrolling.
                    r.invoke(r.method(transform, 'SetAsFirstSibling', 0), r.invoke(getTransform, button));
                    r.invoke(refresh, manager);
                    current = args[0];
                    P.log('Added Judgment link to Gameplay settings.');
                } catch (e) { failed = args[0]; P.log('Settings entry: ' + e.stack); }
                finally { handles.forEach(h => api.gchandle_free(h)); }
            }
        });
        // Reuse the prefab's button wiring; intercept only our own item.
        r.hook(r.method(r.game('ButtonSettingItem'), '<Start>b__3_0', 0), {
            onEnter: function (args) { if (args[0].equals(button)) setImmediate(globalThis.phiSettings); }
        });
        // Clear identity across scene exits, including allocator address reuse.
        r.hook(r.method(setting, 'Back', 0), {onEnter: function () { current = NULL; button = NULL; failed = NULL; }});
        r.hook(r.method(setting, 'Start', 0), {onEnter: function () { current = NULL; button = NULL; failed = NULL; }});
    };
    if (P.ready) P.installSettingsEntry();
})();
