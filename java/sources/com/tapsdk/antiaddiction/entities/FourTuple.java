package com.tapsdk.antiaddiction.entities;

/* JADX INFO: loaded from: classes.dex */
public class FourTuple<U, V, W, X> extends ThreeTuple<U, V, W> {
    public final X fourTuple;

    public FourTuple(U u, V v, W w, X x) {
        super(u, v, w);
        this.fourTuple = x;
    }

    public static <U, V, W, X> FourTuple<U, V, W, X> create(U u, V v, W w, X x) {
        return new FourTuple<>(u, v, w, x);
    }

    public String toString() {
        return "FourTuple{firstParam=" + this.firstParam + ", secondParam=" + this.secondParam + ", thirdParam=" + this.thirdParam + ", fourTuple=" + this.fourTuple + '}';
    }
}
