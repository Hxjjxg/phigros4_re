package com.tapsdk.antiaddictionui;

import android.app.Activity;
import com.tapsdk.antiaddiction.Config;
import com.tds.common.isc.IscMethod;
import com.tds.common.isc.IscService;

/* JADX INFO: loaded from: classes.dex */
@IscService("TapAntiAddiction")
class IscTapAntiAddictionService {
    IscTapAntiAddictionService() {
    }

    @IscMethod("init")
    public static void init(Activity activity, String str, boolean z, boolean z2) {
        AntiAddictionUIKit.init(activity, new Config.Builder().withClientId(str).showSwitchAccount(z).useAgeRange(z2).build());
    }
}
