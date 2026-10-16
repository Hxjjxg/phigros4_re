package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class b1 implements Runnable {
    final /* synthetic */ c1 a;

    b1(c1 c1Var) {
        this.a = c1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        i1 i1Var = this.a.a.h;
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
        this.a.a.h.a.onResume();
    }
}
