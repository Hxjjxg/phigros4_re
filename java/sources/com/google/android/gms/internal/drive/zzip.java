package com.google.android.gms.internal.drive;

import com.twitter.sdk.android.core.TwitterApiErrorConstants;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* JADX INFO: loaded from: classes.dex */
public final class zzip {
    private final ByteBuffer zzmv;

    private zzip(ByteBuffer byteBuffer) {
        this.zzmv = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzip(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt >= 2048) {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char cCharAt2 = charSequence.charAt(i2);
                    if (cCharAt2 < 2048) {
                        i += (127 - cCharAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i2).toString());
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
                break;
            }
            i3 += (127 - cCharAt) >>> 31;
            i2++;
        }
        if (i3 >= length) {
            return i3;
        }
        throw new IllegalArgumentException(new StringBuilder(54).append("UTF-8 length does not fit in int: ").append(((long) i3) + 4294967296L).toString());
    }

    private final void zza(long j) throws IOException {
        while (((-128) & j) != 0) {
            zzn((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzn((int) j);
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int i2;
        char cCharAt;
        int i3;
        int i4;
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int i5 = 0;
        if (!byteBuffer.hasArray()) {
            int length = charSequence.length();
            while (i5 < length) {
                char cCharAt2 = charSequence.charAt(i5);
                if (cCharAt2 >= 128) {
                    if (cCharAt2 < 2048) {
                        i3 = (cCharAt2 >>> 6) | 960;
                    } else {
                        if (cCharAt2 >= 55296 && 57343 >= cCharAt2) {
                            int i6 = i5 + 1;
                            if (i6 != charSequence.length()) {
                                char cCharAt3 = charSequence.charAt(i6);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    byteBuffer.put((byte) ((codePoint >>> 18) | TwitterApiErrorConstants.SPAMMER));
                                    byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                                    byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put((byte) ((codePoint & 63) | 128));
                                    i5 = i6;
                                } else {
                                    i5 = i6;
                                }
                            }
                            throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i5 - 1).toString());
                        }
                        byteBuffer.put((byte) ((cCharAt2 >>> '\f') | 480));
                        i3 = ((cCharAt2 >>> 6) & 63) | 128;
                    }
                    byteBuffer.put((byte) i3);
                    i4 = (cCharAt2 & '?') | 128;
                    i4 = cCharAt2;
                    byteBuffer.put((byte) i4);
                } else {
                    i4 = cCharAt2;
                    byteBuffer.put((byte) i4);
                }
                i5++;
            }
            return;
        }
        try {
            byte[] bArrArray = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int iRemaining = byteBuffer.remaining();
            int length2 = charSequence.length();
            int i7 = iRemaining + iArrayOffset;
            while (i5 < length2) {
                int i8 = i5 + iArrayOffset;
                if (i8 >= i7 || (cCharAt = charSequence.charAt(i5)) >= 128) {
                    break;
                }
                bArrArray[i8] = (byte) cCharAt;
                i5++;
            }
            if (i5 == length2) {
                i = iArrayOffset + length2;
            } else {
                i = iArrayOffset + i5;
                while (i5 < length2) {
                    char cCharAt4 = charSequence.charAt(i5);
                    if (cCharAt4 >= 128 || i >= i7) {
                        if (cCharAt4 < 2048 && i <= i7 - 2) {
                            int i9 = i + 1;
                            bArrArray[i] = (byte) ((cCharAt4 >>> 6) | 960);
                            i = i9 + 1;
                            bArrArray[i9] = (byte) ((cCharAt4 & '?') | 128);
                        } else {
                            if ((cCharAt4 >= 55296 && 57343 >= cCharAt4) || i > i7 - 3) {
                                if (i > i7 - 4) {
                                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(cCharAt4).append(" at index ").append(i).toString());
                                }
                                int i10 = i5 + 1;
                                if (i10 != charSequence.length()) {
                                    char cCharAt5 = charSequence.charAt(i10);
                                    if (Character.isSurrogatePair(cCharAt4, cCharAt5)) {
                                        int codePoint2 = Character.toCodePoint(cCharAt4, cCharAt5);
                                        int i11 = i + 1;
                                        bArrArray[i] = (byte) ((codePoint2 >>> 18) | TwitterApiErrorConstants.SPAMMER);
                                        int i12 = i11 + 1;
                                        bArrArray[i11] = (byte) (((codePoint2 >>> 12) & 63) | 128);
                                        int i13 = i12 + 1;
                                        bArrArray[i12] = (byte) (((codePoint2 >>> 6) & 63) | 128);
                                        i = i13 + 1;
                                        bArrArray[i13] = (byte) ((codePoint2 & 63) | 128);
                                        i5 = i10;
                                    } else {
                                        i5 = i10;
                                    }
                                }
                                throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i5 - 1).toString());
                            }
                            int i14 = i + 1;
                            bArrArray[i] = (byte) ((cCharAt4 >>> '\f') | 480);
                            int i15 = i14 + 1;
                            bArrArray[i14] = (byte) (((cCharAt4 >>> 6) & 63) | 128);
                            i2 = i15 + 1;
                            bArrArray[i15] = (byte) ((cCharAt4 & '?') | 128);
                        }
                        i5++;
                    } else {
                        i2 = i + 1;
                        bArrArray[i] = (byte) cCharAt4;
                    }
                    i = i2;
                    i5++;
                }
            }
            byteBuffer.position(i - byteBuffer.arrayOffset());
        } catch (ArrayIndexOutOfBoundsException e) {
            BufferOverflowException bufferOverflowException = new BufferOverflowException();
            bufferOverflowException.initCause(e);
            throw bufferOverflowException;
        }
    }

    public static int zzb(int i, long j) {
        int i2;
        int iZzo = zzo(i);
        long jZzb = zzb(j);
        if (((-128) & jZzb) == 0) {
            i2 = 1;
        } else if (((-16384) & jZzb) == 0) {
            i2 = 2;
        } else if (((-2097152) & jZzb) == 0) {
            i2 = 3;
        } else if (((-268435456) & jZzb) == 0) {
            i2 = 4;
        } else if (((-34359738368L) & jZzb) == 0) {
            i2 = 5;
        } else if (((-4398046511104L) & jZzb) == 0) {
            i2 = 6;
        } else if (((-562949953421312L) & jZzb) == 0) {
            i2 = 7;
        } else if (((-72057594037927936L) & jZzb) == 0) {
            i2 = 8;
        } else {
            i2 = (jZzb & Long.MIN_VALUE) == 0 ? 9 : 10;
        }
        return iZzo + i2;
    }

    private static long zzb(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static zzip zzb(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static zzip zzb(byte[] bArr, int i, int i2) {
        return new zzip(bArr, 0, i2);
    }

    public static int zzc(int i, int i2) {
        return zzo(i) + zzm(i2);
    }

    public static int zzi(String str) {
        int iZza = zza(str);
        return zzq(iZza) + iZza;
    }

    public static int zzm(int i) {
        if (i >= 0) {
            return zzq(i);
        }
        return 10;
    }

    private final void zzn(int i) throws IOException {
        byte b = (byte) i;
        if (!this.zzmv.hasRemaining()) {
            throw new zziq(this.zzmv.position(), this.zzmv.limit());
        }
        this.zzmv.put(b);
    }

    public static int zzo(int i) {
        return zzq(i << 3);
    }

    public static int zzq(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public final void zza(int i, long j) throws IOException {
        zzd(i, 0);
        zza(zzb(j));
    }

    public final void zzb(int i, int i2) throws IOException {
        zzd(i, 0);
        if (i2 >= 0) {
            zzp(i2);
        } else {
            zza(i2);
        }
    }

    public final void zzbh() {
        if (this.zzmv.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzmv.remaining())));
        }
    }

    public final void zzc(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zzmv.remaining() < length) {
            throw new zziq(this.zzmv.position(), this.zzmv.limit());
        }
        this.zzmv.put(bArr, 0, length);
    }

    public final void zzd(int i, int i2) throws IOException {
        zzp((i << 3) | i2);
    }

    public final void zzh(String str) throws IOException {
        try {
            int iZzq = zzq(str.length());
            if (iZzq != zzq(str.length() * 3)) {
                zzp(zza(str));
                zza(str, this.zzmv);
                return;
            }
            int iPosition = this.zzmv.position();
            if (this.zzmv.remaining() < iZzq) {
                throw new zziq(iPosition + iZzq, this.zzmv.limit());
            }
            this.zzmv.position(iPosition + iZzq);
            zza(str, this.zzmv);
            int iPosition2 = this.zzmv.position();
            this.zzmv.position(iPosition);
            zzp((iPosition2 - iPosition) - iZzq);
            this.zzmv.position(iPosition2);
        } catch (BufferOverflowException e) {
            zziq zziqVar = new zziq(this.zzmv.position(), this.zzmv.limit());
            zziqVar.initCause(e);
            throw zziqVar;
        }
    }

    public final void zzp(int i) throws IOException {
        while ((i & (-128)) != 0) {
            zzn((i & 127) | 128);
            i >>>= 7;
        }
        zzn(i);
    }
}
