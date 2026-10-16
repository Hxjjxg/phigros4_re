package com.squareup.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class AssetBitmapHunter extends BitmapHunter {
    protected static final String ANDROID_ASSET = "android_asset";
    private static final int ASSET_PREFIX_LENGTH = 22;
    private final AssetManager assetManager;

    public AssetBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.assetManager = context.getAssets();
    }

    @Override // com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        return decodeAsset(request.uri.toString().substring(ASSET_PREFIX_LENGTH));
    }

    @Override // com.squareup.picasso.BitmapHunter
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }

    Bitmap decodeAsset(String str) throws Throwable {
        BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(this.data);
        InputStream inputStream = null;
        if (requiresInSampleSize(optionsCreateBitmapOptions)) {
            try {
                InputStream inputStreamOpen = this.assetManager.open(str);
                try {
                    BitmapFactory.decodeStream(inputStreamOpen, null, optionsCreateBitmapOptions);
                    Utils.closeQuietly(inputStreamOpen);
                    calculateInSampleSize(this.data.targetWidth, this.data.targetHeight, optionsCreateBitmapOptions);
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpen;
                    Utils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        InputStream inputStreamOpen2 = this.assetManager.open(str);
        try {
            return BitmapFactory.decodeStream(inputStreamOpen2, null, optionsCreateBitmapOptions);
        } finally {
            Utils.closeQuietly(inputStreamOpen2);
        }
    }
}
