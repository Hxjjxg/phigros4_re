package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.x0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0074x0 extends V0 {
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ UnityPlayer d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0074x0(UnityPlayer unityPlayer, int i, int i2) {
        super(unityPlayer);
        this.d = unityPlayer;
        this.b = i;
        this.c = i2;
    }

    @Override // com.unity3d.player.V0
    public final void a() {
        this.d.nativeSetInputSelection(this.b, this.c);
    }
}
