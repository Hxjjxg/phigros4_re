package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class zza implements Parcelable.Creator<InvitationEntity> {
    @Override // android.os.Parcelable.Creator
    public /* synthetic */ InvitationEntity[] newArray(int i) {
        return new InvitationEntity[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public InvitationEntity createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        GameEntity gameEntity = null;
        String strCreateString = null;
        ParticipantEntity participantEntity = null;
        ArrayList arrayListCreateTypedList = null;
        long j = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
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
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 4:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) SafeParcelReader.createParcelable(parcel, header, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 8:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new InvitationEntity(gameEntity, strCreateString, j, i, participantEntity, arrayListCreateTypedList, i2, i3);
    }
}
