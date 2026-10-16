package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzi implements Parcelable.Creator<PayloadTransferUpdate> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PayloadTransferUpdate createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        int i = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                j = SafeParcelReader.readLong(parcel, header);
            } else if (fieldId == 2) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                j2 = SafeParcelReader.readLong(parcel, header);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                j3 = SafeParcelReader.readLong(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new PayloadTransferUpdate(j, i, j2, j3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PayloadTransferUpdate[] newArray(int i) {
        return new PayloadTransferUpdate[i];
    }
}
