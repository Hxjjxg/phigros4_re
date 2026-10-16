package com.tds.common.bridge;

import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: loaded from: classes.dex */
public class CommonBridge {
    public static String getDeviceId() {
        if (!GUIDHelper.INSTANCE.initialized()) {
            GUIDHelper.INSTANCE.init(UnityPlayer.currentActivity);
        }
        return GUIDHelper.INSTANCE.getUID();
    }

    public static int getDeviceType() {
        boolean zIsRunInSandbox = DeviceUtils.isRunInSandbox();
        if (DeviceUtils.isRunInCloud()) {
            return 2;
        }
        return zIsRunInSandbox ? 1 : 0;
    }
}
