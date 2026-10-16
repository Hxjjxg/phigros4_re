package com.google.android.gms.internal.nearby;

import android.database.ContentObserver;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
final class zzhf extends ContentObserver {
    zzhf(Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        zzhe.zzjq.set(true);
    }
}
