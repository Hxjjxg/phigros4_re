package com.unity3d.player;

import android.graphics.Rect;

/* JADX INFO: renamed from: com.unity3d.player.y0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0076y0 extends V0 {
    final /* synthetic */ Rect b;
    final /* synthetic */ UnityPlayer c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0076y0(UnityPlayer unityPlayer, Rect rect) {
        super(unityPlayer);
        this.c = unityPlayer;
        this.b = rect;
    }

    @Override // com.unity3d.player.V0
    public final void a() {
        UnityPlayer unityPlayer = this.c;
        Rect rect = this.b;
        unityPlayer.nativeSetInputArea(rect.left, rect.top, rect.right, rect.bottom);
    }
}
