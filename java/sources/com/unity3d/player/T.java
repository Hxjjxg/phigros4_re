package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class T implements Runnable {
    final /* synthetic */ U a;

    T(U u) {
        this.a = u;
    }

    @Override // java.lang.Runnable
    public final void run() {
        S s = this.a.d;
        s.a(s.b(), true);
    }
}
