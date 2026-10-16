package com.digits.sdk.android;

import com.twitter.sdk.android.core.PersistedSessionManager;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.SessionMonitor;
import com.twitter.sdk.android.core.internal.scribe.DefaultScribeClient;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
@DependsOn({TwitterCore.class})
public class Digits extends Kit<Void> {
    private static final String KIT_SCRIBE_NAME = "Digits";
    static final String PREF_KEY_ACTIVE_SESSION = "active_session";
    static final String PREF_KEY_SESSION = "session";
    public static final String TAG = "Digits";
    private ActivityClassManager activityClassManager;
    private volatile ContactsClient contactsClient;
    private volatile DigitsClient digitsClient;
    DefaultScribeClient scribeClient;
    private SessionManager<DigitsSession> sessionManager;
    private SessionMonitor<DigitsSession> sessionMonitor;
    private int themeResId;

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "com.digits.sdk.android:digits";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "1.2.0.32";
    }

    public static Digits getInstance() {
        return (Digits) Fabric.getKit(Digits.class);
    }

    public static void authenticate(AuthCallback authCallback, int i) {
        getInstance().setTheme(i);
        getInstance().getDigitsClient().startSignUp(authCallback);
    }

    public static void authenticate(AuthCallback authCallback) {
        authenticate(authCallback, 0);
    }

    public static SessionManager<DigitsSession> getSessionManager() {
        return getInstance().sessionManager;
    }

    @Override // io.fabric.sdk.android.Kit
    protected boolean onPreExecute() {
        this.sessionManager = new PersistedSessionManager(new PreferenceStoreImpl(this), new DigitsSession.Serializer(), PREF_KEY_ACTIVE_SESSION, "session");
        this.sessionMonitor = new SessionMonitor<>(this.sessionManager, getExecutorService());
        return super.onPreExecute();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.fabric.sdk.android.Kit
    public Void doInBackground() {
        this.sessionManager.getActiveSession();
        createDigitsClient();
        createContactsClient();
        setUpScribing();
        this.sessionMonitor.triggerVerificationIfNecessary();
        this.sessionMonitor.monitorActivityLifecycle(getFabric().getActivityLifecycleManager());
        return null;
    }

    int getTheme() {
        int i = this.themeResId;
        return i != 0 ? i : R.style.Digits_default;
    }

    protected void setTheme(int i) {
        this.themeResId = i;
        createActivityClassManager();
    }

    DigitsClient getDigitsClient() {
        if (this.digitsClient == null) {
            createDigitsClient();
        }
        return this.digitsClient;
    }

    private synchronized void createDigitsClient() {
        if (this.digitsClient == null) {
            this.digitsClient = new DigitsClient();
        }
    }

    public ContactsClient getContactsClient() {
        if (this.contactsClient == null) {
            createContactsClient();
        }
        return this.contactsClient;
    }

    private synchronized void createContactsClient() {
        if (this.contactsClient == null) {
            this.contactsClient = new ContactsClient();
        }
    }

    protected ExecutorService getExecutorService() {
        return getFabric().getExecutorService();
    }

    private void setUpScribing() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.sessionManager);
        this.scribeClient = new DefaultScribeClient(this, "Digits", arrayList, getIdManager());
    }

    protected void scribe(EventNamespace... eventNamespaceArr) {
        DefaultScribeClient defaultScribeClient = this.scribeClient;
        if (defaultScribeClient != null) {
            defaultScribeClient.scribeSyndicatedSdkImpressionEvents(eventNamespaceArr);
        }
    }

    protected ActivityClassManager getActivityClassManager() {
        if (this.activityClassManager == null) {
            createActivityClassManager();
        }
        return this.activityClassManager;
    }

    protected void createActivityClassManager() {
        this.activityClassManager = new ActivityClassManagerFactory().createActivityClassManager(getContext(), this.themeResId);
    }
}
