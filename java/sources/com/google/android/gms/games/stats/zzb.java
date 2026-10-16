package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzb implements Parcelable.Creator<zza> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zza createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        Bundle bundleCreateBundle = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    f = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 2:
                    f2 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 4:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 5:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 6:
                    f3 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 7:
                    f4 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 8:
                    bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                case 9:
                    f5 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 10:
                    f6 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 11:
                    f7 = SafeParcelReader.readFloat(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zza(f, f2, i, i2, i3, f3, f4, bundleCreateBundle, f5, f6, f7);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zza[] newArray(int i) {
        return new zza[i];
    }
}
