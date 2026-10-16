package com.voxelbusters.nativeplugins.utilities;

import android.util.Log;
import android.widget.Toast;
import com.voxelbusters.nativeplugins.NativePluginHelper;

/* JADX INFO: loaded from: classes.dex */
public class Debug {
    public static boolean ENABLED = false;

    public static void log(String str, String str2, boolean z) {
        if (ENABLED) {
            Log.d(str, str2);
        }
    }

    public static void log(String str, String str2) {
        log(str, str2, false);
    }

    public static void error(String str, String str2) {
        if (ENABLED) {
            Log.e(str, str2);
            toast("[" + str + "]" + str2);
        }
    }

    public static void warning(String str, String str2) {
        if (ENABLED) {
            Log.w(str, str2);
        }
    }

    static void toast(final String str) {
        NativePluginHelper.executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.utilities.Debug.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(NativePluginHelper.getCurrentContext(), str, 1).show();
            }
        });
    }
}
