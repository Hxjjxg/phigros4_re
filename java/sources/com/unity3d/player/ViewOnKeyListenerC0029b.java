package com.unity3d.player;

import android.view.KeyEvent;
import android.view.View;

/* JADX INFO: renamed from: com.unity3d.player.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class ViewOnKeyListenerC0029b implements View.OnKeyListener {
    ViewOnKeyListenerC0029b() {
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        return i == 4 && keyEvent.getAction() == 1;
    }
}
