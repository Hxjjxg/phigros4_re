package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.nearby.zzgs;
import com.google.android.gms.nearby.messages.Message;

/* JADX INFO: loaded from: classes.dex */
public final class zzci implements Parcelable.Creator<Update> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Update createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        Message message = null;
        zze zzeVar = null;
        zza zzaVar = null;
        zzgs zzgsVar = null;
        byte[] bArrCreateByteArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 3:
                    message = (Message) SafeParcelReader.createParcelable(parcel, header, Message.CREATOR);
                    break;
                case 4:
                    zzeVar = (zze) SafeParcelReader.createParcelable(parcel, header, zze.CREATOR);
                    break;
                case 5:
                    zzaVar = (zza) SafeParcelReader.createParcelable(parcel, header, zza.CREATOR);
                    break;
                case 6:
                    zzgsVar = (zzgs) SafeParcelReader.createParcelable(parcel, header, zzgs.CREATOR);
                    break;
                case 7:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Update(i, i2, message, zzeVar, zzaVar, zzgsVar, bArrCreateByteArray);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Update[] newArray(int i) {
        return new Update[i];
    }
}
