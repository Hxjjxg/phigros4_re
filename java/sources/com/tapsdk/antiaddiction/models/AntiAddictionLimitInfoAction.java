package com.tapsdk.antiaddiction.models;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionLimitInfoAction {
    public final boolean canPlay;
    public final boolean loggedIn;
    public final int strictType;

    public AntiAddictionLimitInfoAction(boolean z, int i, boolean z2) {
        this.canPlay = z;
        this.strictType = i;
        this.loggedIn = z2;
    }
}
