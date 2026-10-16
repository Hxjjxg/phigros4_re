package com.digits.sdk.vcard;

import android.telephony.PhoneNumberUtils;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class VCardUtils {
    private static final String LOG_TAG = "vCard";
    private static final int[] sEscapeIndicatorsV30;
    private static final int[] sEscapeIndicatorsV40;
    private static final SparseArray<String> sKnownImPropNameMap_ItoS;
    private static final Map<String, Integer> sKnownPhoneTypeMap_StoI;
    private static final SparseArray<String> sKnownPhoneTypesMap_ItoS;
    private static final Set<String> sMobilePhoneLabelSet;
    private static final Set<String> sPhoneTypesUnknownToContactsSet;
    private static final Set<Character> sUnAcceptableAsciiInV21WordSet;

    public static class PhoneNumberUtilsPort {
        public static String formatNumber(String str, int i) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            PhoneNumberUtils.formatNumber(spannableStringBuilder, i);
            return spannableStringBuilder.toString();
        }
    }

    public static class TextUtilsPort {
        public static boolean isPrintableAscii(char c) {
            return (' ' <= c && c <= '~') || c == '\r' || c == '\n';
        }

        public static boolean isPrintableAsciiOnly(CharSequence charSequence) {
            int length = charSequence.length();
            for (int i = 0; i < length; i++) {
                if (!isPrintableAscii(charSequence.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        sKnownPhoneTypesMap_ItoS = sparseArray;
        HashMap map = new HashMap();
        sKnownPhoneTypeMap_StoI = map;
        sparseArray.put(9, VCardConstants.PARAM_TYPE_CAR);
        map.put(VCardConstants.PARAM_TYPE_CAR, 9);
        sparseArray.put(6, VCardConstants.PARAM_TYPE_PAGER);
        map.put(VCardConstants.PARAM_TYPE_PAGER, 6);
        sparseArray.put(11, VCardConstants.PARAM_TYPE_ISDN);
        map.put(VCardConstants.PARAM_TYPE_ISDN, 11);
        map.put(VCardConstants.PARAM_TYPE_HOME, 1);
        map.put(VCardConstants.PARAM_TYPE_WORK, 3);
        map.put(VCardConstants.PARAM_TYPE_CELL, 2);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_OTHER, 7);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_CALLBACK, 8);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_COMPANY_MAIN, 10);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_RADIO, 14);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_TTY_TDD, 16);
        map.put(VCardConstants.PARAM_PHONE_EXTRA_TYPE_ASSISTANT, 19);
        map.put(VCardConstants.PARAM_TYPE_VOICE, 7);
        HashSet hashSet = new HashSet();
        sPhoneTypesUnknownToContactsSet = hashSet;
        hashSet.add(VCardConstants.PARAM_TYPE_MODEM);
        hashSet.add(VCardConstants.PARAM_TYPE_MSG);
        hashSet.add(VCardConstants.PARAM_TYPE_BBS);
        hashSet.add(VCardConstants.PARAM_TYPE_VIDEO);
        SparseArray<String> sparseArray2 = new SparseArray<>();
        sKnownImPropNameMap_ItoS = sparseArray2;
        sparseArray2.put(0, VCardConstants.PROPERTY_X_AIM);
        sparseArray2.put(1, VCardConstants.PROPERTY_X_MSN);
        sparseArray2.put(2, VCardConstants.PROPERTY_X_YAHOO);
        sparseArray2.put(3, VCardConstants.PROPERTY_X_SKYPE_USERNAME);
        sparseArray2.put(5, VCardConstants.PROPERTY_X_GOOGLE_TALK);
        sparseArray2.put(6, VCardConstants.PROPERTY_X_ICQ);
        sparseArray2.put(7, VCardConstants.PROPERTY_X_JABBER);
        sparseArray2.put(4, VCardConstants.PROPERTY_X_QQ);
        sparseArray2.put(8, VCardConstants.PROPERTY_X_NETMEETING);
        sMobilePhoneLabelSet = new HashSet(Arrays.asList("MOBILE", "携帯電話", "携帯", "ケイタイ", "ｹｲﾀｲ"));
        sUnAcceptableAsciiInV21WordSet = new HashSet(Arrays.asList('[', ']', '=', ':', '.', ',', ' '));
        sEscapeIndicatorsV30 = new int[]{58, 59, 44, 32};
        sEscapeIndicatorsV40 = new int[]{59, 58};
    }

    public static String getPhoneTypeString(Integer num) {
        return sKnownPhoneTypesMap_ItoS.get(num.intValue());
    }

    public static boolean isMobilePhoneLabel(String str) {
        return "_AUTO_CELL".equals(str) || sMobilePhoneLabelSet.contains(str);
    }

    public static boolean isValidInV21ButUnknownToContactsPhoteType(String str) {
        return sPhoneTypesUnknownToContactsSet.contains(str);
    }

    public static String getPropertyNameForIm(int i) {
        return sKnownImPropNameMap_ItoS.get(i);
    }

    public static String[] sortNameElements(int i, String str, String str2, String str3) {
        String[] strArr = new String[3];
        int nameOrderType = VCardConfig.getNameOrderType(i);
        if (nameOrderType == 4) {
            strArr[0] = str2;
            strArr[1] = str3;
            strArr[2] = str;
        } else if (nameOrderType != 8) {
            strArr[0] = str3;
            strArr[1] = str2;
            strArr[2] = str;
        } else if (containsOnlyPrintableAscii(str) && containsOnlyPrintableAscii(str3)) {
            strArr[0] = str3;
            strArr[1] = str2;
            strArr[2] = str;
        } else {
            strArr[0] = str;
            strArr[1] = str2;
            strArr[2] = str3;
        }
        return strArr;
    }

    public static int getPhoneNumberFormat(int i) {
        return VCardConfig.isJapaneseDevice(i) ? 2 : 1;
    }

    public static String constructNameFromElements(int i, String str, String str2, String str3) {
        return constructNameFromElements(i, str, str2, str3, null, null);
    }

    public static String constructNameFromElements(int i, String str, String str2, String str3, String str4, String str5) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        String[] strArrSortNameElements = sortNameElements(i, str, str2, str3);
        if (TextUtils.isEmpty(str4)) {
            z = true;
        } else {
            sb.append(str4);
            z = false;
        }
        for (String str6 : strArrSortNameElements) {
            if (!TextUtils.isEmpty(str6)) {
                if (z) {
                    z = false;
                } else {
                    sb.append(' ');
                }
                sb.append(str6);
            }
        }
        if (!TextUtils.isEmpty(str5)) {
            if (!z) {
                sb.append(' ');
            }
            sb.append(str5);
        }
        return sb.toString();
    }

    public static boolean containsOnlyPrintableAscii(String... strArr) {
        if (strArr == null) {
            return true;
        }
        return containsOnlyPrintableAscii(Arrays.asList(strArr));
    }

    public static boolean containsOnlyPrintableAscii(Collection<String> collection) {
        if (collection == null) {
            return true;
        }
        for (String str : collection) {
            if (!TextUtils.isEmpty(str) && !TextUtilsPort.isPrintableAsciiOnly(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsOnlyNonCrLfPrintableAscii(String... strArr) {
        if (strArr == null) {
            return true;
        }
        return containsOnlyNonCrLfPrintableAscii(Arrays.asList(strArr));
    }

    public static boolean containsOnlyNonCrLfPrintableAscii(Collection<String> collection) {
        if (collection == null) {
            return true;
        }
        for (String str : collection) {
            if (!TextUtils.isEmpty(str)) {
                int length = str.length();
                for (int iOffsetByCodePoints = 0; iOffsetByCodePoints < length; iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1)) {
                    int iCodePointAt = str.codePointAt(iOffsetByCodePoints);
                    if (32 > iCodePointAt || iCodePointAt > 126) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsOnlyAlphaDigitHyphen(String... strArr) {
        if (strArr == null) {
            return true;
        }
        return containsOnlyAlphaDigitHyphen(Arrays.asList(strArr));
    }

    public static boolean containsOnlyAlphaDigitHyphen(Collection<String> collection) {
        if (collection == null) {
            return true;
        }
        for (String str : collection) {
            if (!TextUtils.isEmpty(str)) {
                int length = str.length();
                for (int iOffsetByCodePoints = 0; iOffsetByCodePoints < length; iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1)) {
                    int iCodePointAt = str.codePointAt(iOffsetByCodePoints);
                    if ((97 > iCodePointAt || iCodePointAt >= 123) && ((65 > iCodePointAt || iCodePointAt >= 91) && ((48 > iCodePointAt || iCodePointAt >= 58) && iCodePointAt != 45))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsOnlyWhiteSpaces(String... strArr) {
        if (strArr == null) {
            return true;
        }
        return containsOnlyWhiteSpaces(Arrays.asList(strArr));
    }

    public static boolean containsOnlyWhiteSpaces(Collection<String> collection) {
        if (collection == null) {
            return true;
        }
        for (String str : collection) {
            if (!TextUtils.isEmpty(str)) {
                int length = str.length();
                for (int iOffsetByCodePoints = 0; iOffsetByCodePoints < length; iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1)) {
                    if (!Character.isWhitespace(str.codePointAt(iOffsetByCodePoints))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isV21Word(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        int length = str.length();
        int iOffsetByCodePoints = 0;
        while (iOffsetByCodePoints < length) {
            int iCodePointAt = str.codePointAt(iOffsetByCodePoints);
            if (32 > iCodePointAt || iCodePointAt > 126 || sUnAcceptableAsciiInV21WordSet.contains(Character.valueOf((char) iCodePointAt))) {
                return false;
            }
            iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
        }
        return true;
    }

    public static String toStringAsV30ParamValue(String str) {
        return toStringAsParamValue(str, sEscapeIndicatorsV30);
    }

    public static String toStringAsV40ParamValue(String str) {
        return toStringAsParamValue(str, sEscapeIndicatorsV40);
    }

    private static String toStringAsParamValue(String str, int[] iArr) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        boolean z = false;
        for (int iOffsetByCodePoints = 0; iOffsetByCodePoints < length; iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1)) {
            int iCodePointAt = str.codePointAt(iOffsetByCodePoints);
            if (iCodePointAt >= 32 && iCodePointAt != 34) {
                sb.appendCodePoint(iCodePointAt);
                for (int i : iArr) {
                    if (iCodePointAt == i) {
                        z = true;
                        break;
                    }
                }
            }
        }
        String string = sb.toString();
        if (string.length() == 0 || containsOnlyWhiteSpaces(string)) {
            return "";
        }
        return z ? "\"" + string + '\"' : string;
    }

    public static String toHalfWidthString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int iOffsetByCodePoints = 0;
        while (iOffsetByCodePoints < length) {
            char cCharAt = str.charAt(iOffsetByCodePoints);
            String strTryGetHalfWidthText = JapaneseUtils.tryGetHalfWidthText(cCharAt);
            if (strTryGetHalfWidthText != null) {
                sb.append(strTryGetHalfWidthText);
            } else {
                sb.append(cCharAt);
            }
            iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
        }
        return sb.toString();
    }

    private VCardUtils() {
    }
}
