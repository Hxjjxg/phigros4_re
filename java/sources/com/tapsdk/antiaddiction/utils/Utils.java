package com.tapsdk.antiaddiction.utils;

import android.text.TextUtils;
import com.tapsdk.antiaddiction.entities.FourTuple;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static <T> boolean checkIsNull(T t) {
        return t == null;
    }

    public static <T> boolean checkNotNull(T t) {
        return t != null;
    }

    public static void printMap(Map<Integer, FourTuple<String, String, String, String>> map) {
        for (Map.Entry<Integer, FourTuple<String, String, String, String>> entry : map.entrySet()) {
            AntiAddictionLogger.d(entry.getKey() + "," + entry.getValue());
        }
    }

    public static String parseErrorMessage(Throwable th) {
        if (!TextUtils.isEmpty(th.getMessage())) {
            return th.getMessage();
        }
        return th.getClass().getSimpleName();
    }

    public static String getMapStr(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("start log map\n");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
