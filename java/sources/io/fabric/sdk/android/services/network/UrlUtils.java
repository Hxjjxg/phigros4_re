package io.fabric.sdk.android.services.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public final class UrlUtils {
    public static final String UTF8 = "UTF8";

    private UrlUtils() {
    }

    public static TreeMap<String, String> getQueryParams(URI uri, boolean z) {
        return getQueryParams(uri.getRawQuery(), z);
    }

    public static TreeMap<String, String> getQueryParams(String str, boolean z) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        if (str == null) {
            return treeMap;
        }
        for (String str2 : str.split("&")) {
            String[] strArrSplit = str2.split("=");
            if (strArrSplit.length == 2) {
                if (z) {
                    treeMap.put(urlDecode(strArrSplit[0]), urlDecode(strArrSplit[1]));
                } else {
                    treeMap.put(strArrSplit[0], strArrSplit[1]);
                }
            } else if (!TextUtils.isEmpty(strArrSplit[0])) {
                if (z) {
                    treeMap.put(urlDecode(strArrSplit[0]), "");
                } else {
                    treeMap.put(strArrSplit[0], "");
                }
            }
        }
        return treeMap;
    }

    public static String urlEncode(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String urlDecode(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLDecoder.decode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String percentEncode(String str) {
        int i;
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String strUrlEncode = urlEncode(str);
        int length = strUrlEncode.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = strUrlEncode.charAt(i2);
            if (cCharAt == '*') {
                sb.append("%2A");
            } else if (cCharAt == '+') {
                sb.append("%20");
            } else if (cCharAt == '%' && (i = i2 + 2) < length && strUrlEncode.charAt(i2 + 1) == '7' && strUrlEncode.charAt(i) == 'E') {
                sb.append('~');
                i2 = i;
            } else {
                sb.append(cCharAt);
            }
            i2++;
        }
        return sb.toString();
    }
}
