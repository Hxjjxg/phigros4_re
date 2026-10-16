package com.twitter.sdk.android.core;

import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.SafeListAdapter;
import com.twitter.sdk.android.core.models.SafeMapAdapter;
import com.twitter.sdk.android.core.services.AccountService;
import com.twitter.sdk.android.core.services.FavoriteService;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import javax.net.ssl.SSLSocketFactory;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

/* JADX INFO: loaded from: classes.dex */
public class TwitterApiClient {
    final RestAdapter adapter;
    final ConcurrentHashMap<Class, Object> services;

    TwitterApiClient(TwitterAuthConfig twitterAuthConfig, Session session, TwitterApi twitterApi, SSLSocketFactory sSLSocketFactory, ExecutorService executorService) {
        if (session == null) {
            throw new IllegalArgumentException("Session must not be null.");
        }
        this.services = new ConcurrentHashMap<>();
        this.adapter = new RestAdapter.Builder().setClient(new AuthenticatedClient(twitterAuthConfig, session, sSLSocketFactory)).setEndpoint(twitterApi.getBaseHostUrl()).setConverter(new GsonConverter(new GsonBuilder().registerTypeAdapterFactory(new SafeListAdapter()).registerTypeAdapterFactory(new SafeMapAdapter()).create())).setExecutors(executorService, new MainThreadExecutor()).build();
    }

    public TwitterApiClient(Session session) {
        this(TwitterCore.getInstance().getAuthConfig(), session, new TwitterApi(), TwitterCore.getInstance().getSSLSocketFactory(), TwitterCore.getInstance().getFabric().getExecutorService());
    }

    public AccountService getAccountService() {
        return (AccountService) getService(AccountService.class);
    }

    public FavoriteService getFavoriteService() {
        return (FavoriteService) getService(FavoriteService.class);
    }

    public StatusesService getStatusesService() {
        return (StatusesService) getService(StatusesService.class);
    }

    public SearchService getSearchService() {
        return (SearchService) getService(SearchService.class);
    }

    protected <T> T getService(Class<T> cls) {
        if (!this.services.contains(cls)) {
            this.services.putIfAbsent(cls, this.adapter.create(cls));
        }
        return (T) this.services.get(cls);
    }
}
