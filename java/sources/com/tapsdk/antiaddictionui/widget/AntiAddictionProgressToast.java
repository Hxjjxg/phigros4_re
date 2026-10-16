package com.tapsdk.antiaddictionui.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddictionui.R;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionProgressToast extends SafeDialogFragment {
    public static final String TAG = "AntiAddictionProgressToast";
    private static final String TOAST_CANCEL = "TOAST_CANCEL";
    private static final String TOAST_LOAD = "TOAST_LOADING";
    private static final String TOAST_MESSAGE = "TOAST_MESSAGE";
    private volatile Animation mAnimation;

    public static AntiAddictionProgressToast newInstance(String str, boolean z) {
        AntiAddictionProgressToast antiAddictionProgressToast = new AntiAddictionProgressToast();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TOAST_LOAD, false);
        bundle.putBoolean(TOAST_CANCEL, z);
        bundle.putString(TOAST_MESSAGE, str);
        antiAddictionProgressToast.setArguments(bundle);
        return antiAddictionProgressToast;
    }

    public static AntiAddictionProgressToast newInstance(boolean z) {
        AntiAddictionProgressToast antiAddictionProgressToast = new AntiAddictionProgressToast();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TOAST_LOAD, true);
        bundle.putBoolean(TOAST_CANCEL, z);
        antiAddictionProgressToast.setArguments(bundle);
        return antiAddictionProgressToast;
    }

    public static AntiAddictionProgressToast newInstance() {
        return newInstance(false);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getDialog() == null || getActivity() == null) {
            return null;
        }
        return layoutInflater.inflate(R.layout.antiaddiction_loading_toast, viewGroup, false);
    }

    @Override // com.tapsdk.antiaddictionui.widget.SafeDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null || getArguments() == null || getActivity() == null || getActivity().getWindowManager() == null || dialog.getWindow() == null) {
            return;
        }
        HoloThemeHelper.fixHoloDialogBlueLine(dialog);
        dialog.getWindow().clearFlags(2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(getArguments().getBoolean(TOAST_CANCEL));
        dialog.setCanceledOnTouchOutside(getArguments().getBoolean(TOAST_CANCEL));
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.tapsdk.antiaddictionui.widget.AntiAddictionProgressToast.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                boolean z = AntiAddictionProgressToast.this.getArguments().getBoolean(AntiAddictionProgressToast.TOAST_CANCEL);
                if (i != 4) {
                    return false;
                }
                if (!z) {
                    return true;
                }
                AntiAddictionProgressToast.this.dismissAllowingStateLoss();
                return true;
            }
        });
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
                window.setAttributes(attributes);
            }
            decorView.setSystemUiVisibility(1024);
        }
        dialog.getWindow().setAttributes(attributes);
        dialog.getWindow().setFlags(1024, 1024);
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(bundle);
        dialogOnCreateDialog.getWindow().requestFeature(1);
        return dialogOnCreateDialog;
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AntiToastManager.instance().resetToastReference(this);
        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        window.setFlags(1024, 1024);
        window.getDecorView().setPadding(0, 0, 0, 0);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Bundle arguments = getArguments();
        if (arguments == null || getDialog() == null || getActivity() == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_toast_loading);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fl_toast_loading);
        if (!arguments.getBoolean(TOAST_LOAD)) {
            frameLayout.setVisibility(4);
            return;
        }
        frameLayout.setVisibility(0);
        this.mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.antiaddiction_anim_loading);
        if (this.mAnimation != null) {
            this.mAnimation.setInterpolator(new LinearInterpolator());
            imageView.setImageResource(R.drawable.antiaddiction_loading);
            imageView.startAnimation(this.mAnimation);
        }
    }

    @Override // android.app.DialogFragment
    public void dismiss() {
        try {
            super.dismiss();
            if (this.mAnimation != null) {
                this.mAnimation.cancel();
                this.mAnimation = null;
            }
        } catch (Exception e) {
            AntiAddictionLogger.e(e.getMessage());
        }
    }

    @Override // android.app.DialogFragment
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        try {
            if (this.mAnimation != null) {
                this.mAnimation.cancel();
                this.mAnimation = null;
            }
        } catch (Exception e) {
            AntiAddictionLogger.e(e.getMessage());
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        dismissAllowingStateLoss();
    }
}
