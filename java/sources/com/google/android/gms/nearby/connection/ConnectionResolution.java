package com.google.android.gms.nearby.connection;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public final class ConnectionResolution {
    private final Status zzt;

    @Deprecated
    public ConnectionResolution(Status status) {
        this.zzt = status;
    }

    public final Status getStatus() {
        return this.zzt;
    }
}
