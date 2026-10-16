package com.tapsdk.antiaddiction.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.entities.TwoTuple;
import com.tapsdk.antiaddiction.entities.response.RealNameConfig;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.skynet.okio.ByteString;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddiction.utils.FileUtil;
import com.tapsdk.antiaddiction.utils.TimeUtil;
import com.tds.gson.Gson;
import com.tds.gson.GsonBuilder;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionSettings {
    public static final String LOCAL_HANDLE_STRATEGY = "local";
    public static final String SERVER_HANDLE_STRATEGY = "server";
    public static final String TIME_RANGE_MODE = "time_range";
    private final String SP_ANTI_ADDICTION_UI_TIP;
    private final String SP_REAL_NAME_TIP_KEY;
    private final String SP_USER_ANTI_CONFIG_KEY;
    private final Gson gson;
    private Set<String> holidaySet;
    private RealNameConfig realNameConfig;
    private final Map<String, SharedPreferences> spCache;
    private UserAntiConfig userAntiConfig;

    static class Holder {
        static AntiAddictionSettings INSTANCE = new AntiAddictionSettings();

        Holder() {
        }
    }

    private AntiAddictionSettings() {
        this.spCache = new HashMap();
        this.userAntiConfig = null;
        this.realNameConfig = null;
        this.holidaySet = new HashSet();
        this.gson = new GsonBuilder().create();
        this.SP_ANTI_ADDICTION_UI_TIP = "SP_AntiAddictionUiTip";
        this.SP_REAL_NAME_TIP_KEY = "realNameTipKey";
        this.SP_USER_ANTI_CONFIG_KEY = "userAntiConfigKey";
    }

    public static AntiAddictionSettings getInstance() {
        return Holder.INSTANCE;
    }

    private String getSPNameByUserId(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(ByteString.encodeString(str, StandardCharsets.UTF_8).md5().hex()).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        }
        return sb.append(Constants.CacheData.TIMING_FILE_SUFFIX).toString();
    }

    public SharedPreferences getSpecificSharedPreference(Context context, String str) {
        if (this.spCache.get(str) != null) {
            return this.spCache.get(str);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        this.spCache.put(str, sharedPreferences);
        return sharedPreferences;
    }

    public void clearAuthInputInfo(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, getSPNameByUserId(str)).edit();
        editorEdit.putString(Constants.CacheData.AUTH_INPUT_INFO, "");
        editorEdit.apply();
    }

    public TwoTuple<String, Integer> getOldCacheUser(Context context, String str) {
        SharedPreferences specificSharedPreference = getSpecificSharedPreference(context, getSPNameByUserId(str));
        return new TwoTuple<>(specificSharedPreference.getString(Constants.CacheData.ACCESS_TOKEN, ""), Integer.valueOf(specificSharedPreference.getInt(Constants.CacheData.AGE_LIMIT, -1)));
    }

    public void clearOldCacheUser(Context context, String str) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, getSPNameByUserId(str)).edit();
        editorEdit.putString(Constants.CacheData.ACCESS_TOKEN, "");
        editorEdit.putInt(Constants.CacheData.AGE_LIMIT, -1);
        editorEdit.apply();
    }

    public void cacheUserInfo(Context context, String str, String str2) {
        if (str2 == null || str2.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, getSPNameByUserId(str)).edit();
        editorEdit.putString(Constants.CacheData.ACCESS_TOKEN_V2, str2);
        editorEdit.apply();
    }

    public String getCachedUserInfo(Context context, String str) {
        return getSpecificSharedPreference(context, getSPNameByUserId(str)).getString(Constants.CacheData.ACCESS_TOKEN_V2, "");
    }

    public void clearCachedUserInfo(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, getSPNameByUserId(str)).edit();
        editorEdit.putInt(Constants.CacheData.AGE_LIMIT, -1);
        editorEdit.putString(Constants.CacheData.ACCESS_TOKEN_V2, "");
        editorEdit.putString(Constants.CacheData.ACCESS_TOKEN, "");
        editorEdit.putString("userAntiConfigKey", "");
        editorEdit.apply();
    }

    public void setUserAntiConfig(UserAntiConfig userAntiConfig) {
        if (userAntiConfig == null || userAntiConfig.localConfig == null) {
            AntiAddictionLogger.w("illegal arguments:".concat(userAntiConfig == null ? "userAntiConfig" : "UIConfig"));
        } else {
            initHolidaySet(userAntiConfig.localConfig.timeRangeConfig.holidays);
            this.userAntiConfig = userAntiConfig;
        }
    }

    public void setCurrentRealNameConfig(RealNameConfig realNameConfig) {
        this.realNameConfig = realNameConfig;
    }

    public RealNameConfig getRealNameConfig() {
        return this.realNameConfig;
    }

    public void saveRealNameConfig(Context context, RealNameConfig realNameConfig) {
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, "SP_AntiAddictionUiTip").edit();
        editorEdit.putString("realNameTipKey", this.gson.toJson(realNameConfig));
        editorEdit.apply();
    }

    public RealNameConfig getRealNameConfigFromCache(Context context) {
        String string = getSpecificSharedPreference(context, "SP_AntiAddictionUiTip").getString("realNameTipKey", "");
        if (string.length() > 0) {
            return (RealNameConfig) this.gson.fromJson(string, RealNameConfig.class);
        }
        return null;
    }

    public boolean hasShowRealNameToast(Context context, String str) {
        return getSpecificSharedPreference(context, "SP_AntiAddictionUiTip").getBoolean(ByteString.encodeString(str, StandardCharsets.UTF_8).md5().hex() + "_RealNameToast", false);
    }

    public void updateShowRealNameToast(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, "SP_AntiAddictionUiTip").edit();
        editorEdit.putBoolean(ByteString.encodeString(str, StandardCharsets.UTF_8).md5().hex() + "_RealNameToast", true);
        editorEdit.apply();
    }

    public UserAntiConfig getUserAntiConfig() {
        return this.userAntiConfig;
    }

    public UserAntiConfig getUserAntiConfigFromCache(Context context, String str) {
        String string = getSpecificSharedPreference(context, getSPNameByUserId(str)).getString("userAntiConfigKey", "");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return (UserAntiConfig) this.gson.fromJson(string, UserAntiConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveUserAntiConfigToCache(Context context, String str, UserAntiConfig userAntiConfig) {
        if (userAntiConfig != null) {
            String json = this.gson.toJson(userAntiConfig);
            SharedPreferences.Editor editorEdit = getSpecificSharedPreference(context, getSPNameByUserId(str)).edit();
            editorEdit.putString("userAntiConfigKey", json);
            editorEdit.apply();
        }
    }

    public RealNameConfig getDefaultRealNameConfig(Context context) {
        try {
            return (RealNameConfig) this.gson.fromJson(FileUtil.getJsonFromAssetsFile(context, "default_config.json"), RealNameConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initHolidaySet(List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.holidaySet = new HashSet(list);
    }

    public boolean isHoliday(String str) {
        return this.holidaySet.contains(str);
    }

    public Map<String, Object> generateAlertMessage(int i, String str, String str2) {
        AntiAddictionLogger.d("-------generateAlertMessage-------");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            UserAntiConfig.UITipText uITipText = getInstance().getUserAntiConfig().localConfig.timeRangeConfig.uiTipText;
            if (i <= 0) {
                String str3 = uITipText.reject.tipTitle;
                str2 = uITipText.reject.tipDescription;
                str = str3;
            } else {
                String str4 = uITipText.allow.tipTitle;
                String restTime = TimeUtil.getRestTime(uITipText.allow.tipDescription, i);
                str = str4;
                str2 = restTime;
            }
        }
        HashMap map = new HashMap();
        map.put("title", str);
        map.put(Constants.MsgExtraParams.DESCRIPTION, str2);
        AntiAddictionLogger.d("generateAlertMessage:" + this.gson.toJson(map));
        return map;
    }
}
