package com.pingak9.nativepopup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.exifinterface.media.ExifInterface;
import com.taptap.sdk.constant.LoginConstants;
import com.unity3d.player.UnityPlayer;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class Bridge {
    static AlertDialog alertDialog;

    public static void ShowDialogNeutral(String str, String str2, String str3, String str4, String str5) {
        DismissCurrentAlert();
        AlertDialog alertDialogCreate = new AlertDialog.Builder(UnityPlayer.currentActivity).create();
        alertDialog = alertDialogCreate;
        alertDialogCreate.setTitle(str);
        alertDialog.setMessage(str2);
        alertDialog.setButton(str3, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogNeutral", "OnAcceptCallBack", LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            }
        });
        alertDialog.setButton2(str4, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogNeutral", "OnNeutralCallBack", LoginConstants.LOGIN_VERSION_RETURN_CODE_1);
            }
        });
        alertDialog.setButton3(str5, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogNeutral", "OnDeclineCallBack", ExifInterface.GPS_MEASUREMENT_2D);
            }
        });
        alertDialog.show();
    }

    private static AlertDialog CreateDialogConfirm(String str, String str2, String str3, String str4) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(UnityPlayer.currentActivity).create();
        alertDialogCreate.setTitle(str);
        alertDialogCreate.setMessage(str2);
        alertDialogCreate.setButton(str3, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogConfirm", "OnYesCallBack", LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            }
        });
        alertDialogCreate.setButton2(str4, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogConfirm", "OnNoCallBack", LoginConstants.LOGIN_VERSION_RETURN_CODE_1);
            }
        });
        return alertDialogCreate;
    }

    public static void ShowDialogConfirm(String str, String str2, String str3, String str4) {
        DismissCurrentAlert();
        AlertDialog alertDialogCreateDialogConfirm = CreateDialogConfirm(str, str2, str3, str4);
        alertDialog = alertDialogCreateDialogConfirm;
        alertDialogCreateDialogConfirm.show();
    }

    public static void ShowDialogConfirm(String str, String str2, String str3, String str4, boolean z) {
        DismissCurrentAlert();
        AlertDialog alertDialogCreateDialogConfirm = CreateDialogConfirm(str, str2, str3, str4);
        alertDialog = alertDialogCreateDialogConfirm;
        alertDialogCreateDialogConfirm.setCancelable(z);
        alertDialog.show();
    }

    public static void ShowDialogInfo(String str, String str2, String str3) {
        DismissCurrentAlert();
        AlertDialog alertDialogCreate = new AlertDialog.Builder(UnityPlayer.currentActivity).create();
        alertDialog = alertDialogCreate;
        alertDialogCreate.setTitle(str);
        alertDialog.setMessage(str2);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setButton(str3, new DialogInterface.OnClickListener() { // from class: com.pingak9.nativepopup.Bridge.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.UnitySendMessage("MobileDialogInfo", "OnOkCallBack", LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            }
        });
        alertDialog.show();
    }

    public static void DismissCurrentAlert() {
        AlertDialog alertDialog2 = alertDialog;
        if (alertDialog2 != null) {
            alertDialog2.hide();
        }
    }

    public static void ShowDatePicker(int i, int i2, int i3) {
        new DatePickerDialog(UnityPlayer.currentActivity, new DatePickerDialog.OnDateSetListener() { // from class: com.pingak9.nativepopup.Bridge.7
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker datePicker, int i4, int i5, int i6) {
                UnityPlayer.UnitySendMessage("MobileDateTimePicker", "PickerClosedEvent", String.format("%d-%d-%d %d:%d:%d", Integer.valueOf(i4), Integer.valueOf(i5 + 1), Integer.valueOf(i6), 0, 0, 0));
            }
        }, i, i2 - 1, i3).show();
    }

    public static void ShowTimePicker(int i, int i2) {
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(UnityPlayer.currentActivity, new TimePickerDialog.OnTimeSetListener() { // from class: com.pingak9.nativepopup.Bridge.8
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker timePicker, int i3, int i4) {
                int i5 = calendar.get(1);
                int i6 = calendar.get(5);
                int i7 = calendar.get(2);
                Calendar.getInstance();
                UnityPlayer.UnitySendMessage("MobileDateTimePicker", "PickerClosedEvent", String.format("%d-%d-%d %d:%d:%d", Integer.valueOf(i5), Integer.valueOf(i7 + 1), Integer.valueOf(i6), Integer.valueOf(i3), Integer.valueOf(i4), 0));
            }
        }, i, i2, true).show();
    }
}
