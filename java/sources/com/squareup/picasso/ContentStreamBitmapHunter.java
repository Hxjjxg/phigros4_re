package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class ContentStreamBitmapHunter extends BitmapHunter {
    final Context context;

    ContentStreamBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    @Override // com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        return decodeContentStream(request);
    }

    @Override // com.squareup.picasso.BitmapHunter
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }

    protected Bitmap decodeContentStream(Request request) throws Throwable {
        ContentResolver contentResolver = this.context.getContentResolver();
        BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(request);
        InputStream inputStream = null;
        if (requiresInSampleSize(optionsCreateBitmapOptions)) {
            try {
                InputStream inputStreamOpenInputStream = contentResolver.openInputStream(request.uri);
                try {
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, optionsCreateBitmapOptions);
                    Utils.closeQuietly(inputStreamOpenInputStream);
                    calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions);
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    Utils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        InputStream inputStreamOpenInputStream2 = contentResolver.openInputStream(request.uri);
        try {
            return BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, optionsCreateBitmapOptions);
        } finally {
            Utils.closeQuietly(inputStreamOpenInputStream2);
        }
    }
}
