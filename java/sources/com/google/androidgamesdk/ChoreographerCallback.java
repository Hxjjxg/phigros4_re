package com.google.androidgamesdk;

import android.view.Choreographer;

/* JADX INFO: loaded from: classes.dex */
public class ChoreographerCallback implements Choreographer.FrameCallback {
    private static final String LOG_TAG = "ChoreographerCallback";
    private long mCookie;
    private b mLooper;

    public ChoreographerCallback(long j) {
        this.mCookie = j;
        b bVar = new b();
        this.mLooper = bVar;
        bVar.start();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        nOnChoreographer(this.mCookie, j);
    }

    public native void nOnChoreographer(long j, long j2);

    public void postFrameCallback() {
        this.mLooper.a.post(new a(this));
    }

    public void postFrameCallbackDelayed(long j) {
        Choreographer.getInstance().postFrameCallbackDelayed(this, j);
    }

    public void terminate() {
        this.mLooper.a.getLooper().quit();
    }
}
