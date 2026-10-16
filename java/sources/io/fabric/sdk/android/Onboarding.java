package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.taptap.sdk.constant.LoginConstants;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
class Onboarding extends Kit<Boolean> {
    static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private String applicationLabel;
    private String installerPackageName;
    private final Collection<Kit> kits;
    private PackageInfo packageInfo;
    private PackageManager packageManager;
    private String packageName;
    private final HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
    private String targetAndroidSdkVersion;
    private String versionCode;
    private String versionName;

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "1.1.1.32";
    }

    public Onboarding(Collection<Kit> collection) {
        this.kits = collection;
    }

    @Override // io.fabric.sdk.android.Kit
    protected boolean onPreExecute() {
        try {
            this.installerPackageName = getIdManager().getInstallerPackageName();
            this.packageManager = getContext().getPackageManager();
            String packageName = getContext().getPackageName();
            this.packageName = packageName;
            PackageInfo packageInfo = this.packageManager.getPackageInfo(packageName, 0);
            this.packageInfo = packageInfo;
            this.versionCode = Integer.toString(packageInfo.versionCode);
            this.versionName = this.packageInfo.versionName == null ? IdManager.DEFAULT_VERSION_NAME : this.packageInfo.versionName;
            this.applicationLabel = this.packageManager.getApplicationLabel(getContext().getApplicationInfo()).toString();
            this.targetAndroidSdkVersion = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed init", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.fabric.sdk.android.Kit
    public Boolean doInBackground() throws Throwable {
        SettingsData settingsDataAwaitSettingsData;
        boolean zPerformAutoConfigure;
        String appIconHashOrNull = CommonUtils.getAppIconHashOrNull(getContext());
        try {
            Settings.getInstance().initialize(this, this.idManager, this.requestFactory, this.versionCode, this.versionName, getOverridenSpiEndpoint()).loadSettingsData();
            settingsDataAwaitSettingsData = Settings.getInstance().awaitSettingsData();
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Error dealing with settings", e);
            settingsDataAwaitSettingsData = null;
        }
        if (settingsDataAwaitSettingsData != null) {
            try {
                zPerformAutoConfigure = performAutoConfigure(appIconHashOrNull, settingsDataAwaitSettingsData.appData, this.kits);
            } catch (Exception e2) {
                Fabric.getLogger().e(Fabric.TAG, "Error performing auto configuration.", e2);
                zPerformAutoConfigure = false;
            }
        } else {
            zPerformAutoConfigure = false;
        }
        return Boolean.valueOf(zPerformAutoConfigure);
    }

    private boolean performAutoConfigure(String str, AppSettingsData appSettingsData, Collection<Kit> collection) {
        if (AppSettingsData.STATUS_NEW.equals(appSettingsData.status)) {
            if (performCreateApp(str, appSettingsData, collection)) {
                return Settings.getInstance().loadSettingsSkippingCache();
            }
            Fabric.getLogger().e(Fabric.TAG, "Failed to create app with Crashlytics service.", null);
            return false;
        }
        if (AppSettingsData.STATUS_CONFIGURED.equals(appSettingsData.status)) {
            return Settings.getInstance().loadSettingsSkippingCache();
        }
        if (appSettingsData.updateRequired) {
            Fabric.getLogger().d(Fabric.TAG, "Server says an update is required - forcing a full App update.");
            performUpdateApp(str, appSettingsData, collection);
        }
        return true;
    }

    private boolean performCreateApp(String str, AppSettingsData appSettingsData, Collection<Kit> collection) {
        return new CreateAppSpiCall(this, getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke(buildAppRequest(IconRequest.build(getContext(), str), collection));
    }

    private boolean performUpdateApp(String str, AppSettingsData appSettingsData, Collection<Kit> collection) {
        return performUpdateApp(appSettingsData, IconRequest.build(getContext(), str), collection);
    }

    private boolean performUpdateApp(AppSettingsData appSettingsData, IconRequest iconRequest, Collection<Kit> collection) {
        return new UpdateAppSpiCall(this, getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke(buildAppRequest(iconRequest, collection));
    }

    private AppRequestData buildAppRequest(IconRequest iconRequest, Collection<Kit> collection) {
        Context context = getContext();
        return new AppRequestData(ApiKey.getApiKey(context, Fabric.isDebuggable()), getIdManager().getAppIdentifier(), this.versionName, this.versionCode, CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(context)), this.applicationLabel, DeliveryMechanism.determineFrom(this.installerPackageName).getId(), this.targetAndroidSdkVersion, LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0, iconRequest, collection);
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getContext(), CRASHLYTICS_API_ENDPOINT);
    }
}
