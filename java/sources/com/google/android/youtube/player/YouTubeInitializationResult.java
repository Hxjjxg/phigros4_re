package com.google.android.youtube.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.m;
import com.google.android.youtube.player.internal.y;
import com.google.android.youtube.player.internal.z;

/* JADX INFO: loaded from: classes.dex */
public enum YouTubeInitializationResult {
    SUCCESS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    SERVICE_MISSING,
    SERVICE_VERSION_UPDATE_REQUIRED,
    SERVICE_DISABLED,
    SERVICE_INVALID,
    ERROR_CONNECTING_TO_SERVICE,
    CLIENT_LIBRARY_UPDATE_REQUIRED,
    NETWORK_ERROR,
    DEVELOPER_KEY_INVALID,
    INVALID_APPLICATION_SIGNATURE;

    /* JADX INFO: renamed from: com.google.android.youtube.player.YouTubeInitializationResult$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[YouTubeInitializationResult.values().length];
            a = iArr;
            try {
                iArr[YouTubeInitializationResult.SERVICE_MISSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[YouTubeInitializationResult.SERVICE_DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static final class a implements DialogInterface.OnClickListener {
        private final Activity a;
        private final Intent b;
        private final int c;

        public a(Activity activity, Intent intent, int i) {
            this.a = (Activity) ab.a(activity);
            this.b = (Intent) ab.a(intent);
            this.c = ((Integer) ab.a(Integer.valueOf(i))).intValue();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            try {
                this.a.startActivityForResult(this.b, this.c);
                dialogInterface.dismiss();
            } catch (ActivityNotFoundException e) {
                y.a("Can't perform resolution for YouTubeInitalizationError", e);
            }
        }
    }

    public final Dialog getErrorDialog(Activity activity, int i) {
        return getErrorDialog(activity, i, null);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0026  */
    public final Dialog getErrorDialog(Activity activity, int i, DialogInterface.OnCancelListener onCancelListener) {
        Intent intentB;
        AlertDialog.Builder message;
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        int i2 = AnonymousClass1.a[ordinal()];
        if (i2 == 1) {
            intentB = z.b(z.a(activity));
        } else if (i2 == 2) {
            intentB = z.a(z.a(activity));
        } else if (i2 != 3) {
            intentB = null;
        } else {
            intentB = z.b(z.a(activity));
        }
        a aVar = new a(activity, intentB, i);
        m mVar = new m(activity);
        int i3 = AnonymousClass1.a[ordinal()];
        if (i3 == 1) {
            message = builder.setTitle(mVar.b).setMessage(mVar.c);
            str = mVar.d;
        } else if (i3 == 2) {
            message = builder.setTitle(mVar.e).setMessage(mVar.f);
            str = mVar.g;
        } else {
            if (i3 != 3) {
                String strValueOf = String.valueOf(name());
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unexpected errorReason: ".concat(strValueOf) : new String("Unexpected errorReason: "));
            }
            message = builder.setTitle(mVar.h).setMessage(mVar.i);
            str = mVar.j;
        }
        return message.setPositiveButton(str, aVar).create();
    }

    public final boolean isUserRecoverableError() {
        int i = AnonymousClass1.a[ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
