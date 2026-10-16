package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.util.AttributeSet;
import com.squareup.picasso.Callback;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;

/* JADX INFO: loaded from: classes.dex */
public class CompactTweetView extends BaseTweetView {
    private static final String VIEW_TYPE_NAME = "compact";

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    String getViewTypeName() {
        return VIEW_TYPE_NAME;
    }

    public CompactTweetView(Context context, Tweet tweet) {
        super(context, tweet);
    }

    public CompactTweetView(Context context, Tweet tweet, int i) {
        super(context, tweet, i);
    }

    public CompactTweetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CompactTweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    protected int getLayout() {
        return R.layout.tw__tweet_compact;
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    void render() {
        super.render();
        this.screenNameView.requestLayout();
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    protected void setTweetPhoto(MediaEntity mediaEntity) {
        TweetUi.getInstance().getImageLoader().load(mediaEntity.mediaUrlHttps).placeholder(this.mediaBg).fit().centerCrop().into(this.mediaPhotoView, new Callback() { // from class: com.twitter.sdk.android.tweetui.CompactTweetView.1
            @Override // com.squareup.picasso.Callback
            public void onSuccess() {
            }

            @Override // com.squareup.picasso.Callback
            public void onError() {
                CompactTweetView.this.setErrorImage();
            }
        });
        this.mediaPhotoView.setVisibility(0);
    }
}
