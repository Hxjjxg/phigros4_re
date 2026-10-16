package com.unity3d.player;

import android.view.KeyEvent;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
final class Q implements TextView.OnEditorActionListener {
    final /* synthetic */ S a;

    Q(S s) {
        this.a = s;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == 6) {
            S s = this.a;
            s.a(s.b(), false);
        }
        return false;
    }
}
