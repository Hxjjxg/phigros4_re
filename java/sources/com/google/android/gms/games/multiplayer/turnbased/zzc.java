package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzc implements Parcelable.Creator<TurnBasedMatchEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        GameEntity gameEntity = null;
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        String strCreateString4 = null;
        byte[] bArrCreateByteArray = null;
        ArrayList arrayListCreateTypedList = null;
        String strCreateString5 = null;
        byte[] bArrCreateByteArray2 = null;
        Bundle bundleCreateBundle = null;
        String strCreateString6 = null;
        String strCreateString7 = null;
        long j = 0;
        long j2 = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    gameEntity = (GameEntity) SafeParcelReader.createParcelable(parcel, header, GameEntity.CREATOR);
                    break;
                case 2:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 3:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 5:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 7:
                    strCreateString4 = SafeParcelReader.createString(parcel, header);
                    break;
                case 8:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 9:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 10:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 11:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 12:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                case 13:
                    arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    strCreateString5 = SafeParcelReader.createString(parcel, header);
                    break;
                case 15:
                    bArrCreateByteArray2 = SafeParcelReader.createByteArray(parcel, header);
                    break;
                case 16:
                    i4 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 17:
                    bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                case 18:
                    i5 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 19:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 20:
                    strCreateString6 = SafeParcelReader.createString(parcel, header);
                    break;
                case 21:
                    strCreateString7 = SafeParcelReader.createString(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new TurnBasedMatchEntity(gameEntity, strCreateString, strCreateString2, j, strCreateString3, j2, strCreateString4, i, i2, i3, bArrCreateByteArray, arrayListCreateTypedList, strCreateString5, bArrCreateByteArray2, i4, bundleCreateBundle, i5, z, strCreateString6, strCreateString7);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ TurnBasedMatchEntity[] newArray(int i) {
        return new TurnBasedMatchEntity[i];
    }
}
