package com.tapsdk.antiaddiction.skynet.okhttp3;

import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import com.tapsdk.antiaddiction.skynet.okio.ByteString;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        return basic(str, str2, Util.ISO_8859_1);
    }

    public static String basic(String str, String str2, Charset charset) {
        return "Basic " + ByteString.encodeString(str + ":" + str2, charset).base64();
    }
}
