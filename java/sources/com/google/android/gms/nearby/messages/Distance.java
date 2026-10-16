package com.google.android.gms.nearby.messages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public interface Distance extends Comparable<Distance> {
    public static final Distance UNKNOWN = new com.google.android.gms.nearby.messages.internal.zze(1, Double.NaN);

    @Retention(RetentionPolicy.SOURCE)
    public @interface Accuracy {
        public static final int LOW = 1;
    }

    int compareTo(Distance distance);

    int getAccuracy();

    double getMeters();
}
