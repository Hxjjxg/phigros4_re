package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzft implements Parcelable.Creator<zzfq> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzfq createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        String strCreateString = null;
        String strCreateString2 = null;
        byte[] bArrCreateByteArray = null;
        IBinder iBinder4 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    iBinder = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 2:
                    iBinder2 = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 3:
                    iBinder3 = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 4:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                case 7:
                    iBinder4 = SafeParcelReader.readIBinder(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzfq(iBinder, iBinder2, iBinder3, strCreateString, strCreateString2, bArrCreateByteArray, iBinder4);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzfq[] newArray(int i) {
        return new zzfq[i];
    }
}
