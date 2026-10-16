package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;

/* JADX INFO: loaded from: classes.dex */
final class zzan extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.DriveIdResult> zzdv;

    public zzan(BaseImplementation.ResultHolder<DriveApi.DriveIdResult> resultHolder) {
        this.zzdv = resultHolder;
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(Status status) throws RemoteException {
        this.zzdv.setResult(new zzao(status, null));
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfh zzfhVar) throws RemoteException {
        this.zzdv.setResult(new zzao(Status.RESULT_SUCCESS, zzfhVar.zzdb));
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfs zzfsVar) throws RemoteException {
        this.zzdv.setResult(new zzao(Status.RESULT_SUCCESS, new zzaa(zzfsVar.zzdl).getDriveId()));
    }
}
