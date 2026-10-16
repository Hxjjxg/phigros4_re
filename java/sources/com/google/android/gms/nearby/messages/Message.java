package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.nearby.zzgs;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Message extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final int MAX_CONTENT_SIZE_BYTES = 102400;
    public static final int MAX_TYPE_LENGTH = 32;
    public static final String MESSAGE_NAMESPACE_RESERVED = "__reserved_namespace";
    public static final String MESSAGE_TYPE_AUDIO_BYTES = "__audio_bytes";
    public static final String MESSAGE_TYPE_EDDYSTONE_UID = "__eddystone_uid";
    public static final String MESSAGE_TYPE_I_BEACON_ID = "__i_beacon_id";
    private final byte[] content;
    private final String namespace;
    private final String type;
    private final int versionCode;

    @Deprecated
    private final zzgs[] zzev;
    private final long zzew;
    public static final Parcelable.Creator<Message> CREATOR = new zza();
    private static final zzgs[] zzeu = {zzgs.zzgv};

    Message(int i, byte[] bArr, String str, String str2, zzgs[] zzgsVarArr, long j) {
        this.versionCode = i;
        this.type = (String) Preconditions.checkNotNull(str2);
        this.namespace = str == null ? "" : str;
        this.zzew = j;
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length <= 102400, "Content length(%d) must not exceed MAX_CONTENT_SIZE_BYTES(%d)", Integer.valueOf(bArr.length), Integer.valueOf(MAX_CONTENT_SIZE_BYTES));
        this.content = bArr;
        this.zzev = (zzgsVarArr == null || zzgsVarArr.length == 0) ? zzeu : zzgsVarArr;
        Preconditions.checkArgument(str2.length() <= 32, "Type length(%d) must not exceed MAX_TYPE_LENGTH(%d)", Integer.valueOf(str2.length()), 32);
    }

    public Message(byte[] bArr) {
        this(bArr, "", "");
    }

    public Message(byte[] bArr, String str) {
        this(bArr, "", str);
    }

    public Message(byte[] bArr, String str, String str2) {
        this(bArr, str, str2, zzeu);
    }

    private Message(byte[] bArr, String str, String str2, zzgs[] zzgsVarArr) {
        this(bArr, str, str2, zzgsVarArr, 0L);
    }

    private Message(byte[] bArr, String str, String str2, zzgs[] zzgsVarArr, long j) {
        this(2, bArr, str, str2, zzgsVarArr, 0L);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return TextUtils.equals(this.namespace, message.namespace) && TextUtils.equals(this.type, message.type) && Arrays.equals(this.content, message.content) && this.zzew == message.zzew;
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hashCode(this.namespace, this.type, Integer.valueOf(Arrays.hashCode(this.content)), Long.valueOf(this.zzew));
    }

    public String toString() {
        String str = this.namespace;
        String str2 = this.type;
        byte[] bArr = this.content;
        return new StringBuilder(String.valueOf(str).length() + 59 + String.valueOf(str2).length()).append("Message{namespace='").append(str).append("', type='").append(str2).append("', content=[").append(bArr == null ? 0 : bArr.length).append(" bytes]}").toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByteArray(parcel, 1, getContent(), false);
        SafeParcelWriter.writeString(parcel, 2, getType(), false);
        SafeParcelWriter.writeString(parcel, 3, getNamespace(), false);
        SafeParcelWriter.writeTypedArray(parcel, 4, this.zzev, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzew);
        SafeParcelWriter.writeInt(parcel, 1000, this.versionCode);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final boolean zzl(String str) {
        return MESSAGE_NAMESPACE_RESERVED.equals(getNamespace()) && str.equals(getType());
    }
}
