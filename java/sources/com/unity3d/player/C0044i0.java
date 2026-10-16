package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.widget.FrameLayout;

/* JADX INFO: renamed from: com.unity3d.player.i0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0044i0 extends FrameLayout {
    private C0037f a;
    private UnityPlayer b;
    private K c;

    public C0044i0(Context context, UnityPlayer unityPlayer) {
        int i;
        super(context);
        this.c = new K(context);
        this.b = unityPlayer;
        C0037f c0037f = new C0037f(context, unityPlayer);
        this.a = c0037f;
        c0037f.setId(context.getResources().getIdentifier("unitySurfaceView", "id", context.getPackageName()));
        if (a()) {
            this.a.getHolder().setFormat(-3);
            this.a.setZOrderOnTop(true);
            i = 0;
        } else {
            this.a.getHolder().setFormat(-1);
            i = -16777216;
        }
        setBackgroundColor(i);
        this.a.getHolder().addCallback(new SurfaceHolderCallbackC0042h0(this));
        this.a.setFocusable(true);
        this.a.setFocusableInTouchMode(true);
        this.a.setContentDescription(a(context));
        addView(this.a, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    private static String a(Context context) {
        return context.getResources().getString(context.getResources().getIdentifier("game_view_content_description", "string", context.getPackageName()));
    }

    private static boolean a() {
        Activity activity = UnityPlayer.currentActivity;
        if (activity == null) {
            return false;
        }
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z;
    }

    final void a(float f) {
        this.a.a(f);
    }

    public final void b() {
        K k = this.c;
        UnityPlayer unityPlayer = this.b;
        J j = k.b;
        if (j != null && j.getParent() != null) {
            unityPlayer.removeView(k.b);
        }
        this.c.b = null;
    }

    public final boolean c() {
        C0037f c0037f = this.a;
        return c0037f != null && c0037f.a();
    }
}
