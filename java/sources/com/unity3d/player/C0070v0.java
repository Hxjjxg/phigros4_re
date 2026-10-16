package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.v0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0070v0 extends V0 {
    final /* synthetic */ boolean b;
    final /* synthetic */ String c;
    final /* synthetic */ int d;
    final /* synthetic */ UnityPlayer e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0070v0(UnityPlayer unityPlayer, boolean z, String str, int i) {
        super(unityPlayer);
        this.e = unityPlayer;
        this.b = z;
        this.c = str;
        this.d = i;
    }

    @Override // com.unity3d.player.V0
    public final void a() {
        if (this.b) {
            this.e.nativeSoftInputCanceled();
        } else {
            String str = this.c;
            if (str != null) {
                this.e.nativeSetInputString(str);
            }
        }
        if (this.d == 1) {
            this.e.nativeSoftInputClosed();
        }
    }
}
