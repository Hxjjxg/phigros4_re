package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.twitter.sdk.android.core.models.Tweet;

/* JADX INFO: loaded from: classes.dex */
public class TweetView extends BaseTweetView {
    private static final String VIEW_TYPE_NAME = "default";
    Button shareButton;

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    String getViewTypeName() {
        return VIEW_TYPE_NAME;
    }

    public TweetView(Context context, Tweet tweet) {
        super(context, tweet);
    }

    public TweetView(Context context, Tweet tweet, int i) {
        super(context, tweet, i);
    }

    public TweetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    protected int getLayout() {
        return R.layout.tw__tweet;
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    void findSubviews() {
        super.findSubviews();
        this.mediaPhotoView = (ImageView) findViewById(R.id.tw__tweet_media);
        this.shareButton = (Button) findViewById(R.id.tw__tweet_share);
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    void render() {
        super.render();
        setVerifiedCheck(this.tweet);
        setShareButtonVisibility(this.tweet);
    }

    @Override // com.twitter.sdk.android.tweetui.BaseTweetView
    protected void applyStyles() {
        super.applyStyles();
        this.shareButton.setTextColor(this.actionColor);
    }

    private void setShareButtonVisibility(Tweet tweet) {
        if (TweetUtils.isTweetResolvable(tweet)) {
            setShareButtonVisible();
            this.shareButton.setOnClickListener(new OnShareButtonClickListener(tweet));
        } else {
            setShareButtonGone();
            this.shareButton.setOnClickListener(null);
        }
    }

    private void setShareButtonVisible() {
        this.shareButton.setVisibility(0);
        this.containerView.setPadding(this.containerView.getPaddingLeft(), this.containerView.getPaddingTop(), this.containerView.getPaddingRight(), 0);
        int dimension = (int) getResources().getDimension(R.dimen.tw__tweet_share_margin_left);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.shareButton.getLayoutParams());
        layoutParams.addRule(3, R.id.tw__tweet_text);
        int dimension2 = (int) getResources().getDimension(R.dimen.tw__tweet_share_extra_bottom_margin);
        if (TextUtils.isEmpty(this.contentView.getText())) {
            layoutParams.setMargins(dimension, (int) getResources().getDimension(R.dimen.tw__tweet_share_extra_top_margin), 0, dimension2);
            this.shareButton.setLayoutParams(layoutParams);
        } else {
            layoutParams.setMargins(dimension, 0, 0, dimension2);
            this.shareButton.setLayoutParams(layoutParams);
        }
        this.shareButton.requestLayout();
    }

    private void setShareButtonGone() {
        this.shareButton.setVisibility(8);
        this.containerView.setPadding(this.containerView.getPaddingLeft(), this.containerView.getPaddingTop(), this.containerView.getPaddingRight(), (int) getResources().getDimension(R.dimen.tw__tweet_container_padding_bottom));
    }

    private void setVerifiedCheck(Tweet tweet) {
        if (tweet != null && tweet.user != null && tweet.user.verified) {
            this.verifiedCheckView.setVisibility(0);
        } else {
            this.verifiedCheckView.setVisibility(8);
        }
    }
}
