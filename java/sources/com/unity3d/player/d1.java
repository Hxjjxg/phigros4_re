package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class d1 implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ int d;
    final /* synthetic */ boolean e;
    final /* synthetic */ long f;
    final /* synthetic */ long g;
    final /* synthetic */ i1 h;

    d1(i1 i1Var, String str, int i, int i2, int i3, boolean z, long j, long j2) {
        this.h = i1Var;
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = z;
        this.f = j;
        this.g = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        i1 i1Var = this.h;
        if (i1Var.f != null) {
            AbstractC0077z.Log(5, "Video already playing");
            i1 i1Var2 = this.h;
            i1Var2.g = 2;
            i1Var2.d.release();
            return;
        }
        i1 i1Var3 = this.h;
        i1Var.f = new a1(i1Var3.b, i1Var3.a, this.a, this.b, this.c, this.d, this.e, this.f, this.g, new c1(this));
        i1 i1Var4 = this.h;
        if (i1Var4.f != null) {
            i1Var4.a.bringToFront();
            i1 i1Var5 = this.h;
            i1Var5.a.addView(i1Var5.f);
        }
    }
}
