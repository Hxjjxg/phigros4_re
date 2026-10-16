package com.unity3d.player;

import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
final class Y implements DialogInterface.OnDismissListener {
    final /* synthetic */ C0032c0 a;

    Y(C0032c0 c0032c0) {
        this.a = c0032c0;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.a.invokeOnClose();
    }
}
