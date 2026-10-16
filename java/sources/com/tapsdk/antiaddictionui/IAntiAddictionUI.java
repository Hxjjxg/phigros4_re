package com.tapsdk.antiaddictionui;

import android.app.Activity;
import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;

/* JADX INFO: loaded from: classes.dex */
public interface IAntiAddictionUI {
    void checkPayLimit(Activity activity, long j, Callback<CheckPayResult> callback);

    void init(Activity activity, Config config);

    void init(Activity activity, Config config, AntiAddictionUICallback antiAddictionUICallback);

    boolean isInitialized();

    boolean isReady();

    void setAntiAddictionCallback(AntiAddictionUICallback antiAddictionUICallback);

    void setTestFloatingWindowEnabled(Activity activity, boolean z);

    void startup(Activity activity, String str);

    void startup(Activity activity, String str, boolean z);

    @Deprecated
    void startup(Activity activity, boolean z, String str);

    void startupWithTapTap(Activity activity, String str);

    void submitPayResult(long j, com.tapsdk.antiaddiction.Callback<SubmitPayResult> callback);
}
