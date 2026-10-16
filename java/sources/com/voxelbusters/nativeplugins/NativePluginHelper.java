package com.voxelbusters.nativeplugins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import com.unity3d.player.UnityPlayerNativeActivity;
import com.voxelbusters.nativeplugins.defines.UnityDefines;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.JSONUtility;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class NativePluginHelper {
    public static void sendMessage(String str) {
        sendMessage(str, "");
    }

    public static void sendMessage(String str, String str2) {
        if (StringUtility.isNullOrEmpty(str)) {
            return;
        }
        Debug.log("UnitySendMessage", "Method Name : " + str + " Message : " + str2);
        if (getCurrentContext() != null) {
            UnityPlayer.UnitySendMessage(UnityDefines.NATIVE_BINDING_EVENT_LISTENER, str, str2);
        }
    }

    public static void sendMessage(String str, HashMap map) {
        sendMessage(str, JSONUtility.getJSONString(map));
    }

    public static boolean isRunningInFullScreenMode() {
        return (getCurrentActivity().getWindow().getAttributes().flags & 1024) == 1024;
    }

    public static Context getCurrentContext() {
        return UnityPlayer.currentActivity;
    }

    public static Activity getCurrentActivity() {
        return (Activity) getCurrentContext();
    }

    public static void updateUnityWindowFocus(boolean z) {
        try {
            try {
                UnityPlayerNativeActivity currentContext = getCurrentContext();
                if (currentContext != null) {
                    currentContext.onWindowFocusChanged(z);
                }
            } catch (Exception unused) {
                UnityPlayerActivity unityPlayerActivity = (UnityPlayerActivity) getCurrentContext();
                if (unityPlayerActivity != null) {
                    unityPlayerActivity.onWindowFocusChanged(z);
                }
            }
        } catch (Exception unused2) {
        }
    }

    public static void executeOnUIThread(Runnable runnable) {
        Activity activity = (Activity) getCurrentContext();
        if (activity != null) {
            activity.runOnUiThread(runnable);
        }
    }

    public static void startActivityOnUiThread(final Intent intent) {
        executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.NativePluginHelper.1
            @Override // java.lang.Runnable
            public void run() {
                NativePluginHelper.getCurrentContext().startActivity(intent);
            }
        });
    }

    public static boolean isApplicationRunning() {
        return getCurrentContext() != null;
    }
}
