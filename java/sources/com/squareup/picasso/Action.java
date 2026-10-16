package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
abstract class Action<T> {
    boolean cancelled;
    final Drawable errorDrawable;
    final int errorResId;
    final String key;
    final boolean noFade;
    final Picasso picasso;
    final Request request;
    final boolean skipCache;
    final WeakReference<T> target;
    boolean willReplay;

    abstract void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom);

    abstract void error();

    static class RequestWeakReference<T> extends WeakReference<T> {
        final Action action;

        public RequestWeakReference(Action action, T t, ReferenceQueue<? super T> referenceQueue) {
            super(t, referenceQueue);
            this.action = action;
        }
    }

    Action(Picasso picasso, T t, Request request, boolean z, boolean z2, int i, Drawable drawable, String str) {
        this.picasso = picasso;
        this.request = request;
        this.target = new RequestWeakReference(this, t, picasso.referenceQueue);
        this.skipCache = z;
        this.noFade = z2;
        this.errorResId = i;
        this.errorDrawable = drawable;
        this.key = str;
    }

    void cancel() {
        this.cancelled = true;
    }

    Request getRequest() {
        return this.request;
    }

    T getTarget() {
        return this.target.get();
    }

    String getKey() {
        return this.key;
    }

    boolean isCancelled() {
        return this.cancelled;
    }

    boolean willReplay() {
        return this.willReplay;
    }

    Picasso getPicasso() {
        return this.picasso;
    }
}
