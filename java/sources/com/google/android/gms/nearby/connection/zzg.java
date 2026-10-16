package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzg implements Parcelable.Creator<DiscoveryOptions> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ DiscoveryOptions createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Strategy strategy = null;
        boolean z = false;
        boolean z2 = true;
        boolean z3 = true;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strategy = (Strategy) SafeParcelReader.createParcelable(parcel, header, Strategy.CREATOR);
            } else if (fieldId == 2) {
                z = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId == 3) {
                z2 = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                z3 = SafeParcelReader.readBoolean(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new DiscoveryOptions(strategy, z, z2, z3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ DiscoveryOptions[] newArray(int i) {
        return new DiscoveryOptions[i];
    }
}
