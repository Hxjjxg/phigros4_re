package com.voxelbusters.nativeplugins.utilities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.core.content.PermissionChecker;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.helpers.PermissionRequestFragment;
import com.voxelbusters.nativeplugins.helpers.interfaces.IPermissionRequestCallback;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class PermissionUtility {
    public static boolean hasPermission(String str) {
        return PermissionChecker.checkSelfPermission(NativePluginHelper.getCurrentActivity(), str) == 0;
    }

    public static boolean verifyPermissions(int[] iArr) {
        if (iArr.length < 1) {
            return false;
        }
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermission(String str, String str2, IPermissionRequestCallback iPermissionRequestCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        requestPermissions(arrayList, str2, iPermissionRequestCallback);
    }

    public static void requestPermissions(ArrayList<String> arrayList, String str, IPermissionRequestCallback iPermissionRequestCallback) {
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : arrayList) {
            if (!hasPermission(str2)) {
                arrayList2.add(str2);
            }
        }
        if (arrayList2.size() == 0) {
            if (iPermissionRequestCallback != null) {
                iPermissionRequestCallback.onPermissionGrant();
                return;
            }
            return;
        }
        PermissionRequestFragment permissionRequestFragment = new PermissionRequestFragment();
        permissionRequestFragment.setCallback(iPermissionRequestCallback);
        Activity currentActivity = NativePluginHelper.getCurrentActivity();
        Bundle bundle = new Bundle();
        bundle.putStringArray(PermissionRequestFragment.PERMISSION_LIST, (String[]) arrayList2.toArray(new String[arrayList.size()]));
        bundle.putString(PermissionRequestFragment.MESSAGE_INFO, str);
        permissionRequestFragment.setArguments(bundle);
        FragmentTransaction fragmentTransactionBeginTransaction = currentActivity.getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.add(0, permissionRequestFragment);
        fragmentTransactionBeginTransaction.commit();
    }
}
