package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JavaSafeSafeDecompressor extends LZ4SafeDecompressor {
    public static final LZ4SafeDecompressor INSTANCE = new LZ4JavaSafeSafeDecompressor();

    LZ4JavaSafeSafeDecompressor() {
    }

    @Override // com.tds.lz4.LZ4SafeDecompressor, com.tds.lz4.LZ4UnknownSizeDecompressor
    public int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int i9 = 1;
        if (i4 == 0) {
            if (i2 == 1 && SafeUtils.readByte(bArr, i) == 0) {
                return 0;
            }
            throw new LZ4Exception("Output buffer too small");
        }
        int i10 = i + i2;
        int i11 = i3 + i4;
        int i12 = i;
        int i13 = i3;
        while (true) {
            int i14 = SafeUtils.readByte(bArr, i12) & 255;
            i5 = i12 + i9;
            i6 = i14 >>> 4;
            if (i6 == 15) {
                byte b = -1;
                while (i5 < i10) {
                    int i15 = i5 + 1;
                    byte b2 = SafeUtils.readByte(bArr, i5);
                    if (b2 != -1) {
                        b = b2;
                        i5 = i15;
                        break;
                    }
                    i6 += 255;
                    b = b2;
                    i5 = i15;
                }
                i6 += b & 255;
            }
            i7 = i13 + i6;
            int i16 = i11 - 8;
            if (i7 > i16 || (i8 = i5 + i6) > i10 - 8) {
                break;
            }
            LZ4SafeUtils.wildArraycopy(bArr, i5, bArr2, i13, i6);
            int shortLE = SafeUtils.readShortLE(bArr, i8);
            int i17 = i8 + 2;
            int i18 = i7 - shortLE;
            if (i18 < i3) {
                throw new LZ4Exception("Malformed input at " + i17);
            }
            int i19 = i14 & 15;
            if (i19 == 15) {
                byte b3 = -1;
                while (i17 < i10) {
                    int i20 = i17 + 1;
                    byte b4 = SafeUtils.readByte(bArr, i17);
                    if (b4 != -1) {
                        i17 = i20;
                        b3 = b4;
                        break;
                    }
                    i19 += 255;
                    i17 = i20;
                    b3 = b4;
                }
                i19 += b3 & 255;
            }
            int i21 = i19 + 4;
            i13 = i7 + i21;
            if (i13 <= i16) {
                LZ4SafeUtils.wildIncrementalCopy(bArr2, i18, i7, i13);
            } else {
                if (i13 > i11) {
                    throw new LZ4Exception("Malformed input at " + i17);
                }
                LZ4SafeUtils.safeIncrementalCopy(bArr2, i18, i7, i21);
            }
            i12 = i17;
            i9 = 1;
        }
        if (i7 > i11) {
            throw new LZ4Exception();
        }
        if (i5 + i6 != i10) {
            throw new LZ4Exception("Malformed input at " + i5);
        }
        LZ4SafeUtils.safeArraycopy(bArr, i5, bArr2, i13, i6);
        return i7 - i3;
    }

    @Override // com.tds.lz4.LZ4SafeDecompressor
    public int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = i;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return decompress(byteBuffer.array(), byteBuffer.arrayOffset() + i9, i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i9, i2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i3, i4);
        int i10 = 1;
        if (i4 == 0) {
            if (i2 == 1 && ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i9) == 0) {
                return 0;
            }
            throw new LZ4Exception("Output buffer too small");
        }
        int i11 = i2 + i9;
        int i12 = i4 + i3;
        int i13 = i3;
        while (true) {
            int i14 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i9) & 255;
            i5 = i9 + i10;
            i6 = i14 >>> 4;
            if (i6 == 15) {
                byte b = -1;
                while (i5 < i11) {
                    int i15 = i5 + 1;
                    byte b2 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i5);
                    if (b2 != -1) {
                        b = b2;
                        i5 = i15;
                        break;
                    }
                    i6 += 255;
                    b = b2;
                    i5 = i15;
                }
                i6 += b & 255;
            }
            i7 = i13 + i6;
            int i16 = i12 - 8;
            if (i7 > i16 || (i8 = i5 + i6) > i11 - 8) {
                break;
            }
            LZ4ByteBufferUtils.wildArraycopy(byteBufferInNativeByteOrder, i5, byteBufferInNativeByteOrder2, i13, i6);
            int shortLE = ByteBufferUtils.readShortLE(byteBufferInNativeByteOrder, i8);
            int i17 = i8 + 2;
            int i18 = i7 - shortLE;
            if (i18 < i3) {
                throw new LZ4Exception("Malformed input at " + i17);
            }
            int i19 = i14 & 15;
            if (i19 == 15) {
                byte b3 = -1;
                while (i17 < i11) {
                    int i20 = i17 + 1;
                    byte b4 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i17);
                    if (b4 != -1) {
                        i17 = i20;
                        b3 = b4;
                        break;
                    }
                    i19 += 255;
                    i17 = i20;
                    b3 = b4;
                }
                i19 += b3 & 255;
            }
            int i21 = i19 + 4;
            i13 = i7 + i21;
            if (i13 <= i16) {
                LZ4ByteBufferUtils.wildIncrementalCopy(byteBufferInNativeByteOrder2, i18, i7, i13);
            } else {
                if (i13 > i12) {
                    throw new LZ4Exception("Malformed input at " + i17);
                }
                LZ4ByteBufferUtils.safeIncrementalCopy(byteBufferInNativeByteOrder2, i18, i7, i21);
            }
            i9 = i17;
            i10 = 1;
        }
        if (i7 > i12) {
            throw new LZ4Exception();
        }
        if (i5 + i6 != i11) {
            throw new LZ4Exception("Malformed input at " + i5);
        }
        LZ4ByteBufferUtils.safeArraycopy(byteBufferInNativeByteOrder, i5, byteBufferInNativeByteOrder2, i13, i6);
        return i7 - i3;
    }
}
