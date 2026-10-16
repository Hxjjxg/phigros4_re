package io.fabric.sdk.android.services.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class CommonUtils {
    static final int BYTES_IN_A_GIGABYTE = 1073741824;
    static final int BYTES_IN_A_KILOBYTE = 1024;
    static final int BYTES_IN_A_MEGABYTE = 1048576;
    private static final String CLS_SHARED_PREFERENCES_NAME = "com.crashlytics.prefs";
    static final boolean CLS_TRACE_DEFAULT = false;
    static final String CLS_TRACE_PREFERENCE_NAME = "com.crashlytics.Trace";
    static final String CRASHLYTICS_BUILD_ID = "com.crashlytics.android.build_id";
    public static final int DEVICE_STATE_BETAOS = 8;
    public static final int DEVICE_STATE_COMPROMISEDLIBRARIES = 32;
    public static final int DEVICE_STATE_DEBUGGERATTACHED = 4;
    public static final int DEVICE_STATE_ISSIMULATOR = 1;
    public static final int DEVICE_STATE_JAILBROKEN = 2;
    public static final int DEVICE_STATE_VENDORINTERNAL = 16;
    static final String ENCRYPTION_AES = "AES/ECB/PKCS7Padding";
    public static final String GOOGLE_SDK = "google_sdk";
    private static final boolean LOGGING_DISABLED_DEFAULT = false;
    private static final String LOGGING_DISABLED_KEY = "com.crashlytics.SilenceCrashlyticsLogCat";
    private static final String LOG_PRIORITY_NAME_ASSERT = "A";
    private static final String LOG_PRIORITY_NAME_DEBUG = "D";
    private static final String LOG_PRIORITY_NAME_ERROR = "E";
    private static final String LOG_PRIORITY_NAME_INFO = "I";
    private static final String LOG_PRIORITY_NAME_UNKNOWN = "?";
    private static final String LOG_PRIORITY_NAME_VERBOSE = "V";
    private static final String LOG_PRIORITY_NAME_WARN = "W";
    public static final String MD5_INSTANCE = "MD5";
    public static final String SDK = "sdk";
    public static final String SHA1_INSTANCE = "SHA-1";
    private static final long UNCALCULATED_TOTAL_RAM = -1;
    private static Boolean clsTrace;
    private static final char[] HEX_VALUES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static long totalRamInBytes = -1;
    private static Boolean loggingEnabled = null;
    public static final Comparator<File> FILE_MODIFIED_COMPARATOR = new Comparator<File>() { // from class: io.fabric.sdk.android.services.common.CommonUtils.1
        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            return (int) (file.lastModified() - file2.lastModified());
        }
    };

    public static String logPriorityToString(int i) {
        switch (i) {
            case 2:
                return "V";
            case 3:
                return LOG_PRIORITY_NAME_DEBUG;
            case 4:
                return LOG_PRIORITY_NAME_INFO;
            case 5:
                return "W";
            case 6:
                return "E";
            case 7:
                return "A";
            default:
                return LOG_PRIORITY_NAME_UNKNOWN;
        }
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(CLS_SHARED_PREFERENCES_NAME, 0);
    }

    public static String extractFieldFromSystemFile(File file, String str) throws Throwable {
        BufferedReader bufferedReader;
        String str2 = null;
        str2 = null;
        str2 = null;
        BufferedReader bufferedReader2 = null;
        if (file.exists()) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file), 1024);
                while (true) {
                    try {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            String[] strArrSplit = Pattern.compile("\\s*:\\s*").split(line, 2);
                            if (strArrSplit.length > 1 && strArrSplit[0].equals(str)) {
                                str2 = strArrSplit[1];
                                break;
                            }
                        } catch (Exception e) {
                            e = e;
                            Fabric.getLogger().e(Fabric.TAG, "Error parsing " + file, e);
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        closeOrLog(bufferedReader2, "Failed to close system file reader.");
                        throw th;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                closeOrLog(bufferedReader2, "Failed to close system file reader.");
                throw th;
            }
            closeOrLog(bufferedReader, "Failed to close system file reader.");
        }
        return str2;
    }

    public static int getCpuArchitectureInt() {
        return Architecture.getValue().ordinal();
    }

    enum Architecture {
        X86_32,
        X86_64,
        ARM_UNKNOWN,
        PPC,
        PPC64,
        ARMV6,
        ARMV7,
        UNKNOWN,
        ARMV7S,
        ARM64;

        private static final Map<String, Architecture> matcher;

        static {
            Architecture architecture = X86_32;
            Architecture architecture2 = ARMV6;
            Architecture architecture3 = ARMV7;
            HashMap map = new HashMap(4);
            matcher = map;
            map.put("armeabi-v7a", architecture3);
            map.put("armeabi", architecture2);
            map.put("x86", architecture);
        }

        static Architecture getValue() {
            String str = Build.CPU_ABI;
            if (TextUtils.isEmpty(str)) {
                Fabric.getLogger().d(Fabric.TAG, "Architecture#getValue()::Build.CPU_ABI returned null or empty");
                return UNKNOWN;
            }
            Architecture architecture = matcher.get(str.toLowerCase(Locale.US));
            return architecture == null ? UNKNOWN : architecture;
        }
    }

    public static synchronized long getTotalRamInBytes() {
        long jConvertMemInfoToBytes;
        if (totalRamInBytes == -1) {
            String strExtractFieldFromSystemFile = extractFieldFromSystemFile(new File("/proc/meminfo"), "MemTotal");
            long j = 0;
            if (!TextUtils.isEmpty(strExtractFieldFromSystemFile)) {
                String upperCase = strExtractFieldFromSystemFile.toUpperCase(Locale.US);
                try {
                    if (upperCase.endsWith("KB")) {
                        jConvertMemInfoToBytes = convertMemInfoToBytes(upperCase, "KB", 1024);
                    } else if (upperCase.endsWith("MB")) {
                        jConvertMemInfoToBytes = convertMemInfoToBytes(upperCase, "MB", 1048576);
                    } else if (upperCase.endsWith("GB")) {
                        jConvertMemInfoToBytes = convertMemInfoToBytes(upperCase, "GB", BYTES_IN_A_GIGABYTE);
                    } else {
                        Fabric.getLogger().d(Fabric.TAG, "Unexpected meminfo format while computing RAM: " + upperCase);
                    }
                    j = jConvertMemInfoToBytes;
                } catch (NumberFormatException e) {
                    Fabric.getLogger().e(Fabric.TAG, "Unexpected meminfo format while computing RAM: " + upperCase, e);
                }
            }
            totalRamInBytes = j;
        }
        return totalRamInBytes;
    }

    static long convertMemInfoToBytes(String str, String str2, int i) {
        return Long.parseLong(str.split(str2)[0].trim()) * ((long) i);
    }

    public static ActivityManager.RunningAppProcessInfo getAppProcessInfo(String str, Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    return runningAppProcessInfo;
                }
            }
        }
        return null;
    }

    public static String streamToString(InputStream inputStream) throws IOException {
        Scanner scannerUseDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return scannerUseDelimiter.hasNext() ? scannerUseDelimiter.next() : "";
    }

    public static String md5(String str) {
        return hash(str, MD5_INSTANCE);
    }

    public static String md5(byte[] bArr) {
        return hash(bArr, MD5_INSTANCE);
    }

    public static String sha1(String str) {
        return hash(str, SHA1_INSTANCE);
    }

    public static String sha1(byte[] bArr) {
        return hash(bArr, SHA1_INSTANCE);
    }

    public static String sha1(InputStream inputStream) {
        return hash(inputStream, SHA1_INSTANCE);
    }

    private static String hash(InputStream inputStream, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA1_INSTANCE);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    messageDigest.update(bArr, 0, i);
                } else {
                    return hexify(messageDigest.digest());
                }
            }
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not calculate hash for app icon.", e);
            return "";
        }
    }

    private static String hash(byte[] bArr, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            messageDigest.update(bArr);
            return hexify(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not create hashing algorithm: " + str + ", returning empty string.", e);
            return "";
        }
    }

    private static String hash(String str, String str2) {
        return hash(str.getBytes(), str2);
    }

    public static String createInstanceIdFrom(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (str != null) {
                arrayList.add(str.replace("-", "").toLowerCase(Locale.US));
            }
        }
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
        }
        String string = sb.toString();
        if (string.length() > 0) {
            return sha1(string);
        }
        return null;
    }

    public static long calculateFreeRamInBytes(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static long calculateUsedDiskSpaceInBytes(String str) {
        StatFs statFs = new StatFs(str);
        long blockSize = statFs.getBlockSize();
        return (((long) statFs.getBlockCount()) * blockSize) - (blockSize * ((long) statFs.getAvailableBlocks()));
    }

    public static float getBatteryLevel(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return intentRegisterReceiver.getIntExtra("level", -1) / intentRegisterReceiver.getIntExtra("scale", -1);
    }

    public static boolean getProximitySensorEnabled(Context context) {
        return (isEmulator(context) || ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) == null) ? false : true;
    }

    public static void logControlled(Context context, String str) {
        if (isClsTrace(context)) {
            Fabric.getLogger().d(Fabric.TAG, str);
        }
    }

    public static void logControlledError(Context context, String str, Throwable th) {
        if (isClsTrace(context)) {
            Fabric.getLogger().e(Fabric.TAG, str);
        }
    }

    public static void logControlled(Context context, int i, String str, String str2) {
        if (isClsTrace(context)) {
            Fabric.getLogger().log(i, Fabric.TAG, str2);
        }
    }

    public static boolean isLoggingEnabled(Context context) {
        if (loggingEnabled == null) {
            loggingEnabled = Boolean.valueOf(!getBooleanResourceValue(context, LOGGING_DISABLED_KEY, false));
        }
        return loggingEnabled.booleanValue();
    }

    public static boolean isClsTrace(Context context) {
        if (clsTrace == null) {
            clsTrace = Boolean.valueOf(getBooleanResourceValue(context, CLS_TRACE_PREFERENCE_NAME, false));
        }
        return clsTrace.booleanValue();
    }

    public static boolean getBooleanResourceValue(Context context, String str, boolean z) {
        Resources resources;
        if (context != null && (resources = context.getResources()) != null) {
            int resourcesIdentifier = getResourcesIdentifier(context, str, "bool");
            if (resourcesIdentifier > 0) {
                return resources.getBoolean(resourcesIdentifier);
            }
            int resourcesIdentifier2 = getResourcesIdentifier(context, str, "string");
            if (resourcesIdentifier2 > 0) {
                return Boolean.parseBoolean(context.getString(resourcesIdentifier2));
            }
        }
        return z;
    }

    public static int getResourcesIdentifier(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, getResourcePackageName(context));
    }

    public static boolean isEmulator(Context context) {
        return SDK.equals(Build.PRODUCT) || GOOGLE_SDK.equals(Build.PRODUCT) || Settings.Secure.getString(context.getContentResolver(), "android_id") == null;
    }

    public static boolean isRooted(Context context) {
        boolean zIsEmulator = isEmulator(context);
        String str = Build.TAGS;
        if ((zIsEmulator || str == null || !str.contains("test-keys")) && !new File("/system/app/Superuser.apk").exists()) {
            return !zIsEmulator && new File("/system/xbin/su").exists();
        }
        return true;
    }

    public static boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static int getDeviceState(Context context) {
        boolean zIsEmulator = isEmulator(context);
        ?? r0 = zIsEmulator;
        if (isRooted(context)) {
            r0 = (zIsEmulator ? 1 : 0) | 2;
        }
        return isDebuggerAttached() ? r0 | 4 : r0;
    }

    public static int getBatteryVelocity(Context context, boolean z) {
        float batteryLevel = getBatteryLevel(context);
        if (!z) {
            return 1;
        }
        if (!z || batteryLevel < 99.0d) {
            return (!z || ((double) batteryLevel) >= 99.0d) ? 0 : 2;
        }
        return 3;
    }

    public static byte[] getBitmapBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Cipher createCipher(int i, String str) throws InvalidKeyException {
        if (str.length() < 32) {
            throw new InvalidKeyException("Key must be at least 32 bytes.");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), 0, 32, ENCRYPTION_AES);
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_AES);
            cipher.init(i, secretKeySpec);
            return cipher;
        } catch (GeneralSecurityException e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not create Cipher for AES/ECB/PKCS7Padding - should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public static String hexify(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            char[] cArr2 = HEX_VALUES;
            cArr[i3] = cArr2[i2 >>> 4];
            cArr[i3 + 1] = cArr2[i2 & 15];
        }
        return new String(cArr);
    }

    public static byte[] dehexify(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static boolean isAppDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public static String getStringsFileValue(Context context, String str) {
        int resourcesIdentifier = getResourcesIdentifier(context, str, "string");
        return resourcesIdentifier > 0 ? context.getString(resourcesIdentifier) : "";
    }

    public static void closeOrLog(Closeable closeable, String str) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Fabric.getLogger().e(Fabric.TAG, str, e);
            }
        }
    }

    public static void flushOrLog(Flushable flushable, String str) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (IOException e) {
                Fabric.getLogger().e(Fabric.TAG, str, e);
            }
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String padWithZerosToMaxIntWidth(int i) {
        if (i >= 0) {
            return String.format(Locale.US, "%1$10s", Integer.valueOf(i)).replace(' ', '0');
        }
        throw new IllegalArgumentException("value must be zero or greater");
    }

    public static boolean stringsEqualIncludingNull(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str != null) {
            return str.equals(str2);
        }
        return false;
    }

    public static String getResourcePackageName(Context context) {
        int i = context.getApplicationContext().getApplicationInfo().icon;
        if (i > 0) {
            return context.getResources().getResourcePackageName(i);
        }
        return context.getPackageName();
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static String getAppIconHashOrNull(Context context) throws Throwable {
        Throwable th;
        InputStream inputStreamOpenRawResource;
        try {
            inputStreamOpenRawResource = context.getResources().openRawResource(getAppIconResourceId(context));
            try {
                try {
                    String strSha1 = sha1(inputStreamOpenRawResource);
                    String str = isNullOrEmpty(strSha1) ? null : strSha1;
                    closeOrLog(inputStreamOpenRawResource, "Failed to close icon input stream.");
                    return str;
                } catch (Exception e) {
                    e = e;
                    Fabric.getLogger().e(Fabric.TAG, "Could not calculate hash for app icon.", e);
                    closeOrLog(inputStreamOpenRawResource, "Failed to close icon input stream.");
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                closeOrLog(inputStreamOpenRawResource, "Failed to close icon input stream.");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            inputStreamOpenRawResource = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpenRawResource = null;
            closeOrLog(inputStreamOpenRawResource, "Failed to close icon input stream.");
            throw th;
        }
    }

    public static int getAppIconResourceId(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static String resolveBuildId(Context context) {
        int resourcesIdentifier = getResourcesIdentifier(context, CRASHLYTICS_BUILD_ID, "string");
        if (resourcesIdentifier == 0) {
            return null;
        }
        String string = context.getResources().getString(resourcesIdentifier);
        Fabric.getLogger().d(Fabric.TAG, "Build ID is: " + string);
        return string;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    public static void finishAffinity(Context context, int i) {
        if (context instanceof Activity) {
            finishAffinity((Activity) context, i);
        }
    }

    public static void finishAffinity(Activity activity, int i) {
        if (activity == null) {
            return;
        }
        activity.finishAffinity();
    }

    public static boolean canTryConnection(Context context) {
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return true;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void logOrThrowIllegalStateException(String str, String str2) {
        if (Fabric.isDebuggable()) {
            throw new IllegalStateException(str2);
        }
        Fabric.getLogger().w(str, str2);
    }

    public static void logOrThrowIllegalArgumentException(String str, String str2) {
        if (Fabric.isDebuggable()) {
            throw new IllegalArgumentException(str2);
        }
        Fabric.getLogger().w(str, str2);
    }
}
