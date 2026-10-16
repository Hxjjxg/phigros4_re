package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.digits.sdk.vcard.VCardConfig;

/* JADX INFO: loaded from: classes.dex */
final class U extends Dialog implements View.OnClickListener {
    protected Context a;
    protected UnityPlayer b;
    protected P c;
    protected S d;
    private C e;

    public U(Context context, UnityPlayer unityPlayer) {
        super(context);
        this.c = null;
        this.d = null;
        this.e = null;
        this.a = context;
        this.b = unityPlayer;
    }

    public final Rect a() {
        Rect rect = new Rect();
        this.b.getWindowVisibleDisplayFrame(rect);
        int[] iArr = new int[2];
        this.b.getLocationOnScreen(iArr);
        Point point = new Point(rect.left - iArr[0], rect.height() - this.c.getHeight());
        Point point2 = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point2);
        int height = this.b.getHeight();
        int i = height - point2.y;
        int i2 = height - point.y;
        int height2 = this.c.getHeight() + i;
        UnityPlayer unityPlayer = this.b;
        if (i2 != height2) {
            unityPlayer.reportSoftInputIsVisible(true);
        } else {
            unityPlayer.reportSoftInputIsVisible(false);
        }
        return new Rect(point.x, point.y, this.c.getWidth(), i2);
    }

    public final void a(S s, boolean z, boolean z2) {
        this.d = s;
        Window window = getWindow();
        window.requestFeature(1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        b();
        this.c = createSoftInputView(this.d.c);
        window.setLayout(-1, -2);
        window.clearFlags(2);
        window.clearFlags(VCardConfig.FLAG_CONVERT_PHONETIC_NAME_STRINGS);
        window.clearFlags(VCardConfig.FLAG_APPEND_TYPE_PARAM);
        if (!z2) {
            window.addFlags(32);
            window.addFlags(262144);
        }
        a(z);
        getWindow().setSoftInputMode(5);
        this.e = D.a(this, 1000000, new T(this));
    }

    public final void a(boolean z) {
        P p = this.c;
        if (z) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) p.b.getLayoutParams();
            layoutParams.height = 1;
            p.b.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) p.a.getLayoutParams();
            layoutParams2.height = 1;
            p.a.setLayoutParams(layoutParams2);
            Rect rect = p.e;
            p.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            p.setVisibility(4);
        } else {
            p.setVisibility(0);
            Rect rect2 = p.d;
            p.setPadding(rect2.left, rect2.top, rect2.right, rect2.bottom);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) p.b.getLayoutParams();
            layoutParams3.height = -2;
            p.b.setLayoutParams(layoutParams3);
            RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) p.a.getLayoutParams();
            layoutParams4.height = -2;
            p.a.setLayoutParams(layoutParams4);
        }
        p.invalidate();
        p.requestLayout();
    }

    public final void b() {
        ColorDrawable colorDrawable = new ColorDrawable(0);
        if (!PlatformSupport.VANILLA_ICE_CREAM_SUPPORT) {
            getWindow().setBackgroundDrawable(colorDrawable);
            return;
        }
        Insets insets = this.b.getActivity().getWindow().getDecorView().getRootWindowInsets().getInsets(WindowInsets.Type.displayCutout());
        getWindow().setBackgroundDrawable(new InsetDrawable((Drawable) colorDrawable, insets.left, insets.top, insets.right, 0));
    }

    protected P createSoftInputView(EditText editText) {
        P p = new P(this.a, editText);
        p.a.setOnClickListener(this);
        setContentView(p);
        return p;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.d.d() || !(motionEvent.getAction() == 4 || this.d.d)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        Runnable runnable;
        C c = this.e;
        if (c != null) {
            com.unity3d.player.a.c cVar = c.a;
            if (!(cVar != null) || (runnable = ((B) cVar).a) == null) {
                return;
            }
            runnable.run();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        S s = this.d;
        s.a(s.b(), false);
    }

    @Override // android.app.Dialog
    public final void onStop() {
        C c = this.e;
        if (c != null) {
            c.unregisterOnBackPressedCallback();
            this.e = null;
        }
        super.onStop();
    }
}
