package com.google.android.gms.nearby.messages.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.nearby.messages.MessagesOptions;

/* JADX INFO: loaded from: classes.dex */
final class zzbj extends Api.AbstractClientBuilder<zzah, MessagesOptions> {
    zzbj() {
    }

    @Override // com.google.android.gms.common.api.Api.AbstractClientBuilder
    public final /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, MessagesOptions messagesOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzah(context, looper, connectionCallbacks, onConnectionFailedListener, clientSettings, messagesOptions);
    }

    @Override // com.google.android.gms.common.api.Api.BaseClientBuilder
    public final int getPriority() {
        return Integer.MAX_VALUE;
    }
}
