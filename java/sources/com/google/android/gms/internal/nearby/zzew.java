package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzew implements Parcelable.Creator<zzev> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzev createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        boolean z = false;
        zzfh zzfhVar = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 2) {
                zzfhVar = (zzfh) SafeParcelReader.createParcelable(parcel, header, zzfh.CREATOR);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                z = SafeParcelReader.readBoolean(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzev(strCreateString, zzfhVar, z);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzev[] newArray(int i) {
        return new zzev[i];
    }
}
