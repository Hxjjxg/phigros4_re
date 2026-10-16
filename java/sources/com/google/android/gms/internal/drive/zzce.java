package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DrivePreferencesApi;

/* JADX INFO: loaded from: classes.dex */
final class zzce extends zzl {
    private final BaseImplementation.ResultHolder<DrivePreferencesApi.FileUploadPreferencesResult> zzdv;
    private final /* synthetic */ zzcb zzfi;

    private zzce(zzcb zzcbVar, BaseImplementation.ResultHolder<DrivePreferencesApi.FileUploadPreferencesResult> resultHolder) {
        this.zzfi = zzcbVar;
        this.zzdv = resultHolder;
    }

    /* synthetic */ zzce(zzcb zzcbVar, BaseImplementation.ResultHolder resultHolder, zzcc zzccVar) {
        this(zzcbVar, resultHolder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(Status status) throws RemoteException {
        this.zzdv.setResult(new zzcf(this.zzfi, status, null, 0 == true ? 1 : 0));
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfd zzfdVar) throws RemoteException {
        this.zzdv.setResult(new zzcf(this.zzfi, Status.RESULT_SUCCESS, zzfdVar.zzhg, null));
    }
}
