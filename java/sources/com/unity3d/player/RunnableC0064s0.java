package com.unity3d.player;

import android.text.InputFilter;
import android.widget.EditText;

/* JADX INFO: renamed from: com.unity3d.player.s0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0064s0 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ UnityPlayer b;

    RunnableC0064s0(UnityPlayer unityPlayer, int i) {
        this.b = unityPlayer;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        S s = this.b.mSoftInput;
        if (s != null) {
            int i = this.a;
            EditText editText = s.c;
            if (editText != null) {
                if (i > 0) {
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
                } else {
                    editText.setFilters(new InputFilter[0]);
                }
            }
        }
    }
}
