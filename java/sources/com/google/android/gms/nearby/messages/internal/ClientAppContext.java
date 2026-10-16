package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class ClientAppContext extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<ClientAppContext> CREATOR = new zzd();
    private final int versionCode;
    private final boolean zzfg;
    private final String zzfi;
    private final String zzfj;
    private final String zzhe;

    @Deprecated
    public final int zzhf;

    ClientAppContext(int i, String str, String str2, boolean z, int i2, String str3) {
        this.versionCode = i;
        this.zzfj = (String) Preconditions.checkNotNull(str);
        if (str2 != null && !str2.isEmpty() && !str2.startsWith("0p:")) {
            Log.w("NearbyMessages", String.format(Locale.US, "ClientAppContext: 0P identifier(%s) without 0P prefix(%s)", str2, "0p:"));
            String strValueOf = String.valueOf(str2);
            str2 = strValueOf.length() != 0 ? "0p:".concat(strValueOf) : new String("0p:");
        }
        this.zzhe = str2;
        this.zzfg = z;
        this.zzhf = i2;
        this.zzfi = str3;
    }

    public ClientAppContext(String str, String str2, boolean z, String str3, int i) {
        this(1, str, str2, z, i, str3);
    }

    static final ClientAppContext zza(ClientAppContext clientAppContext, String str, String str2, boolean z) {
        if (clientAppContext != null) {
            return clientAppContext;
        }
        if (str == null && str2 == null) {
            return null;
        }
        return new ClientAppContext(str, str2, z, null, 0);
    }

    private static boolean zzc(String str, String str2) {
        return TextUtils.isEmpty(str) ? TextUtils.isEmpty(str2) : str.equals(str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClientAppContext)) {
            return false;
        }
        ClientAppContext clientAppContext = (ClientAppContext) obj;
        return zzc(this.zzfj, clientAppContext.zzfj) && zzc(this.zzhe, clientAppContext.zzhe) && this.zzfg == clientAppContext.zzfg && zzc(this.zzfi, clientAppContext.zzfi) && this.zzhf == clientAppContext.zzhf;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzfj, this.zzhe, Boolean.valueOf(this.zzfg), this.zzfi, Integer.valueOf(this.zzhf));
    }

    public final String toString() {
        return String.format(Locale.US, "{realClientPackageName: %s, zeroPartyIdentifier: %s, useRealClientApiKey: %b, apiKey: %s, callingContext: %d}", this.zzfj, this.zzhe, Boolean.valueOf(this.zzfg), this.zzfi, Integer.valueOf(this.zzhf));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.zzfj, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhe, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzfg);
        SafeParcelWriter.writeInt(parcel, 5, this.zzhf);
        SafeParcelWriter.writeString(parcel, 6, this.zzfi, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
