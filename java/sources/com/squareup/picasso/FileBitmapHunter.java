package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class FileBitmapHunter extends ContentStreamBitmapHunter {
    FileBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    @Override // com.squareup.picasso.ContentStreamBitmapHunter, com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        setExifRotation(getFileExifRotation(request.uri));
        return super.decode(request);
    }

    static int getFileExifRotation(Uri uri) throws IOException {
        int attributeInt = new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return 180;
        }
        if (attributeInt != 6) {
            return attributeInt != 8 ? 0 : 270;
        }
        return 90;
    }
}
