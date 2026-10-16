package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

/* JADX INFO: loaded from: classes.dex */
final class zzdz implements DriveResource.MetadataResult {
    private final Status zzdw;
    private final Metadata zzgp;

    public zzdz(Status status, Metadata metadata) {
        this.zzdw = status;
        this.zzgp = metadata;
    }

    @Override // com.google.android.gms.drive.DriveResource.MetadataResult
    public final Metadata getMetadata() {
        return this.zzgp;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzdw;
    }
}
