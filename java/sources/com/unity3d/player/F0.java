package com.unity3d.player;

import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
final class F0 implements Runnable {
    final /* synthetic */ float a;
    final /* synthetic */ UnityPlayer b;

    F0(UnityPlayer unityPlayer, float f) {
        this.b = unityPlayer;
        this.a = f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowManager.LayoutParams attributes = this.b.m_Window.getAttributes();
        attributes.screenBrightness = this.a;
        this.b.m_Window.setAttributes(attributes);
    }
}
