package com.taptap.sdk.openlog.internal;

import android.content.Context;
import com.tds.common.TapCommon;
import com.tds.common.net.PlatformXUA;
import com.tds.common.tracker.TdsTrackerHandler;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.utils.DeviceUtils;
import java.io.File;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapOpenLogRemote {
    private static final TapOpenLogRemote INSTANCE = new TapOpenLogRemote();
    private boolean hasInit = false;
    private boolean hasLoadLib = false;

    private native int onAppStarted(String str);

    public native void disableModules(String str);

    public native void enableModules(String str);

    public native void onAppStopped();

    public native void onBackground();

    public native void onForeground();

    public native void onLogin(String str);

    public native void onLogout();

    public native void sendOpenlog(String str, String str2);

    public native void setExtraAppDurationParams(String str);

    private TapOpenLogRemote() {
    }

    public static TapOpenLogRemote getInstance() {
        return INSTANCE;
    }

    public synchronized void loadNativeLib() {
        if (this.hasLoadLib) {
            return;
        }
        System.loadLibrary("tapsdkcore");
        this.hasLoadLib = true;
    }

    public synchronized void init(Context context, boolean z, int i, String str, String str2) {
        if (this.hasInit) {
            return;
        }
        loadNativeLib();
        JSONObject jSONObject = new JSONObject();
        String str3 = "local";
        if (DeviceUtils.isRunInSandbox()) {
            str3 = "sandbox";
        } else if (DeviceUtils.isRunInCloud()) {
            str3 = "cloud";
        }
        File file = new File(context.getFilesDir(), "tapsdk");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            jSONObject.put("env", str3);
            jSONObject.put("data_dir", file.getAbsolutePath());
            jSONObject.put("log_to_console", 1);
            if (z) {
                i = 2;
            }
            jSONObject.put("region", i);
            jSONObject.put("ua", PlatformXUA.getInstance().getTrackUA());
            jSONObject.put("client_id", str);
            if (str2 == null) {
                str2 = "";
            }
            jSONObject.put("client_token", str2);
            jSONObject.put("log_level", 1);
            jSONObject.put(CommonParam.PLATFORM, "Android");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("tapsdk_version", "3.30.3");
            jSONObject.put("app_duration", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            Map<String, String> commonParams = TdsTrackerHandler.getCommonParams(context, "");
            commonParams.remove(CommonParam.SR);
            commonParams.remove(CommonParam.TDS_USER_ID);
            for (Map.Entry<String, String> entry : commonParams.entrySet()) {
                jSONObject3.put(entry.getKey(), entry.getValue());
            }
            jSONObject.put("common", jSONObject3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onAppStarted(jSONObject.toString());
        this.hasInit = true;
    }

    public String getCommonVariables() {
        JSONObject jSONObject = new JSONObject();
        if (TapCommon.getTapConfig() != null && TapCommon.getTapConfig().appContext != null) {
            try {
                for (Map.Entry<String, String> entry : TdsTrackerHandler.getDynamicParams(TapCommon.getTapConfig().appContext).entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }
}
