package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.TimingMetric;
import java.text.DateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTweetView extends LinearLayout {
    private static final int DEFAULT_STYLE = R.style.tw__TweetLightStyle;
    private static final String EMPTY_STRING = "";
    static final long INVALID_ID = -1;
    static final double MEDIA_BG_DARK_OPACITY = 0.12d;
    static final double MEDIA_BG_LIGHT_OPACITY = 0.08d;
    private static final String PERF_TAG = "BaseTweetViewPerf";
    private static final String RENDER_PERF_LABEL = "render";
    private static final String SCRIBE_CLICK_ACTION = "click";
    private static final String SCRIBE_IMPRESSION_ACTION = "impression";
    static final double SECONDARY_TEXT_COLOR_DARK_OPACITY = 0.35d;
    static final double SECONDARY_TEXT_COLOR_LIGHT_OPACITY = 0.4d;
    private static final String SYNDICATED_SDK_IMPRESSION_COMPONENT = "";
    private static final String SYNDICATED_SDK_IMPRESSION_ELEMENT = "";
    private static final String SYNDICATED_SDK_IMPRESSION_PAGE = "tweet";
    private static final String TAG = "TweetUi";
    private static final String TFW_CLIENT_EVENT_ELEMENT = "";
    private static final String TFW_CLIENT_EVENT_PAGE = "android";
    private static final String TFW_CLIENT_EVENT_SECTION = "tweet";
    int actionColor;
    ImageView avatarView;
    int birdLogoResId;
    int containerBgColor;
    RelativeLayout containerView;
    TextView contentView;
    TextView fullNameView;
    private LinkClickListener linkClickListener;
    ColorDrawable mediaBg;
    int mediaBgColor;
    ImageView mediaPhotoView;
    private Uri permalinkUri;
    int photoErrorResId;
    int primaryTextColor;
    TimingMetric renderMetric;
    TextView screenNameView;
    int secondaryTextColor;
    TextView timestampView;
    Tweet tweet;
    ImageView twitterLogoView;
    ImageView verifiedCheckView;

    abstract int getLayout();

    abstract String getViewTypeName();

    BaseTweetView(Context context, Tweet tweet) {
        this(context, tweet, DEFAULT_STYLE);
    }

    BaseTweetView(Context context, Tweet tweet, int i) {
        super(context, null);
        initMetrics();
        initAttributes(i);
        inflateView(context);
        findSubviews();
        applyStyles();
        if (isTweetUiEnabled()) {
            setTweet(tweet);
        }
    }

    public BaseTweetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initXmlAttributes(context, attributeSet);
        inflateView(context);
    }

    public BaseTweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initXmlAttributes(context, attributeSet);
        inflateView(context);
    }

    private void initMetrics() {
        TimingMetric timingMetric = new TimingMetric(RENDER_PERF_LABEL, PERF_TAG);
        this.renderMetric = timingMetric;
        timingMetric.startMeasuring();
    }

    private void initAttributes(int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(i, R.styleable.tw__TweetView);
        try {
            setStyleAttributes(typedArrayObtainStyledAttributes);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void initXmlAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.tw__TweetView, 0, 0);
        try {
            setXmlDataAttributes(typedArrayObtainStyledAttributes);
            setStyleAttributes(typedArrayObtainStyledAttributes);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void setXmlDataAttributes(TypedArray typedArray) {
        long jLongValue = Utils.numberOrDefault(typedArray.getString(R.styleable.tw__TweetView_tw__tweet_id), -1L).longValue();
        if (jLongValue <= 0) {
            throw new IllegalArgumentException("Invalid tw__tweet_id");
        }
        setPermalinkUri(null, Long.valueOf(jLongValue));
        this.tweet = new TweetBuilder().setId(jLongValue).build();
    }

    private void setStyleAttributes(TypedArray typedArray) {
        this.containerBgColor = typedArray.getColor(R.styleable.tw__TweetView_tw__container_bg_color, getResources().getColor(R.color.tw__tweet_light_container_bg_color));
        this.primaryTextColor = typedArray.getColor(R.styleable.tw__TweetView_tw__primary_text_color, getResources().getColor(R.color.tw__tweet_light_primary_text_color));
        this.actionColor = typedArray.getColor(R.styleable.tw__TweetView_tw__action_color, getResources().getColor(R.color.tw__tweet_action_color));
        boolean zIsLightColor = ColorUtils.isLightColor(this.containerBgColor);
        if (zIsLightColor) {
            this.photoErrorResId = R.drawable.tw__ic_tweet_photo_error_light;
            this.birdLogoResId = R.drawable.tw__ic_logo_blue;
        } else {
            this.photoErrorResId = R.drawable.tw__ic_tweet_photo_error_dark;
            this.birdLogoResId = R.drawable.tw__ic_logo_white;
        }
        this.secondaryTextColor = ColorUtils.calculateOpacityTransform(zIsLightColor ? SECONDARY_TEXT_COLOR_LIGHT_OPACITY : SECONDARY_TEXT_COLOR_DARK_OPACITY, zIsLightColor ? -1 : -16777216, this.primaryTextColor);
        this.mediaBgColor = ColorUtils.calculateOpacityTransform(zIsLightColor ? MEDIA_BG_LIGHT_OPACITY : MEDIA_BG_DARK_OPACITY, zIsLightColor ? -16777216 : -1, this.containerBgColor);
        this.mediaBg = new ColorDrawable(this.mediaBgColor);
    }

    private void inflateView(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(getLayout(), (ViewGroup) null, false);
        viewInflate.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        addView(viewInflate);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isTweetUiEnabled()) {
            findSubviews();
            applyStyles();
            loadTweet();
        }
    }

    boolean isTweetUiEnabled() {
        if (isInEditMode()) {
            return false;
        }
        try {
            TweetUi.getInstance();
            return true;
        } catch (IllegalStateException e) {
            Fabric.getLogger().e(TAG, e.getMessage());
            setEnabled(false);
            return false;
        }
    }

    void findSubviews() {
        this.containerView = (RelativeLayout) findViewById(R.id.tw__tweet_view);
        this.avatarView = (ImageView) findViewById(R.id.tw__tweet_author_avatar);
        this.fullNameView = (TextView) findViewById(R.id.tw__tweet_author_full_name);
        this.screenNameView = (TextView) findViewById(R.id.tw__tweet_author_screen_name);
        this.verifiedCheckView = (ImageView) findViewById(R.id.tw__tweet_author_verified);
        this.mediaPhotoView = (ImageView) findViewById(R.id.tw__tweet_media);
        this.contentView = (TextView) findViewById(R.id.tw__tweet_text);
        this.timestampView = (TextView) findViewById(R.id.tw__tweet_timestamp);
        this.twitterLogoView = (ImageView) findViewById(R.id.tw__twitter_logo);
    }

    public long getTweetId() {
        Tweet tweet = this.tweet;
        if (tweet == null) {
            return -1L;
        }
        return tweet.id;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
        render();
    }

    public Tweet getTweet() {
        return this.tweet;
    }

    void render() {
        setProfilePhotoView(this.tweet);
        setName(this.tweet);
        setScreenName(this.tweet);
        setTimestamp(this.tweet);
        setTweetPhoto(this.tweet);
        setText(this.tweet);
        setContentDescription(this.tweet);
        if (TweetUtils.isTweetResolvable(this.tweet)) {
            setPermalinkUri(this.tweet.user.screenName, Long.valueOf(getTweetId()));
        } else {
            this.permalinkUri = null;
        }
        setPermalinkLauncher();
        scribeImpression();
        TimingMetric timingMetric = this.renderMetric;
        if (timingMetric != null) {
            timingMetric.stopMeasuring();
        }
    }

    private void loadTweet() {
        final long tweetId = getTweetId();
        TweetUi.getInstance().getTweetRepository().loadTweet(getTweetId(), new LoadCallback<Tweet>() { // from class: com.twitter.sdk.android.tweetui.BaseTweetView.1
            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void success(Tweet tweet) {
                BaseTweetView.this.setTweet(tweet);
            }

            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().d(BaseTweetView.TAG, String.format("loadTweet failure for Tweet Id %d.", Long.valueOf(tweetId)));
            }
        });
    }

    Uri getPermalinkUri() {
        return this.permalinkUri;
    }

    void setPermalinkUri(String str, Long l) {
        if (l.longValue() <= 0) {
            return;
        }
        this.permalinkUri = TweetUtils.getPermalink(str, l.longValue());
    }

    private void setPermalinkLauncher() {
        PermalinkClickListener permalinkClickListener = new PermalinkClickListener();
        setOnClickListener(permalinkClickListener);
        this.contentView.setOnClickListener(permalinkClickListener);
    }

    void launchPermalink() {
        getContext().startActivity(new Intent("android.intent.action.VIEW", getPermalinkUri()));
    }

    void scribeImpression() {
        TweetUi.getInstance().scribe(getTfwEventImpressionNamespace(), getSyndicatedSdkImpressionNamespace());
    }

    EventNamespace getTfwEventImpressionNamespace() {
        return new EventNamespace.Builder().setClient("tfw").setPage("android").setSection("tweet").setComponent(getViewTypeName()).setElement("").setAction("impression").builder();
    }

    EventNamespace getSyndicatedSdkImpressionNamespace() {
        return new EventNamespace.Builder().setClient("android").setPage("tweet").setSection(getViewTypeName()).setComponent("").setElement("").setAction("impression").builder();
    }

    void scribePermalinkClick() {
        TweetUi.getInstance().scribe(getTfwEventClickNamespace(), getSyndicatedSdkClickNamespace());
    }

    EventNamespace getTfwEventClickNamespace() {
        return new EventNamespace.Builder().setClient("tfw").setPage("android").setSection("tweet").setComponent(getViewTypeName()).setElement("").setAction("click").builder();
    }

    EventNamespace getSyndicatedSdkClickNamespace() {
        return new EventNamespace.Builder().setClient("android").setPage("tweet").setSection(getViewTypeName()).setComponent("").setElement("").setAction("click").builder();
    }

    protected void applyStyles() {
        this.containerView.setBackgroundColor(this.containerBgColor);
        this.avatarView.setImageDrawable(this.mediaBg);
        this.mediaPhotoView.setImageDrawable(this.mediaBg);
        this.fullNameView.setTextColor(this.primaryTextColor);
        this.screenNameView.setTextColor(this.secondaryTextColor);
        this.contentView.setTextColor(this.primaryTextColor);
        this.timestampView.setTextColor(this.secondaryTextColor);
        this.twitterLogoView.setImageResource(this.birdLogoResId);
    }

    private void setName(Tweet tweet) {
        if (tweet != null && tweet.user != null) {
            this.fullNameView.setText(Utils.stringOrEmpty(tweet.user.name));
        } else {
            this.fullNameView.setText("");
        }
    }

    private void setScreenName(Tweet tweet) {
        if (tweet != null && tweet.user != null) {
            this.screenNameView.setText(UserUtils.formatScreenName(Utils.stringOrEmpty(tweet.user.screenName)));
        } else {
            this.screenNameView.setText("");
        }
    }

    private void setText(Tweet tweet) {
        this.contentView.setMovementMethod(LinkMovementMethod.getInstance());
        this.contentView.setFocusable(false);
        this.contentView.setImportantForAccessibility(2);
        CharSequence charSequenceCharSeqOrEmpty = Utils.charSeqOrEmpty(getLinkifiedText(tweet));
        if (!TextUtils.isEmpty(charSequenceCharSeqOrEmpty)) {
            this.contentView.setText(charSequenceCharSeqOrEmpty);
            this.contentView.setVisibility(0);
        } else {
            this.contentView.setText("");
            this.contentView.setVisibility(8);
        }
    }

    private void setTimestamp(Tweet tweet) {
        String strDotPrefix;
        if (tweet == null || tweet.createdAt == null || !TweetDateUtils.isValidTimestamp(tweet.createdAt)) {
            strDotPrefix = "";
        } else {
            strDotPrefix = TweetDateUtils.dotPrefix(TweetDateUtils.getRelativeTimeString(getResources(), System.currentTimeMillis(), Long.valueOf(TweetDateUtils.apiTimeToLong(tweet.createdAt)).longValue()));
        }
        this.timestampView.setText(strDotPrefix);
    }

    private void setProfilePhotoView(Tweet tweet) {
        TweetUi.getInstance().getImageLoader().load((tweet == null || tweet.user == null) ? null : UserUtils.getProfileImageUrlHttps(tweet.user, UserUtils.AvatarSize.REASONABLY_SMALL)).placeholder(this.mediaBg).into(this.avatarView);
    }

    final void setTweetPhoto(Tweet tweet) {
        clearMediaBackground();
        if (tweet != null && TweetTextUtils.hasPhotoUrl(tweet.entities)) {
            MediaEntity lastPhotoEntity = TweetTextUtils.getLastPhotoEntity(tweet.entities);
            this.mediaPhotoView.setVisibility(0);
            setTweetPhoto(lastPhotoEntity);
            return;
        }
        this.mediaPhotoView.setVisibility(8);
    }

    protected void setTweetPhoto(MediaEntity mediaEntity) {
        TweetUi.getInstance().getImageLoader().load(mediaEntity.mediaUrlHttps).placeholder(this.mediaBg).into(this.mediaPhotoView, new Callback() { // from class: com.twitter.sdk.android.tweetui.BaseTweetView.2
            @Override // com.squareup.picasso.Callback
            public void onSuccess() {
            }

            @Override // com.squareup.picasso.Callback
            public void onError() {
                BaseTweetView.this.setErrorImage();
            }
        });
    }

    protected void clearMediaBackground() {
        this.mediaPhotoView.setBackground(null);
    }

    protected void setErrorImage() {
        TweetUi.getInstance().getImageLoader().load(this.photoErrorResId).into(this.mediaPhotoView, new Callback() { // from class: com.twitter.sdk.android.tweetui.BaseTweetView.3
            @Override // com.squareup.picasso.Callback
            public void onError() {
            }

            @Override // com.squareup.picasso.Callback
            public void onSuccess() {
                BaseTweetView.this.mediaPhotoView.setBackgroundColor(BaseTweetView.this.mediaBgColor);
            }
        });
    }

    protected CharSequence getLinkifiedText(Tweet tweet) {
        FormattedTweetText tweetText = TweetUi.getInstance().getTweetRepository().formatTweetText(tweet);
        if (tweetText == null) {
            return null;
        }
        return TweetTextLinkifier.linkifyUrls(tweetText, getLinkClickListener(), TweetTextUtils.hasPhotoUrl(tweet.entities), this.actionColor);
    }

    void setContentDescription(Tweet tweet) {
        if (!TweetUtils.isTweetResolvable(tweet)) {
            setContentDescription(getResources().getString(R.string.tw__loading_tweet));
            return;
        }
        FormattedTweetText tweetText = TweetUi.getInstance().getTweetRepository().formatTweetText(tweet);
        String str = tweetText != null ? tweetText.text : null;
        long jApiTimeToLong = TweetDateUtils.apiTimeToLong(tweet.createdAt);
        setContentDescription(getResources().getString(R.string.tw__tweet_content_description, Utils.stringOrEmpty(tweet.user.name), Utils.stringOrEmpty(str), Utils.stringOrEmpty(jApiTimeToLong != -1 ? DateFormat.getDateInstance().format(new Date(jApiTimeToLong)) : null)));
    }

    protected LinkClickListener getLinkClickListener() {
        if (this.linkClickListener == null) {
            this.linkClickListener = new LinkClickListener() { // from class: com.twitter.sdk.android.tweetui.BaseTweetView.4
                @Override // com.twitter.sdk.android.tweetui.LinkClickListener
                public void onPhotoClicked(MediaEntity mediaEntity) {
                }

                @Override // com.twitter.sdk.android.tweetui.LinkClickListener
                public void onUrlClicked(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    BaseTweetView.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            };
        }
        return this.linkClickListener;
    }

    class PermalinkClickListener implements View.OnClickListener {
        PermalinkClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (BaseTweetView.this.getPermalinkUri() == null) {
                return;
            }
            BaseTweetView.this.scribePermalinkClick();
            BaseTweetView.this.launchPermalink();
        }
    }
}
