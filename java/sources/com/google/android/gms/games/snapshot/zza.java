package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.internal.zzh;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes.dex */
public final class zza extends com.google.android.gms.games.internal.zzd implements SnapshotContents {
    private Contents zzqm;
    private static final Object zzql = new Object();
    public static final Parcelable.Creator<zza> CREATOR = new zzb();

    public zza(Contents contents) {
        this.zzqm = contents;
    }

    private final boolean zza(int i, byte[] bArr, int i2, int i3, boolean z) {
        Preconditions.checkState(!isClosed(), "Must provide a previously opened SnapshotContents");
        synchronized (zzql) {
            FileOutputStream fileOutputStream = new FileOutputStream(this.zzqm.getParcelFileDescriptor().getFileDescriptor());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            try {
                FileChannel channel = fileOutputStream.getChannel();
                channel.position(i);
                bufferedOutputStream.write(bArr, i2, i3);
                if (z) {
                    channel.truncate(bArr.length);
                }
                bufferedOutputStream.flush();
            } catch (IOException e) {
                zzh.i("SnapshotContentsEntity", "Failed to write snapshot data", e);
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final void close() {
        this.zzqm = null;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final ParcelFileDescriptor getParcelFileDescriptor() {
        Preconditions.checkState(!isClosed(), "Cannot mutate closed contents!");
        return this.zzqm.getParcelFileDescriptor();
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final boolean isClosed() {
        return this.zzqm == null;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final boolean modifyBytes(int i, byte[] bArr, int i2, int i3) {
        return zza(i, bArr, i2, bArr.length, false);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final byte[] readFully() throws IOException {
        byte[] inputStreamFully;
        Preconditions.checkState(!isClosed(), "Must provide a previously opened Snapshot");
        synchronized (zzql) {
            FileInputStream fileInputStream = new FileInputStream(this.zzqm.getParcelFileDescriptor().getFileDescriptor());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            try {
                fileInputStream.getChannel().position(0L);
                inputStreamFully = IOUtils.readInputStreamFully(bufferedInputStream, false);
                fileInputStream.getChannel().position(0L);
            } catch (IOException e) {
                zzh.w("SnapshotContentsEntity", "Failed to read snapshot data", e);
                throw e;
            }
        }
        return inputStreamFully;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final boolean writeBytes(byte[] bArr) {
        return zza(0, bArr, 0, bArr.length, true);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzqm, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final Contents zzcl() {
        return this.zzqm;
    }
}
