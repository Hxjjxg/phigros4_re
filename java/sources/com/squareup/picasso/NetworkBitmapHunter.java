package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class NetworkBitmapHunter extends BitmapHunter {
    static final int DEFAULT_RETRY_COUNT = 2;
    private static final int MARKER = 65536;
    private final Downloader downloader;
    int retryCount;

    @Override // com.squareup.picasso.BitmapHunter
    boolean supportsReplay() {
        return true;
    }

    public NetworkBitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        super(picasso, dispatcher, cache, stats, action);
        this.downloader = downloader;
        this.retryCount = 2;
    }

    @Override // com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        Downloader.Response responseLoad = this.downloader.load(request.uri, this.retryCount == 0);
        if (responseLoad == null) {
            return null;
        }
        this.loadedFrom = responseLoad.cached ? Picasso.LoadedFrom.DISK : Picasso.LoadedFrom.NETWORK;
        Bitmap bitmap = responseLoad.getBitmap();
        if (bitmap != null) {
            return bitmap;
        }
        InputStream inputStream = responseLoad.getInputStream();
        if (inputStream == null) {
            return null;
        }
        if (responseLoad.getContentLength() == 0) {
            Utils.closeQuietly(inputStream);
            throw new IOException("Received response with 0 content-length header.");
        }
        if (this.loadedFrom == Picasso.LoadedFrom.NETWORK && responseLoad.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(responseLoad.getContentLength());
        }
        try {
            return decodeStream(inputStream, request);
        } finally {
            Utils.closeQuietly(inputStream);
        }
    }

    @Override // com.squareup.picasso.BitmapHunter
    boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        int i = this.retryCount;
        if (!(i > 0)) {
            return false;
        }
        this.retryCount = i - 1;
        return networkInfo == null || networkInfo.isConnected();
    }

    private Bitmap decodeStream(InputStream inputStream, Request request) throws IOException {
        MarkableInputStream markableInputStream = new MarkableInputStream(inputStream);
        long jSavePosition = markableInputStream.savePosition(65536);
        BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(request);
        boolean zRequiresInSampleSize = requiresInSampleSize(optionsCreateBitmapOptions);
        boolean zIsWebPFile = Utils.isWebPFile(markableInputStream);
        markableInputStream.reset(jSavePosition);
        if (zIsWebPFile) {
            byte[] byteArray = Utils.toByteArray(markableInputStream);
            if (zRequiresInSampleSize) {
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsCreateBitmapOptions);
                calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions);
            }
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsCreateBitmapOptions);
        }
        if (zRequiresInSampleSize) {
            BitmapFactory.decodeStream(markableInputStream, null, optionsCreateBitmapOptions);
            calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions);
            markableInputStream.reset(jSavePosition);
        }
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(markableInputStream, null, optionsCreateBitmapOptions);
        if (bitmapDecodeStream != null) {
            return bitmapDecodeStream;
        }
        throw new IOException("Failed to decode stream.");
    }
}
