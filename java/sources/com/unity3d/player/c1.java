package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class c1 implements Y0 {
    final /* synthetic */ d1 a;

    c1(d1 d1Var) {
        this.a = d1Var;
    }

    public final void a(int i) {
        this.a.h.e.lock();
        i1 i1Var = this.a.h;
        i1Var.g = i;
        if (i == 3 && i1Var.i) {
            i1Var.runOnUiThread(new b1(this));
        }
        if (i != 0) {
            this.a.h.d.release();
        }
        this.a.h.e.unlock();
    }
}
