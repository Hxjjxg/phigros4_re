package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.content.Intent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.nearby.zzgw;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Messages;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.StatusCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

/* JADX INFO: loaded from: classes.dex */
public final class zzbi implements Messages {
    public static final zzbi zzij = new zzbi();
    public static final Api.ClientKey<zzah> CLIENT_KEY = new Api.ClientKey<>();
    public static final Api.AbstractClientBuilder<zzah, MessagesOptions> CLIENT_BUILDER = new zzbj();

    private zzbi() {
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> getPermissionStatus(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzbr(this, googleApiClient));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final void handleIntent(Intent intent, MessageListener messageListener) {
        zzgw.zza(intent, messageListener);
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> publish(GoogleApiClient googleApiClient, Message message) {
        return publish(googleApiClient, message, PublishOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> publish(GoogleApiClient googleApiClient, Message message, PublishOptions publishOptions) {
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(publishOptions);
        ListenerHolder listenerHolderRegisterListener = publishOptions.getCallback() == null ? null : googleApiClient.registerListener(publishOptions.getCallback());
        return googleApiClient.execute(new zzbl(this, googleApiClient, message, listenerHolderRegisterListener != null ? new zzbt(listenerHolderRegisterListener) : null, publishOptions));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> registerStatusCallback(GoogleApiClient googleApiClient, StatusCallback statusCallback) {
        Preconditions.checkNotNull(statusCallback);
        return googleApiClient.execute(new zzbs(this, googleApiClient, googleApiClient.registerListener(statusCallback)));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return subscribe(googleApiClient, pendingIntent, SubscribeOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, PendingIntent pendingIntent, SubscribeOptions subscribeOptions) {
        Preconditions.checkNotNull(pendingIntent);
        Preconditions.checkNotNull(subscribeOptions);
        ListenerHolder listenerHolderRegisterListener = subscribeOptions.getCallback() == null ? null : googleApiClient.registerListener(subscribeOptions.getCallback());
        return googleApiClient.execute(new zzbo(this, googleApiClient, pendingIntent, listenerHolderRegisterListener != null ? new zzbw(listenerHolderRegisterListener) : null, subscribeOptions));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, MessageListener messageListener) {
        return subscribe(googleApiClient, messageListener, SubscribeOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, MessageListener messageListener, SubscribeOptions subscribeOptions) {
        Preconditions.checkNotNull(messageListener);
        Preconditions.checkNotNull(subscribeOptions);
        Preconditions.checkArgument(subscribeOptions.getStrategy().zzae() == 0, "Strategy.setBackgroundScanMode() is only supported by background subscribe (the version which takes a PendingIntent).");
        ListenerHolder listenerHolderRegisterListener = googleApiClient.registerListener(messageListener);
        ListenerHolder listenerHolderRegisterListener2 = subscribeOptions.getCallback() == null ? null : googleApiClient.registerListener(subscribeOptions.getCallback());
        return googleApiClient.execute(new zzbn(this, googleApiClient, listenerHolderRegisterListener, listenerHolderRegisterListener2 != null ? new zzbw(listenerHolderRegisterListener2) : null, subscribeOptions));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> unpublish(GoogleApiClient googleApiClient, Message message) {
        Preconditions.checkNotNull(message);
        return googleApiClient.execute(new zzbm(this, googleApiClient, message));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> unregisterStatusCallback(GoogleApiClient googleApiClient, StatusCallback statusCallback) {
        Preconditions.checkNotNull(statusCallback);
        return googleApiClient.execute(new zzbk(this, googleApiClient, googleApiClient.registerListener(statusCallback)));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent);
        return googleApiClient.execute(new zzbq(this, googleApiClient, pendingIntent));
    }

    @Override // com.google.android.gms.nearby.messages.Messages
    public final PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, MessageListener messageListener) {
        Preconditions.checkNotNull(messageListener);
        return googleApiClient.execute(new zzbp(this, googleApiClient, googleApiClient.registerListener(messageListener)));
    }
}
