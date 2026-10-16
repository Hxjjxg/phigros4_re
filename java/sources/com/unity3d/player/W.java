package com.unity3d.player;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

/* JADX INFO: loaded from: classes.dex */
final class W extends EditText {
    final /* synthetic */ S a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    W(Context context, S s) {
        super(context);
        this.a = s;
    }

    @Override // android.widget.TextView
    public final void onEditorAction(int i) {
        if (i == 6) {
            S s = this.a;
            s.a(s.b(), false);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (keyEvent.getAction() == 1) {
                S s = this.a;
                s.a(s.b(), false);
            }
            return true;
        }
        if (i == 84) {
            return true;
        }
        if (i != 66 || keyEvent.getAction() != 0 || (getInputType() & 131072) != 0) {
            return super.onKeyPreIme(i, keyEvent);
        }
        S s2 = this.a;
        s2.a(s2.b(), false);
        return true;
    }

    @Override // android.widget.TextView
    protected void onSelectionChanged(int i, int i2) {
        super.onSelectionChanged(i, i2);
        this.a.b.reportSoftInputSelection(i, i2 - i);
    }
}
