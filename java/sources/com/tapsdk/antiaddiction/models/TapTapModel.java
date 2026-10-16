package com.tapsdk.antiaddiction.models;

import android.app.Activity;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.Profile;
import com.taptap.sdk.TapLoginHelper;

/* JADX INFO: loaded from: classes.dex */
public class TapTapModel {
    private static final String COMPLIANCE_BASIC_SCOPE = "compliance_basic";
    private static final String COMPLIANCE_SCOPE = "compliance";

    public void getTapIdentifyToken(Activity activity, boolean z, TapLoginHelper.TapLoginResultCallback tapLoginResultCallback) {
        TapLoginHelper.registerLoginCallback(tapLoginResultCallback);
        TapLoginHelper.startAuthorize(activity, z ? COMPLIANCE_SCOPE : COMPLIANCE_BASIC_SCOPE);
    }

    public static boolean hasComplianceAuthInLocal(AccessToken accessToken, boolean z) {
        if (accessToken == null) {
            accessToken = AccessToken.getCurrentAccessToken();
        }
        if (accessToken == null || accessToken.scopeSet == null) {
            return false;
        }
        if (z) {
            return accessToken.scopeSet.contains(COMPLIANCE_SCOPE);
        }
        return accessToken.scopeSet.contains(COMPLIANCE_SCOPE) || accessToken.scopeSet.contains(COMPLIANCE_BASIC_SCOPE);
    }

    public static boolean isTapLogged() {
        return (AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() == null) ? false : true;
    }
}
