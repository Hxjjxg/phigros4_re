package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantResult extends com.google.android.gms.games.internal.zzd {
    public static final Parcelable.Creator<ParticipantResult> CREATOR = new zzd();
    public static final int MATCH_RESULT_DISAGREED = 5;
    public static final int MATCH_RESULT_DISCONNECT = 4;
    public static final int MATCH_RESULT_LOSS = 1;
    public static final int MATCH_RESULT_NONE = 3;
    public static final int MATCH_RESULT_TIE = 2;
    public static final int MATCH_RESULT_UNINITIALIZED = -1;
    public static final int MATCH_RESULT_WIN = 0;
    public static final int PLACING_UNINITIALIZED = -1;
    private final String zzhl;
    private final int zzom;
    private final int zzon;

    public ParticipantResult(String str, int i, int i2) {
        this.zzhl = (String) Preconditions.checkNotNull(str);
        boolean z = true;
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            z = false;
        }
        Preconditions.checkState(z);
        this.zzom = i;
        this.zzon = i2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ParticipantResult)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ParticipantResult participantResult = (ParticipantResult) obj;
        return participantResult.getPlacing() == getPlacing() && participantResult.getResult() == getResult() && Objects.equal(participantResult.getParticipantId(), getParticipantId());
    }

    public final String getParticipantId() {
        return this.zzhl;
    }

    public final int getPlacing() {
        return this.zzon;
    }

    public final int getResult() {
        return this.zzom;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(getPlacing()), Integer.valueOf(getResult()), getParticipantId());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getParticipantId(), false);
        SafeParcelWriter.writeInt(parcel, 2, getResult());
        SafeParcelWriter.writeInt(parcel, 3, getPlacing());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
