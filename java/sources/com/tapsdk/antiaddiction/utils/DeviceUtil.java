package com.tapsdk.antiaddiction.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class DeviceUtil {
    public static int getOperatorInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return 0;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        Log.i("TdsRegionHelper", "NetworkOperator=" + networkOperator);
        if (networkOperator != null) {
            return (networkOperator.startsWith("460") || networkOperator.startsWith("461")) ? 1 : 0;
        }
        return 0;
    }
}
