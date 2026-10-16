package com.tapsdk.antiaddiction.models;

import android.os.SystemClock;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class ServerTimeModel {
    private static long lastSystemElapsed = 0;
    private static long recentServerTime = 0;
    private static boolean useLocalTime = false;

    public static void setRecentServerTime(long j) {
        recentServerTime = j;
        useLocalTime = false;
        lastSystemElapsed = SystemClock.elapsedRealtime();
    }

    public static void saveRecentLocalTime() {
        if (recentServerTime > 0) {
            return;
        }
        recentServerTime = System.currentTimeMillis() / 1000;
        useLocalTime = true;
        lastSystemElapsed = SystemClock.elapsedRealtime();
    }

    public static long getRecentServerTime() {
        long jElapsedRealtime = recentServerTime + ((lastSystemElapsed > 0 ? SystemClock.elapsedRealtime() - lastSystemElapsed : 0L) / 1000);
        AntiAddictionLogger.d("current serverTime = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1000 * jElapsedRealtime)));
        return jElapsedRealtime;
    }

    public static boolean isUseLocalTime() {
        return useLocalTime;
    }
}
