package io.fabric.sdk.android.services.settings;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticsSettingsData {
    public final String analyticsURL;
    public final int flushIntervalSeconds;
    public final int maxByteSizePerFile;
    public final int maxFileCountPerSend;
    public final int maxPendingSendFileCount;

    public AnalyticsSettingsData(String str, int i, int i2, int i3, int i4) {
        this.analyticsURL = str;
        this.flushIntervalSeconds = i;
        this.maxByteSizePerFile = i2;
        this.maxFileCountPerSend = i3;
        this.maxPendingSendFileCount = i4;
    }
}
