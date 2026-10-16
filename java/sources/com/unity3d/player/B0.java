package com.unity3d.player;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
final class B0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    B0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UnityPlayer unityPlayer;
        Activity activity;
        if (!this.a.nativeIsAutorotationOn() || (activity = (unityPlayer = this.a).mActivity) == null) {
            return;
        }
        activity.setRequestedOrientation(unityPlayer.mInitialScreenOrientation);
    }
}
