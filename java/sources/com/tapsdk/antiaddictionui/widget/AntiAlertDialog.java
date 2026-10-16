package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.utils.ActivityUtil;
import com.tapsdk.antiaddictionui.Callback0;

/* JADX INFO: loaded from: classes.dex */
public class AntiAlertDialog {
    public static void openAlert(Activity activity, String str, String str2, String str3, final Callback0 callback0, boolean z) {
        if (ActivityUtil.isActivityNotAlive(activity)) {
            return;
        }
        AlertDialog.Builder positiveButton = new AlertDialog.Builder(activity).setPositiveButton(str3, new DialogInterface.OnClickListener() { // from class: com.tapsdk.antiaddictionui.widget.AntiAlertDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                callback0.onResult();
                dialogInterface.dismiss();
            }
        });
        if (!TextUtils.isEmpty(str)) {
            positiveButton.setTitle(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            positiveButton.setMessage(str2);
        }
        positiveButton.setCancelable(z);
        positiveButton.create().show();
    }
}
