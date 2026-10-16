package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveResource;

/* JADX INFO: loaded from: classes.dex */
abstract class zzea extends zzau<DriveResource.MetadataResult> {
    private zzea(zzdp zzdpVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzea(zzdp zzdpVar, GoogleApiClient googleApiClient, zzdq zzdqVar) {
        this(zzdpVar, googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzdz(status, null);
    }
}
