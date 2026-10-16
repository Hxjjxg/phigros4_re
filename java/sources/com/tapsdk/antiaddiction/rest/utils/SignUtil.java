package com.tapsdk.antiaddiction.rest.utils;

import android.text.TextUtils;
import com.taptap.sdk.constant.LoginConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class SignUtil {
    private static String byteToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b);
            if (hexString.length() > 3) {
                sb.append(hexString.substring(6));
            } else if (hexString.length() < 2) {
                sb.append(LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0).append(hexString);
            } else {
                sb.append(hexString);
            }
        }
        return sb.toString();
    }

    public static String getSignCode(String str) {
        byte[] bArrDigest = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            bArrDigest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bArrDigest != null ? byteToHexString(bArrDigest) : "";
    }
}
