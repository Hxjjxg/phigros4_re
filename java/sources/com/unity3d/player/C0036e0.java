package com.unity3d.player;

import android.database.ContentObserver;
import android.os.Handler;

/* JADX INFO: renamed from: com.unity3d.player.e0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0036e0 extends ContentObserver {
    private InterfaceC0034d0 a;

    public C0036e0(Handler handler, InterfaceC0034d0 interfaceC0034d0) {
        super(handler);
        this.a = interfaceC0034d0;
    }

    @Override // android.database.ContentObserver
    public final boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        InterfaceC0034d0 interfaceC0034d0 = this.a;
        if (interfaceC0034d0 != null) {
            ((OrientationLockListener) interfaceC0034d0).b();
        }
    }
}
