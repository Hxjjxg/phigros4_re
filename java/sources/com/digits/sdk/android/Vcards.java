package com.digits.sdk.android;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class Vcards {

    @SerializedName("vcards")
    final List<String> vcards;

    Vcards(List<String> list) {
        ArrayList arrayList = new ArrayList();
        this.vcards = arrayList;
        arrayList.addAll(list);
    }
}
