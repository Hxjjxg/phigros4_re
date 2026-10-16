package com.tds.common.notch.phone;

import android.view.Window;
import com.tds.common.notch.core.AbsNotchScreenSupport;
import com.tds.common.notch.helper.NotchStatusBarUtils;

/* JADX INFO: loaded from: classes.dex */
public class OppoNotchScreen extends AbsNotchScreenSupport {
    private final int DEFAULT_NOTCH_WIDTH = 324;

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        return 324;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        if (window == null) {
            return false;
        }
        return window.getContext().getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        if (isNotchScreen(window)) {
            return NotchStatusBarUtils.getStatusBarHeight(window.getContext());
        }
        return 0;
    }
}
