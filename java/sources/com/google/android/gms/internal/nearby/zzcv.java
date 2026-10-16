package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.nearby.Nearby;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcv<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zzx> {
    public zzcv(GoogleApiClient googleApiClient) {
        super(Nearby.CONNECTIONS_API, googleApiClient);
    }
}
