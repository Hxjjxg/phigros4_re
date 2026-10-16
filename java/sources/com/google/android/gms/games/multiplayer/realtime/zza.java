package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class zza implements Parcelable.Creator<RealTimeMessage> {
    zza() {
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ RealTimeMessage createFromParcel(Parcel parcel) {
        return new RealTimeMessage(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ RealTimeMessage[] newArray(int i) {
        return new RealTimeMessage[i];
    }
}
