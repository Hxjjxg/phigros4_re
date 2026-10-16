package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class H implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ PermissionFragment b;

    H(PermissionFragment permissionFragment, String[] strArr) {
        this.b = permissionFragment;
        this.a = strArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.b.reportAllDenied(this.a);
    }
}
