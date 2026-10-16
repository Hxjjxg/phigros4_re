package com.unity3d.player;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class UnityPlayer extends FrameLayout implements IUnityPlayerLifecycleEvents {
    private static final String ARCORE_ENABLE_METADATA_NAME = "unity.arcore-enable";
    private static final String AUTO_REPORT_FULLY_DRAWN_ENABLE_METADATA_NAME = "unity.auto-report-fully-drawn";
    private static final String LAUNCH_FULLSCREEN = "unity.launch-fullscreen";
    private static final int RUN_STATE_CHANGED_MSG_CODE = 2269;
    private static final String SPLASH_ENABLE_METADATA_NAME = "unity.splash-enable";
    private static final String SPLASH_MODE_METADATA_NAME = "unity.splash-mode";
    public static Activity currentActivity;
    public static Context currentContext;
    private Activity mActivity;
    private Context mContext;
    private C0044i0 mGlView;
    Handler mHandler;
    private int mInitialScreenOrientation;
    private boolean mIsFullscreen;
    private boolean mMainDisplayOverride;
    private int mNaturalOrientation;
    private E mOnHandleFocusListener;
    private OrientationEventListener mOrientationListener;
    private boolean mProcessKillRequested;
    private boolean mQuitting;
    S mSoftInput;
    private long mSoftInputTimeoutMilliSeconds;
    private W0 mState;
    private i1 mVideoPlayerProxy;
    private GoogleARCoreApi m_ARCoreApi;
    private boolean m_AddPhoneCallListener;
    private AudioVolumeHandler m_AudioVolumeHandler;
    private Camera2Wrapper m_Camera2Wrapper;
    private ClipboardManager m_ClipboardManager;
    private final ConcurrentLinkedQueue m_Events;
    private Q0 m_FakeListener;
    private HFPStatus m_HFPStatus;
    private int m_IsNoWindowMode;
    U0 m_MainThread;
    private NetworkConnectivity m_NetworkConnectivity;
    private C m_OnBackPressedDispatcher;
    private OrientationLockListener m_OrientationLockListener;
    private R0 m_PhoneCallListener;
    private com.unity3d.player.a.f m_SplashScreen;
    private TelephonyManager m_TelephonyManager;
    private IUnityPlayerLifecycleEvents m_UnityPlayerLifecycleEvents;
    Window m_Window;
    private Uri m_launchUri;
    private Configuration prevConfig;

    public enum SynchronizationTimeout {
        Pause(0),
        SurfaceDetach(1),
        Destroy(2);

        private int m_TimeoutMilliseconds = 2000;
        final int value;

        SynchronizationTimeout(int i) {
            this.value = i;
        }

        public static void setTimeoutForAll(int i) {
            for (SynchronizationTimeout synchronizationTimeout : (SynchronizationTimeout[]) SynchronizationTimeout.class.getEnumConstants()) {
                synchronizationTimeout.setTimeout(i);
            }
        }

        public int getTimeout() {
            return this.m_TimeoutMilliseconds;
        }

        public void setTimeout(int i) {
            this.m_TimeoutMilliseconds = i;
        }
    }

    static {
        new C0040g0().a();
    }

    public UnityPlayer(Context context) {
        this(context, null);
    }

    public UnityPlayer(Context context, IUnityPlayerLifecycleEvents iUnityPlayerLifecycleEvents) {
        super(context);
        this.mHandler = new Handler();
        this.mInitialScreenOrientation = -1;
        this.mMainDisplayOverride = false;
        this.mIsFullscreen = true;
        this.mState = new W0();
        this.m_Events = new ConcurrentLinkedQueue();
        this.mOrientationListener = null;
        this.m_MainThread = new U0(this);
        this.m_AddPhoneCallListener = false;
        this.m_PhoneCallListener = new R0(this);
        this.m_ARCoreApi = null;
        this.m_FakeListener = new Q0();
        this.m_Camera2Wrapper = null;
        this.m_HFPStatus = null;
        this.m_AudioVolumeHandler = null;
        this.m_OrientationLockListener = null;
        this.m_launchUri = null;
        this.m_NetworkConnectivity = null;
        this.m_OnBackPressedDispatcher = null;
        this.m_UnityPlayerLifecycleEvents = null;
        this.m_IsNoWindowMode = -1;
        this.mSoftInputTimeoutMilliSeconds = 1000L;
        this.mProcessKillRequested = true;
        this.mSoftInput = null;
        this.m_UnityPlayerLifecycleEvents = iUnityPlayerLifecycleEvents == null ? this : iUnityPlayerLifecycleEvents;
        C0040g0.a(getUnityNativeLibraryPath(context));
        currentContext = context;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            this.mActivity = activity;
            currentActivity = activity;
            this.mInitialScreenOrientation = activity.getRequestedOrientation();
            this.m_launchUri = this.mActivity.getIntent().getData();
        }
        this.mContext = context;
        EarlyEnableFullScreenIfEnabled();
        Configuration configuration = getResources().getConfiguration();
        this.prevConfig = configuration;
        this.mNaturalOrientation = getNaturalOrientation(configuration.orientation);
        if (this.mActivity != null && getSplashEnabled()) {
            com.unity3d.player.a.f fVar = new com.unity3d.player.a.f(this.mContext, com.unity3d.player.a.e.a()[getSplashMode()]);
            this.m_SplashScreen = fVar;
            addView(fVar);
        }
        preloadJavaPlugins();
        String strLoadNative = loadNative(getUnityNativeLibraryPath(this.mContext));
        if (!W0.d()) {
            AbstractC0077z.Log(6, "Your hardware does not support this application.");
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterfaceOnClickListenerC0072w0(this)).setMessage("Your hardware does not support this application.\n\n" + strLoadNative + "\n\n Press OK to quit.").create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.show();
            return;
        }
        initJni(context);
        this.mState.d(true);
        C0044i0 c0044i0 = new C0044i0(context, this);
        this.mGlView = c0044i0;
        addView(c0044i0);
        bringChildToFront(this.m_SplashScreen);
        this.mQuitting = false;
        Activity activity2 = this.mActivity;
        if (activity2 != null) {
            this.m_Window = activity2.getWindow();
        }
        hideStatusBar();
        this.m_TelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        this.m_ClipboardManager = (ClipboardManager) this.mContext.getSystemService("clipboard");
        this.m_Camera2Wrapper = new Camera2Wrapper(this.mContext);
        this.m_HFPStatus = new HFPStatus(this.mContext);
        this.m_MainThread.start();
        this.m_OnBackPressedDispatcher = D.a(this.mActivity, 1, new H0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void DisableStaticSplashScreen() {
        runOnUiThread(new J0(this));
    }

    private void EarlyEnableFullScreenIfEnabled() {
        View decorView;
        Activity activity = this.mActivity;
        if (activity != null && activity.getWindow() != null && ((getLaunchFullscreen() || this.mActivity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false)) && (decorView = this.mActivity.getWindow().getDecorView()) != null)) {
            decorView.setSystemUiVisibility(7);
        }
        AbstractC0073x.a(this.mActivity);
    }

    private String GetGlViewContentDescription(Context context) {
        return context.getResources().getString(context.getResources().getIdentifier("game_view_content_description", "string", context.getPackageName()));
    }

    private boolean IsWindowTranslucent() {
        Activity activity = this.mActivity;
        if (activity == null) {
            return false;
        }
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z;
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (!W0.d()) {
            AbstractC0077z.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
        } else {
            try {
                nativeUnitySendMessage(str, str2, str3.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException unused) {
            }
        }
    }

    private void checkResumePlayer() {
        Activity activity = this.mActivity;
        if (this.mState.a(activity != null ? MultiWindowSupport.isInMultiWindowMode(activity) : false)) {
            this.mState.c(true);
            queueGLThreadEvent(new RunnableC0048k0(this));
            this.m_MainThread.a(S0.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissSoftInput() {
        S s = this.mSoftInput;
        if (s != null) {
            s.c();
            nativeReportKeyboardConfigChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        this.mActivity.finish();
    }

    private boolean getARCoreEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(ARCORE_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    private ActivityInfo getActivityInfo() {
        return this.mActivity.getPackageManager().getActivityInfo(this.mActivity.getComponentName(), 128);
    }

    private ApplicationInfo getApplicationInfo() {
        return this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getAutoReportFullyDrawnEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(AUTO_REPORT_FULLY_DRAWN_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getHaveAndroidWindowSupport() {
        if (this.m_IsNoWindowMode == -1) {
            this.m_IsNoWindowMode = nativeGetNoWindowMode() ? 1 : 0;
        }
        return this.m_IsNoWindowMode == 1;
    }

    private boolean getLaunchFullscreen() {
        try {
            return getApplicationInfo().metaData.getBoolean(LAUNCH_FULLSCREEN);
        } catch (Exception unused) {
            return false;
        }
    }

    private int getNaturalOrientation(int i) {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if ((rotation == 0 || rotation == 2) && i == 2) {
            return 0;
        }
        return ((rotation == 1 || rotation == 3) && i == 1) ? 0 : 1;
    }

    private String getProcessName() {
        int iMyPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private long getSoftInputTimeout() {
        return Math.round(this.mSoftInputTimeoutMilliSeconds * ((double) Math.max(1.0f, Settings.System.getFloat(this.mContext.getContentResolver(), "animator_duration_scale", 0.0f))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getSplashEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(SPLASH_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    private static String getUnityNativeLibraryPath(Context context) {
        return context.getApplicationInfo().nativeLibraryDir;
    }

    private void hidePreservedContent() {
        runOnUiThread(new RunnableC0054n0(this));
    }

    private void hideStatusBar() {
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.getWindow().setFlags(1024, 1024);
        }
    }

    private final native void initJni(Context context);

    private static String loadNative(String str) {
        String str2 = str + "/libmain.so";
        try {
            try {
                try {
                    System.load(str2);
                } catch (SecurityException e) {
                    return logLoadLibMainError(str2, e.toString());
                }
            } catch (UnsatisfiedLinkError e2) {
                return logLoadLibMainError(str2, e2.toString());
            }
        } catch (UnsatisfiedLinkError unused) {
            System.loadLibrary("main");
        }
        if (NativeLoader.load(str)) {
            W0.e();
            return "";
        }
        AbstractC0077z.Log(6, "NativeLoader.load failure, Unity libraries were not loaded.");
        return "NativeLoader.load failure, Unity libraries were not loaded.";
    }

    private static String logLoadLibMainError(String str, String str2) {
        String str3 = "Failed to load 'libmain.so'\n\n" + str2;
        AbstractC0077z.Log(6, str3);
        return str3;
    }

    private final native void nativeApplicationUnload();

    private final native boolean nativeDone();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeFocusChanged(boolean z);

    private final native boolean nativeGetNoWindowMode();

    private final native void nativeHidePreservedContent();

    private final native boolean nativeInjectEvent(InputEvent inputEvent, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeIsAutorotationOn();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeLowMemory();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeMuteMasterAudio(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeOrientationChanged(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativePause();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeRecreateGfxState(int i, Surface surface);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeRender();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeReportKeyboardConfigChanged();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeResume();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSendSurfaceChangedEvent();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputArea(int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputSelection(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputString(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetKeyboardIsVisible(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetLaunchURL(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputCanceled();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputClosed();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, byte[] bArr);

    private void pauseUnity() {
        reportSoftInputStr(null, 1, true);
        C c = this.m_OnBackPressedDispatcher;
        if (c != null) {
            c.c = c.a != null;
            c.unregisterOnBackPressedCallback();
        }
        if (!this.mState.c() || this.mState.b()) {
            return;
        }
        if (W0.d()) {
            Semaphore semaphore = new Semaphore(0);
            Runnable o0 = isFinishing() ? new O0(this, semaphore) : new P0(this, semaphore);
            U0 u0 = this.m_MainThread;
            if (u0.a != null) {
                u0.a(S0.a);
                Message.obtain(u0.a, o0).sendToTarget();
            }
            try {
                SynchronizationTimeout synchronizationTimeout = SynchronizationTimeout.Pause;
                if (!semaphore.tryAcquire(synchronizationTimeout.getTimeout(), TimeUnit.MILLISECONDS)) {
                    AbstractC0077z.Log(5, "Timeout (" + synchronizationTimeout.getTimeout() + " ms) while trying to pause the Unity Engine.");
                }
            } catch (InterruptedException unused) {
                AbstractC0077z.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
            }
        }
        this.mState.c(false);
        this.mState.e(true);
        if (this.m_AddPhoneCallListener) {
            this.m_TelephonyManager.listen(this.m_PhoneCallListener, 0);
        }
    }

    private static void preloadJavaPlugins() {
        try {
            Class.forName("com.unity3d.JavaPluginPreloader");
        } catch (ClassNotFoundException unused) {
        } catch (LinkageError e) {
            AbstractC0077z.Log(6, "Java class preloading failed: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueDestroy() {
        AbstractC0077z.Log(4, "Queue Destroy");
        runOnUiThread(new N0(this));
    }

    private void queueGLThreadEvent(V0 v0) {
        if (isFinishing()) {
            return;
        }
        queueGLThreadEvent((Runnable) v0);
    }

    private void raiseFocusListener(boolean z) {
        E e = this.mOnHandleFocusListener;
        if (e != null) {
            RunnableC0060q0 runnableC0060q0 = ((C0056o0) e).a;
            runnableC0060q0.b = true;
            if (runnableC0060q0.a) {
                runnableC0060q0.c.release();
            }
        }
    }

    private void setBackButtonLeavesApp(boolean z) {
        C c = this.m_OnBackPressedDispatcher;
        if (c != null) {
            if (!z) {
                c.registerOnBackPressedCallback();
            } else {
                c.unregisterOnBackPressedCallback();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdown() {
        this.mProcessKillRequested = nativeDone();
        this.mState.d(false);
    }

    private void startActivityIndicator(int i) {
        postOnUiThread(new G0(this, i));
    }

    private void stopActivityIndicator() {
        postOnUiThread(new I0());
    }

    private void swapViews(View view, View view2) {
        boolean z;
        if (this.mState.b()) {
            z = false;
        } else {
            setupUnityToBePaused();
            z = true;
        }
        if (view != null) {
            ViewParent parent = view.getParent();
            if (!(parent instanceof UnityPlayer) || ((UnityPlayer) parent) != this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
                addView(view);
                bringChildToFront(view);
                view.setVisibility(0);
            }
        }
        if (view2 != null && view2.getParent() == this) {
            view2.setVisibility(8);
            removeView(view2);
        }
        if (z) {
            setupUnityToBeResumed();
        }
    }

    private static void unloadNative() {
        if (W0.d()) {
            if (!NativeLoader.unload()) {
                throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
            }
            W0.f();
        }
    }

    private boolean updateDisplayInternal(int i, Surface surface) {
        if (!W0.d() || !this.mState.a()) {
            return false;
        }
        Semaphore semaphore = new Semaphore(0);
        L0 l0 = new L0(this, i, surface, semaphore);
        if (i == 0) {
            U0 u0 = this.m_MainThread;
            Handler handler = u0.a;
            if (surface == null) {
                if (handler != null) {
                    u0.a(S0.d);
                    Message.obtain(u0.a, l0).sendToTarget();
                }
            } else if (handler != null) {
                Message.obtain(handler, l0).sendToTarget();
                u0.a(S0.e);
            }
        } else {
            l0.run();
        }
        if (surface != null || i != 0) {
            return true;
        }
        try {
            SynchronizationTimeout synchronizationTimeout = SynchronizationTimeout.SurfaceDetach;
            if (semaphore.tryAcquire(synchronizationTimeout.getTimeout(), TimeUnit.MILLISECONDS)) {
                return true;
            }
            AbstractC0077z.Log(5, "Timeout (" + synchronizationTimeout.getTimeout() + " ms) while trying detaching primary window.");
            return true;
        } catch (InterruptedException unused) {
            AbstractC0077z.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            return true;
        }
    }

    protected void addPhoneCallListener() {
        this.m_AddPhoneCallListener = true;
        this.m_TelephonyManager.listen(this.m_PhoneCallListener, 32);
    }

    public boolean addViewToPlayer(View view, boolean z) {
        swapViews(view, z ? this.mGlView : null);
        boolean z2 = true;
        boolean z3 = view.getParent() == this;
        boolean z4 = z && this.mGlView.getParent() == null;
        boolean z5 = this.mGlView.getParent() == this;
        if (!z3 || (!z4 && !z5)) {
            z2 = false;
        }
        if (!z2) {
            if (!z3) {
                AbstractC0077z.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z4 && !z5) {
                AbstractC0077z.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z2;
    }

    public void configurationChanged(Configuration configuration) {
        int iDiff = this.prevConfig.diff(configuration);
        if ((iDiff & 256) != 0 || (iDiff & 1024) != 0 || (iDiff & 2048) != 0 || (iDiff & 128) != 0) {
            nativeHidePreservedContent();
        }
        this.prevConfig = new Configuration(configuration);
        i1 i1Var = this.mVideoPlayerProxy;
        if (i1Var != null) {
            i1Var.a();
        }
    }

    public void destroy() {
        Camera2Wrapper camera2Wrapper = this.m_Camera2Wrapper;
        if (camera2Wrapper != null) {
            camera2Wrapper.a();
            this.m_Camera2Wrapper = null;
        }
        HFPStatus hFPStatus = this.m_HFPStatus;
        if (hFPStatus != null) {
            hFPStatus.b();
            this.m_HFPStatus = null;
        }
        NetworkConnectivity networkConnectivity = this.m_NetworkConnectivity;
        if (networkConnectivity != null) {
            networkConnectivity.a();
            this.m_NetworkConnectivity = null;
        }
        C c = this.m_OnBackPressedDispatcher;
        if (c != null) {
            c.unregisterOnBackPressedCallback();
            this.m_OnBackPressedDispatcher = null;
        }
        this.mQuitting = true;
        if (!this.mState.b()) {
            setupUnityToBePaused();
        }
        this.m_MainThread.a(S0.c);
        try {
            this.m_MainThread.join(SynchronizationTimeout.Destroy.getTimeout());
        } catch (InterruptedException unused) {
            this.m_MainThread.interrupt();
        }
        if (W0.d()) {
            removeAllViews();
        }
        if (this.mProcessKillRequested) {
            this.m_UnityPlayerLifecycleEvents.onUnityPlayerQuitted();
            kill();
        }
        unloadNative();
    }

    protected void disableLogger() {
        AbstractC0077z.a = true;
    }

    public boolean displayChanged(int i, Surface surface) {
        if (i == 0) {
            this.mMainDisplayOverride = surface != null;
            runOnUiThread(new M0(this));
        }
        return updateDisplayInternal(i, surface);
    }

    protected void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.m_Events.poll();
            if (runnable == null) {
                return;
            } else {
                runnable.run();
            }
        }
    }

    Activity getActivity() {
        return this.mActivity;
    }

    protected String getClipboardText() {
        ClipData primaryClip = this.m_ClipboardManager.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).coerceToText(this.mContext).toString() : "";
    }

    protected String getKeyboardLayout() {
        S s = this.mSoftInput;
        if (s == null) {
            return null;
        }
        return s.a();
    }

    protected String getLaunchURL() {
        Uri uri = this.m_launchUri;
        if (uri != null) {
            return uri.toString();
        }
        return null;
    }

    protected int getNetworkConnectivity() {
        NetworkConnectivity networkConnectivity = this.m_NetworkConnectivity;
        if (networkConnectivity != null) {
            return networkConnectivity.b();
        }
        this.m_NetworkConnectivity = PlatformSupport.NOUGAT_SUPPORT ? new NetworkConnectivityNougat(this.mContext) : new NetworkConnectivity(this.mContext);
        return this.m_NetworkConnectivity.b();
    }

    public String getNetworkProxySettings(String str) {
        String str2;
        String str3;
        if (!str.startsWith("http:")) {
            if (str.startsWith("https:")) {
                str2 = "https.proxyHost";
                str3 = "https.proxyPort";
            }
            return null;
        }
        str2 = "http.proxyHost";
        str3 = "http.proxyPort";
        String property = System.getProperties().getProperty(str2);
        if (property != null && !"".equals(property)) {
            StringBuilder sb = new StringBuilder(property);
            String property2 = System.getProperties().getProperty(str3);
            if (property2 != null && !"".equals(property2)) {
                sb.append(":").append(property2);
            }
            String property3 = System.getProperties().getProperty("http.nonProxyHosts");
            if (property3 != null && !"".equals(property3)) {
                sb.append('\n').append(property3);
            }
            return sb.toString();
        }
        return null;
    }

    public float getScreenBrightness() {
        Window window = this.m_Window;
        if (window == null) {
            return 1.0f;
        }
        float f = window.getAttributes().screenBrightness;
        if (f >= 0.0f) {
            return f;
        }
        int i = Settings.System.getInt(getContext().getContentResolver(), "screen_brightness", 255);
        return PlatformSupport.PIE_SUPPORT ? (float) Math.max(0.0d, Math.min(1.0d, ((Math.log(i) * 19.811d) - 9.411d) / 100.0d)) : i / 255.0f;
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    protected int getSplashMode() {
        try {
            return getApplicationInfo().metaData.getInt(SPLASH_MODE_METADATA_NAME);
        } catch (Exception unused) {
            return 0;
        }
    }

    protected int getUaaLLaunchProcessType() {
        String processName = getProcessName();
        return (processName == null || processName.equals(this.mContext.getPackageName())) ? 0 : 1;
    }

    public View getView() {
        return this;
    }

    protected void hideSoftInput() {
        if (this.mSoftInput == null) {
            return;
        }
        reportSoftInputArea(new Rect());
        reportSoftInputIsVisible(false);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            dismissSoftInput();
            this.mSoftInput = null;
            return;
        }
        Semaphore semaphore = new Semaphore(0);
        postOnUiThread(new RunnableC0060q0(this, semaphore, this));
        try {
            try {
                if (!semaphore.tryAcquire(getSoftInputTimeout(), TimeUnit.MILLISECONDS)) {
                    AbstractC0077z.Log(6, "Timeout (" + getSoftInputTimeout() + " ms) while waiting softinput hiding operation.");
                }
            } catch (InterruptedException unused) {
                AbstractC0077z.Log(6, "UI thread got interrupted while waiting softinput hiding operation.");
            }
        } finally {
            this.mSoftInput = null;
        }
    }

    public void init(int i, boolean z) {
    }

    protected boolean initializeGoogleAr() {
        if (this.m_ARCoreApi != null || this.mActivity == null || !getARCoreEnabled()) {
            return false;
        }
        GoogleARCoreApi googleARCoreApi = new GoogleARCoreApi();
        this.m_ARCoreApi = googleARCoreApi;
        googleARCoreApi.initializeARCore(this.mActivity);
        if (this.mState.b()) {
            return false;
        }
        this.m_ARCoreApi.resumeARCore();
        return false;
    }

    public boolean injectEvent(InputEvent inputEvent) {
        return injectEvent(inputEvent, 0);
    }

    public boolean injectEvent(InputEvent inputEvent, int i) {
        if (W0.d()) {
            return nativeInjectEvent(inputEvent, i);
        }
        return false;
    }

    protected boolean isFinishing() {
        if (this.mQuitting) {
            return true;
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            this.mQuitting = activity.isFinishing();
        }
        return this.mQuitting;
    }

    protected boolean isUaaLUseCase() {
        String callingPackage;
        Activity activity = this.mActivity;
        return (activity == null || (callingPackage = activity.getCallingPackage()) == null || !callingPackage.equals(this.mContext.getPackageName())) ? false : true;
    }

    protected void kill() {
        Process.killProcess(Process.myPid());
    }

    protected boolean loadLibrary(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (Exception | UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public void lowMemory() {
        if (W0.d()) {
            queueGLThreadEvent(new RunnableC0046j0(this));
        }
    }

    public void newIntent(Intent intent) {
        this.m_launchUri = intent.getData();
        this.m_MainThread.a(S0.i);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mGlView.c()) {
            return false;
        }
        return injectEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public void onPause() {
        MultiWindowSupport.saveMultiWindowMode(this.mActivity);
        if (MultiWindowSupport.isInMultiWindowMode(this.mActivity)) {
            return;
        }
        setupUnityToBePaused();
    }

    public void onResume() {
        if (!MultiWindowSupport.isInMultiWindowMode(this.mActivity) || MultiWindowSupport.isMultiWindowModeChangedToTrue(this.mActivity)) {
            setupUnityToBeResumed();
        }
    }

    public void onStart() {
        if (MultiWindowSupport.isInMultiWindowMode(this.mActivity)) {
            setupUnityToBeResumed();
        }
    }

    public void onStop() {
        if (MultiWindowSupport.isInMultiWindowMode(this.mActivity)) {
            setupUnityToBePaused();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mGlView.c()) {
            return false;
        }
        return injectEvent(motionEvent);
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerQuitted() {
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerUnloaded() {
    }

    public void pause() {
        setupUnityToBePaused();
    }

    protected void pauseJavaAndCallUnloadCallback() {
        runOnUiThread(new C0(this));
    }

    void postOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    void queueGLThreadEvent(Runnable runnable) {
        if (W0.d()) {
            if (Thread.currentThread() == this.m_MainThread) {
                runnable.run();
            } else {
                this.m_Events.add(runnable);
            }
        }
    }

    public void quit() {
        destroy();
    }

    public void removeViewFromPlayer(View view) {
        swapViews(this.mGlView, view);
        boolean z = view.getParent() == null;
        boolean z2 = this.mGlView.getParent() == this;
        if (z && z2) {
            return;
        }
        if (!z) {
            AbstractC0077z.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
        }
        if (z2) {
            return;
        }
        AbstractC0077z.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
    }

    public void reportError(String str, String str2) {
        AbstractC0077z.Log(6, str + ": " + str2);
    }

    protected void reportSoftInputArea(Rect rect) {
        queueGLThreadEvent((V0) new C0076y0(this, rect));
    }

    protected void reportSoftInputIsVisible(boolean z) {
        queueGLThreadEvent((V0) new C0078z0(this, z));
    }

    protected void reportSoftInputSelection(int i, int i2) {
        queueGLThreadEvent((V0) new C0074x0(this, i, i2));
    }

    protected void reportSoftInputStr(String str, int i, boolean z) {
        if (i == 1) {
            hideSoftInput();
        }
        queueGLThreadEvent((V0) new C0070v0(this, z, str, i));
    }

    protected void requestUserAuthorization(String str) {
        if (str == null || str.isEmpty() || this.mActivity == null) {
            return;
        }
        UnityPermissions.ModalWaitForPermissionResponse modalWaitForPermissionResponse = new UnityPermissions.ModalWaitForPermissionResponse();
        UnityPermissions.requestUserPermissions(this.mActivity, new String[]{str}, modalWaitForPermissionResponse);
        modalWaitForPermissionResponse.waitForResponse();
    }

    public void resume() {
        setupUnityToBeResumed();
    }

    void runOnAnonymousThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    void runOnUiThread(Runnable runnable) {
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.runOnUiThread(runnable);
        } else if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    void sendSurfaceChangedEvent() {
        if (W0.d() && this.mState.a()) {
            K0 k0 = new K0(this);
            Handler handler = this.m_MainThread.a;
            if (handler != null) {
                Message.obtain(handler, k0).sendToTarget();
            }
        }
    }

    protected void setCharacterLimit(int i) {
        runOnUiThread(new RunnableC0064s0(this, i));
    }

    protected void setClipboardText(String str) {
        this.m_ClipboardManager.setPrimaryClip(ClipData.newPlainText("Text", str));
    }

    protected void setHideInputField(boolean z) {
        runOnUiThread(new RunnableC0066t0(this, z));
    }

    public void setMainSurfaceViewAspectRatio(float f) {
        if (this.mGlView != null) {
            runOnUiThread(new E0(this, f));
        }
    }

    public void setOnHandleFocusListener(E e) {
        this.mOnHandleFocusListener = e;
    }

    public void setScreenBrightness(float f) {
        float fMax = Math.max(0.04f, f);
        if (this.m_Window == null || getScreenBrightness() == fMax) {
            return;
        }
        runOnUiThread(new F0(this, fMax));
    }

    protected void setSelection(int i, int i2) {
        runOnUiThread(new RunnableC0068u0(this, i, i2));
    }

    protected void setSoftInputStr(String str) {
        runOnUiThread(new RunnableC0062r0(this, str));
    }

    protected void setupUnityToBePaused() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.pauseARCore();
        }
        i1 i1Var = this.mVideoPlayerProxy;
        if (i1Var != null) {
            i1Var.b();
        }
        AudioVolumeHandler audioVolumeHandler = this.m_AudioVolumeHandler;
        if (audioVolumeHandler != null) {
            audioVolumeHandler.a();
            this.m_AudioVolumeHandler = null;
        }
        OrientationLockListener orientationLockListener = this.m_OrientationLockListener;
        if (orientationLockListener != null) {
            orientationLockListener.a();
            this.m_OrientationLockListener = null;
        }
        pauseUnity();
    }

    protected void setupUnityToBeResumed() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.resumeARCore();
        }
        this.mState.e(false);
        i1 i1Var = this.mVideoPlayerProxy;
        if (i1Var != null) {
            i1Var.c();
        }
        checkResumePlayer();
        if (this.m_AudioVolumeHandler == null) {
            this.m_AudioVolumeHandler = new AudioVolumeHandler(this.mContext);
        }
        if (this.m_OrientationLockListener == null && W0.d()) {
            this.m_OrientationLockListener = new OrientationLockListener(this.mContext);
        }
        C c = this.m_OnBackPressedDispatcher;
        if (c != null && c.c) {
            c.registerOnBackPressedCallback();
        }
        this.prevConfig = getResources().getConfiguration();
    }

    protected void showSoftInput(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5, boolean z6) {
        Semaphore semaphore = new Semaphore(0);
        postOnUiThread(new RunnableC0052m0(this, this, str, i, z, z2, z3, z4, str2, i2, z5, z6, semaphore));
        try {
            if (semaphore.tryAcquire(getSoftInputTimeout(), TimeUnit.MILLISECONDS)) {
                return;
            }
            AbstractC0077z.Log(6, "Timeout (" + getSoftInputTimeout() + " ms) while waiting softinput showing operation.");
        } catch (InterruptedException unused) {
            AbstractC0077z.Log(6, "UI thread got interrupted while waiting softinput showing operation.");
        }
    }

    protected boolean showVideoPlayer(String str, int i, int i2, int i3, boolean z, int i4, int i5) {
        if (this.mVideoPlayerProxy == null) {
            this.mVideoPlayerProxy = new i1(this);
        }
        boolean zA = this.mVideoPlayerProxy.a(this.mContext, str, i, i2, i3, z, i4, i5, new A0(this));
        if (zA) {
            runOnUiThread(new B0(this));
        }
        return zA;
    }

    protected boolean skipPermissionsDialog() {
        Activity activity = this.mActivity;
        if (activity != null) {
            return UnityPermissions.skipPermissionsDialog(activity);
        }
        return false;
    }

    public boolean startOrientationListener(int i) {
        String str;
        if (this.mOrientationListener != null) {
            str = "Orientation Listener already started.";
        } else {
            D0 d0 = new D0(this, this.mContext, i);
            this.mOrientationListener = d0;
            if (d0.canDetectOrientation()) {
                this.mOrientationListener.enable();
                return true;
            }
            str = "Orientation Listener cannot detect orientation.";
        }
        AbstractC0077z.Log(5, str);
        return false;
    }

    public boolean stopOrientationListener() {
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener == null) {
            AbstractC0077z.Log(5, "Orientation Listener was not started.");
            return false;
        }
        orientationEventListener.disable();
        this.mOrientationListener = null;
        return true;
    }

    protected void toggleGyroscopeSensor(boolean z) {
        SensorManager sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z) {
            sensorManager.registerListener(this.m_FakeListener, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.m_FakeListener);
        }
    }

    public void unload() {
        nativeApplicationUnload();
    }

    void updateGLDisplay(int i, Surface surface) {
        if (this.mMainDisplayOverride) {
            return;
        }
        updateDisplayInternal(i, surface);
    }

    public void windowFocusChanged(boolean z) {
        S s;
        this.mState.b(z);
        if (!this.mState.a() || ((s = this.mSoftInput) != null && !s.d())) {
            raiseFocusListener(z);
            return;
        }
        this.m_MainThread.a(z ? S0.g : S0.f);
        raiseFocusListener(z);
        checkResumePlayer();
    }
}
