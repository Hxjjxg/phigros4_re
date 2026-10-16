package com.tapsdk.antiaddiction.utils;

import android.content.Context;
import com.tapsdk.antiaddiction.skynet.okio.BufferedSource;
import com.tapsdk.antiaddiction.skynet.okio.Okio;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class FileUtil {
    public static String getJsonFromAssetsFile(Context context, String str) {
        String utf8 = "";
        if (Util.checkIsNull(context)) {
            return "";
        }
        try {
            BufferedSource bufferedSourceBuffer = Okio.buffer(Okio.source(context.getAssets().open(str)));
            utf8 = bufferedSourceBuffer.readUtf8();
            bufferedSourceBuffer.close();
            return utf8;
        } catch (IOException e) {
            AntiAddictionLogger.e("getJsonFromAssetsFile:" + e.getCause() + " | " + e.getMessage());
            return utf8;
        }
    }
}
