package com.twitter.sdk.android.core.services.params;

/* JADX INFO: loaded from: classes.dex */
public class Geocode {
    public final Distance distance;
    public final double latitude;
    public final double longitude;
    public final int radius;

    public enum Distance {
        MILES("mi"),
        KILOMETERS("km");

        public final String identifier;

        Distance(String str) {
            this.identifier = str;
        }
    }

    public Geocode(double d, double d2, int i, Distance distance) {
        this.latitude = d;
        this.longitude = d2;
        this.radius = i;
        this.distance = distance;
    }

    public String toString() {
        return this.latitude + "," + this.longitude + "," + this.radius + this.distance.identifier;
    }
}
