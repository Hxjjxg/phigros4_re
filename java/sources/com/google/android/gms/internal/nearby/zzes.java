package com.google.android.gms.internal.nearby;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* JADX INFO: loaded from: classes.dex */
public final class zzes implements Parcelable.Creator<zzer> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzer createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        BluetoothDevice bluetoothDevice = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 2) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                strCreateString3 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                bluetoothDevice = (BluetoothDevice) SafeParcelReader.createParcelable(parcel, header, BluetoothDevice.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzer(strCreateString, strCreateString2, strCreateString3, bluetoothDevice);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzer[] newArray(int i) {
        return new zzer[i];
    }
}
