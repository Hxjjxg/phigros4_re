package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.messages.Message;

/* JADX INFO: loaded from: classes.dex */
public final class zzaf extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzaf> CREATOR = new zzag();
    private final int versionCode;
    private final Message zzhk;

    zzaf(int i, Message message) {
        this.versionCode = i;
        this.zzhk = (Message) Preconditions.checkNotNull(message);
    }

    public static final zzaf zza(Message message) {
        return new zzaf(1, message);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzaf) {
            return Objects.equal(this.zzhk, ((zzaf) obj).zzhk);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzhk);
    }

    public final String toString() {
        String string = this.zzhk.toString();
        return new StringBuilder(String.valueOf(string).length() + 24).append("MessageWrapper{message=").append(string).append("}").toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzhk, i, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.versionCode);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
