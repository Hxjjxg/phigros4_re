package com.voxelbusters.nativeplugins.helpers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;
import com.voxelbusters.nativeplugins.helpers.interfaces.IPermissionRequestCallback;
import com.voxelbusters.nativeplugins.utilities.PermissionUtility;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class PermissionRequestFragment extends Fragment {
    public static final String MESSAGE_INFO = "MessageInfo";
    public static final String PERMISSION_LIST = "PermissionList";
    private String infoMessage;
    private IPermissionRequestCallback m_callback;
    final int PERMISSIONS_REQUEST_CODE = 111;
    HashMap<String, Boolean> beforeRequestRationale = new HashMap<>();
    HashMap<String, Boolean> afterRequestRationale = new HashMap<>();

    public void setCallback(IPermissionRequestCallback iPermissionRequestCallback) {
        this.m_callback = iPermissionRequestCallback;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String[] stringArray = getArguments().getStringArray(PERMISSION_LIST);
        this.infoMessage = getArguments().getString(MESSAGE_INFO);
        updateRequestRationaleStatus(this.beforeRequestRationale, stringArray);
        requestPermissions(stringArray, 111);
        this.beforeRequestRationale.containsValue(true);
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 111) {
            if (!PermissionUtility.verifyPermissions(iArr)) {
                updateRequestRationaleStatus(this.afterRequestRationale, strArr);
                boolean z = false;
                for (String str : strArr) {
                    boolean zBooleanValue = this.beforeRequestRationale.get(str).booleanValue();
                    boolean zBooleanValue2 = this.afterRequestRationale.get(str).booleanValue();
                    z = (zBooleanValue && !zBooleanValue2) || !(zBooleanValue || zBooleanValue2);
                    if (z) {
                        break;
                    }
                }
                if (z) {
                    if (strArr.length == 1) {
                        Toast.makeText(getActivity(), "Please enable the permission from application settings.", 0).show();
                    } else if (strArr.length > 1) {
                        Toast.makeText(getActivity(), "Please enable the permissions from application settings.", 0).show();
                    }
                }
                IPermissionRequestCallback iPermissionRequestCallback = this.m_callback;
                if (iPermissionRequestCallback != null) {
                    iPermissionRequestCallback.onPermissionDeny();
                }
            } else {
                IPermissionRequestCallback iPermissionRequestCallback2 = this.m_callback;
                if (iPermissionRequestCallback2 != null) {
                    iPermissionRequestCallback2.onPermissionGrant();
                }
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this);
        fragmentTransactionBeginTransaction.commit();
    }

    void updateRequestRationaleStatus(HashMap<String, Boolean> map, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            map.put(strArr[i], Boolean.valueOf(shouldShowRequestPermissionRationale(strArr[i])));
        }
    }
}
