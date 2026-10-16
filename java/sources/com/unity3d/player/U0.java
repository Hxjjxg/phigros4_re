package com.unity3d.player;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
final class U0 extends Thread {
    Handler a;
    boolean b;
    boolean c;
    int d;
    int e;
    int f;
    int g;
    int h;
    final /* synthetic */ UnityPlayer i;

    private U0(UnityPlayer unityPlayer) {
        this.i = unityPlayer;
        this.b = false;
        this.c = false;
        this.d = 2;
        this.e = 0;
        this.h = 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(S0 s0) {
        Handler handler = this.a;
        if (handler != null) {
            Message.obtain(handler, 2269, s0).sendToTarget();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        setName("UnityMain");
        Looper.prepare();
        this.a = new Handler(Looper.myLooper(), new T0(this));
        Looper.loop();
    }
}
