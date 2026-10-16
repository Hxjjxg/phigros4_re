package com.google.android.gms.games;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzj extends Games.zzb {
    zzj() {
        super(null);
    }

    @Override // com.google.android.gms.common.api.Api.BaseClientBuilder
    public final /* synthetic */ List getImpliedScopes(Object obj) {
        return Collections.singletonList(Games.zzal);
    }
}
