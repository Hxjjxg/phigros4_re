package io.fabric.sdk.android.services.cache;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractValueCache<T> implements ValueCache<T> {
    private final ValueCache<T> childCache;

    protected abstract void cacheValue(Context context, T t);

    protected abstract void doInvalidate(Context context);

    protected abstract T getCached(Context context);

    public AbstractValueCache() {
        this(null);
    }

    public AbstractValueCache(ValueCache<T> valueCache) {
        this.childCache = valueCache;
    }

    @Override // io.fabric.sdk.android.services.cache.ValueCache
    public final synchronized T get(Context context, ValueLoader<T> valueLoader) throws Exception {
        T cached;
        cached = getCached(context);
        if (cached == null) {
            ValueCache<T> valueCache = this.childCache;
            cached = valueCache != null ? valueCache.get(context, valueLoader) : valueLoader.load(context);
            cache(context, cached);
        }
        return cached;
    }

    @Override // io.fabric.sdk.android.services.cache.ValueCache
    public final synchronized void invalidate(Context context) {
        doInvalidate(context);
    }

    private void cache(Context context, T t) {
        t.getClass();
        cacheValue(context, t);
    }
}
