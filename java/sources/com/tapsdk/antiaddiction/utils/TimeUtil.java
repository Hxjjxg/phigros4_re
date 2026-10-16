package com.tapsdk.antiaddiction.utils;

import android.text.format.DateFormat;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class TimeUtil {
    public static final int SECONDS_IN_DAY = 86400;

    public static boolean isHoliday(long j) {
        return AntiAddictionSettings.getInstance().isHoliday(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Long.valueOf(j)));
    }

    public static int diffNow(String str, long j) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        if (date == null) {
            return Integer.MAX_VALUE;
        }
        return (int) ((date.getTime() - new Date(j).getTime()) / 1000);
    }

    public static Date getTomorrowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) + 1);
        return calendar.getTime();
    }

    public static int getTimeToNightStrict(String str, String str2, long j) {
        AntiAddictionLogger.d("getTimeToNightStrict start");
        try {
            Date date = new Date(j);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String str3 = simpleDateFormat.format(date);
            String str4 = str3 + " " + str2;
            long time = simpleDateFormat2.parse(str3 + " " + str).getTime();
            long time2 = simpleDateFormat2.parse(str4).getTime();
            if (j >= time && j <= time2) {
                return (int) ((time2 - j) / 1000);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public static String getFullTime(long j) {
        try {
            return DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(j)).toString();
        } catch (NumberFormatException unused) {
            return "";
        }
    }

    public static String getRestTime(String str, int i) {
        if (i < 60) {
            int iIndexOf = str.indexOf("# ${remaining} #");
            if (iIndexOf < 0) {
                return str;
            }
            return str.substring(0, iIndexOf) + str.substring(iIndexOf).replace("# ${remaining} #", String.valueOf(i)).replace("分钟", "秒");
        }
        return str.replace("# ${remaining} #", String.valueOf((int) Math.ceil((((double) i) * 1.0d) / 60.0d)));
    }
}
