package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.ScribeEvent;
import com.twitter.sdk.android.core.internal.scribe.SyndicatedSdkImpressionEvent;

/* JADX INFO: loaded from: classes.dex */
class ScribeEventFactory {
    ScribeEventFactory() {
    }

    static ScribeEvent newScribeEvent(EventNamespace eventNamespace, long j, String str, String str2) {
        String str3 = eventNamespace.client;
        str3.hashCode();
        if (str3.equals("tfw")) {
            return new SyndicationClientEvent(eventNamespace, j, str, str2);
        }
        return new SyndicatedSdkImpressionEvent(eventNamespace, j, str, str2);
    }
}
