package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class UrlConnectionDownloader implements Downloader {
    static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    static volatile Object cache;
    private static final Object lock = new Object();
    private final Context context;

    public UrlConnectionDownloader(Context context) {
        this.context = context.getApplicationContext();
    }

    protected HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(20000);
        return httpURLConnection;
    }

    @Override // com.squareup.picasso.Downloader
    public Downloader.Response load(Uri uri, boolean z) throws IOException {
        installCacheIfNeeded(this.context);
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
        return new Downloader.Response(httpURLConnectionOpenConnection.getInputStream(), Utils.parseResponseSourceHeader(httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE)), httpURLConnectionOpenConnection.getHeaderFieldInt(HttpRequest.HEADER_CONTENT_LENGTH, -1));
    }

    private static void installCacheIfNeeded(Context context) {
        if (cache == null) {
            try {
                synchronized (lock) {
                    try {
                        if (cache == null) {
                            cache = ResponseCacheIcs.install(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (IOException unused) {
            }
        }
    }

    private static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        static Object install(Context context) throws IOException {
            File fileCreateDefaultCacheDir = Utils.createDefaultCacheDir(context);
            HttpResponseCache installed = HttpResponseCache.getInstalled();
            return installed == null ? HttpResponseCache.install(fileCreateDefaultCacheDir, Utils.calculateDiskCacheSize(fileCreateDefaultCacheDir)) : installed;
        }
    }
}
