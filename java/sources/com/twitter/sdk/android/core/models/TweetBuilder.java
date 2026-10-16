package com.twitter.sdk.android.core.models;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TweetBuilder {
    private Coordinates coordinates;
    private String createdAt;
    private Object currentUserRetweet;
    private TweetEntities entities;
    private Integer favoriteCount;
    private boolean favorited;
    private String filterLevel;
    private long id = -1;
    private String idStr;
    private String inReplyToScreenName;
    private long inReplyToStatusId;
    private String inReplyToStatusIdStr;
    private long inReplyToUserId;
    private String inReplyToUserIdStr;
    private String lang;
    private Place place;
    private boolean possiblySensitive;
    private int retweetCount;
    private boolean retweeted;
    private Tweet retweetedStatus;
    private Object scopes;
    private String source;
    private String text;
    private boolean truncated;
    private User user;
    private boolean withheldCopyright;
    private List<String> withheldInCountries;
    private String withheldScope;

    public TweetBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public TweetBuilder setCreatedAt(String str) {
        this.createdAt = str;
        return this;
    }

    public TweetBuilder setCurrentUserRetweet(Object obj) {
        this.currentUserRetweet = obj;
        return this;
    }

    public TweetBuilder setEntities(TweetEntities tweetEntities) {
        this.entities = tweetEntities;
        return this;
    }

    public TweetBuilder setFavoriteCount(Integer num) {
        this.favoriteCount = num;
        return this;
    }

    public TweetBuilder setFavorited(boolean z) {
        this.favorited = z;
        return this;
    }

    public TweetBuilder setFilterLevel(String str) {
        this.filterLevel = str;
        return this;
    }

    public TweetBuilder setId(long j) {
        this.id = j;
        return this;
    }

    public TweetBuilder setIdStr(String str) {
        this.idStr = str;
        return this;
    }

    public TweetBuilder setInReplyToScreenName(String str) {
        this.inReplyToScreenName = str;
        return this;
    }

    public TweetBuilder setInReplyToStatusId(long j) {
        this.inReplyToStatusId = j;
        return this;
    }

    public TweetBuilder setInReplyToStatusIdStr(String str) {
        this.inReplyToStatusIdStr = str;
        return this;
    }

    public TweetBuilder setInReplyToUserId(long j) {
        this.inReplyToUserId = j;
        return this;
    }

    public TweetBuilder setInReplyToUserIdStr(String str) {
        this.inReplyToUserIdStr = str;
        return this;
    }

    public TweetBuilder setLang(String str) {
        this.lang = str;
        return this;
    }

    public TweetBuilder setPlace(Place place) {
        this.place = place;
        return this;
    }

    public TweetBuilder setPossiblySensitive(boolean z) {
        this.possiblySensitive = z;
        return this;
    }

    public TweetBuilder setScopes(Object obj) {
        this.scopes = obj;
        return this;
    }

    public TweetBuilder setRetweetCount(int i) {
        this.retweetCount = i;
        return this;
    }

    public TweetBuilder setRetweeted(boolean z) {
        this.retweeted = z;
        return this;
    }

    public TweetBuilder setRetweetedStatus(Tweet tweet) {
        this.retweetedStatus = tweet;
        return this;
    }

    public TweetBuilder setSource(String str) {
        this.source = str;
        return this;
    }

    public TweetBuilder setText(String str) {
        this.text = str;
        return this;
    }

    public TweetBuilder setTruncated(boolean z) {
        this.truncated = z;
        return this;
    }

    public TweetBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public TweetBuilder setWithheldCopyright(boolean z) {
        this.withheldCopyright = z;
        return this;
    }

    public TweetBuilder setWithheldInCountries(List<String> list) {
        this.withheldInCountries = list;
        return this;
    }

    public TweetBuilder setWithheldScope(String str) {
        this.withheldScope = str;
        return this;
    }

    public Tweet build() {
        return new Tweet(this.coordinates, this.createdAt, this.currentUserRetweet, this.entities, this.favoriteCount, this.favorited, this.filterLevel, this.id, this.idStr, this.inReplyToScreenName, this.inReplyToStatusId, this.inReplyToStatusIdStr, this.inReplyToUserId, this.inReplyToUserIdStr, this.lang, this.place, this.possiblySensitive, this.scopes, this.retweetCount, this.retweeted, this.retweetedStatus, this.source, this.text, this.truncated, this.user, this.withheldCopyright, this.withheldInCountries, this.withheldScope);
    }
}
