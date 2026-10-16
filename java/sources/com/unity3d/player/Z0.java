package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
public final class Z0 implements Runnable {
    private a1 a;
    private boolean b = false;

    public Z0(a1 a1Var) {
        this.a = a1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
        if (this.b) {
            return;
        }
        int i = a1.A;
        this.a.cancelOnPrepare();
    }
}
