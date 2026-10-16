package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.utilities.Debug;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class BaseGameUtils {
    public static void showAlert(Activity activity, String str) {
        new AlertDialog.Builder(activity).setMessage(str).setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null).create().show();
    }

    public static boolean resolveConnectionFailure(Activity activity, GoogleApiClient googleApiClient, ConnectionResult connectionResult, int i, String str) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, i);
                return true;
            } catch (IntentSender.SendIntentException unused) {
                googleApiClient.connect();
                return false;
            }
        }
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), activity, i);
        if (errorDialog != null) {
            errorDialog.show();
        } else {
            showAlert(activity, str);
        }
        return false;
    }

    public static boolean verifySampleSetup(Activity activity, int... iArr) {
        boolean z;
        StringBuilder sb = new StringBuilder("The following set up problems were found:\n\n");
        if (activity.getPackageName().startsWith("com.google.example.games")) {
            sb.append("- Package name cannot be com.google.*. You need to change the sample's package name to your own package.\n");
            z = true;
        } else {
            z = false;
        }
        for (int i : iArr) {
            if (activity.getString(i).toLowerCase(Locale.ENGLISH).contains("replaceme")) {
                sb.append("- You must replace all placeholder IDs in the ids.xml file by your project's IDs.\n");
                z = true;
                break;
            }
        }
        if (!z) {
            return true;
        }
        sb.append("\n\nThese problems may prevent the app from working properly.");
        showAlert(activity, sb.toString());
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void showActivityResultError(Activity activity, int i, int i2, int i3) {
        Dialog dialogMakeSimpleDialog;
        if (activity == 0) {
            Log.e("BaseGameUtils", "*** No Activity. Can't show failure dialog!");
            return;
        }
        Debug.log(CommonDefines.GAME_SERVICES_TAG, "Activity :" + activity + " Request Code : " + i + " Error Description : " + i3);
        switch (i2) {
            case GamesActivityResultCodes.RESULT_SIGN_IN_FAILED /* 10002 */:
                dialogMakeSimpleDialog = makeSimpleDialog(activity, activity.getString(com.voxelbusters.androidnativeplugin.R.string.gameservices_sign_in_failed));
                break;
            case GamesActivityResultCodes.RESULT_LICENSE_FAILED /* 10003 */:
                dialogMakeSimpleDialog = makeSimpleDialog(activity, activity.getString(com.voxelbusters.androidnativeplugin.R.string.gameservices_license_failed));
                break;
            case GamesActivityResultCodes.RESULT_APP_MISCONFIGURED /* 10004 */:
                dialogMakeSimpleDialog = makeSimpleDialog(activity, activity.getString(com.voxelbusters.androidnativeplugin.R.string.gameservices_app_misconfigured));
                break;
            default:
                dialogMakeSimpleDialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity), i);
                if (dialogMakeSimpleDialog == null) {
                    Log.e("BaseGamesUtils", "No standard error dialog available. Making fallback dialog.");
                    dialogMakeSimpleDialog = makeSimpleDialog(activity, activity.getString(i3));
                }
                break;
        }
        if (dialogMakeSimpleDialog != null) {
            dialogMakeSimpleDialog.setOnDismissListener((DialogInterface.OnDismissListener) activity);
            dialogMakeSimpleDialog.show();
        }
    }

    public static Dialog makeSimpleDialog(Activity activity, String str) {
        return new AlertDialog.Builder(activity).setMessage(str).setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
    }

    public static Dialog makeSimpleDialog(Activity activity, String str, String str2) {
        return new AlertDialog.Builder(activity).setTitle(str).setMessage(str2).setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
    }
}
