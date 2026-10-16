package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzb implements Parcelable.Creator<AdvertisingOptions> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AdvertisingOptions createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Strategy strategy = null;
        byte[] bArrCreateByteArray = null;
        boolean z = true;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    strategy = (Strategy) SafeParcelReader.createParcelable(parcel, header, Strategy.CREATOR);
                    break;
                case 2:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 4:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 5:
                    z4 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 6:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new AdvertisingOptions(strategy, z, z2, z3, z4, bArrCreateByteArray);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AdvertisingOptions[] newArray(int i) {
        return new AdvertisingOptions[i];
    }
}
