    Phi.log = function (message) {
        Java.perform(() => Java.use('android.util.Log').i('PhiDGLab', String(message)));
    };
    setInterval(function () {
        Java.perform(function () {
            try {
                const status = phiStatus();
                status.timestamp = Date.now();
                status.mode = 'standalone';
                Java.use('re.phigros.dglab.Bootstrap').writeStatus(JSON.stringify(status));
            } catch (error) { Phi.log('Status write failed: ' + error); }
        });
    }, 1000);
    Phi.log('Standalone script initialized; waiting for IL2CPP');
}
