package com.voxelbusters.nativeplugins.utilities;

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.widget.Toast;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.features.sharing.SharingHelper;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public class MiscUtilities {
    public static Comparator<ResolveInfo> resolveInfoComparator = new Comparator<ResolveInfo>() { // from class: com.voxelbusters.nativeplugins.utilities.MiscUtilities.1
        @Override // java.util.Comparator
        public int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
            boolean zIsSocialNetwork = SharingHelper.isSocialNetwork(resolveInfo.activityInfo.packageName);
            boolean zIsSocialNetwork2 = SharingHelper.isSocialNetwork(resolveInfo2.activityInfo.packageName);
            if ((zIsSocialNetwork && zIsSocialNetwork2) || zIsSocialNetwork) {
                return -1;
            }
            return zIsSocialNetwork2 ? 1 : 0;
        }
    };

    public static void toast(final String str, final int i) {
        NativePluginHelper.executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.utilities.MiscUtilities.2
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(NativePluginHelper.getCurrentContext(), str, i).show();
            }
        });
    }

    public static int convertDpToPixels(Activity activity, float f) {
        return Math.round(f * (activity.getResources().getDisplayMetrics().densityDpi / 160.0f));
    }
}
