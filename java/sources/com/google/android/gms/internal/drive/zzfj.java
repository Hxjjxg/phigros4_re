package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEvent;

/* JADX INFO: loaded from: classes.dex */
public final class zzfj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfj> CREATOR = new zzfk();
    private final int zzcy;
    private final ChangeEvent zzhl;
    private final CompletionEvent zzhm;
    private final com.google.android.gms.drive.events.zzo zzhn;
    private final com.google.android.gms.drive.events.zzb zzho;
    private final com.google.android.gms.drive.events.zzv zzhp;
    private final com.google.android.gms.drive.events.zzr zzhq;

    zzfj(int i, ChangeEvent changeEvent, CompletionEvent completionEvent, com.google.android.gms.drive.events.zzo zzoVar, com.google.android.gms.drive.events.zzb zzbVar, com.google.android.gms.drive.events.zzv zzvVar, com.google.android.gms.drive.events.zzr zzrVar) {
        this.zzcy = i;
        this.zzhl = changeEvent;
        this.zzhm = completionEvent;
        this.zzhn = zzoVar;
        this.zzho = zzbVar;
        this.zzhp = zzvVar;
        this.zzhq = zzrVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzcy);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzhl, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzhm, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzhn, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzho, i, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzhp, i, false);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzhq, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final DriveEvent zzak() {
        int i = this.zzcy;
        if (i == 1) {
            return this.zzhl;
        }
        if (i == 2) {
            return this.zzhm;
        }
        if (i == 3) {
            return this.zzhn;
        }
        if (i == 4) {
            return this.zzho;
        }
        if (i == 7) {
            return this.zzhp;
        }
        if (i == 8) {
            return this.zzhq;
        }
        throw new IllegalStateException(new StringBuilder(33).append("Unexpected event type ").append(this.zzcy).toString());
    }
}
