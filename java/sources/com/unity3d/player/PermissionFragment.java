package com.unity3d.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class PermissionFragment extends Fragment {
    public static final String PERMISSION_NAMES = "PermissionNames";
    public static final int UNITY_PERMISSION_REQUEST_CODE = 96489;
    private final Activity m_Activity;
    private final Looper m_Looper;
    private final IPermissionRequestCallbacks m_ResultCallbacks;

    public PermissionFragment() {
        this.m_ResultCallbacks = null;
        this.m_Activity = null;
        this.m_Looper = null;
    }

    public PermissionFragment(Activity activity, IPermissionRequestCallbacks iPermissionRequestCallbacks) {
        this.m_ResultCallbacks = iPermissionRequestCallbacks;
        this.m_Activity = activity;
        this.m_Looper = Looper.myLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportAllDenied(String[] strArr) {
        for (String str : strArr) {
            this.m_ResultCallbacks.onPermissionDenied(str);
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestPermissions(getArguments().getStringArray(PERMISSION_NAMES), UNITY_PERMISSION_REQUEST_CODE);
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 96489) {
            return;
        }
        if (strArr.length != 0) {
            for (int i2 = 0; i2 < strArr.length && i2 < iArr.length; i2++) {
                int i3 = iArr[i2];
                IPermissionRequestCallbacks iPermissionRequestCallbacks = this.m_ResultCallbacks;
                if (iPermissionRequestCallbacks != null && this.m_Activity != null && this.m_Looper != null) {
                    if (iPermissionRequestCallbacks instanceof UnityPermissions.ModalWaitForPermissionResponse) {
                        iPermissionRequestCallbacks.onPermissionGranted(strArr[i2]);
                    } else {
                        String str = strArr[i2];
                        if (str == null) {
                            str = "<null>";
                        }
                        new Handler(this.m_Looper).post(new I(this.m_ResultCallbacks, str, iArr[i2], this.m_Activity.shouldShowRequestPermissionRationale(str)));
                    }
                }
            }
        } else if (this.m_ResultCallbacks != null && this.m_Activity != null && this.m_Looper != null) {
            String[] stringArray = getArguments().getStringArray(PERMISSION_NAMES);
            if (this.m_ResultCallbacks instanceof UnityPermissions.ModalWaitForPermissionResponse) {
                reportAllDenied(stringArray);
            } else {
                new Handler(this.m_Looper).post(new H(this, stringArray));
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this);
        fragmentTransactionBeginTransaction.commit();
    }
}
