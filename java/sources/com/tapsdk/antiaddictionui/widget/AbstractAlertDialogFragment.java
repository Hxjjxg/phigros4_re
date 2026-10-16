package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tapsdk.antiaddictionui.R;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;
import com.tapsdk.antiaddictionui.utils.UIUtil;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractAlertDialogFragment extends SafeDialogFragment {
    protected Activity mActivity;

    public interface AlertClickCallback {
        void onLeftClick(String str);

        void onRightClick(String str);
    }

    public abstract View getContentView();

    public abstract int[] getLayoutParams();

    public abstract Event leftEvent();

    public abstract Event rightEvent();

    public static class Event {
        View.OnClickListener listener;
        String text;

        public Event(String str, View.OnClickListener onClickListener) {
            this.text = str;
            this.listener = onClickListener;
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override // com.tapsdk.antiaddictionui.widget.SafeDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        Dialog dialog = getDialog();
        if (dialog == null || activity == null || activity.getWindowManager() == null || dialog.getWindow() == null) {
            return;
        }
        dialog.getWindow().setLayout(getLayoutParams()[0], -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.antiaddictionui_view_alert, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getArguments() == null) {
            dismissAllowingStateLoss();
        } else {
            initView(view);
        }
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_alert_negative);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_alert_positive);
        if (leftEvent() != null) {
            textView.setText(leftEvent().text);
            textView.setOnClickListener(leftEvent().listener);
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        if (rightEvent() != null) {
            textView2.setText(rightEvent().text);
            textView2.setOnClickListener(rightEvent().listener);
            textView2.setVisibility(0);
        } else {
            textView2.setVisibility(8);
        }
        if (!(view instanceof LinearLayout) || getContentView() == null) {
            return;
        }
        ((LinearLayout) view).addView(getContentView(), 0, new ViewGroup.LayoutParams(-1, UIUtil.dp2px(getActivity(), -2.0f)));
    }

    protected void safeToast(String str) {
        ActivityUtils.isActivityAlive(getActivity());
    }

    protected void safeToastError(Throwable th) {
        ActivityUtils.isActivityAlive(getActivity());
    }
}
