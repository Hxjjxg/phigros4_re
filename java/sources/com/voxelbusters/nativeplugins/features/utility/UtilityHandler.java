package com.voxelbusters.nativeplugins.features.utility;

import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.ShortcutBadger;

/* JADX INFO: loaded from: classes.dex */
public class UtilityHandler {
    private static UtilityHandler INSTANCE;

    public static UtilityHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UtilityHandler();
        }
        return INSTANCE;
    }

    private UtilityHandler() {
    }

    public void setApplicationIconBadgeNumber(int i) {
        ShortcutBadger.applyCount(NativePluginHelper.getCurrentContext(), i);
    }
}
