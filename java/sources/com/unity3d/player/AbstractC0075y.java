package com.unity3d.player;

import android.view.inputmethod.InputMethodSubtype;

/* JADX INFO: renamed from: com.unity3d.player.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0075y {
    public static String a(InputMethodSubtype inputMethodSubtype) {
        return PlatformSupport.NOUGAT_SUPPORT ? inputMethodSubtype.getLanguageTag() : inputMethodSubtype.getLocale();
    }
}
