package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.l0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0050l0 implements F {
    final /* synthetic */ RunnableC0052m0 a;

    C0050l0(RunnableC0052m0 runnableC0052m0) {
        this.a = runnableC0052m0;
    }

    public final void a() {
        this.a.m.nativeSoftInputLostFocus();
        this.a.m.reportSoftInputStr(null, 1, false);
    }
}
