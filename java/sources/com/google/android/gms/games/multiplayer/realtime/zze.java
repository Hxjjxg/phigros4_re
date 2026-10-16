package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class zze implements Parcelable.Creator<RoomEntity> {
    @Override // android.os.Parcelable.Creator
    public /* synthetic */ RoomEntity[] newArray(int i) {
        return new RoomEntity[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public RoomEntity createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        Bundle bundleCreateBundle = null;
        ArrayList arrayListCreateTypedList = null;
        long j = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 2:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 3:
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 4:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 5:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 7:
                    bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                case 8:
                    arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, ParticipantEntity.CREATOR);
                    break;
                case 9:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new RoomEntity(strCreateString, strCreateString2, j, i, strCreateString3, i2, bundleCreateBundle, arrayListCreateTypedList, i3);
    }
}
