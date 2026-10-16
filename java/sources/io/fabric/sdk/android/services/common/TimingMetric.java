package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class TimingMetric {
    private final boolean disabled;
    private long duration;
    private final String eventName;
    private long start;
    private final String tag;

    public TimingMetric(String str, String str2) {
        this.eventName = str;
        this.tag = str2;
        this.disabled = !Log.isLoggable(str2, 2);
    }

    public synchronized void startMeasuring() {
        if (this.disabled) {
            return;
        }
        this.start = SystemClock.elapsedRealtime();
        this.duration = 0L;
    }

    public synchronized void stopMeasuring() {
        if (this.disabled) {
            return;
        }
        if (this.duration != 0) {
            return;
        }
        this.duration = SystemClock.elapsedRealtime() - this.start;
        reportToLog();
    }

    public long getDuration() {
        return this.duration;
    }

    private void reportToLog() {
        Log.v(this.tag, this.eventName + ": " + this.duration + "ms");
    }
}
