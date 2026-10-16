package com.twitter.sdk.android.core.internal.scribe;

import android.content.Context;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.events.DisabledEventsStrategy;
import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.events.QueueFileEventStorage;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class ScribeClient {
    private static final String STORAGE_DIR_BASE = "_se_to_send";
    private static final String WORKING_FILENAME_BASE = "_se.tap";
    private final TwitterAuthConfig authConfig;
    private final ScheduledExecutorService executor;
    private final IdManager idManager;
    private final Kit kit;
    private final ScribeConfig scribeConfig;
    final ConcurrentHashMap<Long, ScribeHandler> scribeHandlers = new ConcurrentHashMap<>(2);
    private final List<SessionManager<? extends Session>> sessionManagers;
    private final SSLSocketFactory sslSocketFactory;
    private final ScribeEvent.Transform transform;

    public ScribeClient(Kit kit, ScheduledExecutorService scheduledExecutorService, ScribeConfig scribeConfig, ScribeEvent.Transform transform, TwitterAuthConfig twitterAuthConfig, List<SessionManager<? extends Session>> list, SSLSocketFactory sSLSocketFactory, IdManager idManager) {
        this.kit = kit;
        this.executor = scheduledExecutorService;
        this.scribeConfig = scribeConfig;
        this.transform = transform;
        this.authConfig = twitterAuthConfig;
        this.sessionManagers = list;
        this.sslSocketFactory = sSLSocketFactory;
        this.idManager = idManager;
    }

    public boolean scribe(ScribeEvent scribeEvent, long j) {
        try {
            getScribeHandler(j).scribe(scribeEvent);
            return true;
        } catch (IOException e) {
            CommonUtils.logControlledError(this.kit.getContext(), "Failed to scribe event", e);
            return false;
        }
    }

    public boolean scribeAndFlush(ScribeEvent scribeEvent, long j) {
        try {
            getScribeHandler(j).scribeAndFlush(scribeEvent);
            return true;
        } catch (IOException e) {
            CommonUtils.logControlledError(this.kit.getContext(), "Failed to scribe event", e);
            return false;
        }
    }

    ScribeHandler getScribeHandler(long j) throws IOException {
        if (!this.scribeHandlers.containsKey(Long.valueOf(j))) {
            this.scribeHandlers.putIfAbsent(Long.valueOf(j), newScribeHandler(j));
        }
        return this.scribeHandlers.get(Long.valueOf(j));
    }

    private ScribeHandler newScribeHandler(long j) throws IOException {
        Context context = this.kit.getContext();
        ScribeFilesManager scribeFilesManager = new ScribeFilesManager(context, this.transform, new SystemCurrentTimeProvider(), new QueueFileEventStorage(context, new FileStoreImpl(this.kit).getFilesDir(), getWorkingFileNameForOwner(j), getStorageDirForOwner(j)), this.scribeConfig.maxFilesToKeep);
        return new ScribeHandler(context, getScribeStrategy(j, scribeFilesManager), scribeFilesManager, this.executor);
    }

    EventsStrategy<ScribeEvent> getScribeStrategy(long j, ScribeFilesManager scribeFilesManager) {
        Context context = this.kit.getContext();
        if (this.scribeConfig.isEnabled) {
            CommonUtils.logControlled(context, "Scribe enabled");
            return new EnabledScribeStrategy(context, this.executor, scribeFilesManager, this.scribeConfig, new ScribeFilesSender(context, this.scribeConfig, j, this.authConfig, this.sessionManagers, this.sslSocketFactory, this.executor, this.idManager));
        }
        CommonUtils.logControlled(context, "Scribe disabled");
        return new DisabledEventsStrategy();
    }

    String getWorkingFileNameForOwner(long j) {
        return j + WORKING_FILENAME_BASE;
    }

    String getStorageDirForOwner(long j) {
        return j + STORAGE_DIR_BASE;
    }
}
