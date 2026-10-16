package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class MediaStoreBitmapHunter extends ContentStreamBitmapHunter {
    private static final String[] CONTENT_ORIENTATION = {Keys.ORIENTATION};

    MediaStoreBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    @Override // com.squareup.picasso.ContentStreamBitmapHunter, com.squareup.picasso.BitmapHunter
    Bitmap decode(Request request) throws IOException {
        Bitmap thumbnail;
        ContentResolver contentResolver = this.context.getContentResolver();
        setExifRotation(getExifOrientation(contentResolver, request.uri));
        String type = contentResolver.getType(request.uri);
        boolean z = type != null && type.startsWith("video/");
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request.targetWidth, request.targetHeight);
            if (!z && picassoKind == PicassoKind.FULL) {
                return super.decode(request);
            }
            long id = ContentUris.parseId(request.uri);
            BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(request);
            optionsCreateBitmapOptions.inJustDecodeBounds = true;
            calculateInSampleSize(request.targetWidth, request.targetHeight, picassoKind.width, picassoKind.height, optionsCreateBitmapOptions);
            if (z) {
                thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, picassoKind != PicassoKind.FULL ? picassoKind.androidKind : 1, optionsCreateBitmapOptions);
            } else {
                thumbnail = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, picassoKind.androidKind, optionsCreateBitmapOptions);
            }
            if (thumbnail != null) {
                return thumbnail;
            }
        }
        return super.decode(request);
    }

    static PicassoKind getPicassoKind(int i, int i2) {
        if (i <= PicassoKind.MICRO.width && i2 <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (i <= PicassoKind.MINI.width && i2 <= PicassoKind.MINI.height) {
            return PicassoKind.MINI;
        }
        return PicassoKind.FULL;
    }

    static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                return cursorQuery.getInt(0);
            }
            return 0;
        } catch (RuntimeException unused) {
            return 0;
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);

        final int androidKind;
        final int height;
        final int width;

        PicassoKind(int i, int i2, int i3) {
            this.androidKind = i;
            this.width = i2;
            this.height = i3;
        }
    }
}
