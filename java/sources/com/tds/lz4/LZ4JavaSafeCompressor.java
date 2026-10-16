package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import com.twitter.sdk.android.core.TwitterApiErrorConstants;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JavaSafeCompressor extends LZ4Compressor {
    public static final LZ4Compressor INSTANCE = new LZ4JavaSafeCompressor();

    LZ4JavaSafeCompressor() {
    }

    static int compress64k(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int iWriteLen;
        int i5;
        int i6 = i;
        int i7 = i6 + i2;
        int i8 = i7 - 5;
        int i9 = i7 - 12;
        if (i2 >= 13) {
            short[] sArr = new short[8192];
            int i10 = i6 + 1;
            iWriteLen = i3;
            int i11 = i6;
            loop0: while (true) {
                int i12 = 1;
                int i13 = 1 << LZ4Constants.SKIP_STRENGTH;
                while (true) {
                    int i14 = i12 + i10;
                    int i15 = i13 + 1;
                    int i16 = i13 >>> LZ4Constants.SKIP_STRENGTH;
                    if (i14 > i9) {
                        i6 = i11;
                        break loop0;
                    }
                    int iHash64k = LZ4Utils.hash64k(SafeUtils.readInt(bArr, i10));
                    i5 = SafeUtils.readShort(sArr, iHash64k) + i6;
                    SafeUtils.writeShort(sArr, iHash64k, i10 - i6);
                    if (LZ4SafeUtils.readIntEquals(bArr, i5, i10)) {
                        break;
                    }
                    i10 = i14;
                    i13 = i15;
                    i12 = i16;
                }
                int iCommonBytesBackward = LZ4SafeUtils.commonBytesBackward(bArr, i5, i10, i6, i11);
                int i17 = i10 - iCommonBytesBackward;
                int i18 = i5 - iCommonBytesBackward;
                int i19 = i17 - i11;
                int iWriteLen2 = iWriteLen + 1;
                if (iWriteLen2 + i19 + 8 + (i19 >>> 8) > i4) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                if (i19 >= 15) {
                    SafeUtils.writeByte(bArr2, iWriteLen, TwitterApiErrorConstants.SPAMMER);
                    iWriteLen2 = LZ4SafeUtils.writeLen(i19 - 15, bArr2, iWriteLen2);
                } else {
                    SafeUtils.writeByte(bArr2, iWriteLen, i19 << 4);
                }
                LZ4SafeUtils.wildArraycopy(bArr, i11, bArr2, iWriteLen2, i19);
                int i20 = iWriteLen2 + i19;
                while (true) {
                    SafeUtils.writeShortLE(bArr2, i20, (short) (i17 - i18));
                    int i21 = i20 + 2;
                    int i22 = i17 + 4;
                    int iCommonBytes = LZ4SafeUtils.commonBytes(bArr, i18 + 4, i22, i8);
                    if (i21 + 6 + (iCommonBytes >>> 8) > i4) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    i17 = i22 + iCommonBytes;
                    if (iCommonBytes >= 15) {
                        SafeUtils.writeByte(bArr2, iWriteLen, SafeUtils.readByte(bArr2, iWriteLen) | 15);
                        iWriteLen = LZ4SafeUtils.writeLen(iCommonBytes - 15, bArr2, i21);
                    } else {
                        SafeUtils.writeByte(bArr2, iWriteLen, iCommonBytes | SafeUtils.readByte(bArr2, iWriteLen));
                        iWriteLen = i21;
                    }
                    if (i17 > i9) {
                        i6 = i17;
                        break loop0;
                    }
                    int i23 = i17 - 2;
                    SafeUtils.writeShort(sArr, LZ4Utils.hash64k(SafeUtils.readInt(bArr, i23)), i23 - i6);
                    int iHash64k2 = LZ4Utils.hash64k(SafeUtils.readInt(bArr, i17));
                    i18 = i6 + SafeUtils.readShort(sArr, iHash64k2);
                    SafeUtils.writeShort(sArr, iHash64k2, i17 - i6);
                    if (!LZ4SafeUtils.readIntEquals(bArr, i17, i18)) {
                        break;
                    }
                    i20 = iWriteLen + 1;
                    SafeUtils.writeByte(bArr2, iWriteLen, 0);
                }
                i11 = i17;
                i10 = i17 + 1;
            }
        } else {
            iWriteLen = i3;
        }
        return LZ4SafeUtils.lastLiterals(bArr, i6, i7 - i6, bArr2, iWriteLen, i4) - i3;
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = i;
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int i9 = i3 + i4;
        if (i2 < 65547) {
            return compress64k(bArr, i, i2, bArr2, i3, i9);
        }
        int i10 = i2 + i8;
        int i11 = i10 - 5;
        int i12 = i10 - 12;
        int i13 = i8 + 1;
        int[] iArr = new int[4096];
        Arrays.fill(iArr, i8);
        int iWriteLen = i3;
        int i14 = i8;
        loop0: while (true) {
            int i15 = 1;
            int i16 = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int i17 = i15 + i13;
                int i18 = i16 + 1;
                int i19 = i16 >>> LZ4Constants.SKIP_STRENGTH;
                if (i17 > i12) {
                    i5 = i14;
                    break loop0;
                }
                int iHash = LZ4Utils.hash(SafeUtils.readInt(bArr, i13));
                i6 = SafeUtils.readInt(iArr, iHash);
                i7 = i13 - i6;
                SafeUtils.writeInt(iArr, iHash, i13);
                if (i7 >= 65536 || !LZ4SafeUtils.readIntEquals(bArr, i6, i13)) {
                    i8 = i;
                    i13 = i17;
                    i16 = i18;
                    i15 = i19;
                }
            }
            int iCommonBytesBackward = LZ4SafeUtils.commonBytesBackward(bArr, i6, i13, i8, i14);
            int i20 = i13 - iCommonBytesBackward;
            int i21 = i6 - iCommonBytesBackward;
            int i22 = i20 - i14;
            int iWriteLen2 = iWriteLen + 1;
            if (iWriteLen2 + i22 + 8 + (i22 >>> 8) > i9) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (i22 >= 15) {
                SafeUtils.writeByte(bArr2, iWriteLen, TwitterApiErrorConstants.SPAMMER);
                iWriteLen2 = LZ4SafeUtils.writeLen(i22 - 15, bArr2, iWriteLen2);
            } else {
                SafeUtils.writeByte(bArr2, iWriteLen, i22 << 4);
            }
            LZ4SafeUtils.wildArraycopy(bArr, i14, bArr2, iWriteLen2, i22);
            int i23 = iWriteLen2 + i22;
            while (true) {
                SafeUtils.writeShortLE(bArr2, i23, i7);
                int i24 = i23 + 2;
                int i25 = i20 + 4;
                int iCommonBytes = LZ4SafeUtils.commonBytes(bArr, i21 + 4, i25, i11);
                if (i24 + 6 + (iCommonBytes >>> 8) > i9) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                i20 = i25 + iCommonBytes;
                if (iCommonBytes >= 15) {
                    SafeUtils.writeByte(bArr2, iWriteLen, SafeUtils.readByte(bArr2, iWriteLen) | 15);
                    iWriteLen = LZ4SafeUtils.writeLen(iCommonBytes - 15, bArr2, i24);
                } else {
                    SafeUtils.writeByte(bArr2, iWriteLen, iCommonBytes | SafeUtils.readByte(bArr2, iWriteLen));
                    iWriteLen = i24;
                }
                if (i20 > i12) {
                    i5 = i20;
                    break loop0;
                }
                int i26 = i20 - 2;
                SafeUtils.writeInt(iArr, LZ4Utils.hash(SafeUtils.readInt(bArr, i26)), i26);
                int iHash2 = LZ4Utils.hash(SafeUtils.readInt(bArr, i20));
                i21 = SafeUtils.readInt(iArr, iHash2);
                SafeUtils.writeInt(iArr, iHash2, i20);
                int i27 = i20 - i21;
                if (i27 >= 65536 || !LZ4SafeUtils.readIntEquals(bArr, i21, i20)) {
                    break;
                }
                i23 = iWriteLen + 1;
                SafeUtils.writeByte(bArr2, iWriteLen, 0);
                i7 = i27;
            }
            i14 = i20;
            i13 = i20 + 1;
            i8 = i;
        }
        return LZ4SafeUtils.lastLiterals(bArr, i5, i10 - i5, bArr2, iWriteLen, i9) - i3;
    }

    static int compress64k(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int iWriteLen;
        int i5;
        int i6 = i;
        int i7 = i6 + i2;
        int i8 = i7 - 5;
        int i9 = i7 - 12;
        if (i2 >= 13) {
            short[] sArr = new short[8192];
            int i10 = i6 + 1;
            iWriteLen = i3;
            int i11 = i6;
            loop0: while (true) {
                int i12 = 1;
                int i13 = 1 << LZ4Constants.SKIP_STRENGTH;
                while (true) {
                    int i14 = i12 + i10;
                    int i15 = i13 + 1;
                    int i16 = i13 >>> LZ4Constants.SKIP_STRENGTH;
                    if (i14 > i9) {
                        i6 = i11;
                        break loop0;
                    }
                    int iHash64k = LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i10));
                    i5 = SafeUtils.readShort(sArr, iHash64k) + i6;
                    SafeUtils.writeShort(sArr, iHash64k, i10 - i6);
                    if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, i5, i10)) {
                        break;
                    }
                    i10 = i14;
                    i13 = i15;
                    i12 = i16;
                }
                int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBuffer, i5, i10, i6, i11);
                int i17 = i10 - iCommonBytesBackward;
                int i18 = i5 - iCommonBytesBackward;
                int i19 = i17 - i11;
                int iWriteLen2 = iWriteLen + 1;
                if (iWriteLen2 + i19 + 8 + (i19 >>> 8) > i4) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                if (i19 >= 15) {
                    ByteBufferUtils.writeByte(byteBuffer2, iWriteLen, TwitterApiErrorConstants.SPAMMER);
                    iWriteLen2 = LZ4ByteBufferUtils.writeLen(i19 - 15, byteBuffer2, iWriteLen2);
                } else {
                    ByteBufferUtils.writeByte(byteBuffer2, iWriteLen, i19 << 4);
                }
                LZ4ByteBufferUtils.wildArraycopy(byteBuffer, i11, byteBuffer2, iWriteLen2, i19);
                int i20 = iWriteLen2 + i19;
                while (true) {
                    ByteBufferUtils.writeShortLE(byteBuffer2, i20, (short) (i17 - i18));
                    int i21 = i20 + 2;
                    int i22 = i17 + 4;
                    int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, i18 + 4, i22, i8);
                    if (i21 + 6 + (iCommonBytes >>> 8) > i4) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    i17 = i22 + iCommonBytes;
                    if (iCommonBytes >= 15) {
                        ByteBufferUtils.writeByte(byteBuffer2, iWriteLen, ByteBufferUtils.readByte(byteBuffer2, iWriteLen) | 15);
                        iWriteLen = LZ4ByteBufferUtils.writeLen(iCommonBytes - 15, byteBuffer2, i21);
                    } else {
                        ByteBufferUtils.writeByte(byteBuffer2, iWriteLen, iCommonBytes | ByteBufferUtils.readByte(byteBuffer2, iWriteLen));
                        iWriteLen = i21;
                    }
                    if (i17 > i9) {
                        i6 = i17;
                        break loop0;
                    }
                    int i23 = i17 - 2;
                    SafeUtils.writeShort(sArr, LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i23)), i23 - i6);
                    int iHash64k2 = LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i17));
                    i18 = i6 + SafeUtils.readShort(sArr, iHash64k2);
                    SafeUtils.writeShort(sArr, iHash64k2, i17 - i6);
                    if (!LZ4ByteBufferUtils.readIntEquals(byteBuffer, i17, i18)) {
                        break;
                    }
                    i20 = iWriteLen + 1;
                    ByteBufferUtils.writeByte(byteBuffer2, iWriteLen, 0);
                }
                i11 = i17;
                i10 = i17 + 1;
            }
        } else {
            iWriteLen = i3;
        }
        return LZ4ByteBufferUtils.lastLiterals(byteBuffer, i6, i7 - i6, byteBuffer2, iWriteLen, i4) - i3;
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = i;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return compress(byteBuffer.array(), i8 + byteBuffer.arrayOffset(), i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i8, i2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i3, i4);
        int i9 = i3 + i4;
        if (i2 < 65547) {
            return compress64k(byteBufferInNativeByteOrder, i, i2, byteBufferInNativeByteOrder2, i3, i9);
        }
        int i10 = i8 + i2;
        int i11 = i10 - 5;
        int i12 = i10 - 12;
        int i13 = i8 + 1;
        int[] iArr = new int[4096];
        Arrays.fill(iArr, i8);
        int i14 = i8;
        int iWriteLen = i3;
        loop0: while (true) {
            int i15 = 1;
            int i16 = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int i17 = i15 + i13;
                int i18 = i16 + 1;
                int i19 = i16 >>> LZ4Constants.SKIP_STRENGTH;
                if (i17 > i12) {
                    i5 = i14;
                    break loop0;
                }
                int iHash = LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i13));
                i6 = SafeUtils.readInt(iArr, iHash);
                i7 = i13 - i6;
                SafeUtils.writeInt(iArr, iHash, i13);
                if (i7 >= 65536 || !LZ4ByteBufferUtils.readIntEquals(byteBufferInNativeByteOrder, i6, i13)) {
                    i15 = i19;
                    i8 = i;
                    i13 = i17;
                    i16 = i18;
                }
            }
            int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBufferInNativeByteOrder, i6, i13, i8, i14);
            int i20 = i13 - iCommonBytesBackward;
            int i21 = i6 - iCommonBytesBackward;
            int i22 = i20 - i14;
            int iWriteLen2 = iWriteLen + 1;
            if (iWriteLen2 + i22 + 8 + (i22 >>> 8) > i9) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (i22 >= 15) {
                ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, iWriteLen, TwitterApiErrorConstants.SPAMMER);
                iWriteLen2 = LZ4ByteBufferUtils.writeLen(i22 - 15, byteBufferInNativeByteOrder2, iWriteLen2);
            } else {
                ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, iWriteLen, i22 << 4);
            }
            LZ4ByteBufferUtils.wildArraycopy(byteBufferInNativeByteOrder, i14, byteBufferInNativeByteOrder2, iWriteLen2, i22);
            int i23 = iWriteLen2 + i22;
            int i24 = i20;
            while (true) {
                ByteBufferUtils.writeShortLE(byteBufferInNativeByteOrder2, i23, i7);
                int i25 = i23 + 2;
                int i26 = i24 + 4;
                int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBufferInNativeByteOrder, i21 + 4, i26, i11);
                if (i25 + 6 + (iCommonBytes >>> 8) > i9) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                i24 = i26 + iCommonBytes;
                if (iCommonBytes >= 15) {
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, iWriteLen, ByteBufferUtils.readByte(byteBufferInNativeByteOrder2, iWriteLen) | 15);
                    iWriteLen = LZ4ByteBufferUtils.writeLen(iCommonBytes - 15, byteBufferInNativeByteOrder2, i25);
                } else {
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, iWriteLen, iCommonBytes | ByteBufferUtils.readByte(byteBufferInNativeByteOrder2, iWriteLen));
                    iWriteLen = i25;
                }
                if (i24 > i12) {
                    i5 = i24;
                    break loop0;
                }
                int i27 = i24 - 2;
                SafeUtils.writeInt(iArr, LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i27)), i27);
                int iHash2 = LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i24));
                int i28 = SafeUtils.readInt(iArr, iHash2);
                SafeUtils.writeInt(iArr, iHash2, i24);
                int i29 = i24 - i28;
                if (i29 >= 65536 || !LZ4ByteBufferUtils.readIntEquals(byteBufferInNativeByteOrder, i28, i24)) {
                    i13 = i24 + 1;
                    i14 = i24;
                    i8 = i;
                } else {
                    i23 = iWriteLen + 1;
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, iWriteLen, 0);
                    i21 = i28;
                    i7 = i29;
                }
            }
        }
        return LZ4ByteBufferUtils.lastLiterals(byteBufferInNativeByteOrder, i5, i10 - i5, byteBufferInNativeByteOrder2, iWriteLen, i9) - i3;
    }
}
