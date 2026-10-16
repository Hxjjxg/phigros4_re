package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class MediaEntity extends UrlEntity {

    @SerializedName("id")
    public final long id;

    @SerializedName("id_str")
    public final String idStr;

    @SerializedName("media_url")
    public final String mediaUrl;

    @SerializedName("media_url_https")
    public final String mediaUrlHttps;

    @SerializedName("sizes")
    public final Sizes sizes;

    @SerializedName("source_status_id")
    public final long sourceStatusId;

    @SerializedName("source_status_id_str")
    public final String sourceStatusIdStr;

    @SerializedName("type")
    public final String type;

    public MediaEntity(String str, String str2, String str3, int i, int i2, long j, String str4, String str5, String str6, Sizes sizes, long j2, String str7, String str8) {
        super(str, str2, str3, i, i2);
        this.id = j;
        this.idStr = str4;
        this.mediaUrl = str5;
        this.mediaUrlHttps = str6;
        this.sizes = sizes;
        this.sourceStatusId = j2;
        this.sourceStatusIdStr = str7;
        this.type = str8;
    }

    public static class Sizes {

        @SerializedName("large")
        public final Size large;

        @SerializedName("medium")
        public final Size medium;

        @SerializedName("small")
        public final Size small;

        @SerializedName("thumb")
        public final Size thumb;

        public Sizes(Size size, Size size2, Size size3, Size size4) {
            this.thumb = size;
            this.small = size2;
            this.medium = size3;
            this.large = size4;
        }
    }

    public static class Size {

        @SerializedName("h")
        public final int h;

        @SerializedName("resize")
        public final String resize;

        @SerializedName("w")
        public final int w;

        public Size(int i, int i2, String str) {
            this.w = i;
            this.h = i2;
            this.resize = str;
        }
    }
}
