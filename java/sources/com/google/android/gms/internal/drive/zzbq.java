package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;

/* JADX INFO: loaded from: classes.dex */
final class zzbq implements ListenerHolder.Notifier<DriveFile.DownloadProgressListener> {
    private final /* synthetic */ long zzez;
    private final /* synthetic */ long zzfa;

    zzbq(zzbp zzbpVar, long j, long j2) {
        this.zzez = j;
        this.zzfa = j2;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(DriveFile.DownloadProgressListener downloadProgressListener) {
        downloadProgressListener.onProgress(this.zzez, this.zzfa);
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
