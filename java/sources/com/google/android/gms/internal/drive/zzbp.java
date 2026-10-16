package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;

/* JADX INFO: loaded from: classes.dex */
final class zzbp implements DriveFile.DownloadProgressListener {
    private final ListenerHolder<DriveFile.DownloadProgressListener> zzey;

    public zzbp(ListenerHolder<DriveFile.DownloadProgressListener> listenerHolder) {
        this.zzey = listenerHolder;
    }

    @Override // com.google.android.gms.drive.DriveFile.DownloadProgressListener
    public final void onProgress(long j, long j2) {
        this.zzey.notifyListener(new zzbq(this, j, j2));
    }
}
