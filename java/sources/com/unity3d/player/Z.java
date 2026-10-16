package com.unity3d.player;

import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
final class Z implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ C0032c0 a;

    Z(C0032c0 c0032c0) {
        this.a = c0032c0;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        this.a.reportSoftInputArea();
        this.a.h.b();
    }
}
