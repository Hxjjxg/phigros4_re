package com.digits.sdk.android;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
class BundleManager {
    BundleManager() {
    }

    static boolean assertContains(Bundle bundle, String... strArr) {
        if (bundle == null || strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (!bundle.containsKey(str)) {
                return false;
            }
        }
        return true;
    }
}
