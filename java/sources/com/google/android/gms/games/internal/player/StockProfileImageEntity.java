package com.google.android.gms.games.internal.player;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class StockProfileImageEntity extends com.google.android.gms.games.internal.zzd implements StockProfileImage {
    public static final Parcelable.Creator<StockProfileImageEntity> CREATOR = new zzf();
    private final String zzmr;
    private final Uri zzms;

    public StockProfileImageEntity(String str, Uri uri) {
        this.zzmr = str;
        this.zzms = uri;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StockProfileImage)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        StockProfileImage stockProfileImage = (StockProfileImage) obj;
        return Objects.equal(this.zzmr, stockProfileImage.getImageUrl()) && Objects.equal(this.zzms, stockProfileImage.zzbz());
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* bridge */ /* synthetic */ StockProfileImage freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.internal.player.StockProfileImage
    public final String getImageUrl() {
        return this.zzmr;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzmr, this.zzms);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("ImageId", this.zzmr).add("ImageUri", this.zzms).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getImageUrl(), false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzms, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.internal.player.StockProfileImage
    public final Uri zzbz() {
        return this.zzms;
    }
}
