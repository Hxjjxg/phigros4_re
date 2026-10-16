package com.twitter.sdk.android.tweetui;

import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.scribe.DefaultScribeClient;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
@DependsOn({TwitterCore.class})
public class TweetUi extends Kit<Boolean> {
    private static final String KIT_SCRIBE_NAME = "TweetUi";
    static final String LOGTAG = "TweetUi";
    static final String NOT_STARTED_ERROR = "Must start TweetUi Kit in Fabric.with().";
    String advertisingId;
    private final AtomicReference<Gson> gsonRef = new AtomicReference<>();
    private Picasso imageLoader;
    private AuthRequestQueue queue;
    DefaultScribeClient scribeClient;
    List<SessionManager<? extends Session>> sessionManagers;
    private TweetRepository tweetRepository;

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "com.twitter.sdk.android:tweet-ui";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "1.0.5.33";
    }

    public static TweetUi getInstance() {
        checkInitialized();
        return (TweetUi) Fabric.getKit(TweetUi.class);
    }

    @Override // io.fabric.sdk.android.Kit
    protected boolean onPreExecute() {
        super.onPreExecute();
        TwitterCore twitterCore = TwitterCore.getInstance();
        ArrayList arrayList = new ArrayList(2);
        this.sessionManagers = arrayList;
        arrayList.add(twitterCore.getSessionManager());
        this.sessionManagers.add(twitterCore.getAppSessionManager());
        this.queue = new AuthRequestQueue(twitterCore, this.sessionManagers);
        this.tweetRepository = new TweetRepository(this, getFabric().getExecutorService(), getFabric().getMainHandler(), this.queue);
        this.imageLoader = Picasso.with(getContext());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.fabric.sdk.android.Kit
    public Boolean doInBackground() {
        this.queue.sessionRestored(SessionUtils.getActiveSession(this.sessionManagers));
        initGson();
        setUpScribeClient();
        this.advertisingId = getIdManager().getAdvertisingId();
        return true;
    }

    private static void checkInitialized() {
        if (Fabric.getKit(TweetUi.class) == null) {
            throw new IllegalStateException(NOT_STARTED_ERROR);
        }
    }

    private void setUpScribeClient() {
        this.scribeClient = new DefaultScribeClient(this, "TweetUi", this.gsonRef.get(), this.sessionManagers, getIdManager());
    }

    void scribe(EventNamespace... eventNamespaceArr) {
        if (this.scribeClient == null) {
            return;
        }
        String language = getContext().getResources().getConfiguration().locale.getLanguage();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (EventNamespace eventNamespace : eventNamespaceArr) {
            this.scribeClient.scribe(ScribeEventFactory.newScribeEvent(eventNamespace, jCurrentTimeMillis, language, this.advertisingId));
        }
    }

    void initGson() {
        if (this.gsonRef.get() == null) {
            zal$$ExternalSyntheticBackportWithForwarding0.m(this.gsonRef, null, new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create());
        }
    }

    TweetRepository getTweetRepository() {
        return this.tweetRepository;
    }

    void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    Picasso getImageLoader() {
        return this.imageLoader;
    }

    void setImageLoader(Picasso picasso) {
        this.imageLoader = picasso;
    }

    void clearAppSession(long j) {
        TwitterCore.getInstance().getAppSessionManager().clearSession(j);
    }
}
