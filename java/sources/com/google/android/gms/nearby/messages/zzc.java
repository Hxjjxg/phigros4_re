package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.nearby.zzgp;
import com.google.android.gms.internal.nearby.zzgu;
import com.google.android.gms.nearby.messages.internal.zzad;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzc implements Parcelable.Creator<MessageFilter> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ MessageFilter createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        boolean z = false;
        int i2 = 0;
        ArrayList arrayListCreateTypedList = null;
        ArrayList arrayListCreateTypedList2 = null;
        ArrayList arrayListCreateTypedList3 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, zzad.CREATOR);
            } else if (fieldId == 2) {
                arrayListCreateTypedList2 = SafeParcelReader.createTypedList(parcel, header, zzgu.CREATOR);
            } else if (fieldId == 3) {
                z = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId == 4) {
                arrayListCreateTypedList3 = SafeParcelReader.createTypedList(parcel, header, zzgp.CREATOR);
            } else if (fieldId == 5) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId != 1000) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                i = SafeParcelReader.readInt(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new MessageFilter(i, arrayListCreateTypedList, arrayListCreateTypedList2, z, arrayListCreateTypedList3, i2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ MessageFilter[] newArray(int i) {
        return new MessageFilter[i];
    }
}
