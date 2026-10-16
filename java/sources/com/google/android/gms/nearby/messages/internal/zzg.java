package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public final class zzg extends zzc {
    public zzg(String str) {
        this(zzm(str));
    }

    public zzg(String str, String str2) {
        this(zzm(str), zzm(str2));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzg(byte[] bArr) {
        super(bArr);
        Preconditions.checkArgument(bArr.length == 10 || bArr.length == 16, "Bytes must be a namespace (10 bytes), or a namespace plus instance (16 bytes).");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private zzg(byte[] bArr, byte[] bArr2) {
        byte[][] bArr3 = new byte[2][];
        Preconditions.checkArgument(bArr.length == 10, new StringBuilder(53).append("Namespace length(").append(bArr.length).append(" bytes) must be 10 bytes.").toString());
        bArr3[0] = bArr;
        Preconditions.checkArgument(bArr2.length == 6, new StringBuilder(51).append("Instance length(").append(bArr2.length).append(" bytes) must be 6 bytes.").toString());
        bArr3[1] = bArr2;
        this(ArrayUtils.concatByteArrays(bArr3));
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzc
    public final String toString() {
        String hex = getHex();
        return new StringBuilder(String.valueOf(hex).length() + 26).append("EddystoneUidPrefix{bytes=").append(hex).append('}').toString();
    }
}
