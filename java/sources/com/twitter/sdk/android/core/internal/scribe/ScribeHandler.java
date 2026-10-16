package com.twitter.sdk.android.core.internal.scribe;

import android.content.Context;
import io.fabric.sdk.android.services.events.DisabledEventsStrategy;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsHandler;
import io.fabric.sdk.android.services.events.EventsStrategy;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
class ScribeHandler extends EventsHandler<ScribeEvent> {
    public ScribeHandler(Context context, EventsStrategy<ScribeEvent> eventsStrategy, EventsFilesManager eventsFilesManager, ScheduledExecutorService scheduledExecutorService) {
        super(context, eventsStrategy, eventsFilesManager, scheduledExecutorService);
    }

    public void scribe(ScribeEvent scribeEvent) {
        recordEventAsync(scribeEvent, false);
    }

    public void scribeAndFlush(ScribeEvent scribeEvent) {
        recordEventAsync(scribeEvent, true);
    }

    @Override // io.fabric.sdk.android.services.events.EventsHandler
    protected EventsStrategy<ScribeEvent> getDisabledEventsStrategy() {
        return new DisabledEventsStrategy();
    }
}
