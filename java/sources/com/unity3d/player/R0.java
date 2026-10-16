package com.unity3d.player;

import android.telephony.PhoneStateListener;

/* JADX INFO: loaded from: classes.dex */
final class R0 extends PhoneStateListener {
    final /* synthetic */ UnityPlayer a;

    private R0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // android.telephony.PhoneStateListener
    public final void onCallStateChanged(int i, String str) {
        this.a.nativeMuteMasterAudio(i == 1);
    }
}
