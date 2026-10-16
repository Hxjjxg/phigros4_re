package com.google.androidgamesdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
final class d extends Thread {
    public Handler a;
    private ReentrantLock b;
    private Condition c;

    private d() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.b = reentrantLock;
        this.c = reentrantLock.newCondition();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Log.i("SwappyDisplayManager", "Starting looper thread");
        this.b.lock();
        Looper.prepare();
        this.a = new Handler();
        this.c.signal();
        this.b.unlock();
        Looper.loop();
        Log.i("SwappyDisplayManager", "Terminating looper thread");
    }

    @Override // java.lang.Thread
    public final void start() {
        this.b.lock();
        super.start();
        try {
            this.c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.b.unlock();
    }
}
