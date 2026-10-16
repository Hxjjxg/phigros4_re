package com.unity3d.player;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class AudioVolumeHandler implements InterfaceC0057p {
    private C0059q a;

    AudioVolumeHandler(Context context) {
        C0059q c0059q = new C0059q(context);
        this.a = c0059q;
        c0059q.a(this);
    }

    public final void a() {
        this.a.a();
        this.a = null;
    }

    @Override // com.unity3d.player.InterfaceC0057p
    public final native void onAudioVolumeChanged(int i);
}
