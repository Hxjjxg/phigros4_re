package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class e1 implements Runnable {
    final /* synthetic */ i1 a;

    e1(i1 i1Var) {
        this.a = i1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onPause();
    }
}
