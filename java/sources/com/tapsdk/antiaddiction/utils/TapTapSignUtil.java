package com.tapsdk.antiaddiction.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.tapsdk.antiaddiction.models.ServerTimeModel;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TapTapSignUtil {
    private static String getTime() {
        long recentServerTime = ServerTimeModel.getRecentServerTime() * 1000;
        if (recentServerTime <= 0) {
            recentServerTime = System.currentTimeMillis();
        }
        return String.format(Locale.US, "%010d", Long.valueOf(recentServerTime / 1000));
    }

    public static String getRandomString(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(36)));
        }
        return stringBuffer.toString();
    }

    private static String mergeSign(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
            return null;
        }
        String str8 = str + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5 + "\n" + str6 + "\n";
        if (TextUtils.isEmpty(str7)) {
            return str8 + "\n";
        }
        return str8 + str7 + "\n";
    }

    public static String getUA() {
        return "AntiAddictionAndroidSDK/3.30.3 " + System.getProperty("http.agent");
    }

    public static String getAuthorization(String str, String str2, String str3, String str4) {
        try {
            URL url = new URL(str);
            String time = getTime();
            String randomString = getRandomString(5);
            String host = url.getHost();
            return "MAC " + getAuthorizationParam("id", str3) + "," + getAuthorizationParam("ts", time) + "," + getAuthorizationParam("nonce", randomString) + "," + getAuthorizationParam("mac", sign(mergeSign(time, randomString, str2, str.substring(str.lastIndexOf(host) + host.length()), host, str.startsWith(Keys.Scheme.HTTPS) ? "443" : "80", ""), str4));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String sign(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return new String(Base64.encode(mac.doFinal(str.getBytes("UTF-8")), 2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e2) {
            throw new IllegalStateException(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new IllegalStateException(e3);
        }
    }

    private static String getAuthorizationParam(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return str + "=\"" + str2 + "\"";
    }
}
