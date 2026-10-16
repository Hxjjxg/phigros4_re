package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzp implements Parcelable.Creator<zzm> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        String strCreateString = null;
        byte[] bArrCreateByteArray = null;
        IBinder iBinder3 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                iBinder = SafeParcelReader.readIBinder(parcel, header);
            } else if (fieldId == 2) {
                iBinder2 = SafeParcelReader.readIBinder(parcel, header);
            } else if (fieldId == 3) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 4) {
                bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
            } else if (fieldId != 5) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                iBinder3 = SafeParcelReader.readIBinder(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzm(iBinder, iBinder2, strCreateString, bArrCreateByteArray, iBinder3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm[] newArray(int i) {
        return new zzm[i];
    }
}
