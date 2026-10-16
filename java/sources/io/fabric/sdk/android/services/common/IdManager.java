package io.fabric.sdk.android.services.common;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.google.android.gms.games.quest.Quests;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class IdManager {
    public static final String APPLICATION_INSTALL_ID_FIELD = "APPLICATION_INSTALLATION_UUID";
    private static final String BAD_ANDROID_ID = "9774d56d682e549c";
    public static final String BETA_DEVICE_TOKEN_FIELD = "font_token";
    private static final String BLUETOOTH_ERROR_MESSAGE = "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()";
    public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
    public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
    public static final String DEFAULT_VERSION_NAME = "0.0";
    public static final String MODEL_FIELD = "model";
    public static final String OS_VERSION_FIELD = "os_version";
    private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
    private static final String SDK_ASSETS_ROOT = ".TwitterSdk";
    private final Context appContext;
    private final String appIdentifier;
    private final String appInstallIdentifier;
    private final boolean collectHardwareIds;
    private final boolean collectUserIds;
    private final ReentrantLock installationIdLock = new ReentrantLock();
    private final InstallerPackageNameProvider installerPackageNameProvider;
    private final Collection<Kit> kits;
    private static final Pattern ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
    private static final String FORWARD_SLASH_REGEX = Pattern.quote("/");

    private String[] getTwitterSdkAssetsList() {
        return new String[0];
    }

    public enum DeviceIdentifierType {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(Quests.SELECT_COMPLETED_UNCLAIMED),
        ANDROID_SERIAL(Quests.SELECT_ENDING_SOON),
        ANDROID_ADVERTISING_ID(Quests.SELECT_RECENTLY_FAILED);

        public final int protobufIndex;

        DeviceIdentifierType(int i) {
            this.protobufIndex = i;
        }
    }

    public IdManager(Context context, String str, String str2, Collection<Kit> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        }
        if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        }
        this.appContext = context;
        this.appIdentifier = str;
        this.appInstallIdentifier = str2;
        this.kits = collection;
        this.installerPackageNameProvider = new InstallerPackageNameProvider();
        boolean booleanResourceValue = CommonUtils.getBooleanResourceValue(context, COLLECT_DEVICE_IDENTIFIERS, true);
        this.collectHardwareIds = booleanResourceValue;
        if (!booleanResourceValue) {
            Fabric.getLogger().d(Fabric.TAG, "Device ID collection disabled for " + context.getPackageName());
        }
        boolean booleanResourceValue2 = CommonUtils.getBooleanResourceValue(context, COLLECT_USER_IDENTIFIERS, true);
        this.collectUserIds = booleanResourceValue2;
        if (booleanResourceValue2) {
            return;
        }
        Fabric.getLogger().d(Fabric.TAG, "User information collection disabled for " + context.getPackageName());
    }

    public String createIdHeaderValue(String str, String str2) {
        try {
            Cipher cipherCreateCipher = CommonUtils.createCipher(1, CommonUtils.sha1(str + str2.replaceAll("\\.", new StringBuilder("slc").reverse().toString())));
            JSONObject jSONObject = new JSONObject();
            addAppInstallIdTo(jSONObject);
            addDeviceIdentifiersTo(jSONObject);
            addOsVersionTo(jSONObject);
            addModelName(jSONObject);
            if (jSONObject.length() <= 0) {
                return "";
            }
            try {
                return CommonUtils.hexify(cipherCreateCipher.doFinal(jSONObject.toString().getBytes()));
            } catch (GeneralSecurityException e) {
                Fabric.getLogger().e(Fabric.TAG, "Could not encrypt IDs", e);
                return "";
            }
        } catch (GeneralSecurityException e2) {
            Fabric.getLogger().e(Fabric.TAG, "Could not create cipher to encrypt headers.", e2);
            return "";
        }
    }

    private void addAppInstallIdTo(JSONObject jSONObject) {
        try {
            jSONObject.put(APPLICATION_INSTALL_ID_FIELD.toLowerCase(Locale.US), getAppInstallIdentifier());
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not write application id to JSON", e);
        }
    }

    private void addDeviceIdentifiersTo(JSONObject jSONObject) {
        for (Map.Entry<DeviceIdentifierType, String> entry : getDeviceIdentifiers().entrySet()) {
            try {
                jSONObject.put(entry.getKey().name().toLowerCase(Locale.US), entry.getValue());
            } catch (Exception e) {
                Fabric.getLogger().e(Fabric.TAG, "Could not write value to JSON: " + entry.getKey().name(), e);
            }
        }
    }

    private void addOsVersionTo(JSONObject jSONObject) {
        try {
            jSONObject.put(OS_VERSION_FIELD, getOsVersionString());
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not write OS version to JSON", e);
        }
    }

    private void addModelName(JSONObject jSONObject) {
        try {
            jSONObject.put(MODEL_FIELD, getModelName());
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not write model to JSON", e);
        }
    }

    public boolean canCollectUserIds() {
        return this.collectUserIds;
    }

    private boolean hasPermission(String str) {
        return this.appContext.checkCallingPermission(str) == 0;
    }

    private String formatId(String str) {
        if (str == null) {
            return null;
        }
        return ID_PATTERN.matcher(str).replaceAll("").toLowerCase(Locale.US);
    }

    public String getAppInstallIdentifier() {
        String str = this.appInstallIdentifier;
        if (str != null) {
            return str;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.appContext);
        String string = sharedPrefs.getString(PREFKEY_INSTALLATION_UUID, null);
        return string == null ? createInstallationUUID(sharedPrefs) : string;
    }

    public String getAppIdentifier() {
        return this.appIdentifier;
    }

    public String getOsVersionString() {
        return String.format(Locale.US, "%s/%s", removeForwardSlashesIn(Build.VERSION.RELEASE), removeForwardSlashesIn(Build.VERSION.INCREMENTAL));
    }

    public String getModelName() {
        return String.format(Locale.US, "%s/%s", removeForwardSlashesIn(Build.MANUFACTURER), removeForwardSlashesIn(Build.MODEL));
    }

    private String removeForwardSlashesIn(String str) {
        return str.replaceAll(FORWARD_SLASH_REGEX, "");
    }

    public String getDeviceUUID() {
        if (!this.collectHardwareIds) {
            return "";
        }
        String androidId = getAndroidId();
        if (androidId != null) {
            return androidId;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.appContext);
        String string = sharedPrefs.getString(PREFKEY_INSTALLATION_UUID, null);
        return string == null ? createInstallationUUID(sharedPrefs) : string;
    }

    private String createInstallationUUID(SharedPreferences sharedPreferences) {
        this.installationIdLock.lock();
        try {
            String string = sharedPreferences.getString(PREFKEY_INSTALLATION_UUID, null);
            if (string == null) {
                string = formatId(UUID.randomUUID().toString());
                sharedPreferences.edit().putString(PREFKEY_INSTALLATION_UUID, string).commit();
            }
            return string;
        } finally {
            this.installationIdLock.unlock();
        }
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        HashMap map = new HashMap();
        for (Object obj : this.kits) {
            if (obj instanceof DeviceIdentifierProvider) {
                for (Map.Entry<DeviceIdentifierType, String> entry : ((DeviceIdentifierProvider) obj).getDeviceIdentifiers().entrySet()) {
                    putNonNullIdInto(map, entry.getKey(), entry.getValue());
                }
            }
        }
        putNonNullIdInto(map, DeviceIdentifierType.ANDROID_ID, getAndroidId());
        putNonNullIdInto(map, DeviceIdentifierType.ANDROID_DEVICE_ID, getTelephonyId());
        putNonNullIdInto(map, DeviceIdentifierType.ANDROID_SERIAL, getSerialNumber());
        putNonNullIdInto(map, DeviceIdentifierType.WIFI_MAC_ADDRESS, getWifiMacAddress());
        putNonNullIdInto(map, DeviceIdentifierType.BLUETOOTH_MAC_ADDRESS, getBluetoothMacAddress());
        putNonNullIdInto(map, DeviceIdentifierType.ANDROID_ADVERTISING_ID, getAdvertisingId());
        return Collections.unmodifiableMap(map);
    }

    public String getInstallerPackageName() {
        return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
    }

    public String getAdvertisingId() {
        AdvertisingInfo advertisingInfo;
        if (!this.collectHardwareIds || (advertisingInfo = new AdvertisingInfoProvider(this.appContext).getAdvertisingInfo()) == null) {
            return null;
        }
        return advertisingInfo.advertisingId;
    }

    private void putNonNullIdInto(Map<DeviceIdentifierType, String> map, DeviceIdentifierType deviceIdentifierType, String str) {
        if (str != null) {
            map.put(deviceIdentifierType, str);
        }
    }

    public String getAndroidId() {
        if (this.collectHardwareIds) {
            String string = Settings.Secure.getString(this.appContext.getContentResolver(), "android_id");
            if (!BAD_ANDROID_ID.equals(string)) {
                return formatId(string);
            }
        }
        return null;
    }

    public String getTelephonyId() {
        TelephonyManager telephonyManager;
        if (this.collectHardwareIds && hasPermission("android.permission.READ_PHONE_STATE") && (telephonyManager = (TelephonyManager) this.appContext.getSystemService("phone")) != null) {
            return formatId(telephonyManager.getDeviceId());
        }
        return null;
    }

    public String getWifiMacAddress() {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        if (!this.collectHardwareIds || !hasPermission("android.permission.ACCESS_WIFI_STATE") || (wifiManager = (WifiManager) this.appContext.getSystemService("wifi")) == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return null;
        }
        return formatId(connectionInfo.getMacAddress());
    }

    public String getBluetoothMacAddress() {
        if (!this.collectHardwareIds || !hasPermission("android.permission.BLUETOOTH")) {
            return null;
        }
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                return null;
            }
            formatId(defaultAdapter.getAddress());
            return null;
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, BLUETOOTH_ERROR_MESSAGE, e);
            return null;
        }
    }

    public String getSerialNumber() {
        if (this.collectHardwareIds) {
            try {
                return formatId((String) Build.class.getField("SERIAL").get(null));
            } catch (Exception e) {
                Fabric.getLogger().e(Fabric.TAG, "Could not retrieve android.os.Build.SERIAL value", e);
            }
        }
        return null;
    }
}
