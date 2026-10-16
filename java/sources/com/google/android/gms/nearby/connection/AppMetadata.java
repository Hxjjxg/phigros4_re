package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class AppMetadata extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AppMetadata> CREATOR = new zzd();
    private final List<AppIdentifier> zzp;

    public AppMetadata(List<AppIdentifier> list) {
        this.zzp = (List) Preconditions.checkNotNull(list, "Must specify application identifiers");
        Preconditions.checkNotZero(list.size(), (Object) "Application identifiers cannot be empty");
    }

    public final List<AppIdentifier> getAppIdentifiers() {
        return this.zzp;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getAppIdentifiers(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
