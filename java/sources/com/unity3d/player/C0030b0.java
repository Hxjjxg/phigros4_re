package com.unity3d.player;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

/* JADX INFO: renamed from: com.unity3d.player.b0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0030b0 extends EditText {
    final /* synthetic */ S a;
    final /* synthetic */ C0032c0 b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0030b0(C0032c0 c0032c0, Context context, S s) {
        super(context);
        this.b = c0032c0;
        this.a = s;
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (keyEvent.getAction() == 1) {
                this.b.h.onBackPressed();
            }
            return true;
        }
        if (i == 84) {
            return true;
        }
        if (i == 66 && keyEvent.getAction() == 0 && (getInputType() & 131072) == 0) {
            S s = this.a;
            s.a(s.b(), false);
            return true;
        }
        if (i != 111 || keyEvent.getAction() != 0) {
            return super.onKeyPreIme(i, keyEvent);
        }
        S s2 = this.a;
        s2.a(s2.b(), true);
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            requestFocus();
            this.a.f();
        }
    }
}
