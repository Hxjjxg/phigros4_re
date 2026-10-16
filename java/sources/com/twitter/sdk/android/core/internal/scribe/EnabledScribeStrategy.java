package com.twitter.sdk.android.core.internal.scribe;

import android.content.Context;
import io.fabric.sdk.android.services.events.EnabledEventsStrategy;
import io.fabric.sdk.android.services.events.FilesSender;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
class EnabledScribeStrategy extends EnabledEventsStrategy<ScribeEvent> {
    private final FilesSender filesSender;

    public EnabledScribeStrategy(Context context, ScheduledExecutorService scheduledExecutorService, ScribeFilesManager scribeFilesManager, ScribeConfig scribeConfig, ScribeFilesSender scribeFilesSender) {
        super(context, scheduledExecutorService, scribeFilesManager);
        this.filesSender = scribeFilesSender;
        configureRollover(scribeConfig.sendIntervalSeconds);
    }

    @Override // io.fabric.sdk.android.services.events.EventsStrategy
    public FilesSender getFilesSender() {
        return this.filesSender;
    }
}
