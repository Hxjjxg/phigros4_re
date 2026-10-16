package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class ResourceBitmapHunter extends BitmapHunter {
    private final Context context;

    ResourceBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    @Override // com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        Resources resources = Utils.getResources(this.context, request);
        return decodeResource(resources, Utils.getResourceId(resources, request), request);
    }

    @Override // com.squareup.picasso.BitmapHunter
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }

    private Bitmap decodeResource(Resources resources, int i, Request request) {
        BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(request);
        if (requiresInSampleSize(optionsCreateBitmapOptions)) {
            BitmapFactory.decodeResource(resources, i, optionsCreateBitmapOptions);
            calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions);
        }
        return BitmapFactory.decodeResource(resources, i, optionsCreateBitmapOptions);
    }
}
