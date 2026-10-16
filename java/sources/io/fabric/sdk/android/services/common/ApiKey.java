package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Bundle;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
public class ApiKey {
    static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";

    private static String buildApiKeyInstructions() {
        return "Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }

    public static String getApiKey(Context context) {
        return getApiKey(context, false);
    }

    public static String getApiKey(Context context, boolean z) {
        String string;
        int resourcesIdentifier;
        try {
            Context applicationContext = context.getApplicationContext();
            Bundle bundle = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128).metaData;
            string = bundle != null ? bundle.getString(CRASHLYTICS_API_KEY) : null;
        } catch (Exception e) {
            Fabric.getLogger().d(Fabric.TAG, "Caught non-fatal exception while retrieving apiKey: " + e);
        }
        if (CommonUtils.isNullOrEmpty(string) && (resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, CRASHLYTICS_API_KEY, "string")) != 0) {
            string = context.getResources().getString(resourcesIdentifier);
        }
        if (CommonUtils.isNullOrEmpty(string)) {
            if (z || CommonUtils.isAppDebuggable(context)) {
                throw new IllegalArgumentException(buildApiKeyInstructions());
            }
            Fabric.getLogger().e(Fabric.TAG, buildApiKeyInstructions(), null);
        }
        return string;
    }
}
