package com.google.androidgamesdk;

import android.view.Choreographer;

/* JADX INFO: loaded from: classes.dex */
final class a implements Runnable {
    final /* synthetic */ ChoreographerCallback a;

    a(ChoreographerCallback choreographerCallback) {
        this.a = choreographerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Choreographer.getInstance().postFrameCallback(this.a);
    }
}
