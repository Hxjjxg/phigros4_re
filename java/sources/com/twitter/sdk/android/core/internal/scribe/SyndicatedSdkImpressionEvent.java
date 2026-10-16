package com.twitter.sdk.android.core.internal.scribe;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class SyndicatedSdkImpressionEvent extends ScribeEvent {
    public static final String CLIENT_NAME = "android";
    private static final String SCRIBE_CATEGORY = "syndicated_sdk_impression";

    @SerializedName("device_id_created_at")
    public final long deviceIdCreatedAt;

    @SerializedName("external_ids")
    public final ExternalIds externalIds;

    @SerializedName("language")
    public final String language;

    public SyndicatedSdkImpressionEvent(EventNamespace eventNamespace, long j, String str, String str2) {
        super(SCRIBE_CATEGORY, eventNamespace, j);
        this.language = str;
        this.externalIds = new ExternalIds(str2);
        this.deviceIdCreatedAt = 0L;
    }

    public class ExternalIds {

        @SerializedName("AD_ID")
        public final String adId;

        public ExternalIds(String str) {
            this.adId = str;
        }
    }
}
