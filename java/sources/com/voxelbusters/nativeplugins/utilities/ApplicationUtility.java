package com.voxelbusters.nativeplugins.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationUtility {
    static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100000;

    public static boolean isGooglePlayServicesAvailable(final Context context, boolean z) {
        final GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        final int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (iIsGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (iIsGooglePlayServicesAvailable != 1 && iIsGooglePlayServicesAvailable != 2 && iIsGooglePlayServicesAvailable != 3 && iIsGooglePlayServicesAvailable != 9) {
            Debug.error(CommonDefines.APPLICATION_UTILITY_TAG, "This device does not support Google Play Services. Unknown Error Occured!");
            return false;
        }
        if (z) {
            NativePluginHelper.executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.utilities.ApplicationUtility.1
                @Override // java.lang.Runnable
                public void run() {
                    if (googleApiAvailability.isUserResolvableError(iIsGooglePlayServicesAvailable)) {
                        Debug.log(CommonDefines.APPLICATION_UTILITY_TAG, "Google Play Services error - " + googleApiAvailability.getErrorString(iIsGooglePlayServicesAvailable));
                        googleApiAvailability.getErrorDialog((Activity) context, iIsGooglePlayServicesAvailable, ApplicationUtility.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                        return;
                    }
                    Debug.error(CommonDefines.APPLICATION_UTILITY_TAG, "This device does not support Google Play Services.");
                }
            });
        }
        return false;
    }

    public static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(getPackageName(context), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Debug.error(CommonDefines.APPLICATION_UTILITY_TAG, "Package name not found!");
            return null;
        }
    }

    public static String getApplicationName(Context context) {
        return context.getPackageManager().getApplicationLabel(getApplicationInfo(context)).toString();
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getFileProviderAuthoityName(Context context) {
        return getPackageName(context) + ".fileprovider";
    }

    public static Context getApplicationContext(Context context) {
        return context.getApplicationContext();
    }

    public static boolean isIntentAvailable(Context context, String str, String str2, String str3) {
        Intent intent = new Intent(str);
        intent.setType(str2);
        if (str3 != null) {
            intent.setPackage(str3);
        }
        return isIntentAvailable(context, intent);
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static int getResourceId(Context context, String str, String str2) {
        return context.getResources().getIdentifier(StringUtility.getFileNameWithoutExtension(str), str2, getPackageName(context));
    }

    public static Class<?> GetMainLauncherActivity(Context context) {
        try {
            return Class.forName(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasPermission(Context context, String str) {
        return context.getPackageManager().checkPermission(str, getPackageName(context)) == 0;
    }

    public static File getLocalSaveDirectory(Context context, String str) {
        return getSaveDirectory(context, str, context.getApplicationContext().getFilesDir());
    }

    public static File getExternalTempDirectoryIfExists(Context context, String str) {
        if (hasExternalStorageWritable(context)) {
            return getSaveDirectory(context, str, context.getApplicationContext().getExternalCacheDir());
        }
        return getLocalSaveDirectory(context, str);
    }

    static File getSaveDirectory(Context context, String str, File file) {
        if (StringUtility.isNullOrEmpty(str)) {
            str = getApplicationName(context);
        }
        File file2 = new File(file, str);
        file2.mkdirs();
        return file2;
    }

    public static boolean hasExternalStorageWritable(Context context) {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static Object getSystemService(Context context, String str) {
        return context.getSystemService(str);
    }

    public static String getString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static boolean isAmazonPlatform(Context context) {
        return getPackageInstallerName(context).startsWith("com.amazon");
    }

    public static boolean isGooglePlatform(Context context) {
        return "com.android.vending".equals(getPackageInstallerName(context));
    }

    public static String getPackageInstallerName(Context context) {
        String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        Debug.log(CommonDefines.APPLICATION_UTILITY_TAG, "Installer Name : " + installerPackageName);
        return StringUtility.isNullOrEmpty(installerPackageName) ? "" : installerPackageName;
    }
}
