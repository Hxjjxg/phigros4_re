package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class M0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    M0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UnityPlayer unityPlayer = this.a;
        if (unityPlayer.mMainDisplayOverride) {
            unityPlayer.removeView(unityPlayer.mGlView);
        } else if (unityPlayer.mGlView.getParent() != null) {
            AbstractC0077z.Log(5, "Couldn't add view, because it's already assigned to another parent");
        } else {
            UnityPlayer unityPlayer2 = this.a;
            unityPlayer2.addView(unityPlayer2.mGlView);
        }
    }
}
