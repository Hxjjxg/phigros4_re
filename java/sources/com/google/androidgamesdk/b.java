package com.google.androidgamesdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class b extends Thread {
    public Handler a;

    private b() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Log.i("ChoreographerCallback", "Starting looper thread");
        Looper.prepare();
        this.a = new Handler();
        Looper.loop();
        Log.i("ChoreographerCallback", "Terminating looper thread");
    }
}
