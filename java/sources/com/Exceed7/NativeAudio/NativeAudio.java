package com.Exceed7.NativeAudio;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class NativeAudio {
    private static final boolean enableLogging = false;
    private static boolean lifecycleCallbackRegistered = false;
    private static RememberedInitialization rememberedInitialization;
    private static Application.ActivityLifecycleCallbacks lifecycleTracker = new Application.ActivityLifecycleCallbacks() { // from class: com.Exceed7.NativeAudio.NativeAudio.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            if (activity == UnityPlayer.currentActivity) {
                NativeAudio.Initialize(NativeAudio.rememberedInitialization.numberOfAudioPlayer, NativeAudio.rememberedInitialization.minimumBufferSize, false);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            if (activity == UnityPlayer.currentActivity) {
                NativeAudio.disposeIfAllocated();
            }
        }
    };
    private static String stereoSilence = "52494646D400000057415645666D7420100000000100020044AC000010B102000400100064617461B000000001000100FDFFFDFF04000400FDFFFDFF0100010001000100FEFFFEFF02000200FFFFFFFF00000000000000000000000001000100FEFFFEFF03000300FCFFFCFF03000300FFFFFFFFFFFFFFFF02000200FEFFFEFF010001000000000000000000FFFFFFFF02000200FDFFFDFF03000300FFFFFFFFFFFFFFFF02000200FDFFFDFF0200020000000000FFFFFFFF01000100FFFFFFFF01000100FFFFFFFF02000200FDFFFDFF03000300FDFFFDFF04000400";

    private static void Log(String str) {
    }

    public static native void disposeIfAllocated();

    public static native void initialize(int i, int i2, int i3);

    public static native int sendWavByteArray(byte[] bArr, int i);

    public class DeviceAudioInformation {
        boolean lowLatencyFeature;
        int nativeSamplingRate;
        int optimalBufferSize;
        AudioDeviceInfo[] outputDevices;
        boolean proAudioFeature;

        public DeviceAudioInformation() {
        }
    }

    static {
        System.loadLibrary("nativeaudioe7");
    }

    private static DeviceAudioInformation GetDeviceAudioInformation() {
        DeviceAudioInformation deviceAudioInformation = new NativeAudio().new DeviceAudioInformation();
        AudioManager audioManager = (AudioManager) UnityPlayer.currentActivity.getSystemService("audio");
        PackageManager packageManager = UnityPlayer.currentActivity.getPackageManager();
        try {
            deviceAudioInformation.nativeSamplingRate = Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"));
            Log("NATIVE SAMPLING RATE : " + deviceAudioInformation.nativeSamplingRate);
        } catch (NumberFormatException unused) {
            Log.e("Android Native Audio", "Received non-parsable sampling rate " + audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"));
            deviceAudioInformation.nativeSamplingRate = 44100;
        }
        try {
            deviceAudioInformation.optimalBufferSize = Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER"));
        } catch (NumberFormatException unused2) {
            Log.e("Android Native Audio", "Received non-parsable buffer size " + audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER"));
            deviceAudioInformation.optimalBufferSize = 256;
        }
        Log("OPTIMAL BUFFER SIZE : " + deviceAudioInformation.optimalBufferSize);
        deviceAudioInformation.lowLatencyFeature = packageManager.hasSystemFeature("android.hardware.audio.low_latency");
        deviceAudioInformation.proAudioFeature = packageManager.hasSystemFeature("android.hardware.audio.pro");
        Log("LOW LATENCY FEATURE (indicates a continuous output latency of 45 ms or less) : " + String.valueOf(deviceAudioInformation.lowLatencyFeature));
        Log("PRO AUDIO FEATURE (indicates a continuous round-trip latency of 20 ms or less) : " + String.valueOf(deviceAudioInformation.proAudioFeature));
        deviceAudioInformation.outputDevices = audioManager.getDevices(2);
        return deviceAudioInformation;
    }

    private static class RememberedInitialization {
        int minimumBufferSize;
        int numberOfAudioPlayer;

        private RememberedInitialization() {
        }
    }

    public static void DebugDialog() {
        String packageName = UnityPlayer.currentActivity.getPackageName();
        try {
            UnityPlayer.currentActivity.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            UnityPlayer.currentActivity.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static int Initialize(int i, int i2, boolean z) {
        if (!z) {
            Log("Not preserve on minimize.");
            RememberedInitialization rememberedInitialization2 = new RememberedInitialization();
            rememberedInitialization = rememberedInitialization2;
            rememberedInitialization2.numberOfAudioPlayer = i;
            rememberedInitialization.minimumBufferSize = i2;
            if (!lifecycleCallbackRegistered) {
                Log("Registering the lifecycle callback.");
                UnityPlayer.currentActivity.getApplication().registerActivityLifecycleCallbacks(lifecycleTracker);
                lifecycleCallbackRegistered = true;
            }
        } else {
            Log("Preserve on minimize.");
            if (lifecycleCallbackRegistered) {
                Log("Unregistering the lifecycle callback.");
                UnityPlayer.currentActivity.getApplication().unregisterActivityLifecycleCallbacks(lifecycleTracker);
                lifecycleCallbackRegistered = false;
            }
        }
        DeviceAudioInformation deviceAudioInformationGetDeviceAudioInformation = GetDeviceAudioInformation();
        int i3 = deviceAudioInformationGetDeviceAudioInformation.optimalBufferSize;
        if (i2 != -1 && i3 < i2) {
            i3 = ((i2 / deviceAudioInformationGetDeviceAudioInformation.optimalBufferSize) + 1) * deviceAudioInformationGetDeviceAudioInformation.optimalBufferSize;
            Log("Adjusted buffer size to : " + i3 + " according to minimum " + i2);
        }
        initialize(deviceAudioInformationGetDeviceAudioInformation.nativeSamplingRate, i3, i);
        Log("Initialized");
        return 0;
    }

    public static int LoadAudio(String str, int i) {
        byte[] bArr;
        byte[] bArrHexStringToByteArray;
        if (str.equals("")) {
            Log("Loading SILENCE");
        } else {
            Log("Loading " + str);
        }
        Activity activity = UnityPlayer.currentActivity;
        AssetManager assets = activity.getAssets();
        try {
            try {
                ZipResourceFile aPKExpansionZipFile = APKExpansionSupport.getAPKExpansionZipFile(activity, activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode, 0);
                if (str.equals("")) {
                    bArrHexStringToByteArray = hexStringToByteArray(stereoSilence);
                } else {
                    try {
                        try {
                            bArr = new byte[(int) assets.openFd(str).getLength()];
                            InputStream inputStreamOpen = assets.open(str);
                            inputStreamOpen.read(bArr);
                            inputStreamOpen.close();
                            Log("Loaded audio " + str + " from the main APK.");
                        } catch (IOException unused) {
                            bArr = new byte[(int) aPKExpansionZipFile.getAssetFileDescriptor("assets/" + str).getLength()];
                            InputStream inputStream = aPKExpansionZipFile.getInputStream("assets/" + str);
                            inputStream.read(bArr);
                            inputStream.close();
                            Log("Loaded audio " + str + " from an OBB expansion file.");
                        }
                        bArrHexStringToByteArray = bArr;
                    } catch (IOException | NullPointerException e) {
                        Log("Error reading audio file " + str);
                        e.printStackTrace();
                        return -1;
                    }
                }
                int iSendWavByteArray = sendWavByteArray(bArrHexStringToByteArray, i);
                Log("Loaded to byteArrayIndex: " + iSendWavByteArray);
                return iSendWavByteArray;
            } catch (IOException e2) {
                e2.printStackTrace();
                return -1;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    public static void Dispose() {
        if (lifecycleCallbackRegistered) {
            UnityPlayer.currentActivity.getApplication().unregisterActivityLifecycleCallbacks(lifecycleTracker);
            lifecycleCallbackRegistered = false;
        }
        disposeIfAllocated();
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
