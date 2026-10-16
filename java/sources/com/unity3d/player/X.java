package com.unity3d.player;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

/* JADX INFO: loaded from: classes.dex */
final class X extends S {
    private boolean h;
    private Handler i;
    private Runnable j;

    public X(Context context, UnityPlayer unityPlayer) {
        super(context, unityPlayer);
        this.h = false;
    }

    @Override // com.unity3d.player.S
    public final void a(boolean z) {
        EditText editText;
        int i;
        this.d = z;
        if (z) {
            editText = this.c;
            i = 4;
        } else {
            editText = this.c;
            i = 0;
        }
        editText.setVisibility(i);
        this.c.invalidate();
        this.c.requestLayout();
    }

    @Override // com.unity3d.player.S
    public final void c() {
        Runnable runnable;
        Handler handler = this.i;
        if (handler != null && (runnable = this.j) != null) {
            handler.removeCallbacks(runnable);
        }
        this.b.removeView(this.c);
        this.h = false;
        invokeOnClose();
    }

    @Override // com.unity3d.player.S
    protected EditText createEditText(S s) {
        return new W(this.a, s);
    }

    @Override // com.unity3d.player.S
    public final boolean d() {
        return false;
    }

    @Override // com.unity3d.player.S
    public final void e() {
        if (this.h) {
            return;
        }
        this.b.addView(this.c);
        this.b.bringChildToFront(this.c);
        this.c.setVisibility(0);
        this.c.requestFocus();
        this.j = new V(this);
        Handler handler = new Handler(Looper.getMainLooper());
        this.i = handler;
        handler.postDelayed(this.j, 400L);
        this.h = true;
    }
}
