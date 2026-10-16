package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.UnsafeUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class LZ4HCJavaUnsafeCompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LZ4Compressor INSTANCE = new LZ4HCJavaUnsafeCompressor();
    final int compressionLevel;
    private final int maxAttempts;

    LZ4HCJavaUnsafeCompressor() {
        this(9);
    }

    LZ4HCJavaUnsafeCompressor(int i) {
        this.maxAttempts = 1 << (i - 1);
        this.compressionLevel = i;
    }

    private class HashTable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int MASK = 65535;
        private final int base;
        private final short[] chainTable;
        private final int[] hashTable;
        int nextToUpdate;

        HashTable(int i) {
            this.base = i;
            this.nextToUpdate = i;
            int[] iArr = new int[32768];
            this.hashTable = iArr;
            Arrays.fill(iArr, -1);
            this.chainTable = new short[65536];
        }

        private int hashPointer(byte[] bArr, int i) {
            return hashPointer(UnsafeUtils.readInt(bArr, i));
        }

        private int hashPointer(ByteBuffer byteBuffer, int i) {
            return hashPointer(ByteBufferUtils.readInt(byteBuffer, i));
        }

        private int hashPointer(int i) {
            return this.hashTable[LZ4Utils.hashHC(i)];
        }

        private int next(int i) {
            return i - (this.chainTable[i & 65535] & 65535);
        }

        private void addHash(byte[] bArr, int i) {
            addHash(UnsafeUtils.readInt(bArr, i), i);
        }

        private void addHash(ByteBuffer byteBuffer, int i) {
            addHash(ByteBufferUtils.readInt(byteBuffer, i), i);
        }

        private void addHash(int i, int i2) {
            int iHashHC = LZ4Utils.hashHC(i);
            int[] iArr = this.hashTable;
            int i3 = i2 - iArr[iHashHC];
            if (i3 >= 65536) {
                i3 = 65535;
            }
            this.chainTable[65535 & i2] = (short) i3;
            iArr[iHashHC] = i2;
        }

        void insert(int i, byte[] bArr) {
            while (true) {
                int i2 = this.nextToUpdate;
                if (i2 >= i) {
                    return;
                }
                addHash(bArr, i2);
                this.nextToUpdate++;
            }
        }

        void insert(int i, ByteBuffer byteBuffer) {
            while (true) {
                int i2 = this.nextToUpdate;
                if (i2 >= i) {
                    return;
                }
                addHash(byteBuffer, i2);
                this.nextToUpdate++;
            }
        }

        boolean insertAndFindBestMatch(byte[] bArr, int i, int i2, LZ4Utils.Match match) {
            int i3;
            int iCommonBytes;
            int iCommonBytes2;
            match.start = i;
            match.len = 0;
            insert(i, bArr);
            int iHashPointer = hashPointer(bArr, i);
            if (iHashPointer < i - 4 || iHashPointer > i || iHashPointer < this.base) {
                i3 = 0;
                iCommonBytes = 0;
            } else {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i)) {
                    i3 = i - iHashPointer;
                    iCommonBytes = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i2) + 4;
                    match.len = iCommonBytes;
                    match.ref = iHashPointer;
                } else {
                    i3 = 0;
                    iCommonBytes = 0;
                }
                iHashPointer = next(iHashPointer);
            }
            for (int i4 = 0; i4 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i4++) {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i) && (iCommonBytes2 = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i2) + 4) > match.len) {
                    match.ref = iHashPointer;
                    match.len = iCommonBytes2;
                }
                iHashPointer = next(iHashPointer);
            }
            if (iCommonBytes != 0) {
                int i5 = (iCommonBytes + i) - 3;
                while (i < i5 - i3) {
                    this.chainTable[65535 & i] = (short) i3;
                    i++;
                }
                do {
                    this.chainTable[i & 65535] = (short) i3;
                    this.hashTable[LZ4Utils.hashHC(UnsafeUtils.readInt(bArr, i))] = i;
                    i++;
                } while (i < i5);
                this.nextToUpdate = i5;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(byte[] bArr, int i, int i2, int i3, int i4, LZ4Utils.Match match) {
            match.len = i4;
            insert(i, bArr);
            int iHashPointer = hashPointer(bArr, i);
            for (int i5 = 0; i5 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i5++) {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i)) {
                    int iCommonBytes = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i3) + 4;
                    int iCommonBytesBackward = LZ4UnsafeUtils.commonBytesBackward(bArr, iHashPointer, i, this.base, i2);
                    int i6 = iCommonBytes + iCommonBytesBackward;
                    if (i6 > match.len) {
                        match.len = i6;
                        match.ref = iHashPointer - iCommonBytesBackward;
                        match.start = i - iCommonBytesBackward;
                    }
                }
                iHashPointer = next(iHashPointer);
            }
            return match.len > i4;
        }

        boolean insertAndFindBestMatch(ByteBuffer byteBuffer, int i, int i2, LZ4Utils.Match match) {
            int i3;
            int iCommonBytes;
            int iCommonBytes2;
            match.start = i;
            match.len = 0;
            insert(i, byteBuffer);
            int iHashPointer = hashPointer(byteBuffer, i);
            if (iHashPointer < i - 4 || iHashPointer > i || iHashPointer < this.base) {
                i3 = 0;
                iCommonBytes = 0;
            } else {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i)) {
                    i3 = i - iHashPointer;
                    iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i2) + 4;
                    match.len = iCommonBytes;
                    match.ref = iHashPointer;
                } else {
                    i3 = 0;
                    iCommonBytes = 0;
                }
                iHashPointer = next(iHashPointer);
            }
            for (int i4 = 0; i4 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i4++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i) && (iCommonBytes2 = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i2) + 4) > match.len) {
                    match.ref = iHashPointer;
                    match.len = iCommonBytes2;
                }
                iHashPointer = next(iHashPointer);
            }
            if (iCommonBytes != 0) {
                int i5 = (iCommonBytes + i) - 3;
                while (i < i5 - i3) {
                    this.chainTable[65535 & i] = (short) i3;
                    i++;
                }
                do {
                    this.chainTable[i & 65535] = (short) i3;
                    this.hashTable[LZ4Utils.hashHC(ByteBufferUtils.readInt(byteBuffer, i))] = i;
                    i++;
                } while (i < i5);
                this.nextToUpdate = i5;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, LZ4Utils.Match match) {
            match.len = i4;
            insert(i, byteBuffer);
            int iHashPointer = hashPointer(byteBuffer, i);
            for (int i5 = 0; i5 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i5++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i)) {
                    int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i3) + 4;
                    int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBuffer, iHashPointer, i, this.base, i2);
                    int i6 = iCommonBytes + iCommonBytesBackward;
                    if (i6 > match.len) {
                        match.len = i6;
                        match.ref = iHashPointer - iCommonBytesBackward;
                        match.start = i - iCommonBytesBackward;
                    }
                }
                iHashPointer = next(iHashPointer);
            }
            return match.len > i4;
        }
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        LZ4Utils.Match match;
        LZ4Utils.Match match2;
        LZ4Utils.Match match3;
        LZ4Utils.Match match4;
        UnsafeUtils.checkRange(bArr, i, i2);
        UnsafeUtils.checkRange(bArr2, i3, i4);
        int i5 = i + i2;
        int i6 = i3 + i4;
        int i7 = i5 - 12;
        int i8 = i5 - 5;
        int i9 = i + 1;
        HashTable hashTable = new HashTable(i);
        LZ4Utils.Match match5 = new LZ4Utils.Match();
        LZ4Utils.Match match6 = new LZ4Utils.Match();
        LZ4Utils.Match match7 = new LZ4Utils.Match();
        LZ4Utils.Match match8 = new LZ4Utils.Match();
        int iEncodeSequence = i3;
        int iEnd = i;
        while (i9 < i7) {
            if (hashTable.insertAndFindBestMatch(bArr, i9, i8, match6)) {
                LZ4Utils.copyTo(match6, match5);
                int iEnd2 = iEnd;
                int iEncodeSequence2 = iEncodeSequence;
                while (true) {
                    if (match6.end() < i7) {
                        LZ4Utils.Match match9 = match8;
                        LZ4Utils.Match match10 = match7;
                        if (hashTable.insertAndFindWiderMatch(bArr, match6.end() - 2, match6.start + 1, i8, match6.len, match7)) {
                            match7 = match10;
                            if (match5.start < match6.start && match7.start < match6.start + match5.len) {
                                LZ4Utils.copyTo(match5, match6);
                            }
                            char c = 3;
                            if (match7.start - match6.start < 3) {
                                LZ4Utils.copyTo(match7, match6);
                                match8 = match9;
                            } else {
                                while (true) {
                                    if (match7.start - match6.start < 18) {
                                        int i10 = match6.len;
                                        if (i10 > 18) {
                                            i10 = 18;
                                        }
                                        if (match6.start + i10 > match7.end() - 4) {
                                            i10 = ((match7.start - match6.start) + match7.len) - 4;
                                        }
                                        int i11 = i10 - (match7.start - match6.start);
                                        if (i11 > 0) {
                                            match7.fix(i11);
                                        }
                                    }
                                    if (match7.start + match7.len < i7) {
                                        char c2 = c;
                                        match3 = match7;
                                        if (hashTable.insertAndFindWiderMatch(bArr, match7.end() - 3, match7.start, i8, match7.len, match9)) {
                                            LZ4Utils.Match match11 = match9;
                                            if (match11.start < match6.end() + 3) {
                                                if (match11.start >= match6.end()) {
                                                    if (match3.start < match6.end()) {
                                                        match3.fix(match6.end() - match3.start);
                                                        if (match3.len < 4) {
                                                            LZ4Utils.copyTo(match11, match3);
                                                        }
                                                    }
                                                    LZ4Utils.Match match12 = match6;
                                                    iEncodeSequence2 = LZ4UnsafeUtils.encodeSequence(bArr, iEnd2, match6.start, match6.ref, match6.len, bArr2, iEncodeSequence2, i6);
                                                    iEnd2 = match12.end();
                                                    match6 = match12;
                                                    LZ4Utils.copyTo(match11, match6);
                                                    LZ4Utils.copyTo(match3, match5);
                                                    match7 = match3;
                                                    match8 = match11;
                                                    i7 = i7;
                                                } else {
                                                    match4 = match11;
                                                    LZ4Utils.copyTo(match4, match3);
                                                }
                                            } else {
                                                match4 = match11;
                                                if (match3.start < match6.end()) {
                                                    if (match3.start - match6.start < 15) {
                                                        if (match6.len > 18) {
                                                            match6.len = 18;
                                                        }
                                                        if (match6.end() > match3.end() - 4) {
                                                            match6.len = (match3.end() - match6.start) - 4;
                                                        }
                                                        match3.fix(match6.end() - match3.start);
                                                    } else {
                                                        match6.len = match3.start - match6.start;
                                                    }
                                                }
                                                LZ4Utils.Match match13 = match6;
                                                iEncodeSequence2 = LZ4UnsafeUtils.encodeSequence(bArr, iEnd2, match6.start, match6.ref, match6.len, bArr2, iEncodeSequence2, i6);
                                                iEnd2 = match13.end();
                                                match6 = match13;
                                                LZ4Utils.copyTo(match3, match6);
                                                LZ4Utils.copyTo(match4, match3);
                                            }
                                            match7 = match3;
                                            match9 = match4;
                                            c = c2;
                                            i7 = i7;
                                        }
                                    } else {
                                        match3 = match7;
                                    }
                                    i7 = i7;
                                    LZ4Utils.Match match14 = match9;
                                    if (match3.start < match6.end()) {
                                        match6.len = match3.start - match6.start;
                                    }
                                    LZ4Utils.Match match15 = match6;
                                    iEncodeSequence = LZ4UnsafeUtils.encodeSequence(bArr, match15.end(), match3.start, match3.ref, match3.len, bArr2, LZ4UnsafeUtils.encodeSequence(bArr, iEnd2, match6.start, match6.ref, match6.len, bArr2, iEncodeSequence2, i6), i6);
                                    iEnd = match3.end();
                                    match6 = match15;
                                    match7 = match3;
                                    i9 = iEnd;
                                    match8 = match14;
                                }
                            }
                        } else {
                            match = match10;
                            match2 = match9;
                        }
                        i7 = i7;
                    } else {
                        match = match7;
                        match2 = match8;
                    }
                    int i12 = iEnd2;
                    LZ4Utils.Match match16 = match6;
                    iEncodeSequence = LZ4UnsafeUtils.encodeSequence(bArr, i12, match6.start, match6.ref, match6.len, bArr2, iEncodeSequence2, i6);
                    iEnd = match16.end();
                    match7 = match;
                    i9 = iEnd;
                    match8 = match2;
                    match6 = match16;
                    i7 = i7;
                }
            } else {
                i9++;
            }
        }
        return LZ4UnsafeUtils.lastLiterals(bArr, iEnd, i5 - iEnd, bArr2, iEncodeSequence, i6) - i3;
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        LZ4Utils.Match match;
        int i5;
        LZ4Utils.Match match2;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return compress(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i, i2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i3, i4);
        int i6 = i + i2;
        int i7 = i3 + i4;
        int i8 = i6 - 12;
        int i9 = i6 - 5;
        int i10 = i + 1;
        HashTable hashTable = new HashTable(i);
        LZ4Utils.Match match3 = new LZ4Utils.Match();
        LZ4Utils.Match match4 = new LZ4Utils.Match();
        LZ4Utils.Match match5 = new LZ4Utils.Match();
        LZ4Utils.Match match6 = new LZ4Utils.Match();
        int iEnd = i;
        int iEncodeSequence = i3;
        while (i10 < i8) {
            if (hashTable.insertAndFindBestMatch(byteBufferInNativeByteOrder, i10, i9, match4)) {
                LZ4Utils.copyTo(match4, match3);
                int iEncodeSequence2 = iEncodeSequence;
                int i11 = iEnd;
                while (true) {
                    if (match4.end() < i8) {
                        match = match6;
                        LZ4Utils.Match match7 = match5;
                        match4 = match4;
                        i5 = i6;
                        match3 = match3;
                        if (hashTable.insertAndFindWiderMatch(byteBufferInNativeByteOrder, match4.end() - 2, match4.start + 1, i9, match4.len, match7)) {
                            LZ4Utils.Match match8 = match7;
                            if (match3.start < match4.start && match8.start < match4.start + match3.len) {
                                LZ4Utils.copyTo(match3, match4);
                            }
                            char c = 3;
                            if (match8.start - match4.start < 3) {
                                LZ4Utils.copyTo(match8, match4);
                                match6 = match;
                                match5 = match8;
                            } else {
                                while (true) {
                                    if (match8.start - match4.start < 18) {
                                        int i12 = match4.len;
                                        if (i12 > 18) {
                                            i12 = 18;
                                        }
                                        if (match4.start + i12 > match8.end() - 4) {
                                            i12 = ((match8.start - match4.start) + match8.len) - 4;
                                        }
                                        int i13 = i12 - (match8.start - match4.start);
                                        if (i13 > 0) {
                                            match8.fix(i13);
                                        }
                                    }
                                    if (match8.start + match8.len < i8) {
                                        HashTable hashTable2 = hashTable;
                                        HashTable hashTable3 = hashTable;
                                        c = c;
                                        LZ4Utils.Match match9 = match8;
                                        if (hashTable2.insertAndFindWiderMatch(byteBufferInNativeByteOrder, match8.end() - 3, match8.start, i9, match8.len, match)) {
                                            LZ4Utils.Match match10 = match;
                                            if (match10.start < match4.end() + 3) {
                                                if (match10.start >= match4.end()) {
                                                    if (match9.start < match4.end()) {
                                                        match9.fix(match4.end() - match9.start);
                                                        if (match9.len < 4) {
                                                            LZ4Utils.copyTo(match10, match9);
                                                        }
                                                    }
                                                    iEncodeSequence2 = LZ4ByteBufferUtils.encodeSequence(byteBufferInNativeByteOrder, i11, match4.start, match4.ref, match4.len, byteBufferInNativeByteOrder2, iEncodeSequence2, i7);
                                                    int iEnd2 = match4.end();
                                                    LZ4Utils.copyTo(match10, match4);
                                                    LZ4Utils.copyTo(match9, match3);
                                                    match6 = match10;
                                                    i11 = iEnd2;
                                                    match5 = match9;
                                                    hashTable = hashTable3;
                                                    i8 = i8;
                                                } else {
                                                    LZ4Utils.copyTo(match10, match9);
                                                    match8 = match9;
                                                    i8 = i8;
                                                    match = match10;
                                                    hashTable = hashTable3;
                                                }
                                            } else {
                                                int i14 = i8;
                                                if (match9.start < match4.end()) {
                                                    if (match9.start - match4.start < 15) {
                                                        if (match4.len > 18) {
                                                            match4.len = 18;
                                                        }
                                                        if (match4.end() > match9.end() - 4) {
                                                            match4.len = (match9.end() - match4.start) - 4;
                                                        }
                                                        match9.fix(match4.end() - match9.start);
                                                    } else {
                                                        match4.len = match9.start - match4.start;
                                                    }
                                                }
                                                iEncodeSequence2 = LZ4ByteBufferUtils.encodeSequence(byteBufferInNativeByteOrder, i11, match4.start, match4.ref, match4.len, byteBufferInNativeByteOrder2, iEncodeSequence2, i7);
                                                int iEnd3 = match4.end();
                                                LZ4Utils.copyTo(match9, match4);
                                                LZ4Utils.copyTo(match10, match9);
                                                match8 = match9;
                                                hashTable = hashTable3;
                                                i8 = i14;
                                                match = match10;
                                                i11 = iEnd3;
                                            }
                                        } else {
                                            hashTable = hashTable3;
                                            match2 = match9;
                                        }
                                    } else {
                                        hashTable = hashTable;
                                        match2 = match8;
                                    }
                                    if (match2.start < match4.end()) {
                                        match4.len = match2.start - match4.start;
                                    }
                                    iEncodeSequence = LZ4ByteBufferUtils.encodeSequence(byteBufferInNativeByteOrder, match4.end(), match2.start, match2.ref, match2.len, byteBufferInNativeByteOrder2, LZ4ByteBufferUtils.encodeSequence(byteBufferInNativeByteOrder, i11, match4.start, match4.ref, match4.len, byteBufferInNativeByteOrder2, iEncodeSequence2, i7), i7);
                                    iEnd = match2.end();
                                }
                            }
                            i6 = i5;
                        } else {
                            match = match;
                            match2 = match7;
                        }
                        match4 = match4;
                        i10 = iEnd;
                        match3 = match3;
                        match5 = match2;
                        match6 = match;
                        hashTable = hashTable;
                        i8 = i8;
                        i6 = i5;
                    } else {
                        match = match6;
                        match4 = match4;
                        i5 = i6;
                        match2 = match5;
                        match3 = match3;
                    }
                    iEncodeSequence = LZ4ByteBufferUtils.encodeSequence(byteBufferInNativeByteOrder, i11, match4.start, match4.ref, match4.len, byteBufferInNativeByteOrder2, iEncodeSequence2, i7);
                    iEnd = match4.end();
                    match4 = match4;
                    i10 = iEnd;
                    match3 = match3;
                    match5 = match2;
                    match6 = match;
                    hashTable = hashTable;
                    i8 = i8;
                    i6 = i5;
                }
            } else {
                i10++;
            }
        }
        return LZ4ByteBufferUtils.lastLiterals(byteBufferInNativeByteOrder, iEnd, i6 - iEnd, byteBufferInNativeByteOrder2, iEncodeSequence, i7) - i3;
    }
}
