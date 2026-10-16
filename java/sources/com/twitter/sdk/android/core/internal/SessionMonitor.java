package com.twitter.sdk.android.core.internal;

import android.app.Activity;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.services.AccountService;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import retrofit.RetrofitError;

/* JADX INFO: loaded from: classes.dex */
public class SessionMonitor<T extends Session> {
    private final AccountServiceProvider accountServiceProvider;
    private final ExecutorService executorService;
    protected final MonitorState monitorState;
    private final SessionManager<T> sessionManager;
    private final SystemCurrentTimeProvider time;

    public SessionMonitor(SessionManager<T> sessionManager, ExecutorService executorService) {
        this(sessionManager, new SystemCurrentTimeProvider(), new AccountServiceProvider(), executorService, new MonitorState());
    }

    SessionMonitor(SessionManager<T> sessionManager, SystemCurrentTimeProvider systemCurrentTimeProvider, AccountServiceProvider accountServiceProvider, ExecutorService executorService, MonitorState monitorState) {
        this.time = systemCurrentTimeProvider;
        this.sessionManager = sessionManager;
        this.accountServiceProvider = accountServiceProvider;
        this.executorService = executorService;
        this.monitorState = monitorState;
    }

    public void monitorActivityLifecycle(ActivityLifecycleManager activityLifecycleManager) {
        activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks() { // from class: com.twitter.sdk.android.core.internal.SessionMonitor.1
            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityStarted(Activity activity) {
                SessionMonitor.this.triggerVerificationIfNecessary();
            }
        });
    }

    public void triggerVerificationIfNecessary() {
        if (this.sessionManager.getActiveSession() != null && this.monitorState.beginVerification(this.time.getCurrentTimeMillis())) {
            this.executorService.submit(new Runnable() { // from class: com.twitter.sdk.android.core.internal.SessionMonitor.2
                @Override // java.lang.Runnable
                public void run() {
                    SessionMonitor.this.verifyAll();
                }
            });
        }
    }

    protected void verifyAll() {
        Iterator<T> it = this.sessionManager.getSessionMap().values().iterator();
        while (it.hasNext()) {
            verifySession(it.next());
        }
        this.monitorState.endVerification(this.time.getCurrentTimeMillis());
    }

    protected void verifySession(Session session) {
        try {
            this.accountServiceProvider.getAccountService(session).verifyCredentials(true, false);
        } catch (RetrofitError unused) {
        }
    }

    protected static class MonitorState {
        private static final long TIME_THRESHOLD_IN_MILLIS = 21600000;
        public long lastVerification;
        private final Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        public boolean verifying;

        public synchronized boolean beginVerification(long j) {
            long j2 = this.lastVerification;
            boolean z = j - j2 > TIME_THRESHOLD_IN_MILLIS;
            boolean z2 = !isOnSameDate(j, j2);
            if (this.verifying || !(z || z2)) {
                return false;
            }
            this.verifying = true;
            return true;
        }

        public synchronized void endVerification(long j) {
            this.verifying = false;
            this.lastVerification = j;
        }

        private boolean isOnSameDate(long j, long j2) {
            this.utcCalendar.setTimeInMillis(j);
            int i = this.utcCalendar.get(6);
            int i2 = this.utcCalendar.get(1);
            this.utcCalendar.setTimeInMillis(j2);
            return i == this.utcCalendar.get(6) && i2 == this.utcCalendar.get(1);
        }
    }

    protected static class AccountServiceProvider {
        protected AccountServiceProvider() {
        }

        public AccountService getAccountService(Session session) {
            return new TwitterApiClient(session).getAccountService();
        }
    }
}
