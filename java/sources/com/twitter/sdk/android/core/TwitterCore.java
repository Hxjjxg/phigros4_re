package com.twitter.sdk.android.core;

import android.app.Activity;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.internal.SessionMonitor;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Service;
import com.twitter.sdk.android.core.internal.scribe.TwitterCoreScribeClientHolder;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.NetworkUtils;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class TwitterCore extends Kit<Boolean> {
    static final String PREF_KEY_ACTIVE_APP_SESSION = "active_appsession";
    static final String PREF_KEY_ACTIVE_TWITTER_SESSION = "active_twittersession";
    static final String PREF_KEY_APP_SESSION = "appsession";
    static final String PREF_KEY_TWITTER_SESSION = "twittersession";
    public static final String TAG = "Twitter";
    private final ConcurrentHashMap<Session, TwitterApiClient> apiClients;
    SessionManager<AppSession> appSessionManager;
    private final TwitterAuthConfig authConfig;
    SessionMonitor<TwitterSession> sessionMonitor;
    private volatile SSLSocketFactory sslSocketFactory;
    SessionManager<TwitterSession> twitterSessionManager;

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "com.twitter.sdk.android:twitter-core";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "1.3.0.33";
    }

    public TwitterCore(TwitterAuthConfig twitterAuthConfig) {
        this.authConfig = twitterAuthConfig;
        this.apiClients = new ConcurrentHashMap<>();
    }

    TwitterCore(TwitterAuthConfig twitterAuthConfig, ConcurrentHashMap<Session, TwitterApiClient> concurrentHashMap) {
        this.authConfig = twitterAuthConfig;
        this.apiClients = concurrentHashMap;
    }

    public static TwitterCore getInstance() {
        checkInitialized();
        return (TwitterCore) Fabric.getKit(TwitterCore.class);
    }

    public TwitterAuthConfig getAuthConfig() {
        return this.authConfig;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        checkInitialized();
        if (this.sslSocketFactory == null) {
            createSSLSocketFactory();
        }
        return this.sslSocketFactory;
    }

    private synchronized void createSSLSocketFactory() {
        if (this.sslSocketFactory == null) {
            try {
                this.sslSocketFactory = NetworkUtils.getSSLSocketFactory(new TwitterPinningInfoProvider(getContext()));
                Fabric.getLogger().d(TAG, "Custom SSL pinning enabled");
            } catch (Exception e) {
                Fabric.getLogger().e(TAG, "Exception setting up custom SSL pinning", e);
            }
        }
    }

    @Override // io.fabric.sdk.android.Kit
    protected boolean onPreExecute() {
        this.twitterSessionManager = new PersistedSessionManager(new PreferenceStoreImpl(this), new TwitterSession.Serializer(), PREF_KEY_ACTIVE_TWITTER_SESSION, PREF_KEY_TWITTER_SESSION);
        this.sessionMonitor = new SessionMonitor<>(this.twitterSessionManager, getFabric().getExecutorService());
        this.appSessionManager = new PersistedSessionManager(new PreferenceStoreImpl(this), new AppSession.Serializer(), PREF_KEY_ACTIVE_APP_SESSION, PREF_KEY_APP_SESSION);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.fabric.sdk.android.Kit
    public Boolean doInBackground() {
        this.twitterSessionManager.getActiveSession();
        this.appSessionManager.getActiveSession();
        getSSLSocketFactory();
        initializeScribeClient();
        this.sessionMonitor.triggerVerificationIfNecessary();
        this.sessionMonitor.monitorActivityLifecycle(getFabric().getActivityLifecycleManager());
        return true;
    }

    private static void checkInitialized() {
        if (Fabric.getKit(TwitterCore.class) == null) {
            throw new IllegalStateException("Must start Twitter Kit with Fabric.with() first");
        }
    }

    private void initializeScribeClient() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.twitterSessionManager);
        arrayList.add(this.appSessionManager);
        TwitterCoreScribeClientHolder.initialize(this, arrayList, getIdManager());
    }

    public void logIn(Activity activity, Callback<TwitterSession> callback) {
        checkInitialized();
        new TwitterAuthClient().authorize(activity, callback);
    }

    public void logInGuest(Callback<AppSession> callback) {
        checkInitialized();
        new GuestAuthClient(new OAuth2Service(this, null, new TwitterApi())).authorize(this.appSessionManager, callback);
    }

    public void logOut() {
        checkInitialized();
        SessionManager<TwitterSession> sessionManager = getSessionManager();
        if (sessionManager != null) {
            sessionManager.clearActiveSession();
        }
    }

    public SessionManager<TwitterSession> getSessionManager() {
        checkInitialized();
        return this.twitterSessionManager;
    }

    public SessionManager<AppSession> getAppSessionManager() {
        checkInitialized();
        return this.appSessionManager;
    }

    private Session getActiveSession() {
        Session activeSession = this.twitterSessionManager.getActiveSession();
        return activeSession == null ? this.appSessionManager.getActiveSession() : activeSession;
    }

    public TwitterApiClient getApiClient() {
        checkInitialized();
        Session activeSession = getActiveSession();
        if (activeSession == null) {
            throw new IllegalStateException("Must have valid session. Did you authenticate with Twitter?");
        }
        return getApiClient(activeSession);
    }

    public TwitterApiClient getApiClient(Session session) {
        checkInitialized();
        if (!this.apiClients.containsKey(session)) {
            this.apiClients.putIfAbsent(session, new TwitterApiClient(session));
        }
        return this.apiClients.get(session);
    }
}
