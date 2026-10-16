package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;
import com.taptap.sdk.TapLoginHelperActivity;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Tweet {
    public static final long INVALID_ID = -1;

    @SerializedName("coordinates")
    public final Coordinates coordinates;

    @SerializedName("created_at")
    public final String createdAt;

    @SerializedName("current_user_retweet")
    public final Object currentUserRetweet;

    @SerializedName("entities")
    public final TweetEntities entities;

    @SerializedName("favorite_count")
    public final Integer favoriteCount;

    @SerializedName("favorited")
    public final boolean favorited;

    @SerializedName("filter_level")
    public final String filterLevel;

    @SerializedName("id")
    public final long id;

    @SerializedName("id_str")
    public final String idStr;

    @SerializedName("in_reply_to_screen_name")
    public final String inReplyToScreenName;

    @SerializedName("in_reply_to_status_id")
    public final long inReplyToStatusId;

    @SerializedName("in_reply_to_status_id_str")
    public final String inReplyToStatusIdStr;

    @SerializedName("in_reply_to_user_id")
    public final long inReplyToUserId;

    @SerializedName("in_reply_to_user_id_str")
    public final String inReplyToUserIdStr;

    @SerializedName("lang")
    public final String lang;

    @SerializedName("place")
    public final Place place;

    @SerializedName("possibly_sensitive")
    public final boolean possiblySensitive;

    @SerializedName("retweet_count")
    public final int retweetCount;

    @SerializedName("retweeted")
    public final boolean retweeted;

    @SerializedName("retweeted_status")
    public final Tweet retweetedStatus;

    @SerializedName("scopes")
    public final Object scopes;

    @SerializedName(TapLoginHelperActivity.INTENT_KEY_SOURCE)
    public final String source;

    @SerializedName("text")
    public final String text;

    @SerializedName("truncated")
    public final boolean truncated;

    @SerializedName(Keys.GameServices.SCORE_USER)
    public final User user;

    @SerializedName("withheld_copyright")
    public final boolean withheldCopyright;

    @SerializedName("withheld_in_countries")
    public final List<String> withheldInCountries;

    @SerializedName("withheld_scope")
    public final String withheldScope;

    public Tweet(Coordinates coordinates, String str, Object obj, TweetEntities tweetEntities, Integer num, boolean z, String str2, long j, String str3, String str4, long j2, String str5, long j3, String str6, String str7, Place place, boolean z2, Object obj2, int i, boolean z3, Tweet tweet, String str8, String str9, boolean z4, User user, boolean z5, List<String> list, String str10) {
        this.coordinates = coordinates;
        this.createdAt = str;
        this.currentUserRetweet = obj;
        this.entities = tweetEntities;
        this.favoriteCount = num;
        this.favorited = z;
        this.filterLevel = str2;
        this.id = j;
        this.idStr = str3;
        this.inReplyToScreenName = str4;
        this.inReplyToStatusId = j2;
        this.inReplyToStatusIdStr = str5;
        this.inReplyToUserId = j3;
        this.inReplyToUserIdStr = str6;
        this.lang = str7;
        this.place = place;
        this.possiblySensitive = z2;
        this.scopes = obj2;
        this.retweetCount = i;
        this.retweeted = z3;
        this.retweetedStatus = tweet;
        this.source = str8;
        this.text = str9;
        this.truncated = z4;
        this.user = user;
        this.withheldCopyright = z5;
        this.withheldInCountries = list;
        this.withheldScope = str10;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Tweet) && this.id == ((Tweet) obj).id;
    }

    public int hashCode() {
        return (int) this.id;
    }
}
