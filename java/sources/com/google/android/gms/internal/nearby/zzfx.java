package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzfx implements Parcelable.Creator<zzfu> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzfu createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder = null;
        zzfh zzfhVar = null;
        boolean z = false;
        String[] strArrCreateStringArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                iBinder = SafeParcelReader.readIBinder(parcel, header);
            } else if (fieldId == 2) {
                strArrCreateStringArray = SafeParcelReader.createStringArray(parcel, header);
            } else if (fieldId == 3) {
                zzfhVar = (zzfh) SafeParcelReader.createParcelable(parcel, header, zzfh.CREATOR);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                z = SafeParcelReader.readBoolean(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzfu(iBinder, strArrCreateStringArray, zzfhVar, z);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzfu[] newArray(int i) {
        return new zzfu[i];
    }
}
