package com.voxelbusters.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.voxelbusters.common.Configuration;

/* JADX INFO: loaded from: classes.dex */
public class NativePlatformInfo {
    public static String getPackageName() {
        PackageInfo packageInfo = getPackageInfo();
        Log.e("Utility", packageInfo.toString());
        if (packageInfo != null) {
            return packageInfo.packageName;
        }
        return null;
    }

    public static String getPackageVersionName() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    private static PackageInfo getPackageInfo() {
        Context applicationContext = Configuration.getContext().getApplicationContext();
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
