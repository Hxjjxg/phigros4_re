package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
abstract class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>() { // from class: com.squareup.picasso.BitmapHunter.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    Picasso.LoadedFrom loadedFrom;
    final Picasso picasso;
    Bitmap result;
    final boolean skipMemoryCache;
    final Stats stats;

    abstract Bitmap decode(Request request) throws IOException;

    boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        return false;
    }

    boolean supportsReplay() {
        return false;
    }

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.key = action.getKey();
        this.data = action.getRequest();
        this.skipMemoryCache = action.skipCache;
        this.action = action;
    }

    protected void setExifRotation(int i) {
        this.exifRotation = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                try {
                    updateThreadName(this.data);
                    if (this.picasso.loggingEnabled) {
                        Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
                    }
                    Bitmap bitmapHunt = hunt();
                    this.result = bitmapHunt;
                    if (bitmapHunt == null) {
                        this.dispatcher.dispatchFailed(this);
                    } else {
                        this.dispatcher.dispatchComplete(this);
                    }
                } catch (Downloader.ResponseException e) {
                    this.exception = e;
                    this.dispatcher.dispatchFailed(this);
                } catch (Exception e2) {
                    this.exception = e2;
                    this.dispatcher.dispatchFailed(this);
                }
            } catch (IOException e3) {
                this.exception = e3;
                this.dispatcher.dispatchRetry(this);
            } catch (OutOfMemoryError e4) {
                StringWriter stringWriter = new StringWriter();
                this.stats.createSnapshot().dump(new PrintWriter(stringWriter));
                this.exception = new RuntimeException(stringWriter.toString(), e4);
                this.dispatcher.dispatchFailed(this);
            }
        } finally {
            Thread.currentThread().setName("Picasso-Idle");
        }
    }

    Bitmap hunt() throws IOException {
        Bitmap bitmap;
        if (!this.skipMemoryCache && (bitmap = this.cache.get(this.key)) != null) {
            this.stats.dispatchCacheHit();
            this.loadedFrom = Picasso.LoadedFrom.MEMORY;
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
            }
            return bitmap;
        }
        Bitmap bitmapDecode = decode(this.data);
        if (bitmapDecode != null) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(bitmapDecode);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                        bitmapDecode = transformResult(this.data, bitmapDecode, this.exifRotation);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId());
                        }
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmapDecode = applyCustomTransformations(this.data.transformations, bitmapDecode);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                        }
                    }
                }
                if (bitmapDecode != null) {
                    this.stats.dispatchBitmapTransformed(bitmapDecode);
                }
            }
        }
        return bitmapDecode;
    }

    void attach(Action action) {
        boolean z = this.picasso.loggingEnabled;
        Request request = action.request;
        if (this.action == null) {
            this.action = action;
            if (z) {
                List<Action> list = this.actions;
                if (list == null || list.isEmpty()) {
                    Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                    return;
                } else {
                    Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                    return;
                }
            }
            return;
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action);
        if (z) {
            Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
        }
    }

    void detach(Action action) {
        if (this.action == action) {
            this.action = null;
        } else {
            List<Action> list = this.actions;
            if (list != null) {
                list.remove(action);
            }
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    boolean cancel() {
        Future<?> future;
        if (this.action != null) {
            return false;
        }
        List<Action> list = this.actions;
        return (list == null || list.isEmpty()) && (future = this.future) != null && future.cancel(false);
    }

    boolean isCancelled() {
        Future<?> future = this.future;
        return future != null && future.isCancelled();
    }

    boolean shouldSkipMemoryCache() {
        return this.skipMemoryCache;
    }

    Bitmap getResult() {
        return this.result;
    }

    String getKey() {
        return this.key;
    }

    Request getData() {
        return this.data;
    }

    Action getAction() {
        return this.action;
    }

    Picasso getPicasso() {
        return this.picasso;
    }

    List<Action> getActions() {
        return this.actions;
    }

    Exception getException() {
        return this.exception;
    }

    Picasso.LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    static void updateThreadName(Request request) {
        String name = request.getName();
        StringBuilder sb = NAME_BUILDER.get();
        sb.ensureCapacity(name.length() + 8);
        sb.replace(8, sb.length(), name);
        Thread.currentThread().setName(sb.toString());
    }

    static BitmapHunter forRequest(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        if (action.getRequest().resourceId != 0) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        Uri uri = action.getRequest().uri;
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            if (ContactsContract.Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && !uri.getPathSegments().contains("photo")) {
                return new ContactsPhotoBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            if ("media".equals(uri.getAuthority())) {
                return new MediaStoreBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new ContentStreamBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        if (Keys.Scheme.FILE.equals(scheme)) {
            if (!uri.getPathSegments().isEmpty() && "android_asset".equals(uri.getPathSegments().get(0))) {
                return new AssetBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new FileBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        if ("android.resource".equals(scheme)) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        return new NetworkBitmapHunter(picasso, dispatcher, cache, stats, action, downloader);
    }

    static BitmapFactory.Options createBitmapOptions(Request request) {
        boolean zHasSize = request.hasSize();
        boolean z = request.config != null;
        if (!zHasSize && !z) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = zHasSize;
        if (z) {
            options.inPreferredConfig = request.config;
        }
        return options;
    }

    static boolean requiresInSampleSize(BitmapFactory.Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    static void calculateInSampleSize(int i, int i2, BitmapFactory.Options options) {
        calculateInSampleSize(i, i2, options.outWidth, options.outHeight, options);
    }

    static void calculateInSampleSize(int i, int i2, int i3, int i4, BitmapFactory.Options options) {
        int iRound;
        if (i4 > i2 || i3 > i) {
            int iRound2 = Math.round(i4 / i2);
            iRound = Math.round(i3 / i);
            if (iRound2 < iRound) {
                iRound = iRound2;
            }
        } else {
            iRound = 1;
        }
        options.inSampleSize = iRound;
        options.inJustDecodeBounds = false;
    }

    static Bitmap applyCustomTransformations(List<Transformation> list, Bitmap bitmap) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            final Transformation transformation = list.get(i);
            Bitmap bitmapTransform = transformation.transform(bitmap);
            if (bitmapTransform == null) {
                final StringBuilder sbAppend = new StringBuilder().append("Transformation ").append(transformation.key()).append(" returned null after ").append(i).append(" previous transformation(s).\n\nTransformation list:\n");
                Iterator<Transformation> it = list.iterator();
                while (it.hasNext()) {
                    sbAppend.append(it.next().key()).append('\n');
                }
                Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        throw new NullPointerException(sbAppend.toString());
                    }
                });
                return null;
            }
            if (bitmapTransform == bitmap && bitmap.isRecycled()) {
                Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.3
                    @Override // java.lang.Runnable
                    public void run() {
                        throw new IllegalStateException("Transformation " + transformation.key() + " returned input Bitmap but recycled it.");
                    }
                });
                return null;
            }
            if (bitmapTransform != bitmap && !bitmap.isRecycled()) {
                Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.4
                    @Override // java.lang.Runnable
                    public void run() {
                        throw new IllegalStateException("Transformation " + transformation.key() + " mutated input Bitmap but failed to recycle the original.");
                    }
                });
                return null;
            }
            i++;
            bitmap = bitmapTransform;
        }
        return bitmap;
    }

    static Bitmap transformResult(Request request, Bitmap bitmap, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = 0;
        boolean z = i == 90 || i == 270;
        int height = z ? bitmap.getHeight() : bitmap.getWidth();
        int width = z ? bitmap.getWidth() : bitmap.getHeight();
        Matrix matrix = new Matrix();
        if (request.needsMatrixTransform()) {
            int i9 = request.targetWidth;
            int i10 = request.targetHeight;
            float f = request.rotationDegrees;
            if (f != 0.0f) {
                if (request.hasRotationPivot) {
                    matrix.setRotate(f, request.rotationPivotX, request.rotationPivotY);
                } else {
                    matrix.setRotate(f);
                }
            }
            if (request.centerCrop) {
                float f2 = i9;
                float f3 = height;
                float f4 = f2 / f3;
                float f5 = width;
                float f6 = i10 / f5;
                if (f4 > f6) {
                    int iCeil = (int) Math.ceil(f5 * (f6 / f4));
                    int i11 = height;
                    i7 = (width - iCeil) / 2;
                    width = iCeil;
                    i6 = i11;
                } else {
                    int iCeil2 = (int) Math.ceil(f3 * (f4 / f6));
                    int i12 = (height - iCeil2) / 2;
                    i6 = iCeil2;
                    f4 = f6;
                    i7 = 0;
                    i8 = i12;
                }
                matrix.preScale(f4, f4);
                i2 = width;
                i3 = i8;
                i4 = i7;
                i5 = i6;
            } else {
                if (request.centerInside) {
                    float f7 = i9 / height;
                    float f8 = i10 / width;
                    if (f7 >= f8) {
                        f7 = f8;
                    }
                    matrix.preScale(f7, f7);
                } else if (i9 != 0 && i10 != 0 && (i9 != height || i10 != width)) {
                    matrix.preScale(i9 / height, i10 / width);
                }
                i2 = width;
                i3 = 0;
                i4 = 0;
                i5 = height;
            }
        } else {
            i2 = width;
            i3 = 0;
            i4 = 0;
            i5 = height;
        }
        if (i != 0) {
            matrix.preRotate(i);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, i3, i4, i5, i2, matrix, true);
        if (bitmapCreateBitmap == bitmap) {
            return bitmap;
        }
        bitmap.recycle();
        return bitmapCreateBitmap;
    }
}
