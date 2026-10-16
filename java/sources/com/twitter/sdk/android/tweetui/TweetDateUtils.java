package com.twitter.sdk.android.tweetui;

import android.content.res.Resources;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
final class TweetDateUtils {
    static final long INVALID_DATE = -1;
    static final SimpleDateFormat DATE_TIME_RFC822 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    static final SimpleDateFormat RELATIVE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);

    private TweetDateUtils() {
    }

    static long apiTimeToLong(String str) {
        if (str == null) {
            return -1L;
        }
        try {
            return DATE_TIME_RFC822.parse(str).getTime();
        } catch (ParseException unused) {
            return -1L;
        }
    }

    static boolean isValidTimestamp(String str) {
        return apiTimeToLong(str) != -1;
    }

    public static String dotPrefix(String str) {
        return str.charAt(0) == 8226 ? str : "• " + str;
    }

    static String getRelativeTimeString(Resources resources, long j, long j2) {
        long j3 = j - j2;
        if (j3 < 0) {
            SimpleDateFormat simpleDateFormat = RELATIVE_DATE_FORMAT;
            simpleDateFormat.applyPattern(resources.getString(R.string.tw__relative_date_format_long));
            return simpleDateFormat.format(new Date(j2));
        }
        if (j3 < 60000) {
            int i = (int) (j3 / 1000);
            return resources.getQuantityString(R.plurals.tw__time_secs, i, Integer.valueOf(i));
        }
        if (j3 < 3600000) {
            int i2 = (int) (j3 / 60000);
            return resources.getQuantityString(R.plurals.tw__time_mins, i2, Integer.valueOf(i2));
        }
        if (j3 < 86400000) {
            int i3 = (int) (j3 / 3600000);
            return resources.getQuantityString(R.plurals.tw__time_hours, i3, Integer.valueOf(i3));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        Date date = new Date(j2);
        if (calendar.get(1) == calendar2.get(1)) {
            RELATIVE_DATE_FORMAT.applyPattern(resources.getString(R.string.tw__relative_date_format_short));
        } else {
            RELATIVE_DATE_FORMAT.applyPattern(resources.getString(R.string.tw__relative_date_format_long));
        }
        return RELATIVE_DATE_FORMAT.format(date);
    }
}
