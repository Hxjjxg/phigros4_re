package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzr extends zza {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private List<Filter> zzlc;
    private final zzx zzlj;
    private final List<FilterHolder> zzly;

    public zzr(zzx zzxVar, Filter filter, Filter... filterArr) {
        this.zzlj = zzxVar;
        ArrayList arrayList = new ArrayList(filterArr.length + 1);
        this.zzly = arrayList;
        arrayList.add(new FilterHolder(filter));
        ArrayList arrayList2 = new ArrayList(filterArr.length + 1);
        this.zzlc = arrayList2;
        arrayList2.add(filter);
        for (Filter filter2 : filterArr) {
            this.zzly.add(new FilterHolder(filter2));
            this.zzlc.add(filter2);
        }
    }

    public zzr(zzx zzxVar, Iterable<Filter> iterable) {
        this.zzlj = zzxVar;
        this.zzlc = new ArrayList();
        this.zzly = new ArrayList();
        for (Filter filter : iterable) {
            this.zzlc.add(filter);
            this.zzly.add(new FilterHolder(filter));
        }
    }

    zzr(zzx zzxVar, List<FilterHolder> list) {
        this.zzlj = zzxVar;
        this.zzly = list;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlj, i, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzly, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.drive.query.Filter
    public final <T> T zza(zzj<T> zzjVar) {
        ArrayList arrayList = new ArrayList();
        Iterator<FilterHolder> it = this.zzly.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getFilter().zza(zzjVar));
        }
        return zzjVar.zza(this.zzlj, arrayList);
    }
}
