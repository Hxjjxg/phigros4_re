package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    public DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory) {
        this(kit, str, str2, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00db  */
    @Override // io.fabric.sdk.android.services.settings.SettingsSpiCall
    public JSONObject invoke(SettingsRequest settingsRequest) throws Throwable {
        String strBody;
        HttpRequest httpRequest;
        String str = AbstractSpiCall.HEADER_REQUEST_ID;
        HttpRequest httpRequest2 = null;
        jSONObject = null;
        JSONObject jSONObject = null;
        try {
            try {
                Map<String, String> queryParamsFor = getQueryParamsFor(settingsRequest);
                httpRequest = getHttpRequest(queryParamsFor);
                try {
                    httpRequest = applyHeadersTo(httpRequest, settingsRequest);
                    Fabric.getLogger().d(Fabric.TAG, "Requesting settings from " + getUrl());
                    Fabric.getLogger().d(Fabric.TAG, "Settings query params were: " + queryParamsFor);
                    Fabric.getLogger().d(Fabric.TAG, "Settings result was: " + httpRequest.code());
                    strBody = httpRequest.body();
                    try {
                        JSONObject jSONObject2 = new JSONObject(strBody);
                        if (httpRequest != null) {
                            Logger logger = Fabric.getLogger();
                            str = "Settings request ID: " + httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                            logger.d(Fabric.TAG, str);
                        }
                        jSONObject = jSONObject2;
                    } catch (Exception e) {
                        e = e;
                        Fabric.getLogger().e(Fabric.TAG, "Failed to retrieve settings from " + getUrl(), e);
                        Fabric.getLogger().d(Fabric.TAG, "Settings response " + strBody);
                        if (httpRequest != null) {
                            Logger logger2 = Fabric.getLogger();
                            str = "Settings request ID: " + httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                            logger2.d(Fabric.TAG, str);
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    strBody = null;
                }
            } catch (Throwable th) {
                th = th;
                httpRequest2 = httpRequest;
                if (httpRequest2 != null) {
                    Fabric.getLogger().d(Fabric.TAG, "Settings request ID: " + httpRequest2.header(str));
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            strBody = null;
            httpRequest = null;
        } catch (Throwable th2) {
            th = th2;
            if (httpRequest2 != null) {
                Fabric.getLogger().d(Fabric.TAG, "Settings request ID: " + httpRequest2.header(str));
            }
            throw th;
        }
        return jSONObject;
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest settingsRequest) {
        HashMap map = new HashMap();
        map.put(BUILD_VERSION_PARAM, settingsRequest.buildVersion);
        map.put(DISPLAY_VERSION_PARAM, settingsRequest.displayVersion);
        map.put("source", Integer.toString(settingsRequest.source));
        if (settingsRequest.iconHash != null) {
            map.put(ICON_HASH, settingsRequest.iconHash);
        }
        String str = settingsRequest.instanceId;
        if (!CommonUtils.isNullOrEmpty(str)) {
            map.put(INSTANCE_PARAM, str);
        }
        return map;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        return httpRequest.header(AbstractSpiCall.HEADER_API_KEY, settingsRequest.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, "android").header(AbstractSpiCall.HEADER_D, settingsRequest.deviceId).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion()).header("Accept", "application/json");
    }
}
