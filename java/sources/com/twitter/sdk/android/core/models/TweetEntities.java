package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TweetEntities {

    @SerializedName("hashtags")
    public final List<HashtagEntity> hashtags;

    @SerializedName("media")
    public final List<MediaEntity> media;

    @SerializedName("urls")
    public final List<UrlEntity> urls;

    @SerializedName("user_mentions")
    public final List<MentionEntity> userMentions;

    public TweetEntities(List<UrlEntity> list, List<MentionEntity> list2, List<MediaEntity> list3, List<HashtagEntity> list4) {
        this.urls = getSafeList(list);
        this.userMentions = getSafeList(list2);
        this.media = getSafeList(list3);
        this.hashtags = getSafeList(list4);
    }

    private <T> List<T> getSafeList(List<T> list) {
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(list);
    }
}
