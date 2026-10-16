package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0043i implements Runnable {
    private IAssetPackManagerMobileDataConfirmationCallback a;
    private boolean b;

    RunnableC0043i(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback, boolean z) {
        this.a = iAssetPackManagerMobileDataConfirmationCallback;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.onMobileDataConfirmationResult(this.b);
    }
}
