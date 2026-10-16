package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
class SSOAuthHandler extends AuthHandler {
    static final String APP_SIGNATURE = "3082025d308201c6a00302010202044bd76cce300d06092a864886f70d01010505003073310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f31163014060355040a130d547769747465722c20496e632e310f300d060355040b13064d6f62696c65311630140603550403130d4c656c616e6420526563686973301e170d3130303432373233303133345a170d3438303832353233303133345a3073310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f31163014060355040a130d547769747465722c20496e632e310f300d060355040b13064d6f62696c65311630140603550403130d4c656c616e642052656368697330819f300d06092a864886f70d010101050003818d003081890281810086233c2e51c62232d49cc932e470713d63a6a1106b38f9e442e01bc79ca4f95c72b2cb3f1369ef7dea6036bff7c4b2828cb3787e7657ad83986751ced5b131fcc6f413efb7334e32ed9787f9e9a249ae108fa66009ac7a7932c25d37e1e07d4f9f66aa494c270dbac87d261c9668d321c2fba4ef2800e46671a597ff2eac5d7f0203010001300d06092a864886f70d0101050500038181003e1f01cb6ea8be8d2cecef5cd2a64c97ba8728aa5f08f8275d00508d64d139b6a72c5716b40a040df0eeeda04de9361107e123ee8d3dc05e70c8a355f46dbadf1235443b0b214c57211afd4edd147451c443d49498d2a7ff27e45a99c39b9e47429a1dae843ba233bf8ca81296dbe1dc5c5434514d995b0279246809392a219b";
    private static final String EXTRA_CONSUMER_KEY = "ck";
    private static final String EXTRA_CONSUMER_SECRET = "cs";
    static final String PACKAGE_NAME = "com.twitter.android";
    static final String SSO_CLASS_NAME = "com.twitter.android.SingleSignOnActivity";
    static final ComponentName SSO_ACTIVITY = new ComponentName("com.twitter.android", SSO_CLASS_NAME);

    public SSOAuthHandler(TwitterAuthConfig twitterAuthConfig, Callback<TwitterSession> callback, int i) {
        super(twitterAuthConfig, callback, i);
    }

    @Override // com.twitter.sdk.android.core.identity.AuthHandler
    public boolean authorize(Activity activity) {
        return startAuthActivityForResult(activity);
    }

    private boolean startAuthActivityForResult(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        if (!checkAppSignature(packageManager)) {
            Fabric.getLogger().e(TwitterCore.TAG, "SSO app signature check failed", null);
            return false;
        }
        try {
            ComponentName componentName = SSO_ACTIVITY;
            packageManager.getActivityInfo(componentName, 0);
            TwitterAuthConfig authConfig = getAuthConfig();
            Intent component = new Intent().setComponent(componentName);
            component.putExtra(EXTRA_CONSUMER_KEY, authConfig.getConsumerKey()).putExtra(EXTRA_CONSUMER_SECRET, authConfig.getConsumerSecret());
            try {
                activity.startActivityForResult(component, this.requestCode);
                return true;
            } catch (Exception e) {
                Fabric.getLogger().e(TwitterCore.TAG, "SSO exception occurred", e);
                return false;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Fabric.getLogger().e(TwitterCore.TAG, "SSO auth activity not found", null);
            return false;
        }
    }

    public static boolean isAvailable(Context context) {
        return checkAppSignature(context.getPackageManager());
    }

    private static boolean checkAppSignature(PackageManager packageManager) {
        try {
            for (Signature signature : packageManager.getPackageInfo("com.twitter.android", 64).signatures) {
                if (APP_SIGNATURE.equals(signature.toCharsString())) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }
}
