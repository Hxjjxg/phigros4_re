package com.twitter.sdk.android.core.internal.scribe;

import android.os.Build;
import android.text.TextUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class DefaultScribeClient extends ScribeClient {
    private static final String SCRIBE_PATH_TYPE = "sdk";
    private static final String SCRIBE_PATH_VERSION = "i";
    private static final String SCRIBE_URL = "https://syndication.twitter.com";
    private static volatile ScheduledExecutorService executor;
    private final String advertisingId;
    private final Kit kit;
    private final List<SessionManager<? extends Session>> sessionManagers;

    public DefaultScribeClient(Kit kit, String str, List<SessionManager<? extends Session>> list, IdManager idManager) {
        this(kit, str, getGson(), list, idManager);
    }

    public DefaultScribeClient(Kit kit, String str, Gson gson, List<SessionManager<? extends Session>> list, IdManager idManager) {
        super(kit, getExecutor(), getScribeConfig(Settings.getInstance().awaitSettingsData(), getUserAgent(str, kit)), new ScribeEvent.Transform(gson), TwitterCore.getInstance().getAuthConfig(), list, TwitterCore.getInstance().getSSLSocketFactory(), idManager);
        this.sessionManagers = list;
        this.kit = kit;
        this.advertisingId = idManager.getAdvertisingId();
    }

    public void scribeSyndicatedSdkImpressionEvents(EventNamespace... eventNamespaceArr) {
        String language = this.kit.getContext() != null ? this.kit.getContext().getResources().getConfiguration().locale.getLanguage() : "";
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (EventNamespace eventNamespace : eventNamespaceArr) {
            scribe(new SyndicatedSdkImpressionEvent(eventNamespace, jCurrentTimeMillis, language, this.advertisingId));
        }
    }

    public void scribe(ScribeEvent scribeEvent) {
        super.scribe(scribeEvent, getScribeSessionId(getActiveSession()));
    }

    Session getActiveSession() {
        Iterator<SessionManager<? extends Session>> it = this.sessionManagers.iterator();
        Session activeSession = null;
        while (it.hasNext() && (activeSession = it.next().getActiveSession()) == null) {
        }
        return activeSession;
    }

    long getScribeSessionId(Session session) {
        if (session != null) {
            return session.getId();
        }
        return 0L;
    }

    private static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    private static ScheduledExecutorService getExecutor() {
        if (executor == null) {
            synchronized (DefaultScribeClient.class) {
                if (executor == null) {
                    executor = ExecutorUtils.buildSingleThreadScheduledExecutorService("scribe");
                }
            }
        }
        return executor;
    }

    static ScribeConfig getScribeConfig(SettingsData settingsData, String str) {
        int i;
        int i2;
        if (settingsData == null || settingsData.analyticsSettingsData == null) {
            i = 100;
            i2 = 600;
        } else {
            i = settingsData.analyticsSettingsData.maxPendingSendFileCount;
            i2 = settingsData.analyticsSettingsData.flushIntervalSeconds;
        }
        int i3 = i;
        return new ScribeConfig(true, getScribeUrl(SCRIBE_URL, ""), SCRIBE_PATH_VERSION, "sdk", "", str, i3, i2);
    }

    static String getUserAgent(String str, Kit kit) {
        return "Fabric/" + kit.getFabric().getVersion() + " (Android " + Build.VERSION.SDK_INT + ") " + str + "/" + kit.getVersion();
    }

    static String getScribeUrl(String str, String str2) {
        return !TextUtils.isEmpty(str2) ? str2 : str;
    }
}
