package com.google.android.gms.games;

/* JADX INFO: loaded from: classes.dex */
public class AnnotatedData<T> {
    private final T data;
    private final boolean zzi;

    public AnnotatedData(T t, boolean z) {
        this.data = t;
        this.zzi = z;
    }

    public T get() {
        return this.data;
    }

    public boolean isStale() {
        return this.zzi;
    }
}
