package io.fabric.sdk.android.services.settings;

import android.content.res.Resources;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
abstract class AbstractAppSpiCall extends AbstractSpiCall implements AppSpiCall {
    public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
    public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
    public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
    public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
    public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
    public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
    public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
    public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
    public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
    public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
    public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
    public static final String APP_NAME_PARAM = "app[name]";
    public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries]";
    public static final String APP_SOURCE_PARAM = "app[source]";
    static final String ICON_CONTENT_TYPE = "application/octet-stream";
    static final String ICON_FILE_NAME = "icon.png";

    public AbstractAppSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    @Override // io.fabric.sdk.android.services.settings.AppSpiCall
    public boolean invoke(AppRequestData appRequestData) {
        HttpRequest httpRequestApplyMultipartDataTo = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), appRequestData), appRequestData);
        Fabric.getLogger().d(Fabric.TAG, "Sending app info to " + getUrl());
        if (appRequestData.icon != null) {
            Fabric.getLogger().d(Fabric.TAG, "App icon hash is " + appRequestData.icon.hash);
            Fabric.getLogger().d(Fabric.TAG, "App icon size is " + appRequestData.icon.width + "x" + appRequestData.icon.height);
        }
        int iCode = httpRequestApplyMultipartDataTo.code();
        Fabric.getLogger().d(Fabric.TAG, (HttpRequest.METHOD_POST.equals(httpRequestApplyMultipartDataTo.method()) ? "Create" : "Update") + " app request ID: " + httpRequestApplyMultipartDataTo.header(AbstractSpiCall.HEADER_REQUEST_ID));
        Fabric.getLogger().d(Fabric.TAG, "Result was " + iCode);
        return ResponseParser.parse(iCode) == 0;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, AppRequestData appRequestData) {
        return httpRequest.header(AbstractSpiCall.HEADER_API_KEY, appRequestData.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, "android").header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
    }

    private HttpRequest applyMultipartDataTo(HttpRequest httpRequest, AppRequestData appRequestData) {
        HttpRequest httpRequestPart = httpRequest.part(APP_IDENTIFIER_PARAM, appRequestData.appId).part(APP_NAME_PARAM, appRequestData.name).part(APP_DISPLAY_VERSION_PARAM, appRequestData.displayVersion).part(APP_BUILD_VERSION_PARAM, appRequestData.buildVersion).part(APP_SOURCE_PARAM, Integer.valueOf(appRequestData.source)).part(APP_MIN_SDK_VERSION_PARAM, appRequestData.minSdkVersion).part(APP_BUILT_SDK_VERSION_PARAM, appRequestData.builtSdkVersion);
        if (!CommonUtils.isNullOrEmpty(appRequestData.instanceIdentifier)) {
            httpRequestPart.part(APP_INSTANCE_IDENTIFIER_PARAM, appRequestData.instanceIdentifier);
        }
        if (appRequestData.icon != null) {
            InputStream inputStreamOpenRawResource = null;
            try {
                try {
                    inputStreamOpenRawResource = this.kit.getContext().getResources().openRawResource(appRequestData.icon.iconResourceId);
                    httpRequestPart.part(APP_ICON_HASH_PARAM, appRequestData.icon.hash).part(APP_ICON_DATA_PARAM, ICON_FILE_NAME, ICON_CONTENT_TYPE, inputStreamOpenRawResource).part(APP_ICON_WIDTH_PARAM, Integer.valueOf(appRequestData.icon.width)).part(APP_ICON_HEIGHT_PARAM, Integer.valueOf(appRequestData.icon.height));
                } catch (Resources.NotFoundException e) {
                    Fabric.getLogger().e(Fabric.TAG, "Failed to find app icon with resource ID: " + appRequestData.icon.iconResourceId, e);
                }
                CommonUtils.closeOrLog(inputStreamOpenRawResource, "Failed to close app icon InputStream.");
            } catch (Throwable th) {
                CommonUtils.closeOrLog(inputStreamOpenRawResource, "Failed to close app icon InputStream.");
                throw th;
            }
        }
        if (appRequestData.sdkKits != null) {
            for (Kit kit : appRequestData.sdkKits) {
                httpRequestPart.part(sdkKitParamName(kit), kit.getVersion() == null ? "" : kit.getVersion());
            }
        }
        return httpRequestPart;
    }

    String sdkKitParamName(Kit kit) {
        return "app[build][libraries][" + (kit.getIdentifier() == null ? "" : kit.getIdentifier()) + "]";
    }
}
