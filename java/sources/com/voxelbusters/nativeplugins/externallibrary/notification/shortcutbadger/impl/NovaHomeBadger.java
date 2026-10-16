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
public class NovaHomeBadger implements Badger {
    private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tag", componentName.getPackageName() + "/" + componentName.getClassName());
        contentValues.put("count", Integer.valueOf(i));
        context.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
    }

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.teslacoilsw.launcher");
    }
}
