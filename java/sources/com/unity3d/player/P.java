package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
final class P extends RelativeLayout {
    protected Button a;
    protected EditText b;
    protected Context c;
    protected Rect d;
    protected Rect e;

    public P(Context context, EditText editText) {
        super(context);
        this.b = editText;
        this.c = context;
        this.d = new Rect(16, 16, 16, 16);
        this.e = new Rect(0, 0, 0, 0);
        createUI();
        setBackgroundColor(-1);
    }

    protected void createUI() {
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Button button = new Button(this.c);
        this.a = button;
        button.setId(View.generateViewId());
        this.a.setText(this.c.getResources().getIdentifier("ok", "string", "android"));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(11);
        this.a.setLayoutParams(layoutParams);
        this.a.setBackgroundColor(0);
        addView(this.a);
        this.b.setId(View.generateViewId());
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(0, this.a.getId());
        this.b.setLayoutParams(layoutParams2);
        addView(this.b);
        Rect rect = this.d;
        setPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.a.setOnClickListener(onClickListener);
    }
}
