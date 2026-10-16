package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
class C {
    protected Runnable b;
    protected com.unity3d.player.a.c a = null;
    protected boolean c = true;

    protected C(Runnable runnable) {
        this.b = runnable;
    }

    protected void registerOnBackPressedCallback() {
        if (this.a != null) {
            return;
        }
        this.a = new B(this.b);
    }

    protected void unregisterOnBackPressedCallback() {
        this.a = null;
    }
}
