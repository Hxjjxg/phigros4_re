package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TweetViewAdapter<T extends BaseTweetView> extends BaseAdapter {
    protected final Context context;
    protected List<Tweet> tweets;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public TweetViewAdapter(Context context) {
        this.context = context;
        this.tweets = new ArrayList();
    }

    public TweetViewAdapter(Context context, List<Tweet> list) {
        this.context = context;
        this.tweets = list;
    }

    public T getTweetView(Context context, Tweet tweet) {
        return new CompactTweetView(context, tweet);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<Tweet> list = this.tweets;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Tweet getItem(int i) {
        return this.tweets.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Tweet item = getItem(i);
        if (view == null) {
            return getTweetView(this.context, item);
        }
        ((BaseTweetView) view).setTweet(item);
        return view;
    }

    public List<Tweet> getTweets() {
        return this.tweets;
    }

    public void setTweets(List<Tweet> list) {
        if (list == null) {
            this.tweets = new ArrayList();
        } else {
            this.tweets = list;
        }
        notifyDataSetChanged();
    }
}
