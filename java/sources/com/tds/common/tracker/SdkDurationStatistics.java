package com.tds.common.tracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.taptap.sdk.openlog.internal.TapOpenLogRemote;
import com.tds.common.account.AccountUser;
import com.tds.common.account.LoginStatusListener;
import com.tds.common.account.LoginStatusManager;
import com.tds.common.account.TdsAccount;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.utils.JsonUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SdkDurationStatistics {
    public static final String ENVIRONMENT_RELEASE = "duration_statistics_release";
    private static final String TAG = "durationStatistics";
    private static final String TAP_ANTI_SYMBOL = "com.tapsdk.antiaddictionui.AntiAddictionUIKit";
    private static final String TAP_USER_SYMBOL = "open_id";
    private static final String TDS_USER_SYMBOL = "tds_user_id";
    private static String currentUser = null;
    private static volatile boolean enableSdkDurationStatistics = true;
    private static volatile boolean hasInit = false;
    private static LoginStatusListener loginStatusListener;
    private static final List<WeakReference<Activity>> aliveActivityList = new ArrayList();
    private static final Object activityLock = new Object();
    private static volatile boolean isBackground = false;

    public static void init(Context context, String str, String str2, int i) {
        if (context == null || TextUtils.isEmpty(str) || hasInit) {
            return;
        }
        if (!enableSdkDurationStatistics) {
            Log.i(TAG, "current app disable sdk durationStatistics, just return");
            return;
        }
        try {
            initCore(context, str, str2, i);
            registerAppRunState((Application) context.getApplicationContext());
            registerLoginStatusListener();
            hasInit = true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void updateUserInfo(AccountUser accountUser) {
        if (!hasInit || accountUser == null) {
            return;
        }
        String accountUserString = getAccountUserString(accountUser);
        if (TextUtils.isEmpty(accountUserString)) {
            return;
        }
        String str = currentUser;
        if (str == null || !str.equals(accountUserString)) {
            currentUser = accountUserString;
            TapOpenLogRemote.getInstance().onLogin(accountUserString);
            Log.i(TAG, " update userLogin : " + currentUser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyUserLogout() {
        if (currentUser != null) {
            Log.i(TAG, " userLogout ");
            currentUser = null;
            TapOpenLogRemote.getInstance().onLogout();
        }
    }

    private static boolean isSupportSwitch() {
        boolean z;
        try {
            Class.forName(TAP_ANTI_SYMBOL);
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        return true ^ z;
    }

    private static void initCore(Context context, String str, String str2, int i) {
        TapOpenLogRemote.getInstance().init(context, !HostReplaceUtil.getInstance().getReplacedHost(ENVIRONMENT_RELEASE).equals(ENVIRONMENT_RELEASE) || i == 2, i, str, str2);
        TapOpenLogRemote.getInstance().enableModules("[\"app_duration\"]");
    }

    private static void registerLoginStatusListener() {
        loginStatusListener = new LoginStatusListener() { // from class: com.tds.common.tracker.SdkDurationStatistics.1
            @Override // com.tds.common.account.LoginStatusListener
            public void onBindAccount(AccountUser accountUser) {
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onUnBindAccount(String str) {
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onLoginSuccess(AccountUser accountUser) {
                SdkDurationStatistics.updateUserInfo(accountUser);
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onLogout(TdsAccount.AccountType accountType) {
                SdkDurationStatistics.notifyUserLogout();
            }
        };
        LoginStatusManager.registerLoginStatusListener(TdsAccount.AccountType.TAP, loginStatusListener);
    }

    public static void setExtraCommonMessage(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        Log.i("duration", " set durationExtra message = " + map);
        try {
            TapOpenLogRemote.getInstance().setExtraAppDurationParams(JsonUtil.toJsonStr(map));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, " setExtraCommonMessage fail error =   " + e.getMessage());
        }
    }

    private static void registerAppRunState(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.tds.common.tracker.SdkDurationStatistics.2
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                if (SdkDurationStatistics.checkActivityExist(activity, true) && SdkDurationStatistics.aliveActivityList.size() == 0 && !SdkDurationStatistics.isBackground) {
                    Log.i(SdkDurationStatistics.TAG, " enterBackground  ");
                    TapOpenLogRemote.getInstance().onBackground();
                    boolean unused = SdkDurationStatistics.isBackground = true;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                if (SdkDurationStatistics.isBackground) {
                    TapOpenLogRemote.getInstance().onAppStopped();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleAppActive(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            synchronized (activityLock) {
                try {
                    List<WeakReference<Activity>> list = aliveActivityList;
                    if (list.size() == 0 && isBackground) {
                        Log.i(TAG, " enterForeground  ");
                        TapOpenLogRemote.getInstance().onForeground();
                        isBackground = false;
                    }
                    if (!checkActivityExist(activity, false)) {
                        list.add(new WeakReference<>(activity));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkActivityExist(Activity activity, boolean z) {
        try {
            synchronized (activityLock) {
                Iterator<WeakReference<Activity>> it = aliveActivityList.iterator();
                while (it.hasNext()) {
                    if (it.next().get() == activity) {
                        if (z) {
                            it.remove();
                        }
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static String getAccountUserString(AccountUser accountUser) {
        if (accountUser == null || !accountUser.isValid() || accountUser.getAccountType() != TdsAccount.AccountType.TAP) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("open_id", accountUser.getUserId());
            return jSONObject.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static void setEnableSdkDurationStatistics(boolean z) {
        if (isSupportSwitch()) {
            enableSdkDurationStatistics = z;
        }
    }
}
