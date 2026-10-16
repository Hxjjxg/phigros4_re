package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.p0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0058p0 implements G {
    final /* synthetic */ RunnableC0060q0 a;

    C0058p0(RunnableC0060q0 runnableC0060q0) {
        this.a = runnableC0060q0;
    }

    public final void a() {
        RunnableC0060q0 runnableC0060q0 = this.a;
        runnableC0060q0.a = true;
        if (runnableC0060q0.b) {
            runnableC0060q0.c.release();
        }
    }
}
