package com.unity3d.player;

import android.content.DialogInterface;

/* JADX INFO: renamed from: com.unity3d.player.a0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class DialogInterfaceOnCancelListenerC0028a0 implements DialogInterface.OnCancelListener {
    final /* synthetic */ C0032c0 a;

    DialogInterfaceOnCancelListenerC0028a0(C0032c0 c0032c0) {
        this.a = c0032c0;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        F f = this.a.f;
        if (f != null) {
            ((C0050l0) f).a();
        }
    }
}
