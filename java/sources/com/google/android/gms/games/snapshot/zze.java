package com.google.android.gms.games.snapshot;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zze extends com.google.android.gms.games.internal.zzd implements SnapshotMetadataChange {
    public static final Parcelable.Creator<zze> CREATOR = new zzd();
    private final String description;
    private final Long zzqq;
    private final Uri zzqs;
    private final Long zzqt;
    private BitmapTeleporter zzqu;

    zze() {
        this(null, null, null, null, null);
    }

    zze(String str, Long l, BitmapTeleporter bitmapTeleporter, Uri uri, Long l2) {
        this.description = str;
        this.zzqt = l;
        this.zzqu = bitmapTeleporter;
        this.zzqs = uri;
        this.zzqq = l2;
        if (bitmapTeleporter != null) {
            Preconditions.checkState(uri == null, "Cannot set both a URI and an image");
        } else if (uri != null) {
            Preconditions.checkState(bitmapTeleporter == null, "Cannot set both a URI and an image");
        }
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadataChange
    public final Bitmap getCoverImage() {
        BitmapTeleporter bitmapTeleporter = this.zzqu;
        if (bitmapTeleporter == null) {
            return null;
        }
        return bitmapTeleporter.get();
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadataChange
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadataChange
    public final Long getPlayedTimeMillis() {
        return this.zzqt;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadataChange
    public final Long getProgressValue() {
        return this.zzqq;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getDescription(), false);
        SafeParcelWriter.writeLongObject(parcel, 2, getPlayedTimeMillis(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzqs, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzqu, i, false);
        SafeParcelWriter.writeLongObject(parcel, 6, getProgressValue(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadataChange
    public final BitmapTeleporter zzcm() {
        return this.zzqu;
    }
}
