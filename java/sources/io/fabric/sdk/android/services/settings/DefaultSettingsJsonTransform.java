package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class DefaultSettingsJsonTransform implements SettingsJsonTransform {
    DefaultSettingsJsonTransform() {
    }

    @Override // io.fabric.sdk.android.services.settings.SettingsJsonTransform
    public SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject) throws JSONException {
        int iOptInt = jSONObject.optInt(SettingsJsonConstants.SETTINGS_VERSION, 0);
        int iOptInt2 = jSONObject.optInt(SettingsJsonConstants.CACHE_DURATION_KEY, 3600);
        return new SettingsData(getExpiresAtFrom(currentTimeProvider, iOptInt2, jSONObject), buildAppDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.APP_KEY)), buildSessionDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.SESSION_KEY)), buildPromptDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.PROMPT_KEY)), buildFeaturesSessionDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.FEATURES_KEY)), buildAnalyticsSessionDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.ANALYTICS_KEY)), buildBetaSettingsDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.BETA_KEY)), iOptInt, iOptInt2);
    }

    @Override // io.fabric.sdk.android.services.settings.SettingsJsonTransform
    public JSONObject toJson(SettingsData settingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.EXPIRES_AT_KEY, settingsData.expiresAtMillis).put(SettingsJsonConstants.CACHE_DURATION_KEY, settingsData.cacheDuration).put(SettingsJsonConstants.SETTINGS_VERSION, settingsData.settingsVersion).put(SettingsJsonConstants.FEATURES_KEY, toFeaturesJson(settingsData.featuresData)).put(SettingsJsonConstants.ANALYTICS_KEY, toAnalyticsJson(settingsData.analyticsSettingsData)).put(SettingsJsonConstants.BETA_KEY, toBetaJson(settingsData.betaSettingsData)).put(SettingsJsonConstants.APP_KEY, toAppJson(settingsData.appData)).put(SettingsJsonConstants.SESSION_KEY, toSessionJson(settingsData.sessionData)).put(SettingsJsonConstants.PROMPT_KEY, toPromptJson(settingsData.promptData));
    }

    @Override // io.fabric.sdk.android.services.settings.SettingsJsonTransform
    public JSONObject sanitizeTraceInfo(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
        jSONObject2.getJSONObject(SettingsJsonConstants.FEATURES_KEY).remove(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY);
        jSONObject2.remove(SettingsJsonConstants.ANALYTICS_KEY);
        return jSONObject2;
    }

    private AppSettingsData buildAppDataFrom(JSONObject jSONObject) throws JSONException {
        return new AppSettingsData(jSONObject.getString("identifier"), jSONObject.getString("status"), jSONObject.getString("url"), jSONObject.getString(SettingsJsonConstants.APP_REPORTS_URL_KEY), jSONObject.optBoolean(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, false), (jSONObject.has(SettingsJsonConstants.APP_ICON_KEY) && jSONObject.getJSONObject(SettingsJsonConstants.APP_ICON_KEY).has(SettingsJsonConstants.ICON_HASH_KEY)) ? buildIconDataFrom(jSONObject.getJSONObject(SettingsJsonConstants.APP_ICON_KEY)) : null);
    }

    private AppIconSettingsData buildIconDataFrom(JSONObject jSONObject) throws JSONException {
        return new AppIconSettingsData(jSONObject.getString(SettingsJsonConstants.ICON_HASH_KEY), jSONObject.getInt("width"), jSONObject.getInt("height"));
    }

    private FeaturesSettingsData buildFeaturesSessionDataFrom(JSONObject jSONObject) {
        return new FeaturesSettingsData(jSONObject.optBoolean(SettingsJsonConstants.FEATURES_PROMPT_ENABLED_KEY, false), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, true), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, true), jSONObject.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, false));
    }

    private AnalyticsSettingsData buildAnalyticsSessionDataFrom(JSONObject jSONObject) {
        return new AnalyticsSettingsData(jSONObject.optString("url", SettingsJsonConstants.ANALYTICS_URL_DEFAULT), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, 600), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, 8000), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, 1), jSONObject.optInt(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, 100));
    }

    private SessionSettingsData buildSessionDataFrom(JSONObject jSONObject) throws JSONException {
        return new SessionSettingsData(jSONObject.optInt(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, 8), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, 64), jSONObject.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, 64), jSONObject.optInt(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, 255), jSONObject.optBoolean(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, false));
    }

    private PromptSettingsData buildPromptDataFrom(JSONObject jSONObject) throws JSONException {
        return new PromptSettingsData(jSONObject.optString("title", SettingsJsonConstants.PROMPT_TITLE_DEFAULT), jSONObject.optString("message", SettingsJsonConstants.PROMPT_MESSAGE_DEFAULT), jSONObject.optString(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_DEFAULT), jSONObject.optBoolean(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, true), jSONObject.optString(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_DEFAULT), jSONObject.optBoolean(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, true), jSONObject.optString(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_DEFAULT));
    }

    private BetaSettingsData buildBetaSettingsDataFrom(JSONObject jSONObject) throws JSONException {
        return new BetaSettingsData(jSONObject.optString(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), jSONObject.optInt(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, 3600));
    }

    private long getExpiresAtFrom(CurrentTimeProvider currentTimeProvider, long j, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has(SettingsJsonConstants.EXPIRES_AT_KEY)) {
            return jSONObject.getLong(SettingsJsonConstants.EXPIRES_AT_KEY);
        }
        return currentTimeProvider.getCurrentTimeMillis() + (j * 1000);
    }

    private JSONObject toAppJson(AppSettingsData appSettingsData) throws JSONException {
        JSONObject jSONObjectPut = new JSONObject().put("identifier", appSettingsData.identifier).put("status", appSettingsData.status).put("url", appSettingsData.url).put(SettingsJsonConstants.APP_REPORTS_URL_KEY, appSettingsData.reportsUrl).put(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, appSettingsData.updateRequired);
        if (appSettingsData.icon != null) {
            jSONObjectPut.put(SettingsJsonConstants.APP_ICON_KEY, toIconJson(appSettingsData.icon));
        }
        return jSONObjectPut;
    }

    private JSONObject toIconJson(AppIconSettingsData appIconSettingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.ICON_HASH_KEY, appIconSettingsData.hash).put("width", appIconSettingsData.width).put("height", appIconSettingsData.height);
    }

    private JSONObject toFeaturesJson(FeaturesSettingsData featuresSettingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, featuresSettingsData.collectLoggedException).put(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, featuresSettingsData.collectReports).put(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, featuresSettingsData.collectAnalytics);
    }

    private JSONObject toSessionJson(SessionSettingsData sessionSettingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, sessionSettingsData.logBufferSize).put(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, sessionSettingsData.maxChainedExceptionDepth).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, sessionSettingsData.maxCustomExceptionEvents).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, sessionSettingsData.maxCustomKeyValuePairs).put(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, sessionSettingsData.identifierMask).put(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, sessionSettingsData.sendSessionWithoutCrash);
    }

    private JSONObject toAnalyticsJson(AnalyticsSettingsData analyticsSettingsData) throws JSONException {
        return new JSONObject().put("url", analyticsSettingsData.analyticsURL).put(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, analyticsSettingsData.flushIntervalSeconds).put(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, analyticsSettingsData.maxByteSizePerFile).put(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, analyticsSettingsData.maxFileCountPerSend).put(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, analyticsSettingsData.maxPendingSendFileCount);
    }

    private JSONObject toBetaJson(BetaSettingsData betaSettingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, betaSettingsData.updateUrl).put(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, betaSettingsData.updateSuspendDurationSeconds);
    }

    private JSONObject toPromptJson(PromptSettingsData promptSettingsData) throws JSONException {
        return new JSONObject().put("title", promptSettingsData.title).put("message", promptSettingsData.message).put(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, promptSettingsData.sendButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, promptSettingsData.showCancelButton).put(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, promptSettingsData.cancelButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, promptSettingsData.showAlwaysSendButton).put(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, promptSettingsData.alwaysSendButtonTitle);
    }
}
