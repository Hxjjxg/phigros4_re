package com.tapsdk.antiaddiction.entities;

/* JADX INFO: loaded from: classes.dex */
public class TwoTuple<U, V> {
    public final U firstParam;
    public final V secondParam;

    public TwoTuple(U u, V v) {
        this.firstParam = u;
        this.secondParam = v;
    }

    public static <U, V> TwoTuple<U, V> create(U u, V v) {
        return new TwoTuple<>(u, v);
    }
}
