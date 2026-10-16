package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzgl implements Parcelable.Creator<zzgj> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzgj createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            SafeParcelReader.getFieldId(header);
            SafeParcelReader.skipUnknownField(parcel, header);
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzgj();
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzgj[] newArray(int i) {
        return new zzgj[i];
    }
}
