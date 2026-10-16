package com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.ShortcutBadgeException;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ZukHomeBadger implements Badger {
    private final Uri CONTENT_URI = Uri.parse("content://com.android.badge/badge");

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Bundle bundle = new Bundle();
        bundle.putInt("app_badge_count", i);
        context.getContentResolver().call(this.CONTENT_URI, "setAppBadgeCount", (String) null, bundle);
    }

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public List<String> getSupportLaunchers() {
        return Collections.singletonList("com.zui.launcher");
    }
}
