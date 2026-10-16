package com.tds.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.tds.common.tracker.annotations.Login;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class DeviceUtils {
    public static String getPlatform() {
        return "Android";
    }

    public static String getOSVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getCurLanguageDisplayName() {
        return CommonUtils.getCurrentLocale().toLanguageTag();
    }

    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceTotalRam(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager == null) {
                return null;
            }
            activityManager.getMemoryInfo(memoryInfo);
            return Formatter.formatFileSize(context, memoryInfo.totalMem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDeviceTotalRom(Context context) {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return Formatter.formatFileSize(context, statFs.getBlockCountLong() * statFs.getBlockSizeLong());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRemainingRamSize(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return String.valueOf(memoryInfo.availMem);
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    public static String getRemainingRomSize() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return String.valueOf(statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong());
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    public static String getCpuInfo() {
        return Build.CPU_ABI;
    }

    public static String getCpuABIS() {
        String[] strArr = Build.SUPPORTED_ABIS;
        return (strArr == null || strArr.length == 0) ? "" : Arrays.toString(strArr);
    }

    public static int getPackageVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPackageVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isAppExist(Context context, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        return applicationInfo != null;
    }

    public static boolean isRunInSandbox() {
        return System.getProperties().get("flag_running_in_sandbox") != null;
    }

    public static boolean isRunInCloud() {
        if (TextUtils.isEmpty(Build.MODEL)) {
            return false;
        }
        return Build.MODEL.contains("haima_cloudplay") || Build.MODEL.toLowerCase().contains(Login.TAPTAP_LOGIN_TYPE);
    }

    public static boolean isRunInCanary() {
        return !TextUtils.isEmpty((String) System.getProperties().get("flag_tap_canary"));
    }

    public static String getCPUHardware() {
        BufferedReader bufferedReader;
        Throwable th;
        String str = "";
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.contains("Hardware")) {
                            str = line.split(":")[1];
                            break;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            th.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str;
                        } catch (Throwable th3) {
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            throw th3;
                        }
                    }
                }
                bufferedReader.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th4) {
            bufferedReader = null;
            th = th4;
        }
        return str;
    }
}
