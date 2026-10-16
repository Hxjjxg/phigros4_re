package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class EnabledEventsStrategy<T> implements EventsStrategy<T> {
    protected static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
    protected final Context context;
    protected final ScheduledExecutorService executorService;
    protected final EventsFilesManager<T> filesManager;
    protected volatile int rolloverIntervalSeconds = -1;
    protected final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef = new AtomicReference<>();

    public EnabledEventsStrategy(Context context, ScheduledExecutorService scheduledExecutorService, EventsFilesManager<T> eventsFilesManager) {
        this.context = context;
        this.executorService = scheduledExecutorService;
        this.filesManager = eventsFilesManager;
    }

    @Override // io.fabric.sdk.android.services.events.FileRollOverManager
    public void scheduleTimeBasedRollOverIfNeeded() {
        boolean z = this.rolloverIntervalSeconds != -1;
        boolean z2 = this.scheduledRolloverFutureRef.get() == null;
        if (z && z2) {
            scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
        }
    }

    void sendAndCleanUpIfSuccess() {
        FilesSender filesSender = getFilesSender();
        if (filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        List<File> batchOfFilesToSend = this.filesManager.getBatchOfFilesToSend();
        int size = 0;
        try {
            CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", Integer.valueOf(batchOfFilesToSend.size())));
            while (batchOfFilesToSend.size() > 0) {
                boolean zSend = filesSender.send(batchOfFilesToSend);
                if (zSend) {
                    size += batchOfFilesToSend.size();
                    this.filesManager.deleteSentFiles(batchOfFilesToSend);
                }
                if (!zSend) {
                    break;
                } else {
                    batchOfFilesToSend = this.filesManager.getBatchOfFilesToSend();
                }
            }
        } catch (Exception e) {
            CommonUtils.logControlledError(this.context, "Failed to send batch of analytics files to server: " + e.getMessage(), e);
        }
        if (size == 0) {
            this.filesManager.deleteOldestInRollOverIfOverMax();
        }
    }

    @Override // io.fabric.sdk.android.services.events.EventsManager
    public void sendEvents() {
        sendAndCleanUpIfSuccess();
    }

    protected void scheduleTimeBasedFileRollOver(int i, int i2) {
        try {
            TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            CommonUtils.logControlled(this.context, "Scheduling time based file roll over every " + i2 + " seconds");
            this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(timeBasedFileRollOverRunnable, i, i2, TimeUnit.SECONDS));
        } catch (RejectedExecutionException e) {
            CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", e);
        }
    }

    @Override // io.fabric.sdk.android.services.events.FileRollOverManager
    public void cancelTimeBasedFileRollOver() {
        if (this.scheduledRolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            this.scheduledRolloverFutureRef.get().cancel(false);
            this.scheduledRolloverFutureRef.set(null);
        }
    }

    protected void configureRollover(int i) {
        this.rolloverIntervalSeconds = i;
        scheduleTimeBasedFileRollOver(0, this.rolloverIntervalSeconds);
    }

    @Override // io.fabric.sdk.android.services.events.EventsManager
    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    @Override // io.fabric.sdk.android.services.events.EventsManager
    public void recordEvent(T t) {
        CommonUtils.logControlled(this.context, t.toString());
        try {
            this.filesManager.writeEvent(t);
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to write event.", e);
        }
        scheduleTimeBasedRollOverIfNeeded();
    }

    @Override // io.fabric.sdk.android.services.events.FileRollOverManager
    public boolean rollFileOver() {
        try {
            return this.filesManager.rollFileOver();
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", e);
            return false;
        }
    }
}
