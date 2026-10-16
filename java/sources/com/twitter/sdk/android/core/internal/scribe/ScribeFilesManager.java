package com.twitter.sdk.android.core.internal.scribe;

import android.content.Context;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventTransform;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.QueueFileEventStorage;
import java.io.IOException;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
class ScribeFilesManager extends EventsFilesManager<ScribeEvent> {
    static final String FILE_EXTENSION = ".tap";
    static final String FILE_PREFIX = "se";

    public ScribeFilesManager(Context context, EventTransform<ScribeEvent> eventTransform, CurrentTimeProvider currentTimeProvider, QueueFileEventStorage queueFileEventStorage, int i) throws IOException {
        super(context, eventTransform, currentTimeProvider, queueFileEventStorage, i);
    }

    @Override // io.fabric.sdk.android.services.events.EventsFilesManager
    protected String generateUniqueRollOverFileName() {
        return "se_" + UUID.randomUUID().toString() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.currentTimeProvider.getCurrentTimeMillis() + FILE_EXTENSION;
    }
}
