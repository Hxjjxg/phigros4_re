package com.tapsdk.antiaddiction.entities;

/* JADX INFO: loaded from: classes.dex */
public class ThreeTuple<U, V, W> extends TwoTuple<U, V> {
    public final W thirdParam;

    public ThreeTuple(U u, V v, W w) {
        super(u, v);
        this.thirdParam = w;
    }

    public static <U, V, W> ThreeTuple<U, V, W> create(U u, V v, W w) {
        return new ThreeTuple<>(u, v, w);
    }
}
