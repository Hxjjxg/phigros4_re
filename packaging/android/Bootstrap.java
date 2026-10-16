package re.phigros.dglab;

import android.content.Context;
import android.os.Process;
import android.util.AtomicFile;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/** Called after UnityPlayerActivity.onCreate; never waits on the UI thread. */
public final class Bootstrap {
    private static boolean started;
    private static Context context;

    public static synchronized void start(Context activity) {
        if (started) return;
        started = true;
        context = activity.getApplicationContext();
        if (!Process.is64Bit()) {
            Log.e("PhiDGLab", "This build requires arm64");
            return;
        }
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    writeStatus("{\"phase\":\"loading_gadget\"}");
                    System.loadLibrary("phi_gadget");
                    Log.i("PhiDGLab", "Gadget 16.7.19 loaded in autonomous script mode");
                } catch (Throwable error) {
                    Log.e("PhiDGLab", "Gadget startup failed", error);
                    try {
                        org.json.JSONObject status = new org.json.JSONObject();
                        status.put("phase", "startup_failed");
                        status.put("error", error.toString());
                        writeStatus(status.toString());
                    } catch (Exception ignored) { }
                }
            }
        }, "Phigros-DGLab-start").start();
    }

    public static synchronized void writeStatus(String json) {
        if (context == null) return;
        AtomicFile file = new AtomicFile(new File(context.getFilesDir(), "dglab-status.json"));
        FileOutputStream stream = null;
        try {
            stream = file.startWrite();
            stream.write(json.getBytes(StandardCharsets.UTF_8));
            file.finishWrite(stream);
        } catch (Exception error) {
            if (stream != null) file.failWrite(stream);
            Log.e("PhiDGLab", "Cannot write status", error);
        }
    }
}
