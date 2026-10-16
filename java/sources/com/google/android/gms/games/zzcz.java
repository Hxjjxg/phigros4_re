package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.games.video.Videos;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzcz extends com.google.android.gms.games.internal.zzt<Videos.CaptureOverlayStateListener> {
    zzcz(VideosClient videosClient, ListenerHolder.ListenerKey listenerKey) {
        super(listenerKey);
    }

    @Override // com.google.android.gms.games.internal.zzt
    protected final void zzc(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException, SecurityException {
        zzeVar.zzbb();
        taskCompletionSource.setResult(true);
    }
}
