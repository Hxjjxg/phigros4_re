package com.tapsdk.antiaddictionui.widget;

import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.tapsdk.antiaddiction.entities.Prompt;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddictionui.callback.AuthorizationResultCallback;
import com.tapsdk.antiaddictionui.utils.UIUtil;
import com.tds.common.utils.NavigationBarUtil;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbsBaseDialogFragment extends SafeDialogFragment {
    protected static final int GAME_AUTHORIZATION_TYPE_3 = 3;
    protected static final int GAME_AUTHORIZATION_TYPE_4 = 4;
    protected static final int GAME_AUTHORIZATION_TYPE_5 = 5;
    protected static final int TAP_AUTHORIZATION_TYPE_0 = 0;
    protected static final int TAP_AUTHORIZATION_TYPE_1 = 1;
    protected static final int TAP_AUTHORIZATION_TYPE_2 = 2;
    public final String TAG = getClass().getSimpleName();
    protected List<Prompt> authIdentifyPromptList;
    protected AuthorizationResultCallback mCallback;

    public abstract void handleArgumentsData();

    public abstract void initView(View view);

    public abstract int layoutResId();

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Window window;
        Dialog dialog = getDialog();
        if (dialog != null && (window = dialog.getWindow()) != null) {
            window.setFlags(16777216, 16777216);
        }
        handleArgumentsData();
        if (getArguments() == null) {
            dismissAllowingStateLoss();
            return null;
        }
        View viewInflate = layoutInflater.inflate(layoutResId(), viewGroup, false);
        if (viewInflate != null) {
            initView(viewInflate);
        }
        return viewInflate;
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.tapsdk.antiaddictionui.widget.SafeDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null || getActivity() == null || getActivity().getWindowManager() == null || dialog.getWindow() == null) {
            return;
        }
        int[] screenSize = UIUtil.getScreenSize(getActivity());
        AntiAddictionLogger.d(" screen width = " + screenSize[0] + " height = " + screenSize[1]);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = UIUtil.dp2px(getActivity(), 335.0f);
        attributes.height = UIUtil.dp2px(getActivity(), 320.0f);
        attributes.width = Math.min(attributes.width, screenSize[0]);
        attributes.height = Math.min(attributes.height, screenSize[1]);
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
                window.setAttributes(attributes);
            }
            decorView.setSystemUiVisibility(1024);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        NavigationBarUtil.hideNavigationBar(getDialog().getWindow());
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(bundle);
        dialogOnCreateDialog.getWindow().requestFeature(1);
        return dialogOnCreateDialog;
    }

    @Override // android.app.DialogFragment
    public void show(FragmentManager fragmentManager, String str) {
        try {
            super.show(fragmentManager, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
