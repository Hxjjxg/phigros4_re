package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzgi implements Parcelable.Creator<zzgg> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzgg createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            SafeParcelReader.getFieldId(header);
            SafeParcelReader.skipUnknownField(parcel, header);
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzgg();
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzgg[] newArray(int i) {
        return new zzgg[i];
    }
}
