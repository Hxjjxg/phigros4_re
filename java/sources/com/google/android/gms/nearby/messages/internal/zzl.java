package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.nio.ByteBuffer;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class zzl extends zzc {
    /* JADX WARN: Illegal instructions before constructor call */
    public zzl(UUID uuid, Short sh, Short sh2) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((sh == null ? 0 : 2) + 16 + (sh2 != null ? 2 : 0));
        byteBufferAllocate.putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());
        if (sh != null) {
            byteBufferAllocate.putShort(sh.shortValue());
        }
        if (sh2 != null) {
            byteBufferAllocate.putShort(sh2.shortValue());
        }
        this(byteBufferAllocate.array());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzl(byte[] bArr) {
        super(bArr);
        Preconditions.checkArgument(bArr.length == 16 || bArr.length == 18 || bArr.length == 20, "Prefix must be a UUID, a UUID and a major, or a UUID, a major, and a minor.");
    }

    public final UUID getProximityUuid() {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(getBytes());
        return new UUID(byteBufferWrap.getLong(), byteBufferWrap.getLong());
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzc
    public final String toString() {
        String strValueOf = String.valueOf(getProximityUuid());
        String strValueOf2 = String.valueOf(zzaf());
        String strValueOf3 = String.valueOf(zzag());
        return new StringBuilder(String.valueOf(strValueOf).length() + 47 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length()).append("IBeaconIdPrefix{proximityUuid=").append(strValueOf).append(", major=").append(strValueOf2).append(", minor=").append(strValueOf3).append('}').toString();
    }

    public final Short zzaf() {
        byte[] bytes = getBytes();
        if (bytes.length >= 18) {
            return Short.valueOf(ByteBuffer.wrap(bytes).getShort(16));
        }
        return null;
    }

    public final Short zzag() {
        byte[] bytes = getBytes();
        if (bytes.length == 20) {
            return Short.valueOf(ByteBuffer.wrap(bytes).getShort(18));
        }
        return null;
    }
}
