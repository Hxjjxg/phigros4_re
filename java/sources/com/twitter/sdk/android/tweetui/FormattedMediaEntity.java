package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.models.MediaEntity;

/* JADX INFO: loaded from: classes.dex */
class FormattedMediaEntity extends FormattedUrlEntity {
    final String mediaUrlHttps;
    final String type;

    FormattedMediaEntity(MediaEntity mediaEntity) {
        super(mediaEntity);
        this.type = mediaEntity.type;
        this.mediaUrlHttps = mediaEntity.mediaUrlHttps;
    }
}
