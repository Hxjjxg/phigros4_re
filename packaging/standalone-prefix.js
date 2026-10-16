// The builder inserts the generated bundle inside startStandalone().
'use strict';
globalThis.PHIGROS_STANDALONE = true;
let standaloneStarted = false, standalonePending = false;
const standaloneDeadline = Date.now() + 120000;
const standaloneWait = setInterval(function () {
    if (standaloneStarted || standalonePending) return;
    if (Date.now() >= standaloneDeadline) {
        clearInterval(standaloneWait);
        console.error('[PhiDGLab] Android startup timed out');
        return;
    }
    if (!Java.available) return;
    standalonePending = true;
    Java.perform(function () {
        standalonePending = false;
        const app = Java.use('android.app.ActivityThread').currentApplication();
        if (!app) return;
        standaloneStarted = true;
        clearInterval(standaloneWait);
        try { startStandalone(); }
        catch (error) {
            Java.use('android.util.Log').e('PhiDGLab', String(error.stack || error));
            Java.use('re.phigros.dglab.Bootstrap').writeStatus(JSON.stringify({
                phase: 'script_failed', error: String(error), timestamp: Date.now()
            }));
        }
    });
}, 250);
function startStandalone() {
