package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class f1 implements Runnable {
    final /* synthetic */ i1 a;

    f1(i1 i1Var) {
        this.a = i1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        i1 i1Var = this.a;
        a1 a1Var = i1Var.f;
        if (a1Var != null) {
            i1Var.a.addViewToPlayer(a1Var, true);
            i1 i1Var2 = this.a;
            i1Var2.i = true;
            i1Var2.f.requestFocus();
        }
    }
}
