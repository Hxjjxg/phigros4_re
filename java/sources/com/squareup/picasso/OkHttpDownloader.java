package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class OkHttpDownloader implements Downloader {
    static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
    static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
    private final OkUrlFactory urlFactory;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File file) {
        this(file, Utils.calculateDiskCacheSize(file));
    }

    public OkHttpDownloader(Context context, long j) {
        this(Utils.createDefaultCacheDir(context), j);
    }

    public OkHttpDownloader(File file, long j) {
        this(new OkHttpClient());
        try {
            this.urlFactory.client().setCache(new com.squareup.okhttp.Cache(file, j));
        } catch (IOException unused) {
        }
    }

    public OkHttpDownloader(OkHttpClient okHttpClient) {
        this.urlFactory = new OkUrlFactory(okHttpClient);
    }

    protected HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection httpURLConnectionOpen = this.urlFactory.open(new URL(uri.toString()));
        httpURLConnectionOpen.setConnectTimeout(15000);
        httpURLConnectionOpen.setReadTimeout(20000);
        return httpURLConnectionOpen;
    }

    protected OkHttpClient getClient() {
        return this.urlFactory.client();
    }

    @Override // com.squareup.picasso.Downloader
    public Downloader.Response load(Uri uri, boolean z) throws IOException {
        HttpURLConnection httpURLConnectionOpenConnection = openConnection(uri);
        httpURLConnectionOpenConnection.setUseCaches(true);
        if (z) {
            httpURLConnectionOpenConnection.setRequestProperty(HttpRequest.HEADER_CACHE_CONTROL, "only-if-cached,max-age=2147483647");
        }
        int responseCode = httpURLConnectionOpenConnection.getResponseCode();
        if (responseCode >= 300) {
            httpURLConnectionOpenConnection.disconnect();
            throw new Downloader.ResponseException(responseCode + " " + httpURLConnectionOpenConnection.getResponseMessage());
        }
        String headerField = httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE_OKHTTP);
        if (headerField == null) {
            headerField = httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE_ANDROID);
        }
        return new Downloader.Response(httpURLConnectionOpenConnection.getInputStream(), Utils.parseResponseSourceHeader(headerField), httpURLConnectionOpenConnection.getHeaderFieldInt(HttpRequest.HEADER_CONTENT_LENGTH, -1));
    }
}
