package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class g1 implements Runnable {
    final /* synthetic */ i1 a;

    g1(i1 i1Var) {
        this.a = i1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        i1 i1Var = this.a;
        a1 a1Var = i1Var.f;
        if (a1Var != null) {
            i1Var.a.removeViewFromPlayer(a1Var);
            i1Var.i = false;
            i1Var.f.destroyPlayer();
            i1Var.f = null;
            h1 h1Var = i1Var.c;
            if (h1Var != null) {
                ((A0) h1Var).a();
            }
        }
        this.a.a.onResume();
    }
}
