package com.google.android.gms.nearby.connection;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class Payload {
    private final long id;
    private final int type;
    private final Stream zzaa;
    private final byte[] zzy;
    private final File zzz;

    public static class File {
        private final java.io.File zzab;
        private final ParcelFileDescriptor zzac;
        private final long zzad;

        private File(java.io.File file, ParcelFileDescriptor parcelFileDescriptor, long j) {
            this.zzab = file;
            this.zzac = parcelFileDescriptor;
            this.zzad = j;
        }

        public static File zza(ParcelFileDescriptor parcelFileDescriptor) {
            return new File(null, (ParcelFileDescriptor) Preconditions.checkNotNull(parcelFileDescriptor, "Cannot create Payload.File from null ParcelFileDescriptor."), parcelFileDescriptor.getStatSize());
        }

        public static File zza(java.io.File file, long j) throws FileNotFoundException {
            return new File((java.io.File) Preconditions.checkNotNull(file, "Cannot create Payload.File from null java.io.File."), ParcelFileDescriptor.open(file, 268435456), j);
        }

        public java.io.File asJavaFile() {
            return this.zzab;
        }

        public ParcelFileDescriptor asParcelFileDescriptor() {
            return this.zzac;
        }

        public long getSize() {
            return this.zzad;
        }
    }

    public static class Stream {
        private final ParcelFileDescriptor zzac;
        private InputStream zzae;

        private Stream(ParcelFileDescriptor parcelFileDescriptor, InputStream inputStream) {
            this.zzac = parcelFileDescriptor;
            this.zzae = inputStream;
        }

        public static Stream zza(InputStream inputStream) {
            Preconditions.checkNotNull(inputStream, "Cannot create Payload.Stream from null InputStream.");
            return new Stream(null, inputStream);
        }

        public static Stream zzb(ParcelFileDescriptor parcelFileDescriptor) {
            Preconditions.checkNotNull(parcelFileDescriptor, "Cannot create Payload.Stream from null ParcelFileDescriptor.");
            return new Stream(parcelFileDescriptor, null);
        }

        public InputStream asInputStream() {
            if (this.zzae == null) {
                this.zzae = new ParcelFileDescriptor.AutoCloseInputStream(this.zzac);
            }
            return this.zzae;
        }

        public ParcelFileDescriptor asParcelFileDescriptor() {
            return this.zzac;
        }
    }

    public @interface Type {
        public static final int BYTES = 1;
        public static final int FILE = 2;
        public static final int STREAM = 3;
    }

    private Payload(long j, int i, byte[] bArr, File file, Stream stream) {
        this.id = j;
        this.type = i;
        this.zzy = bArr;
        this.zzz = file;
        this.zzaa = stream;
    }

    public static Payload fromBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr, "Cannot create a Payload from null bytes.");
        return zza(bArr, UUID.randomUUID().getLeastSignificantBits());
    }

    public static Payload fromFile(ParcelFileDescriptor parcelFileDescriptor) {
        return zza(File.zza(parcelFileDescriptor), UUID.randomUUID().getLeastSignificantBits());
    }

    public static Payload fromFile(java.io.File file) throws FileNotFoundException {
        return zza(File.zza(file, file.length()), UUID.randomUUID().getLeastSignificantBits());
    }

    public static Payload fromStream(ParcelFileDescriptor parcelFileDescriptor) {
        return zza(Stream.zzb(parcelFileDescriptor), UUID.randomUUID().getLeastSignificantBits());
    }

    public static Payload fromStream(InputStream inputStream) {
        return zza(Stream.zza(inputStream), UUID.randomUUID().getLeastSignificantBits());
    }

    public static Payload zza(File file, long j) {
        return new Payload(j, 2, null, file, null);
    }

    public static Payload zza(Stream stream, long j) {
        return new Payload(j, 3, null, null, stream);
    }

    public static Payload zza(byte[] bArr, long j) {
        return new Payload(j, 1, bArr, null, null);
    }

    public byte[] asBytes() {
        return this.zzy;
    }

    public File asFile() {
        return this.zzz;
    }

    public Stream asStream() {
        return this.zzaa;
    }

    public long getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }
}
