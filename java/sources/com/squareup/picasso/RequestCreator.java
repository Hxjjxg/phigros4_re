package com.squareup.picasso;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class RequestCreator {
    private static int nextId;
    private final Request.Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean skipMemoryCache;

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRequestId() {
        if (Utils.isMain()) {
            int i = nextId;
            nextId = i + 1;
            return i;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicInteger atomicInteger = new AtomicInteger();
        Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.RequestCreator.1
            @Override // java.lang.Runnable
            public void run() {
                atomicInteger.set(RequestCreator.getRequestId());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.RequestCreator.2
                @Override // java.lang.Runnable
                public void run() {
                    throw new RuntimeException(e);
                }
            });
        }
        return atomicInteger.get();
    }

    RequestCreator(Picasso picasso, Uri uri, int i) {
        if (picasso.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso;
        this.data = new Request.Builder(uri, i);
    }

    RequestCreator() {
        this.picasso = null;
        this.data = new Request.Builder((Uri) null, 0);
    }

    public RequestCreator placeholder(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.placeholderResId = i;
        return this;
    }

    public RequestCreator placeholder(Drawable drawable) {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.placeholderDrawable = drawable;
        return this;
    }

    public RequestCreator error(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        }
        if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorResId = i;
        return this;
    }

    public RequestCreator error(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        }
        if (this.errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorDrawable = drawable;
        return this;
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    public RequestCreator resizeDimen(int i, int i2) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(i), resources.getDimensionPixelSize(i2));
    }

    public RequestCreator resize(int i, int i2) {
        this.data.resize(i, i2);
        return this;
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator rotate(float f) {
        this.data.rotate(f);
        return this;
    }

    public RequestCreator rotate(float f, float f2, float f3) {
        this.data.rotate(f, f2, f3);
        return this;
    }

    public RequestCreator config(Bitmap.Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public RequestCreator skipMemoryCache() {
        this.skipMemoryCache = true;
        return this;
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public Bitmap get() throws IOException {
        long jNanoTime = System.nanoTime();
        Utils.checkNotMain();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with get.");
        }
        if (!this.data.hasImage()) {
            return null;
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        GetAction getAction = new GetAction(this.picasso, requestCreateRequest, this.skipMemoryCache, Utils.createKey(requestCreateRequest, new StringBuilder()));
        Context context = this.picasso.context;
        Picasso picasso = this.picasso;
        return BitmapHunter.forRequest(context, picasso, picasso.dispatcher, this.picasso.cache, this.picasso.stats, getAction, this.picasso.dispatcher.downloader).hunt();
    }

    public void fetch() {
        long jNanoTime = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        }
        if (this.data.hasImage()) {
            Request requestCreateRequest = createRequest(jNanoTime);
            this.picasso.submit(new FetchAction(this.picasso, requestCreateRequest, this.skipMemoryCache, Utils.createKey(requestCreateRequest, new StringBuilder())));
        }
    }

    public void into(Target target) {
        Bitmap bitmapQuickMemoryCacheCheck;
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        }
        Drawable drawable = this.placeholderResId != 0 ? this.picasso.context.getResources().getDrawable(this.placeholderResId) : this.placeholderDrawable;
        if (!this.data.hasImage()) {
            this.picasso.cancelRequest(target);
            target.onPrepareLoad(drawable);
            return;
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        String strCreateKey = Utils.createKey(requestCreateRequest);
        if (!this.skipMemoryCache && (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(strCreateKey)) != null) {
            this.picasso.cancelRequest(target);
            target.onBitmapLoaded(bitmapQuickMemoryCacheCheck, Picasso.LoadedFrom.MEMORY);
        } else {
            target.onPrepareLoad(drawable);
            this.picasso.enqueueAndSubmit(new TargetAction(this.picasso, target, requestCreateRequest, this.skipMemoryCache, this.errorResId, this.errorDrawable, strCreateKey));
        }
    }

    public void into(RemoteViews remoteViews, int i, int i2, Notification notification) {
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        }
        if (notification == null) {
            throw new IllegalArgumentException("Notification must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        }
        if (this.placeholderDrawable != null || this.errorDrawable != null) {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        performRemoteViewInto(new RemoteViewsAction.NotificationAction(this.picasso, requestCreateRequest, remoteViews, i, i2, notification, this.skipMemoryCache, this.errorResId, Utils.createKey(requestCreateRequest)));
    }

    public void into(RemoteViews remoteViews, int i, int[] iArr) {
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        }
        if (iArr == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        }
        if (this.placeholderDrawable != null || this.errorDrawable != null) {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        performRemoteViewInto(new RemoteViewsAction.AppWidgetAction(this.picasso, requestCreateRequest, remoteViews, i, iArr, this.skipMemoryCache, this.errorResId, Utils.createKey(requestCreateRequest)));
    }

    public void into(ImageView imageView) {
        into(imageView, null);
    }

    public void into(ImageView imageView, Callback callback) {
        Bitmap bitmapQuickMemoryCacheCheck;
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (imageView == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (!this.data.hasImage()) {
            this.picasso.cancelRequest(imageView);
            PicassoDrawable.setPlaceholder(imageView, this.placeholderResId, this.placeholderDrawable);
            return;
        }
        if (this.deferred) {
            if (this.data.hasSize()) {
                throw new IllegalStateException("Fit cannot be used with resize.");
            }
            int measuredWidth = imageView.getMeasuredWidth();
            int measuredHeight = imageView.getMeasuredHeight();
            if (measuredWidth == 0 || measuredHeight == 0) {
                PicassoDrawable.setPlaceholder(imageView, this.placeholderResId, this.placeholderDrawable);
                this.picasso.defer(imageView, new DeferredRequestCreator(this, imageView, callback));
                return;
            }
            this.data.resize(measuredWidth, measuredHeight);
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        String strCreateKey = Utils.createKey(requestCreateRequest);
        if (!this.skipMemoryCache && (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(strCreateKey)) != null) {
            this.picasso.cancelRequest(imageView);
            PicassoDrawable.setBitmap(imageView, this.picasso.context, bitmapQuickMemoryCacheCheck, Picasso.LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
            if (this.picasso.loggingEnabled) {
                Utils.log("Main", "completed", requestCreateRequest.plainId(), "from " + Picasso.LoadedFrom.MEMORY);
            }
            if (callback != null) {
                callback.onSuccess();
                return;
            }
            return;
        }
        PicassoDrawable.setPlaceholder(imageView, this.placeholderResId, this.placeholderDrawable);
        this.picasso.enqueueAndSubmit(new ImageViewAction(this.picasso, imageView, requestCreateRequest, this.skipMemoryCache, this.noFade, this.errorResId, this.errorDrawable, strCreateKey, callback));
    }

    private Request createRequest(long j) {
        int requestId = getRequestId();
        Request requestBuild = this.data.build();
        requestBuild.id = requestId;
        requestBuild.started = j;
        boolean z = this.picasso.loggingEnabled;
        if (z) {
            Utils.log("Main", "created", requestBuild.plainId(), requestBuild.toString());
        }
        Request requestTransformRequest = this.picasso.transformRequest(requestBuild);
        if (requestTransformRequest != requestBuild) {
            requestTransformRequest.id = requestId;
            requestTransformRequest.started = j;
            if (z) {
                Utils.log("Main", "changed", requestTransformRequest.logId(), "into " + requestTransformRequest);
            }
        }
        return requestTransformRequest;
    }

    private void performRemoteViewInto(RemoteViewsAction remoteViewsAction) {
        Bitmap bitmapQuickMemoryCacheCheck;
        if (!this.skipMemoryCache && (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(remoteViewsAction.getKey())) != null) {
            remoteViewsAction.complete(bitmapQuickMemoryCacheCheck, Picasso.LoadedFrom.MEMORY);
            return;
        }
        int i = this.placeholderResId;
        if (i != 0) {
            remoteViewsAction.setImageResource(i);
        }
        this.picasso.enqueueAndSubmit(remoteViewsAction);
    }
}
