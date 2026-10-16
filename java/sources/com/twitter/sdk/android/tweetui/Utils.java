package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.models.Tweet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class Utils {
    static CharSequence charSeqOrDefault(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence != null ? charSequence : charSequence2;
    }

    static String stringOrDefault(String str, String str2) {
        return str != null ? str : str2;
    }

    private Utils() {
    }

    static Long numberOrDefault(String str, long j) {
        try {
            return Long.valueOf(Long.parseLong(str));
        } catch (NumberFormatException unused) {
            return Long.valueOf(j);
        }
    }

    static String stringOrEmpty(String str) {
        return stringOrDefault(str, "");
    }

    static CharSequence charSeqOrEmpty(CharSequence charSequence) {
        return charSeqOrDefault(charSequence, "");
    }

    static List<Tweet> orderTweets(List<Long> list, List<Tweet> list2) {
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (Tweet tweet : list2) {
            map.put(Long.valueOf(tweet.id), tweet);
        }
        for (Long l : list) {
            if (map.containsKey(l)) {
                arrayList.add(map.get(l));
            }
        }
        return arrayList;
    }
}
