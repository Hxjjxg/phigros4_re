package com.voxelbusters.common;

import android.content.Context;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: loaded from: classes.dex */
public class Configuration {
    public static Context getContext() {
        return UnityPlayer.currentActivity;
    }
}
