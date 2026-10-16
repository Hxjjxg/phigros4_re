package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public class zzh implements Parcelable.Creator<GameEntity> {
    @Override // android.os.Parcelable.Creator
    public /* synthetic */ GameEntity[] newArray(int i) {
        return new GameEntity[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public GameEntity createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        String strCreateString4 = null;
        String strCreateString5 = null;
        String strCreateString6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        String strCreateString7 = null;
        String strCreateString8 = null;
        String strCreateString9 = null;
        String strCreateString10 = null;
        String strCreateString11 = null;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
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
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    strCreateString4 = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    strCreateString5 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    strCreateString6 = SafeParcelReader.createString(parcel, header);
                    break;
                case 7:
                    uri = (Uri) SafeParcelReader.createParcelable(parcel, header, Uri.CREATOR);
                    break;
                case 8:
                    uri2 = (Uri) SafeParcelReader.createParcelable(parcel, header, Uri.CREATOR);
                    break;
                case 9:
                    uri3 = (Uri) SafeParcelReader.createParcelable(parcel, header, Uri.CREATOR);
                    break;
                case 10:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 11:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 12:
                    strCreateString7 = SafeParcelReader.createString(parcel, header);
                    break;
                case 13:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 14:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 15:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 16:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 17:
                    z4 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 18:
                    strCreateString8 = SafeParcelReader.createString(parcel, header);
                    break;
                case 19:
                    strCreateString9 = SafeParcelReader.createString(parcel, header);
                    break;
                case 20:
                    strCreateString10 = SafeParcelReader.createString(parcel, header);
                    break;
                case 21:
                    z5 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 22:
                    z6 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 23:
                    z7 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 24:
                    strCreateString11 = SafeParcelReader.createString(parcel, header);
                    break;
                case 25:
                    z8 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new GameEntity(strCreateString, strCreateString2, strCreateString3, strCreateString4, strCreateString5, strCreateString6, uri, uri2, uri3, z, z2, strCreateString7, i, i2, i3, z3, z4, strCreateString8, strCreateString9, strCreateString10, z5, z6, z7, strCreateString11, z8);
    }
}
