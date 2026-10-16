package com.tapsdk.antiaddictionui.utils;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class TapSDKHelper {
    public static String getTapTokenJSONStr() {
        try {
            Class<?> cls = Class.forName("com.taptap.sdk.AccessToken");
            Method declaredMethod = cls.getDeclaredMethod("getCurrentAccessToken", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, new Object[0]);
            Method declaredMethod2 = cls.getDeclaredMethod("toJsonString", new Class[0]);
            declaredMethod2.setAccessible(true);
            return (String) declaredMethod2.invoke(objInvoke, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
