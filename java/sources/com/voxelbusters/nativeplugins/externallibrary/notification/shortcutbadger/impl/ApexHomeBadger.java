package com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.ShortcutBadgeException;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.util.BroadcastHelper;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ApexHomeBadger implements Badger {
    private static final String CLASS = "class";
    private static final String COUNT = "count";
    private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String PACKAGENAME = "package";

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, componentName.getPackageName());
        intent.putExtra("count", i);
        intent.putExtra(CLASS, componentName.getClassName());
        if (BroadcastHelper.canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
            return;
        }
        throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
    }

    @Override // com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.Badger
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.anddoes.launcher");
    }
}
