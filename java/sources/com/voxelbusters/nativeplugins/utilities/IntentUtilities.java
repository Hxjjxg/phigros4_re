package com.voxelbusters.nativeplugins.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class IntentUtilities {
    public static Intent getGalleryIntent(String str) {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(str);
        return intent;
    }

    public static Intent getCameraIntent(Context context, Uri uri) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intent, 131072).iterator();
        while (it.hasNext()) {
            context.grantUriPermission(it.next().activityInfo.packageName, uri, 3);
        }
        FileUtility.grantPermissions(context, uri, 3);
        intent.putExtra("output", uri);
        return intent;
    }
}
