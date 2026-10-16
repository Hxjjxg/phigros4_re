package com.unity3d.player;

import android.widget.EditText;

/* JADX INFO: renamed from: com.unity3d.player.r0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0062r0 implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ UnityPlayer b;

    RunnableC0062r0(UnityPlayer unityPlayer, String str) {
        this.b = unityPlayer;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        EditText editText;
        S s = this.b.mSoftInput;
        if (s == null || (str = this.a) == null || (editText = s.c) == null) {
            return;
        }
        editText.setText(str);
        s.c.setSelection(str.length());
    }
}
