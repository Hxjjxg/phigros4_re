package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Coordinates {
    public static final int INDEX_LATITUDE = 1;
    public static final int INDEX_LONGITUDE = 0;

    @SerializedName("coordinates")
    public final List<Double> coordinates;

    @SerializedName("type")
    public final String type;

    public Coordinates(Double d, Double d2, String str) {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(0, d);
        arrayList.add(1, d2);
        this.coordinates = arrayList;
        this.type = str;
    }

    public Double getLongitude() {
        return this.coordinates.get(0);
    }

    public Double getLatitude() {
        return this.coordinates.get(1);
    }
}
