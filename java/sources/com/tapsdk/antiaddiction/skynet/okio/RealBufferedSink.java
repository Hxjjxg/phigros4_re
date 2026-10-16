package com.tapsdk.antiaddiction.skynet.okio;

import android.support.v4.media.session.PlaybackStateCompat;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
final class RealBufferedSink implements BufferedSink {
    public final Buffer buffer = new Buffer();
    boolean closed;
    public final Sink sink;

    RealBufferedSink(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.sink = sink;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.write(buffer, j);
        emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink write(ByteString byteString) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.write(byteString);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeUtf8(String str) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeUtf8(str);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeUtf8(String str, int i, int i2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeUtf8(str, i, i2);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeUtf8CodePoint(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeUtf8CodePoint(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeString(String str, Charset charset) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeString(str, charset);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeString(String str, int i, int i2, Charset charset) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeString(str, i, i2, charset);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink write(byte[] bArr) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.write(bArr);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink write(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.write(bArr, i, i2);
        return emitCompleteSegments();
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        int iWrite = this.buffer.write(byteBuffer);
        emitCompleteSegments();
        return iWrite;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = source.read(this.buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j2 == -1) {
                return j;
            }
            j += j2;
            emitCompleteSegments();
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink write(Source source, long j) throws IOException {
        while (j > 0) {
            long j2 = source.read(this.buffer, j);
            if (j2 == -1) {
                throw new EOFException();
            }
            j -= j2;
            emitCompleteSegments();
        }
        return this;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeByte(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeByte(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeShort(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeShort(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeShortLe(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeShortLe(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeInt(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeInt(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeIntLe(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeIntLe(i);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeLong(long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeLong(j);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeLongLe(long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeLongLe(j);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeDecimalLong(long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeDecimalLong(j);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink writeHexadecimalUnsignedLong(long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        this.buffer.writeHexadecimalUnsignedLong(j);
        return emitCompleteSegments();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        long jCompleteSegmentByteCount = this.buffer.completeSegmentByteCount();
        if (jCompleteSegmentByteCount > 0) {
            this.sink.write(this.buffer, jCompleteSegmentByteCount);
        }
        return this;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public BufferedSink emit() throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        long size = this.buffer.size();
        if (size > 0) {
            this.sink.write(this.buffer, size);
        }
        return this;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink
    public OutputStream outputStream() {
        return new OutputStream() { // from class: com.tapsdk.antiaddiction.skynet.okio.RealBufferedSink.1
            @Override // java.io.OutputStream
            public void write(int i) throws IOException {
                if (RealBufferedSink.this.closed) {
                    throw new IOException(Keys.Sharing.CLOSED);
                }
                RealBufferedSink.this.buffer.writeByte((int) ((byte) i));
                RealBufferedSink.this.emitCompleteSegments();
            }

            @Override // java.io.OutputStream
            public void write(byte[] bArr, int i, int i2) throws IOException {
                if (RealBufferedSink.this.closed) {
                    throw new IOException(Keys.Sharing.CLOSED);
                }
                RealBufferedSink.this.buffer.write(bArr, i, i2);
                RealBufferedSink.this.emitCompleteSegments();
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() throws IOException {
                if (RealBufferedSink.this.closed) {
                    return;
                }
                RealBufferedSink.this.flush();
            }

            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws Throwable {
                RealBufferedSink.this.close();
            }

            public String toString() {
                return RealBufferedSink.this + ".outputStream()";
            }
        };
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.BufferedSink, com.tapsdk.antiaddiction.skynet.okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        if (this.closed) {
            throw new IllegalStateException(Keys.Sharing.CLOSED);
        }
        if (this.buffer.size > 0) {
            Sink sink = this.sink;
            Buffer buffer = this.buffer;
            sink.write(buffer, buffer.size);
        }
        this.sink.flush();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.closed;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        if (this.buffer.size > 0) {
            Sink sink = this.sink;
            Buffer buffer = this.buffer;
            sink.write(buffer, buffer.size);
        }
        th = null;
        try {
            this.sink.close();
        } catch (Throwable th) {
            if (th == null) {
                th = th;
            }
        }
        this.closed = true;
        if (th != null) {
            Util.sneakyRethrow(th);
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }
}
