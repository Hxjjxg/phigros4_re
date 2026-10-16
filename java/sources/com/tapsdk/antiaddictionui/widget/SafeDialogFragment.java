package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;

/* JADX INFO: loaded from: classes.dex */
public class SafeDialogFragment extends DialogFragment {
    @Override // android.app.DialogFragment, android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        try {
            super.onActivityCreated(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        adapterPhoneCompatibility();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        adapterBlackScreenOnUnity();
    }

    private void adapterPhoneCompatibility() {
        Window window;
        View decorView;
        Dialog dialog = getDialog();
        if (dialog == null || (window = dialog.getWindow()) == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.setPadding(0, 0, 0, 0);
    }

    private void adapterBlackScreenOnUnity() {
        Activity activity = getActivity();
        if (ActivityUtils.isActivityAlive(activity)) {
            activity.onWindowFocusChanged(true);
        }
    }
}
