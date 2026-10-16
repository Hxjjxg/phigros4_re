package com.tapsdk.antiaddiction.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes.dex */
public class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        return (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
