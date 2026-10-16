package com.unity3d.player;

import android.content.Context;
import android.widget.EditText;

/* JADX INFO: renamed from: com.unity3d.player.c0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0032c0 extends S {
    U h;

    public C0032c0(Context context, UnityPlayer unityPlayer) {
        super(context, unityPlayer);
    }

    @Override // com.unity3d.player.S
    public final void a(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5, boolean z6) {
        U u = new U(this.a, this.b);
        this.h = u;
        u.a(this, z5, z6);
        this.h.setOnDismissListener(new Y(this));
        super.a(str, i, z, z2, z3, z4, str2, i2, z5, z6);
        this.b.getViewTreeObserver().addOnGlobalLayoutListener(new Z(this));
        this.c.requestFocus();
        this.h.setOnCancelListener(new DialogInterfaceOnCancelListenerC0028a0(this));
    }

    @Override // com.unity3d.player.S
    public final void a(boolean z) {
        this.d = z;
        this.h.a(z);
    }

    @Override // com.unity3d.player.S
    public final void c() {
        this.h.dismiss();
    }

    @Override // com.unity3d.player.S
    protected EditText createEditText(S s) {
        return new C0030b0(this, this.a, s);
    }

    @Override // com.unity3d.player.S
    public final void e() {
        this.h.show();
    }

    protected void reportSoftInputArea() {
        if (this.h.isShowing()) {
            this.b.reportSoftInputArea(this.h.a());
        }
    }
}
