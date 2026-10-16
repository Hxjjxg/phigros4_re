package com.google.android.gms.games.internal.experience;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class ExperienceEventEntity extends zzd implements ExperienceEvent {
    public static final Parcelable.Creator<ExperienceEventEntity> CREATOR = new zza();
    private final int type;
    private final String zzac;
    private final String zzkx;
    private final GameEntity zzky;
    private final String zzkz;
    private final String zzla;
    private final long zzlb;
    private final long zzlc;
    private final long zzld;
    private final int zzle;
    private final Uri zzr;

    ExperienceEventEntity(String str, GameEntity gameEntity, String str2, String str3, String str4, Uri uri, long j, long j2, long j3, int i, int i2) {
        this.zzkx = str;
        this.zzky = gameEntity;
        this.zzkz = str2;
        this.zzla = str3;
        this.zzac = str4;
        this.zzr = uri;
        this.zzlb = j;
        this.zzlc = j2;
        this.zzld = j3;
        this.type = i;
        this.zzle = i2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExperienceEvent)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ExperienceEvent experienceEvent = (ExperienceEvent) obj;
        return Objects.equal(experienceEvent.zzbm(), zzbm()) && Objects.equal(experienceEvent.getGame(), getGame()) && Objects.equal(experienceEvent.zzbn(), zzbn()) && Objects.equal(experienceEvent.zzbo(), zzbo()) && Objects.equal(experienceEvent.getIconImageUrl(), getIconImageUrl()) && Objects.equal(experienceEvent.getIconImageUri(), getIconImageUri()) && Objects.equal(Long.valueOf(experienceEvent.zzbp()), Long.valueOf(zzbp())) && Objects.equal(Long.valueOf(experienceEvent.zzbq()), Long.valueOf(zzbq())) && Objects.equal(Long.valueOf(experienceEvent.zzbr()), Long.valueOf(zzbr())) && Objects.equal(Integer.valueOf(experienceEvent.getType()), Integer.valueOf(getType())) && Objects.equal(Integer.valueOf(experienceEvent.zzbs()), Integer.valueOf(zzbs()));
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* bridge */ /* synthetic */ ExperienceEvent freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final Game getGame() {
        return this.zzky;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final Uri getIconImageUri() {
        return this.zzr;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final String getIconImageUrl() {
        return this.zzac;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final int getType() {
        return this.type;
    }

    public final int hashCode() {
        return Objects.hashCode(zzbm(), getGame(), zzbn(), zzbo(), getIconImageUrl(), getIconImageUri(), Long.valueOf(zzbp()), Long.valueOf(zzbq()), Long.valueOf(zzbr()), Integer.valueOf(getType()), Integer.valueOf(zzbs()));
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("ExperienceId", zzbm()).add("Game", getGame()).add("DisplayTitle", zzbn()).add("DisplayDescription", zzbo()).add("IconImageUrl", getIconImageUrl()).add("IconImageUri", getIconImageUri()).add("CreatedTimestamp", Long.valueOf(zzbp())).add("XpEarned", Long.valueOf(zzbq())).add("CurrentXp", Long.valueOf(zzbr())).add("Type", Integer.valueOf(getType())).add("NewLevel", Integer.valueOf(zzbs())).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkx, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzky, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzkz, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzla, false);
        SafeParcelWriter.writeString(parcel, 5, getIconImageUrl(), false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzr, i, false);
        SafeParcelWriter.writeLong(parcel, 7, this.zzlb);
        SafeParcelWriter.writeLong(parcel, 8, this.zzlc);
        SafeParcelWriter.writeLong(parcel, 9, this.zzld);
        SafeParcelWriter.writeInt(parcel, 10, this.type);
        SafeParcelWriter.writeInt(parcel, 11, this.zzle);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final String zzbm() {
        return this.zzkx;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final String zzbn() {
        return this.zzkz;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final String zzbo() {
        return this.zzla;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final long zzbp() {
        return this.zzlb;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final long zzbq() {
        return this.zzlc;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final long zzbr() {
        return this.zzld;
    }

    @Override // com.google.android.gms.games.internal.experience.ExperienceEvent
    public final int zzbs() {
        return this.zzle;
    }
}
