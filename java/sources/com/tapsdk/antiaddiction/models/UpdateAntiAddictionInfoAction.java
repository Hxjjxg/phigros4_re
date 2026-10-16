package com.tapsdk.antiaddiction.models;

/* JADX INFO: loaded from: classes.dex */
public class UpdateAntiAddictionInfoAction {
    public final boolean playing;
    public final long remainTime;
    public final long serverTimeInSeconds;

    public UpdateAntiAddictionInfoAction(long j, long j2, boolean z) {
        this.serverTimeInSeconds = j;
        this.remainTime = j2;
        this.playing = z;
    }
}
