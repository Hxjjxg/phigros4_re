package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.models.UrlEntity;

/* JADX INFO: loaded from: classes.dex */
class FormattedUrlEntity {
    final String displayUrl;
    int end;
    int start;
    final String url;

    FormattedUrlEntity(UrlEntity urlEntity) {
        this.start = urlEntity.getStart();
        this.end = urlEntity.getEnd();
        this.displayUrl = urlEntity.displayUrl;
        this.url = urlEntity.url;
    }
}
