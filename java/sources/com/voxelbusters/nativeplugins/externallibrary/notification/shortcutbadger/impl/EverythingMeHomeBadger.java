package com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.ShortcutBadgeException;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EverythingMeHomeBadger implements Badger {
    private static final String COLUMN_ACTIVITY_NAME = "activity_name";
    private static final String COLUMN_COUNT = "count";
    private static final String COLUMN_PACKAGE_NAME = "package_name";
    private static final String CONTENT_URI = "content://me.everything.badger/apps";

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PACKAGE_NAME, componentName.getPackageName());
        contentValues.put(COLUMN_ACTIVITY_NAME, componentName.getClassName());
        contentValues.put("count", Integer.valueOf(i));
        context.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
    }

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public List<String> getSupportLaunchers() {
        return Arrays.asList("me.everything.launcher");
    }
}
