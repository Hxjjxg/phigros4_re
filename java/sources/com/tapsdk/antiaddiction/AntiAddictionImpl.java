package com.tapsdk.antiaddiction;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.entities.CheckPlayResult;
import com.tapsdk.antiaddiction.entities.TwoTuple;
import com.tapsdk.antiaddiction.entities.UserInfo;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.models.ConfigModel;
import com.tapsdk.antiaddiction.models.IdentifyModel;
import com.tapsdk.antiaddiction.models.PaymentModel;
import com.tapsdk.antiaddiction.models.PlayLogModel;
import com.tapsdk.antiaddiction.models.TapTapModel;
import com.tapsdk.antiaddiction.models.TimingModel;
import com.tapsdk.antiaddiction.models.UserModel;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.functions.Action1;
import com.tapsdk.antiaddiction.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tapsdk.antiaddiction.reactor.schedulers.Schedulers;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.NetServerErrorChecker;
import com.tapsdk.antiaddiction.skynet.Skynet;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.skynet.logging.HttpLoggingInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.OkHttpClient;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.retrofit2.Retrofit;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.gson.GsonConverterFactory;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddiction.utils.NetworkUtils;
import com.tapsdk.antiaddiction.utils.TapTapSignUtil;
import com.tds.common.TapCommon;
import com.tds.common.entities.TapConfig;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.widgets.TdsActivityManager;
import com.tds.gson.Gson;
import com.tds.gson.GsonBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionImpl implements IAntiAddiction {
    static Set<String> dnotCheckUrlSet;
    public static Boolean runInFakeEnvironment = false;
    private AntiAddictionCallback antiAddictionCallback;
    private TdsActivityManager.AppLifecycleCallback appLifecycleCallback;
    private Context applicationContext;
    private Config config;
    private String gameIdentifier;
    private String sessionId;
    private TimingModel timingModel;
    private boolean initialized = false;
    private final Handler mainLooperHandler = new Handler(Looper.getMainLooper());
    private boolean canPlay = false;
    private final UserModel userModel = new UserModel();
    private final IdentifyModel identifyModel = new IdentifyModel();
    private final ConfigModel configModel = new ConfigModel();
    private final PaymentModel paymentModel = new PaymentModel();
    private String unityVersion = "";
    private final Gson gson = new GsonBuilder().create();
    private int tapErrorRetryTimes = 1;
    private int authTypeForToken = 0;

    static {
        HashSet hashSet = new HashSet();
        dnotCheckUrlSet = hashSet;
        hashSet.addAll(Arrays.asList("server-time", "configuration", "payable", "payments"));
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void setUnityVersion(String str) {
        this.unityVersion = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Request buildAntiAddictionRequest(Interceptor.Chain chain, UserInfo userInfo) {
        StringBuilder sb = new StringBuilder("Version=3.30.3&Platform=Android");
        if (!TextUtils.isEmpty(this.unityVersion)) {
            sb.append("&Unity-SDK-Version=").append(this.unityVersion);
        }
        Request.Builder builderHeader = chain.request().newBuilder().header("User-Agent", sb.toString()).header("accept-language", "zh-cn");
        if (userInfo != null && !TextUtils.isEmpty(userInfo.accessToken)) {
            builderHeader.header("X-TAP-Anti-Addiction-Token", userInfo.accessToken);
        }
        return builderHeader.build();
    }

    private void initSkynet(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(new Interceptor() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.1
            @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                Response responseProceed;
                int i;
                UserInfo currentUser = AntiAddictionImpl.this.userModel.getCurrentUser();
                try {
                    responseProceed = chain.proceed(AntiAddictionImpl.this.buildAntiAddictionRequest(chain, currentUser));
                    while (true) {
                        i--;
                        if (i <= 0 || (!(responseProceed == null && NetworkUtils.isNetworkAvailable(AntiAddictionImpl.this.applicationContext)) && (responseProceed == null || responseProceed.code() < 500))) {
                            break;
                        }
                        try {
                            responseProceed = chain.proceed(AntiAddictionImpl.this.buildAntiAddictionRequest(chain, currentUser));
                        } catch (Exception e) {
                            AntiAddictionLogger.e("<-- HTTP FAILED: " + e);
                        }
                    }
                } catch (Exception e2) {
                    AntiAddictionLogger.e("<-- HTTP FAILED: " + e2);
                    responseProceed = null;
                }
                i = 3;
                return responseProceed;
            }
        });
        builder.addInterceptor(httpLoggingInterceptor);
        Skynet.getInstance().registerRetrofit(Skynet.RETROFIT_FOR_ANTI_ADDICTION, new Retrofit.Builder().baseUrl(HostReplaceUtil.getInstance().getReplacedHost("https://tds-tapsdk.cn.tapapis.com")).client(builder.build()).addConverterFactory(GsonConverterFactory.create(Collections.singletonList(new NetServerErrorChecker()))).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build());
        OkHttpClient.Builder builder2 = new OkHttpClient.Builder();
        builder2.addInterceptor(new Interceptor() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.2
            @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                Response responseProceed;
                int i;
                try {
                    responseProceed = chain.proceed(chain.request());
                    while (true) {
                        i--;
                        if (i <= 0 || (!(responseProceed == null && NetworkUtils.isNetworkAvailable(AntiAddictionImpl.this.applicationContext)) && (responseProceed == null || responseProceed.code() < 500))) {
                            break;
                        }
                        try {
                            responseProceed = chain.proceed(chain.request());
                        } catch (Exception e) {
                            AntiAddictionLogger.e("<-- HTTP FAILED: " + e);
                        }
                    }
                } catch (Exception e2) {
                    AntiAddictionLogger.e("<-- HTTP FAILED: " + e2);
                    responseProceed = null;
                }
                i = 3;
                return responseProceed;
            }
        });
        builder2.addInterceptor(httpLoggingInterceptor);
        Skynet.getInstance().registerRetrofit(Skynet.RETROFIT_FOR_TAPTAP_SERVICE, new Retrofit.Builder().baseUrl(HostReplaceUtil.getInstance().getReplacedHost("https://tds-tapsdk.cn.tapapis.com")).client(builder2.build()).addConverterFactory(GsonConverterFactory.create(Collections.singletonList(new NetServerErrorChecker()))).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build());
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public synchronized void init(Context context, Config config, AntiAddictionCallback antiAddictionCallback) {
        if (this.initialized) {
            return;
        }
        TapCommon.init(new TapConfig.Builder().withClientId(config.clientId).withRegionType(config.region == Region.China ? 0 : 1).build());
        initCommonParams(context, config.clientId, antiAddictionCallback);
        this.config = config;
        this.initialized = true;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public synchronized void init(Context context, String str, AntiAddictionCallback antiAddictionCallback) {
        if (this.initialized) {
            return;
        }
        TapCommon.init(new TapConfig.Builder().withClientId(str).withRegionType(0).build());
        initCommonParams(context, str, antiAddictionCallback);
        this.initialized = true;
    }

    private void initCommonParams(Context context, String str, AntiAddictionCallback antiAddictionCallback) {
        this.applicationContext = context.getApplicationContext();
        this.gameIdentifier = str;
        this.antiAddictionCallback = antiAddictionCallback;
        this.timingModel = new TimingModel(this.userModel, this.applicationContext, new TimingModel.TimingMessageListener() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.3
            @Override // com.tapsdk.antiaddiction.models.TimingModel.TimingMessageListener
            public void onMessage(int i, Map<String, Object> map) {
                AntiAddictionImpl.this.notifyAntiAddictionMessage(i, map);
            }
        });
        PlayLogModel.getInstance().init(this.applicationContext, this.userModel);
        initSkynet(context);
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public boolean initialized() {
        return this.initialized;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public String getSessionId() {
        return this.sessionId;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void getRealNameConfig(Context context, String str, final Callback<Void> callback) {
        this.configModel.fetchAndSetupRealNameTipConfigAsync(context, this.config.clientId, str).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.4
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                HashMap map = new HashMap();
                map.put(Constants.MsgExtraParams.DESCRIPTION, "应用配置参数无效");
                AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
                callback.onError(th);
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    callback.onSuccess(null);
                    return;
                }
                HashMap map = new HashMap();
                map.put(Constants.MsgExtraParams.DESCRIPTION, "网络异常，请稍后重试");
                AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
                callback.onError(new RuntimeException("network error or server down"));
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void setAntiAddictionCallback(AntiAddictionCallback antiAddictionCallback) {
        this.antiAddictionCallback = antiAddictionCallback;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void reStartTiming() {
        this.canPlay = true;
        enterGame();
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void login(final String str, String str2, final AntiAddictionLoginCallback antiAddictionLoginCallback) {
        logoutWithNoNotification();
        IdentifyState identifyState = new IdentifyState(str2);
        this.userModel.setIdentifyState(identifyState, str);
        AntiAddictionSettings.getInstance().cacheUserInfo(this.applicationContext, str, str2);
        processUserConfig(str, identifyState, new Callback<IdentifyState>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.5
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(IdentifyState identifyState2) {
                if (identifyState2 != null) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.accessToken = identifyState2.antiAddictionToken;
                    userInfo.userId = str;
                    userInfo.ageLimit = identifyState2.ageLimit;
                    userInfo.clientId = AntiAddictionImpl.this.gameIdentifier;
                    AntiAddictionImpl.this.userModel.setCurrentUser(userInfo);
                    AntiAddictionImpl antiAddictionImpl = AntiAddictionImpl.this;
                    antiAddictionImpl.processPlayable(antiAddictionImpl.config.clientId, str, new Callback<Void>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.5.1
                        @Override // com.tapsdk.antiaddiction.Callback
                        public void onSuccess(Void r1) {
                            antiAddictionLoginCallback.onSuccess();
                        }

                        @Override // com.tapsdk.antiaddiction.Callback
                        public void onError(Throwable th) {
                            antiAddictionLoginCallback.onError(th);
                        }
                    });
                }
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                antiAddictionLoginCallback.onError(th);
            }
        });
    }

    private void processUserConfig(String str, final IdentifyState identifyState, final Callback<IdentifyState> callback) {
        this.configModel.fetchAndSetupUserAntiConfigAsync(this.applicationContext, this.config.clientId, str).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UserAntiConfig>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.6
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(UserAntiConfig userAntiConfig) {
                if (userAntiConfig.userState.ageLimit > -1) {
                    identifyState.ageLimit = userAntiConfig.userState.ageLimit;
                } else {
                    identifyState.ageLimit = userAntiConfig.userState.isAdult ? Constants.AgeLimit.UNKNOWN_AGE_RANGE_ADULT : 100;
                }
                AntiAddictionImpl.this.timingModel.setHeartbeatInterval(userAntiConfig.policy.heartbeatInterval);
                AntiAddictionImpl.this.sessionId = TapTapSignUtil.getRandomString(32);
                PlayLogModel.getInstance().setSessionId(AntiAddictionImpl.this.sessionId);
                if (!userAntiConfig.ageCheckResult.isAllow) {
                    throw new AgeRestrictException();
                }
                callback.onSuccess(identifyState);
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                if ((th instanceof AgeRestrictException) || (th.getCause() instanceof AgeRestrictException)) {
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.AGE_RESTRICT, null);
                    callback.onSuccess(null);
                } else if ((th instanceof AntiServerException) && ((AntiServerException) th).isTokenExpired()) {
                    AntiAddictionImpl.this.logout();
                    callback.onSuccess(null);
                } else {
                    HashMap map = new HashMap();
                    map.put(Constants.MsgExtraParams.DESCRIPTION, "网络异常，请稍后重试");
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
                    callback.onError(th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processPlayable(final String str, final String str2, final Callback<Void> callback) {
        Observable.create(new Observable.OnSubscribe<TwoTuple<Boolean, CheckPlayResult>>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.8
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Subscriber<? super TwoTuple<Boolean, CheckPlayResult>> subscriber) {
                subscriber.onNext(PlayLogModel.getInstance().checkUserStateSync(str, str2));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TwoTuple<Boolean, CheckPlayResult>>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.7
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                if ((th instanceof RuntimeException) && th.getCause() != null && (th.getCause() instanceof AntiServerException) && ((AntiServerException) th.getCause()).isTokenExpired()) {
                    AntiAddictionImpl.this.logout();
                    callback.onSuccess(null);
                } else {
                    HashMap map = new HashMap();
                    map.put(Constants.MsgExtraParams.DESCRIPTION, "网络异常，请稍后重试");
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
                    callback.onError(th);
                }
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(TwoTuple<Boolean, CheckPlayResult> twoTuple) {
                if (twoTuple.firstParam.booleanValue()) {
                    AntiAddictionImpl.this.processNonage(twoTuple.secondParam);
                } else {
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(500, null);
                }
                callback.onSuccess(null);
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void checkPlayableWhenEnterByUI(final String str, final String str2) {
        Observable.create(new Observable.OnSubscribe<TwoTuple<Boolean, CheckPlayResult>>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.10
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Subscriber<? super TwoTuple<Boolean, CheckPlayResult>> subscriber) {
                subscriber.onNext(PlayLogModel.getInstance().checkUserStateSync(str, str2));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TwoTuple<Boolean, CheckPlayResult>>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.9
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                if ((th instanceof RuntimeException) && th.getCause() != null && (th.getCause() instanceof AntiServerException) && ((AntiServerException) th.getCause()).isTokenExpired()) {
                    AntiAddictionImpl.this.logout();
                    return;
                }
                HashMap map = new HashMap();
                map.put(Constants.MsgExtraParams.DESCRIPTION, "网络异常，请稍后重试");
                AntiAddictionImpl.this.notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(TwoTuple<Boolean, CheckPlayResult> twoTuple) {
                if (twoTuple.firstParam.booleanValue()) {
                    if (twoTuple.secondParam.remainTime > 0) {
                        AntiAddictionImpl.this.notifyAntiAddictionMessage(500, null);
                        return;
                    } else {
                        AntiAddictionImpl.this.notifyAntiAddictionMessage(1030, AntiAddictionSettings.getInstance().generateAlertMessage(twoTuple.secondParam.remainTime, twoTuple.secondParam.title, twoTuple.secondParam.description));
                        return;
                    }
                }
                AntiAddictionImpl.this.notifyAntiAddictionMessage(500, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNonage(CheckPlayResult checkPlayResult) {
        if (!this.userModel.getCurrentUser().isAdult()) {
            if (checkPlayResult.remainTime <= 0) {
                notifyAntiAddictionMessage(1030, AntiAddictionSettings.getInstance().generateAlertMessage(checkPlayResult.remainTime, checkPlayResult.title, checkPlayResult.description));
                return;
            }
            Map<String, Object> mapGenerateAlertMessage = AntiAddictionSettings.getInstance().generateAlertMessage(checkPlayResult.remainTime, checkPlayResult.title, checkPlayResult.description);
            mapGenerateAlertMessage.put(Constants.MsgExtraParams.IS_LOGIN_TIP, true);
            notifyAntiAddictionMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.OPEN_ALERT_TIP, mapGenerateAlertMessage);
            return;
        }
        notifyAntiAddictionMessage(500, null);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0029  */
    public void notifyAntiAddictionMessage(final int i, final Map<String, Object> map) {
        if (i == 500) {
            this.canPlay = true;
        } else if (i == 1030 || i == 1050 || i == 1095 || i == 1100 || i == 1200) {
            this.canPlay = false;
        } else if (i == 9001) {
            logout();
        } else if (i == 1000 || i == 1001) {
            this.canPlay = false;
        }
        this.mainLooperHandler.post(new Runnable() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.11
            @Override // java.lang.Runnable
            public void run() {
                if (i != 9001 && AntiAddictionImpl.this.antiAddictionCallback != null) {
                    AntiAddictionImpl.this.antiAddictionCallback.onCallback(i, map);
                }
                if (AntiAddictionImpl.this.canPlay && AntiAddictionImpl.this.appLifecycleCallback == null) {
                    AntiAddictionImpl.this.appLifecycleCallback = new TdsActivityManager.AppLifecycleCallback() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.11.1
                        @Override // com.tds.common.widgets.TdsActivityManager.AppLifecycleCallback
                        public void onEnterBackground() {
                            AntiAddictionImpl.this.leaveGame();
                        }

                        @Override // com.tds.common.widgets.TdsActivityManager.AppLifecycleCallback
                        public void onEnterForeground() {
                            AntiAddictionImpl.this.enterGame();
                        }
                    };
                    TdsActivityManager.getInstance().registerAppLifecycleCallback(AntiAddictionImpl.this.appLifecycleCallback);
                }
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void logout() {
        notifyAntiAddictionMessage(1000, null);
        UserInfo currentUser = this.userModel.getCurrentUser();
        if (currentUser != null && !TextUtils.isEmpty(currentUser.userId)) {
            AntiAddictionSettings.getInstance().clearCachedUserInfo(this.applicationContext, currentUser.userId);
        }
        this.userModel.logout();
        this.canPlay = false;
        if (this.appLifecycleCallback != null) {
            TdsActivityManager.getInstance().unregisterAppLifecycleCallback(this.appLifecycleCallback);
            this.appLifecycleCallback = null;
        }
    }

    private void logoutWithNoNotification() {
        this.userModel.logout();
        this.canPlay = false;
        if (this.appLifecycleCallback != null) {
            TdsActivityManager.getInstance().unregisterAppLifecycleCallback(this.appLifecycleCallback);
            this.appLifecycleCallback = null;
        }
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void enterGame() {
        if (this.userModel.getCurrentUser() == null || !this.initialized || !this.canPlay || this.timingModel.inTiming) {
            return;
        }
        AntiAddictionLogger.d("EnterGame");
        this.timingModel.bind();
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void leaveGame() {
        if (this.userModel.getCurrentUser() != null && this.initialized && this.canPlay) {
            AntiAddictionLogger.d("leaveGame");
            this.timingModel.unbind();
        }
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void checkPayLimit(long j, final Callback<CheckPayResult> callback) {
        if (this.userModel.getCurrentUser() == null || !this.initialized) {
            return;
        }
        this.paymentModel.checkPay(j, this.gameIdentifier, this.userModel.getCurrentUser().clientId, this.userModel.getCurrentUser().userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CheckPayResult>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.12
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(CheckPayResult checkPayResult) {
                callback.onSuccess(checkPayResult);
            }
        }, new Action1<Throwable>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.13
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Throwable th) {
                callback.onError(th);
                if ((th instanceof AntiServerException) && ((AntiServerException) th).isTokenExpired()) {
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(9001, null);
                }
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void submitPayResult(long j, final Callback<SubmitPayResult> callback) {
        if (this.userModel.getCurrentUser() == null || !this.initialized) {
            return;
        }
        this.paymentModel.paySuccess(j, this.userModel.getCurrentUser().clientId, this.userModel.getCurrentUser().userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SubmitPayResult>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.14
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(SubmitPayResult submitPayResult) {
                callback.onSuccess(submitPayResult);
            }
        }, new Action1<Throwable>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.15
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Throwable th) {
                callback.onError(th);
                if ((th instanceof AntiServerException) && ((AntiServerException) th).isTokenExpired()) {
                    AntiAddictionImpl.this.notifyAntiAddictionMessage(9001, null);
                }
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void checkStandaloneCacheAndFetchUserIdentifyState(String str, String str2, final Callback<IdentifyState> callback) {
        String cachedUserInfo = AntiAddictionSettings.getInstance().getCachedUserInfo(this.applicationContext, str2);
        if (!TextUtils.isEmpty(cachedUserInfo)) {
            callback.onSuccess(new IdentifyState(cachedUserInfo));
        } else if (TapTapModel.hasComplianceAuthInLocal(null, this.config.useAgeRange)) {
            this.identifyModel.getTokenByTapToken(str, str2, this.config.useAgeRange, null, new Callback<IdentifyState>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.16
                @Override // com.tapsdk.antiaddiction.Callback
                public void onSuccess(IdentifyState identifyState) {
                    callback.onSuccess(identifyState);
                }

                @Override // com.tapsdk.antiaddiction.Callback
                public void onError(Throwable th) {
                    callback.onError(th);
                }
            });
        } else {
            this.identifyModel.inquireIdentifyState(str, str2, new Callback<IdentifyState>() { // from class: com.tapsdk.antiaddiction.AntiAddictionImpl.17
                @Override // com.tapsdk.antiaddiction.Callback
                public void onSuccess(IdentifyState identifyState) {
                    callback.onSuccess(identifyState);
                }

                @Override // com.tapsdk.antiaddiction.Callback
                public void onError(Throwable th) {
                    callback.onError(th);
                }
            });
        }
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public String currentToken() {
        UserInfo currentUser = this.userModel.getCurrentUser();
        return currentUser != null ? currentUser.accessToken : "";
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public int currentAgeLimit() {
        UserInfo currentUser;
        if (!this.config.useAgeRange || (currentUser = this.userModel.getCurrentUser()) == null || currentUser.ageLimit == 100 || currentUser.ageLimit == 110) {
            return -1;
        }
        return currentUser.ageLimit;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public boolean isNotAdult() {
        UserInfo currentUser = this.userModel.getCurrentUser();
        if (currentUser != null) {
            return !currentUser.isAdult();
        }
        return true;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public int currentUserRemainTime() {
        UserInfo currentUser = this.userModel.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getRemainTime();
        }
        return 0;
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public void switchAccount() {
        logoutWithNoNotification();
        notifyAntiAddictionMessage(1001, null);
    }

    @Override // com.tapsdk.antiaddiction.IAntiAddiction
    public boolean showSwitchAccount() {
        Config config = this.config;
        if (config == null) {
            return false;
        }
        return config.showSwitchAccount;
    }

    public static void setTestEnvironment(boolean z) {
        runInFakeEnvironment = Boolean.valueOf(z);
    }
}
