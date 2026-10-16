package com.digits.sdk.vcard;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class VCardBuilder {
    public static final int DEFAULT_EMAIL_TYPE = 3;
    public static final int DEFAULT_PHONE_TYPE = 1;
    private static final String LOG_TAG = "vCard";
    private static final String SHIFT_JIS = "SHIFT_JIS";
    private static final String VCARD_DATA_PUBLIC = "PUBLIC";
    private static final String VCARD_DATA_SEPARATOR = ":";
    private static final String VCARD_DATA_VCARD = "VCARD";
    public static final String VCARD_END_OF_LINE = "\r\n";
    private static final String VCARD_ITEM_SEPARATOR = ";";
    private static final String VCARD_PARAM_ENCODING_QP = "ENCODING=QUOTED-PRINTABLE";
    private static final String VCARD_PARAM_EQUAL = "=";
    private static final String VCARD_PARAM_SEPARATOR = ";";
    private static final Set<String> sAllowedAndroidPropertySet = Collections.unmodifiableSet(new HashSet(Arrays.asList("vnd.android.cursor.item/nickname", "vnd.android.cursor.item/contact_event", "vnd.android.cursor.item/relation")));
    private final boolean mAppendTypeParamName;
    private StringBuilder mBuilder;
    private final String mCharset;
    private boolean mEndAppended;
    private final boolean mIsDoCoMo;
    private final boolean mIsJapaneseMobilePhone;
    private final boolean mIsV30OrV40;
    private final boolean mNeedsToConvertPhoneticString;
    private final boolean mRefrainsQPToNameProperties;
    private final boolean mShouldAppendCharsetParam;
    private final boolean mShouldUseQuotedPrintable;
    private final boolean mUsesDefactProperty;
    private final String mVCardCharsetParameter;
    private final int mVCardType;

    public VCardBuilder(int i) {
        this(i, null);
    }

    public VCardBuilder(int i, String str) {
        this.mVCardType = i;
        if (VCardConfig.isVersion40(i)) {
            Log.w(LOG_TAG, "Should not use vCard 4.0 when building vCard. It is not officially published yet.");
        }
        this.mIsV30OrV40 = VCardConfig.isVersion30(i) || VCardConfig.isVersion40(i);
        this.mShouldUseQuotedPrintable = VCardConfig.shouldUseQuotedPrintable(i);
        this.mIsDoCoMo = VCardConfig.isDoCoMo(i);
        this.mIsJapaneseMobilePhone = VCardConfig.needsToConvertPhoneticString(i);
        this.mUsesDefactProperty = VCardConfig.usesDefactProperty(i);
        this.mRefrainsQPToNameProperties = VCardConfig.shouldRefrainQPToNameProperties(i);
        this.mAppendTypeParamName = VCardConfig.appendTypeParamName(i);
        this.mNeedsToConvertPhoneticString = VCardConfig.needsToConvertPhoneticString(i);
        this.mShouldAppendCharsetParam = (VCardConfig.isVersion30(i) && "UTF-8".equalsIgnoreCase(str)) ? false : true;
        if (VCardConfig.isDoCoMo(i)) {
            if (!SHIFT_JIS.equalsIgnoreCase(str) && TextUtils.isEmpty(str)) {
                this.mCharset = SHIFT_JIS;
            } else {
                this.mCharset = str;
            }
            this.mVCardCharsetParameter = "CHARSET=SHIFT_JIS";
        } else if (TextUtils.isEmpty(str)) {
            Log.i(LOG_TAG, "Use the charset \"UTF-8\" for export.");
            this.mCharset = "UTF-8";
            this.mVCardCharsetParameter = "CHARSET=UTF-8";
        } else {
            this.mCharset = str;
            this.mVCardCharsetParameter = "CHARSET=" + str;
        }
        clear();
    }

    public void clear() {
        this.mBuilder = new StringBuilder();
        this.mEndAppended = false;
        appendLine(VCardConstants.PROPERTY_BEGIN, VCARD_DATA_VCARD);
        if (VCardConfig.isVersion40(this.mVCardType)) {
            appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V40);
        } else {
            if (VCardConfig.isVersion30(this.mVCardType)) {
                appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V30);
                return;
            }
            if (!VCardConfig.isVersion21(this.mVCardType)) {
                Log.w(LOG_TAG, "Unknown vCard version detected.");
            }
            appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V21);
        }
    }

    private boolean containsNonEmptyName(ContentValues contentValues) {
        return (TextUtils.isEmpty(contentValues.getAsString("data3")) && TextUtils.isEmpty(contentValues.getAsString("data5")) && TextUtils.isEmpty(contentValues.getAsString("data2")) && TextUtils.isEmpty(contentValues.getAsString("data4")) && TextUtils.isEmpty(contentValues.getAsString("data6")) && TextUtils.isEmpty(contentValues.getAsString("data9")) && TextUtils.isEmpty(contentValues.getAsString("data8")) && TextUtils.isEmpty(contentValues.getAsString("data7")) && TextUtils.isEmpty(contentValues.getAsString("data1"))) ? false : true;
    }

    private ContentValues getPrimaryContentValueWithStructuredName(List<ContentValues> list) {
        ContentValues contentValues = null;
        ContentValues contentValues2 = null;
        for (ContentValues contentValues3 : list) {
            if (contentValues3 != null) {
                Integer asInteger = contentValues3.getAsInteger("is_super_primary");
                if (asInteger != null && asInteger.intValue() > 0) {
                    contentValues = contentValues3;
                    break;
                }
                if (contentValues == null) {
                    Integer asInteger2 = contentValues3.getAsInteger("is_primary");
                    if (asInteger2 != null && asInteger2.intValue() > 0 && containsNonEmptyName(contentValues3)) {
                        contentValues = contentValues3;
                    } else if (contentValues2 == null && containsNonEmptyName(contentValues3)) {
                        contentValues2 = contentValues3;
                    }
                }
            }
        }
        if (contentValues == null) {
            return contentValues2 != null ? contentValues2 : new ContentValues();
        }
        return contentValues;
    }

    private VCardBuilder appendNamePropertiesV40(List<ContentValues> list) {
        String str;
        if (this.mIsDoCoMo || this.mNeedsToConvertPhoneticString) {
            Log.w(LOG_TAG, "Invalid flag is used in vCard 4.0 construction. Ignored.");
        }
        if (list == null || list.isEmpty()) {
            appendLine(VCardConstants.PROPERTY_FN, "");
            return this;
        }
        ContentValues primaryContentValueWithStructuredName = getPrimaryContentValueWithStructuredName(list);
        String asString = primaryContentValueWithStructuredName.getAsString("data3");
        String asString2 = primaryContentValueWithStructuredName.getAsString("data5");
        String asString3 = primaryContentValueWithStructuredName.getAsString("data2");
        String asString4 = primaryContentValueWithStructuredName.getAsString("data4");
        String asString5 = primaryContentValueWithStructuredName.getAsString("data6");
        String asString6 = primaryContentValueWithStructuredName.getAsString("data1");
        if (!TextUtils.isEmpty(asString) || !TextUtils.isEmpty(asString3) || !TextUtils.isEmpty(asString2) || !TextUtils.isEmpty(asString4) || !TextUtils.isEmpty(asString5)) {
            str = asString;
        } else {
            if (TextUtils.isEmpty(asString6)) {
                appendLine(VCardConstants.PROPERTY_FN, "");
                return this;
            }
            str = asString6;
        }
        String asString7 = primaryContentValueWithStructuredName.getAsString("data9");
        String asString8 = primaryContentValueWithStructuredName.getAsString("data8");
        String asString9 = primaryContentValueWithStructuredName.getAsString("data7");
        String strEscapeCharacters = escapeCharacters(str);
        String strEscapeCharacters2 = escapeCharacters(asString3);
        String strEscapeCharacters3 = escapeCharacters(asString2);
        String strEscapeCharacters4 = escapeCharacters(asString4);
        String strEscapeCharacters5 = escapeCharacters(asString5);
        this.mBuilder.append("N");
        if (!TextUtils.isEmpty(asString7) || !TextUtils.isEmpty(asString8) || !TextUtils.isEmpty(asString9)) {
            this.mBuilder.append(";");
            this.mBuilder.append("SORT-AS=").append(VCardUtils.toStringAsV40ParamValue(escapeCharacters(asString7) + ';' + escapeCharacters(asString9) + ';' + escapeCharacters(asString8)));
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(strEscapeCharacters);
        this.mBuilder.append(";");
        this.mBuilder.append(strEscapeCharacters2);
        this.mBuilder.append(";");
        this.mBuilder.append(strEscapeCharacters3);
        this.mBuilder.append(";");
        this.mBuilder.append(strEscapeCharacters4);
        this.mBuilder.append(";");
        this.mBuilder.append(strEscapeCharacters5);
        this.mBuilder.append(VCARD_END_OF_LINE);
        if (TextUtils.isEmpty(asString6)) {
            Log.w(LOG_TAG, "DISPLAY_NAME is empty.");
            appendLine(VCardConstants.PROPERTY_FN, escapeCharacters(VCardUtils.constructNameFromElements(VCardConfig.getNameOrderType(this.mVCardType), str, asString2, asString3, asString4, asString5)));
        } else {
            String strEscapeCharacters6 = escapeCharacters(asString6);
            this.mBuilder.append(VCardConstants.PROPERTY_FN);
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(strEscapeCharacters6);
            this.mBuilder.append(VCARD_END_OF_LINE);
        }
        appendPhoneticNameFields(primaryContentValueWithStructuredName);
        return this;
    }

    public VCardBuilder appendNameProperties(List<ContentValues> list) {
        String strEscapeCharacters;
        String strEscapeCharacters2;
        String strEscapeCharacters3;
        String strEscapeCharacters4;
        String strEscapeCharacters5;
        String str;
        if (VCardConfig.isVersion40(this.mVCardType)) {
            return appendNamePropertiesV40(list);
        }
        if (list == null || list.isEmpty()) {
            if (VCardConfig.isVersion30(this.mVCardType)) {
                appendLine("N", "");
                appendLine(VCardConstants.PROPERTY_FN, "");
            } else if (this.mIsDoCoMo) {
                appendLine("N", "");
            }
            return this;
        }
        ContentValues primaryContentValueWithStructuredName = getPrimaryContentValueWithStructuredName(list);
        String asString = primaryContentValueWithStructuredName.getAsString("data3");
        String asString2 = primaryContentValueWithStructuredName.getAsString("data5");
        String asString3 = primaryContentValueWithStructuredName.getAsString("data2");
        String asString4 = primaryContentValueWithStructuredName.getAsString("data4");
        String asString5 = primaryContentValueWithStructuredName.getAsString("data6");
        String asString6 = primaryContentValueWithStructuredName.getAsString("data1");
        if (!TextUtils.isEmpty(asString) || !TextUtils.isEmpty(asString3)) {
            boolean z = false;
            boolean zShouldAppendCharsetParam = shouldAppendCharsetParam(asString, asString3, asString2, asString4, asString5);
            boolean z2 = (this.mRefrainsQPToNameProperties || (VCardUtils.containsOnlyNonCrLfPrintableAscii(asString) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString3) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString2) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString4) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString5))) ? false : true;
            if (TextUtils.isEmpty(asString6)) {
                asString6 = VCardUtils.constructNameFromElements(VCardConfig.getNameOrderType(this.mVCardType), asString, asString2, asString3, asString4, asString5);
            }
            boolean zShouldAppendCharsetParam2 = shouldAppendCharsetParam(asString6);
            if (!this.mRefrainsQPToNameProperties && !VCardUtils.containsOnlyNonCrLfPrintableAscii(asString6)) {
                z = true;
            }
            if (z2) {
                strEscapeCharacters = encodeQuotedPrintable(asString);
                strEscapeCharacters2 = encodeQuotedPrintable(asString3);
                strEscapeCharacters3 = encodeQuotedPrintable(asString2);
                strEscapeCharacters4 = encodeQuotedPrintable(asString4);
                strEscapeCharacters5 = encodeQuotedPrintable(asString5);
            } else {
                strEscapeCharacters = escapeCharacters(asString);
                strEscapeCharacters2 = escapeCharacters(asString3);
                strEscapeCharacters3 = escapeCharacters(asString2);
                strEscapeCharacters4 = escapeCharacters(asString4);
                strEscapeCharacters5 = escapeCharacters(asString5);
            }
            String strEncodeQuotedPrintable = z ? encodeQuotedPrintable(asString6) : escapeCharacters(asString6);
            this.mBuilder.append("N");
            if (this.mIsDoCoMo) {
                if (zShouldAppendCharsetParam) {
                    str = ";";
                    this.mBuilder.append(str);
                    this.mBuilder.append(this.mVCardCharsetParameter);
                } else {
                    str = r10;
                }
                if (z2) {
                    this.mBuilder.append(str);
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(asString6);
                this.mBuilder.append(str);
                this.mBuilder.append(str);
                this.mBuilder.append(str);
                this.mBuilder.append(str);
            } else {
                str = r10;
                if (zShouldAppendCharsetParam) {
                    this.mBuilder.append(str);
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                if (z2) {
                    this.mBuilder.append(str);
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(strEscapeCharacters);
                this.mBuilder.append(str);
                this.mBuilder.append(strEscapeCharacters2);
                this.mBuilder.append(str);
                this.mBuilder.append(strEscapeCharacters3);
                this.mBuilder.append(str);
                this.mBuilder.append(strEscapeCharacters4);
                this.mBuilder.append(str);
                this.mBuilder.append(strEscapeCharacters5);
            }
            this.mBuilder.append(VCARD_END_OF_LINE);
            this.mBuilder.append(VCardConstants.PROPERTY_FN);
            if (zShouldAppendCharsetParam2) {
                this.mBuilder.append(str);
                this.mBuilder.append(this.mVCardCharsetParameter);
            }
            if (z) {
                this.mBuilder.append(str);
                this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            }
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(strEncodeQuotedPrintable);
            this.mBuilder.append(VCARD_END_OF_LINE);
        } else if (!TextUtils.isEmpty(asString6)) {
            buildSinglePartNameField("N", asString6);
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_END_OF_LINE);
            buildSinglePartNameField(VCardConstants.PROPERTY_FN, asString6);
            this.mBuilder.append(VCARD_END_OF_LINE);
        } else if (VCardConfig.isVersion30(this.mVCardType)) {
            appendLine("N", "");
            appendLine(VCardConstants.PROPERTY_FN, "");
        } else if (this.mIsDoCoMo) {
            appendLine("N", "");
        }
        appendPhoneticNameFields(primaryContentValueWithStructuredName);
        return this;
    }

    private void buildSinglePartNameField(String str, String str2) {
        boolean z = (this.mRefrainsQPToNameProperties || VCardUtils.containsOnlyNonCrLfPrintableAscii(str2)) ? false : true;
        String strEncodeQuotedPrintable = z ? encodeQuotedPrintable(str2) : escapeCharacters(str2);
        this.mBuilder.append(str);
        if (shouldAppendCharsetParam(str2)) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (z) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(strEncodeQuotedPrintable);
    }

    private void appendPhoneticNameFields(ContentValues contentValues) {
        String strEscapeCharacters;
        String strEscapeCharacters2;
        String strEscapeCharacters3;
        String strEscapeCharacters4;
        String strEscapeCharacters5;
        String strEscapeCharacters6;
        boolean z;
        String asString = contentValues.getAsString("data9");
        String asString2 = contentValues.getAsString("data8");
        String asString3 = contentValues.getAsString("data7");
        if (this.mNeedsToConvertPhoneticString) {
            asString = VCardUtils.toHalfWidthString(asString);
            asString2 = VCardUtils.toHalfWidthString(asString2);
            asString3 = VCardUtils.toHalfWidthString(asString3);
        }
        if (TextUtils.isEmpty(asString) && TextUtils.isEmpty(asString2) && TextUtils.isEmpty(asString3)) {
            if (this.mIsDoCoMo) {
                this.mBuilder.append(VCardConstants.PROPERTY_SOUND);
                this.mBuilder.append(";");
                this.mBuilder.append("X-IRMC-N");
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(VCARD_END_OF_LINE);
                return;
            }
            return;
        }
        if (!VCardConfig.isVersion40(this.mVCardType)) {
            if (VCardConfig.isVersion30(this.mVCardType)) {
                String strConstructNameFromElements = VCardUtils.constructNameFromElements(this.mVCardType, asString, asString2, asString3);
                this.mBuilder.append(VCardConstants.PROPERTY_SORT_STRING);
                if (VCardConfig.isVersion30(this.mVCardType) && shouldAppendCharsetParam(strConstructNameFromElements)) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(escapeCharacters(strConstructNameFromElements));
                this.mBuilder.append(VCARD_END_OF_LINE);
            } else if (this.mIsJapaneseMobilePhone) {
                this.mBuilder.append(VCardConstants.PROPERTY_SOUND);
                this.mBuilder.append(";");
                this.mBuilder.append("X-IRMC-N");
                if ((this.mRefrainsQPToNameProperties || (VCardUtils.containsOnlyNonCrLfPrintableAscii(asString) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString2) && VCardUtils.containsOnlyNonCrLfPrintableAscii(asString3))) ? false : true) {
                    strEscapeCharacters4 = encodeQuotedPrintable(asString);
                    strEscapeCharacters5 = encodeQuotedPrintable(asString2);
                    strEscapeCharacters6 = encodeQuotedPrintable(asString3);
                } else {
                    strEscapeCharacters4 = escapeCharacters(asString);
                    strEscapeCharacters5 = escapeCharacters(asString2);
                    strEscapeCharacters6 = escapeCharacters(asString3);
                }
                if (shouldAppendCharsetParam(strEscapeCharacters4, strEscapeCharacters5, strEscapeCharacters6)) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                if (TextUtils.isEmpty(strEscapeCharacters4)) {
                    z = true;
                } else {
                    this.mBuilder.append(strEscapeCharacters4);
                    z = false;
                }
                if (!TextUtils.isEmpty(strEscapeCharacters5)) {
                    if (z) {
                        z = false;
                    } else {
                        this.mBuilder.append(' ');
                    }
                    this.mBuilder.append(strEscapeCharacters5);
                }
                if (!TextUtils.isEmpty(strEscapeCharacters6)) {
                    if (!z) {
                        this.mBuilder.append(' ');
                    }
                    this.mBuilder.append(strEscapeCharacters6);
                }
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(";");
                this.mBuilder.append(VCARD_END_OF_LINE);
            }
        }
        if (this.mUsesDefactProperty) {
            if (!TextUtils.isEmpty(asString3)) {
                boolean z2 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(asString3);
                if (z2) {
                    strEscapeCharacters3 = encodeQuotedPrintable(asString3);
                } else {
                    strEscapeCharacters3 = escapeCharacters(asString3);
                }
                this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_FIRST_NAME);
                if (shouldAppendCharsetParam(asString3)) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                if (z2) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(strEscapeCharacters3);
                this.mBuilder.append(VCARD_END_OF_LINE);
            }
            if (!TextUtils.isEmpty(asString2)) {
                boolean z3 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(asString2);
                if (z3) {
                    strEscapeCharacters2 = encodeQuotedPrintable(asString2);
                } else {
                    strEscapeCharacters2 = escapeCharacters(asString2);
                }
                this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_MIDDLE_NAME);
                if (shouldAppendCharsetParam(asString2)) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                if (z3) {
                    this.mBuilder.append(";");
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(strEscapeCharacters2);
                this.mBuilder.append(VCARD_END_OF_LINE);
            }
            if (TextUtils.isEmpty(asString)) {
                return;
            }
            boolean z4 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(asString);
            if (z4) {
                strEscapeCharacters = encodeQuotedPrintable(asString);
            } else {
                strEscapeCharacters = escapeCharacters(asString);
            }
            this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_LAST_NAME);
            if (shouldAppendCharsetParam(asString)) {
                this.mBuilder.append(";");
                this.mBuilder.append(this.mVCardCharsetParameter);
            }
            if (z4) {
                this.mBuilder.append(";");
                this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            }
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(strEscapeCharacters);
            this.mBuilder.append(VCARD_END_OF_LINE);
        }
    }

    public VCardBuilder appendPhones(List<ContentValues> list, VCardPhoneNumberTranslationCallback vCardPhoneNumberTranslationCallback) {
        boolean z;
        if (list != null) {
            HashSet hashSet = new HashSet();
            z = false;
            for (ContentValues contentValues : list) {
                Integer asInteger = contentValues.getAsInteger("data2");
                String asString = contentValues.getAsString("data3");
                Integer asInteger2 = contentValues.getAsInteger("is_primary");
                boolean z2 = asInteger2 != null && asInteger2.intValue() > 0;
                String asString2 = contentValues.getAsString("data1");
                if (asString2 != null) {
                    asString2 = asString2.trim();
                }
                if (!TextUtils.isEmpty(asString2)) {
                    int iIntValue = asInteger != null ? asInteger.intValue() : 1;
                    if (vCardPhoneNumberTranslationCallback != null) {
                        String strOnValueReceived = vCardPhoneNumberTranslationCallback.onValueReceived(asString2, iIntValue, asString, z2);
                        if (!hashSet.contains(strOnValueReceived)) {
                            hashSet.add(strOnValueReceived);
                            appendTelLine(Integer.valueOf(iIntValue), asString, strOnValueReceived, z2);
                        }
                    } else if (iIntValue == 6 || VCardConfig.refrainPhoneNumberFormatting(this.mVCardType)) {
                        if (!hashSet.contains(asString2)) {
                            hashSet.add(asString2);
                            appendTelLine(Integer.valueOf(iIntValue), asString, asString2, z2);
                        }
                        z = true;
                    } else {
                        List<String> listSplitPhoneNumbers = splitPhoneNumbers(asString2);
                        if (!listSplitPhoneNumbers.isEmpty()) {
                            for (String str : listSplitPhoneNumbers) {
                                if (!hashSet.contains(str)) {
                                    String strReplace = str.replace(',', 'p').replace(';', 'w');
                                    if (TextUtils.equals(strReplace, str)) {
                                        StringBuilder sb = new StringBuilder();
                                        int length = str.length();
                                        for (int i = 0; i < length; i++) {
                                            char cCharAt = str.charAt(i);
                                            if (Character.isDigit(cCharAt) || cCharAt == '+') {
                                                sb.append(cCharAt);
                                            }
                                        }
                                        strReplace = VCardUtils.PhoneNumberUtilsPort.formatNumber(sb.toString(), VCardUtils.getPhoneNumberFormat(this.mVCardType));
                                    }
                                    if (VCardConfig.isVersion40(this.mVCardType) && !TextUtils.isEmpty(strReplace) && !strReplace.startsWith("tel:")) {
                                        strReplace = "tel:" + strReplace;
                                    }
                                    hashSet.add(str);
                                    appendTelLine(Integer.valueOf(iIntValue), asString, strReplace, z2);
                                }
                            }
                            z = true;
                        }
                    }
                }
            }
        } else {
            z = false;
        }
        if (!z && this.mIsDoCoMo) {
            appendTelLine(1, "", "", false);
        }
        return this;
    }

    private List<String> splitPhoneNumbers(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n' && sb.length() > 0) {
                arrayList.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(cCharAt);
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public VCardBuilder appendEmails(List<ContentValues> list) {
        boolean z;
        if (list != null) {
            HashSet hashSet = new HashSet();
            z = false;
            for (ContentValues contentValues : list) {
                String asString = contentValues.getAsString("data1");
                if (asString != null) {
                    asString = asString.trim();
                }
                if (!TextUtils.isEmpty(asString)) {
                    Integer asInteger = contentValues.getAsInteger("data2");
                    int iIntValue = asInteger != null ? asInteger.intValue() : 3;
                    String asString2 = contentValues.getAsString("data3");
                    Integer asInteger2 = contentValues.getAsInteger("is_primary");
                    boolean z2 = asInteger2 != null && asInteger2.intValue() > 0;
                    if (!hashSet.contains(asString)) {
                        hashSet.add(asString);
                        appendEmailLine(iIntValue, asString2, asString, z2);
                    }
                    z = true;
                }
            }
        } else {
            z = false;
        }
        if (!z && this.mIsDoCoMo) {
            appendEmailLine(1, "", "", false);
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0025  */
    public void appendEmailLine(int i, String str, String str2, boolean z) {
        String str3 = VCardConstants.PARAM_TYPE_CELL;
        if (i != 0) {
            if (i == 1) {
                str3 = VCardConstants.PARAM_TYPE_HOME;
            } else if (i == 2) {
                str3 = VCardConstants.PARAM_TYPE_WORK;
            } else if (i == 3) {
                str3 = null;
            } else if (i != 4) {
                Log.e(LOG_TAG, "Unknown Email type: " + i);
                str3 = null;
            }
        } else if (!VCardUtils.isMobilePhoneLabel(str)) {
            if (TextUtils.isEmpty(str) || !VCardUtils.containsOnlyAlphaDigitHyphen(str)) {
                str3 = null;
            } else {
                str3 = "X-" + str;
            }
        }
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(VCardConstants.PARAM_TYPE_PREF);
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(str3);
        }
        appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_EMAIL, arrayList, str2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void appendTelLine(Integer num, String str, String str2, boolean z) {
        this.mBuilder.append(VCardConstants.PROPERTY_TEL);
        this.mBuilder.append(";");
        int iIntValue = num == null ? 7 : num.intValue();
        ArrayList arrayList = new ArrayList();
        switch (iIntValue) {
            case 0:
                if (TextUtils.isEmpty(str)) {
                    arrayList.add(VCardConstants.PARAM_TYPE_VOICE);
                } else if (VCardUtils.isMobilePhoneLabel(str)) {
                    arrayList.add(VCardConstants.PARAM_TYPE_CELL);
                } else if (this.mIsV30OrV40) {
                    arrayList.add(str);
                } else {
                    String upperCase = str.toUpperCase(Locale.getDefault());
                    if (VCardUtils.isValidInV21ButUnknownToContactsPhoteType(upperCase)) {
                        arrayList.add(upperCase);
                    } else if (VCardUtils.containsOnlyAlphaDigitHyphen(str)) {
                        arrayList.add("X-" + str);
                    }
                }
                break;
            case 1:
                arrayList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_HOME));
                break;
            case 2:
                arrayList.add(VCardConstants.PARAM_TYPE_CELL);
                break;
            case 3:
                arrayList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK));
                break;
            case 4:
                arrayList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK, VCardConstants.PARAM_TYPE_FAX));
                break;
            case 5:
                arrayList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_HOME, VCardConstants.PARAM_TYPE_FAX));
                break;
            case 6:
                if (this.mIsDoCoMo) {
                    arrayList.add(VCardConstants.PARAM_TYPE_VOICE);
                } else {
                    arrayList.add(VCardConstants.PARAM_TYPE_PAGER);
                }
                break;
            case 7:
                arrayList.add(VCardConstants.PARAM_TYPE_VOICE);
                break;
            case 9:
                arrayList.add(VCardConstants.PARAM_TYPE_CAR);
                break;
            case 10:
                arrayList.add(VCardConstants.PARAM_TYPE_WORK);
                z = true;
                break;
            case 11:
                arrayList.add(VCardConstants.PARAM_TYPE_ISDN);
                break;
            case 12:
                z = true;
                break;
            case 13:
                arrayList.add(VCardConstants.PARAM_TYPE_FAX);
                break;
            case 15:
                arrayList.add(VCardConstants.PARAM_TYPE_TLX);
                break;
            case 17:
                arrayList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK, VCardConstants.PARAM_TYPE_CELL));
                break;
            case 18:
                arrayList.add(VCardConstants.PARAM_TYPE_WORK);
                if (this.mIsDoCoMo) {
                    arrayList.add(VCardConstants.PARAM_TYPE_VOICE);
                } else {
                    arrayList.add(VCardConstants.PARAM_TYPE_PAGER);
                }
                break;
            case 20:
                arrayList.add(VCardConstants.PARAM_TYPE_MSG);
                break;
        }
        if (z) {
            arrayList.add(VCardConstants.PARAM_TYPE_PREF);
        }
        if (arrayList.isEmpty()) {
            appendUncommonPhoneType(this.mBuilder, Integer.valueOf(iIntValue));
        } else {
            appendTypeParameters(arrayList);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(str2);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    private void appendUncommonPhoneType(StringBuilder sb, Integer num) {
        if (this.mIsDoCoMo) {
            sb.append(VCardConstants.PARAM_TYPE_VOICE);
            return;
        }
        String phoneTypeString = VCardUtils.getPhoneTypeString(num);
        if (phoneTypeString != null) {
            appendTypeParameter(phoneTypeString);
        } else {
            Log.e(LOG_TAG, "Unknown or unsupported (by vCard) Phone type: " + num);
        }
    }

    public void appendLineWithCharsetAndQPDetection(String str, List<String> list, String str2) {
        appendLine(str, list, str2, !VCardUtils.containsOnlyPrintableAscii(str2), this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(str2));
    }

    public void appendLine(String str, String str2) {
        appendLine(str, str2, false, false);
    }

    public void appendLine(String str, String str2, boolean z, boolean z2) {
        appendLine(str, null, str2, z, z2);
    }

    public void appendLine(String str, List<String> list, String str2, boolean z, boolean z2) {
        String strEscapeCharacters;
        this.mBuilder.append(str);
        if (list != null && list.size() > 0) {
            this.mBuilder.append(";");
            appendTypeParameters(list);
        }
        if (z) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (z2) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            strEscapeCharacters = encodeQuotedPrintable(str2);
        } else {
            strEscapeCharacters = escapeCharacters(str2);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(strEscapeCharacters);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    private void appendTypeParameters(List<String> list) {
        boolean z = true;
        for (String str : list) {
            if (VCardConfig.isVersion30(this.mVCardType) || VCardConfig.isVersion40(this.mVCardType)) {
                String stringAsV40ParamValue = VCardConfig.isVersion40(this.mVCardType) ? VCardUtils.toStringAsV40ParamValue(str) : VCardUtils.toStringAsV30ParamValue(str);
                if (!TextUtils.isEmpty(stringAsV40ParamValue)) {
                    if (z) {
                        z = false;
                    } else {
                        this.mBuilder.append(";");
                    }
                    appendTypeParameter(stringAsV40ParamValue);
                }
            } else if (VCardUtils.isV21Word(str)) {
                if (z) {
                    z = false;
                } else {
                    this.mBuilder.append(";");
                }
                appendTypeParameter(str);
            }
        }
    }

    private void appendTypeParameter(String str) {
        appendTypeParameter(this.mBuilder, str);
    }

    private void appendTypeParameter(StringBuilder sb, String str) {
        if (VCardConfig.isVersion40(this.mVCardType) || ((VCardConfig.isVersion30(this.mVCardType) || this.mAppendTypeParamName) && !this.mIsDoCoMo)) {
            sb.append(VCardConstants.PARAM_TYPE).append(VCARD_PARAM_EQUAL);
        }
        sb.append(str);
    }

    private boolean shouldAppendCharsetParam(String... strArr) {
        if (!this.mShouldAppendCharsetParam) {
            return false;
        }
        for (String str : strArr) {
            if (!VCardUtils.containsOnlyPrintableAscii(str)) {
                return true;
            }
        }
        return false;
    }

    private String encodeQuotedPrintable(String str) {
        byte[] bytes;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            bytes = str.getBytes(this.mCharset);
        } catch (UnsupportedEncodingException unused) {
            Log.e(LOG_TAG, "Charset " + this.mCharset + " cannot be used. Try default charset");
            bytes = str.getBytes();
        }
        int i = 0;
        int i2 = 0;
        while (i < bytes.length) {
            sb.append(String.format("=%02X", Byte.valueOf(bytes[i])));
            i++;
            i2 += 3;
            if (i2 >= 67) {
                sb.append("=\r\n");
                i2 = 0;
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0049  */
    /* JADX WARN: Code duplicated, block: B:28:0x004d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0054  */
    /* JADX WARN: Code duplicated, block: B:39:0x0071  */
    private String escapeCharacters(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt != '\r') {
                if (cCharAt != ',') {
                    if (cCharAt == '>') {
                        if (this.mIsDoCoMo) {
                            sb.append('\\');
                            sb.append(cCharAt);
                        } else {
                            sb.append(cCharAt);
                        }
                    } else if (cCharAt != '\\') {
                        if (cCharAt == ';') {
                            sb.append("\\;");
                        } else if (cCharAt == '<') {
                            if (this.mIsDoCoMo) {
                                sb.append('\\');
                                sb.append(cCharAt);
                            } else {
                                sb.append(cCharAt);
                            }
                        } else {
                            sb.append(cCharAt);
                        }
                    } else if (this.mIsV30OrV40) {
                        sb.append("\\\\");
                    } else if (this.mIsDoCoMo) {
                        sb.append('\\');
                        sb.append(cCharAt);
                    } else {
                        sb.append(cCharAt);
                    }
                } else if (this.mIsV30OrV40) {
                    sb.append("\\,");
                } else {
                    sb.append(cCharAt);
                }
            } else if (i + 1 >= length || str.charAt(i) != '\n') {
                sb.append("\\n");
            }
        }
        return sb.toString();
    }

    public String toString() {
        if (!this.mEndAppended) {
            if (this.mIsDoCoMo) {
                appendLine(VCardConstants.PROPERTY_X_CLASS, VCARD_DATA_PUBLIC);
                appendLine(VCardConstants.PROPERTY_X_REDUCTION, "");
                appendLine(VCardConstants.PROPERTY_X_NO, "");
                appendLine(VCardConstants.PROPERTY_X_DCM_HMN_MODE, "");
            }
            appendLine(VCardConstants.PROPERTY_END, VCARD_DATA_VCARD);
            this.mEndAppended = true;
        }
        return this.mBuilder.toString();
    }
}
