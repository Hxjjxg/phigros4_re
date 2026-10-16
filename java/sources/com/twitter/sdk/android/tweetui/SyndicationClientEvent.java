package com.twitter.sdk.android.tweetui;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.ScribeEvent;

/* JADX INFO: loaded from: classes.dex */
class SyndicationClientEvent extends ScribeEvent {
    static final String CLIENT_NAME = "tfw";
    private static final String SCRIBE_CATEGORY = "tfw_client_event";

    @SerializedName("external_ids")
    final ExternalIds externalIds;

    @SerializedName("language")
    final String language;

    SyndicationClientEvent(EventNamespace eventNamespace, long j, String str, String str2) {
        super(SCRIBE_CATEGORY, eventNamespace, j);
        this.language = str;
        this.externalIds = new ExternalIds(str2);
    }

    class ExternalIds {

        @SerializedName("6")
        final String adId;

        ExternalIds(String str) {
            this.adId = str;
        }
    }
}
