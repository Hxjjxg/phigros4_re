package com.voxelbusters.nativeplugins.utilities;

import android.util.Base64;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class StringUtility {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("") || str.equals("null");
    }

    public static String[] convertJsonStringToStringArray(String str) {
        String[] strArr = null;
        if (isNullOrEmpty(str)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = new String(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Debug.error(CommonDefines.STRING_UTILS_TAG, "Error in parsing jsonString " + str);
        }
        return strArr;
    }

    public static boolean contains(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getBase64DecodedString(String str) {
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getBase64EncodedString(String str) {
        try {
            return new String(Base64.encode(str.getBytes(), 0), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrencySymbolFromCode(String str) {
        try {
            return Currency.getInstance(str).getSymbol();
        } catch (Exception unused) {
            Debug.log(CommonDefines.STRING_UTILS_TAG, "Error in converting currency code : " + str);
            return "";
        }
    }

    public static String getCurrencyCodeFromCountryCode(String str) {
        String currencyCode = Currency.getInstance(new Locale("", str)).getCurrencyCode();
        Debug.log(CommonDefines.STRING_UTILS_TAG, "Country Code  : " + str + " = Currency Code :" + currencyCode);
        return currencyCode;
    }

    public static String getFileNameWithoutExtension(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf >= 0 ? str.substring(0, iLastIndexOf) : str;
    }
}
