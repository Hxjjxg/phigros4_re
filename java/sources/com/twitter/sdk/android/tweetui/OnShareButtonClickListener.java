package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import com.twitter.sdk.android.core.models.Tweet;
import com.voxelbusters.nativeplugins.defines.Keys;

/* JADX INFO: loaded from: classes.dex */
class OnShareButtonClickListener implements View.OnClickListener {
    final Tweet tweet;

    OnShareButtonClickListener(Tweet tweet) {
        this.tweet = tweet;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        onClick(view.getContext(), view.getResources());
    }

    void onClick(Context context, Resources resources) {
        Tweet tweet = this.tweet;
        if (tweet == null || tweet.user == null) {
            return;
        }
        launchShareIntent(Intent.createChooser(getShareIntent(getShareSubject(resources), getShareContent(resources)), resources.getString(R.string.tw__share_tweet)), context);
    }

    String getShareContent(Resources resources) {
        return resources.getString(R.string.tw__share_content_format, this.tweet.user.screenName, Long.valueOf(this.tweet.id));
    }

    String getShareSubject(Resources resources) {
        return resources.getString(R.string.tw__share_subject_format, this.tweet.user.name, this.tweet.user.screenName);
    }

    void launchShareIntent(Intent intent, Context context) {
        context.startActivity(intent);
    }

    Intent getShareIntent(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.TEXT", str2);
        intent.setType(Keys.Mime.PLAIN_TEXT);
        return intent;
    }
}
