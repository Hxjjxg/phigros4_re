package com.tapsdk.antiaddiction.skynet;

import androidx.core.app.NotificationCompat;
import com.tapsdk.antiaddiction.models.ServerTimeModel;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.RespInterceptor;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tds.common.entities.AccessToken;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.gson.JsonObject;
import com.voxelbusters.nativeplugins.defines.Keys;

/* JADX INFO: loaded from: classes.dex */
public class NetServerErrorChecker implements RespInterceptor {
    private String extractErrorDescription(JsonObject jsonObject) {
        try {
            return (!jsonObject.has("error_description") || jsonObject.get("error_description").isJsonNull() || jsonObject.get("error_description").getAsString().length() <= 0) ? "" : jsonObject.get("error_description").getAsString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    private String extractErrorType(JsonObject jsonObject) {
        try {
            return (!jsonObject.has("error") || jsonObject.get("error").isJsonNull() || jsonObject.get("error").getAsString().length() <= 0) ? "" : jsonObject.get("error").getAsString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    private int extractErrorCode(JsonObject jsonObject) {
        try {
            if (!jsonObject.has(NetworkStateModel.PARAM_CODE) || jsonObject.get(NetworkStateModel.PARAM_CODE).isJsonNull()) {
                return 0;
            }
            return jsonObject.get(NetworkStateModel.PARAM_CODE).getAsInt();
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    private String extractErrorMsg(JsonObject jsonObject) {
        try {
            return (!jsonObject.has(NotificationCompat.CATEGORY_MESSAGE) || jsonObject.get(NotificationCompat.CATEGORY_MESSAGE).isJsonNull() || jsonObject.get(NotificationCompat.CATEGORY_MESSAGE).getAsString().length() <= 0) ? "" : jsonObject.get(NotificationCompat.CATEGORY_MESSAGE).getAsString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.RespInterceptor
    public void invoke(JsonObject jsonObject, int i) {
        String str;
        String str2;
        String strExtractErrorMsg;
        String str3;
        String str4;
        String str5;
        int iExtractErrorCode;
        if (jsonObject.has(Keys.Billing.Validation.SUCCESS)) {
            int iExtractErrorCode2 = 0;
            if (!jsonObject.get(Keys.Billing.Validation.SUCCESS).getAsBoolean()) {
                if (jsonObject.has(AccessToken.ROOT_ELEMENT_NAME)) {
                    JsonObject asJsonObject = jsonObject.getAsJsonObject(AccessToken.ROOT_ELEMENT_NAME);
                    String strExtractErrorDescription = extractErrorDescription(asJsonObject);
                    String strExtractErrorType = extractErrorType(asJsonObject);
                    String strExtractErrorMsg2 = extractErrorMsg(asJsonObject);
                    iExtractErrorCode = extractErrorCode(asJsonObject);
                    str4 = strExtractErrorType;
                    str3 = strExtractErrorDescription;
                    str5 = strExtractErrorMsg2;
                } else {
                    str3 = "";
                    str4 = str3;
                    str5 = str4;
                    iExtractErrorCode = 0;
                }
                try {
                    if (jsonObject.has("now")) {
                        ServerTimeModel.setRecentServerTime(jsonObject.get("now").getAsLong());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AntiAddictionLogger.d("net response {message:" + str5 + ", statusCode:" + i);
                throw new AntiServerException(str3, i, str4, iExtractErrorCode, str5);
            }
            if (i != 200) {
                if (jsonObject.has(AccessToken.ROOT_ELEMENT_NAME)) {
                    JsonObject asJsonObject2 = jsonObject.getAsJsonObject(AccessToken.ROOT_ELEMENT_NAME);
                    String strExtractErrorDescription2 = extractErrorDescription(asJsonObject2);
                    String strExtractErrorType2 = extractErrorType(asJsonObject2);
                    iExtractErrorCode2 = extractErrorCode(asJsonObject2);
                    strExtractErrorMsg = extractErrorMsg(asJsonObject2);
                    str2 = strExtractErrorType2;
                    str = strExtractErrorDescription2;
                } else {
                    str = "";
                    str2 = str;
                    strExtractErrorMsg = str2;
                }
                int i2 = iExtractErrorCode2;
                try {
                    if (jsonObject.has("now")) {
                        ServerTimeModel.setRecentServerTime(jsonObject.get("now").getAsLong());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                AntiAddictionLogger.d("net response {message:" + strExtractErrorMsg + ", statusCode:" + i);
                throw new AntiServerException(str, i, str2, i2, strExtractErrorMsg);
            }
            return;
        }
        throw new AntiServerException("illegal response", i);
    }
}
