package com.google.android.gms.nearby.messages.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.nearby.zzgw;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.StatusCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzak extends MessagesClient {
    private static final Api.AbstractClientBuilder<zzah, MessagesOptions> CLIENT_BUILDER;
    private static final Api.ClientKey<zzah> CLIENT_KEY;
    private static final Api<MessagesOptions> MESSAGES_API;
    private final int zzhf;

    static {
        Api.ClientKey<zzah> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzau zzauVar = new zzau();
        CLIENT_BUILDER = zzauVar;
        MESSAGES_API = new Api<>("Nearby.MESSAGES_API", zzauVar, clientKey);
    }

    public zzak(Activity activity, MessagesOptions messagesOptions) {
        super(activity, MESSAGES_API, messagesOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zzhf = 1;
        activity.getApplication().registerActivityLifecycleCallbacks(new zzbc(activity, this, null));
    }

    public zzak(Context context, MessagesOptions messagesOptions) {
        super(context, MESSAGES_API, messagesOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zzhf = zzah.zzb(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> ListenerHolder<BaseImplementation.ResultHolder<Status>> zza(TaskCompletionSource<T> taskCompletionSource) {
        return registerListener(new zzax(this, taskCompletionSource), Status.class.getName());
    }

    private final <T> Task<Void> zza(ListenerHolder<T> listenerHolder, zzbd zzbdVar, zzbd zzbdVar2) {
        return doRegisterEventListener(new zzaz(this, listenerHolder, zzbdVar), new zzba(this, listenerHolder.getListenerKey(), zzbdVar2));
    }

    private final Task<Void> zza(zzbd zzbdVar) {
        return doWrite(new zzbb(this, zzbdVar));
    }

    private final <T> Task<Void> zza(T t) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        doUnregisterEventListener(ListenerHolders.createListenerKey(t, t.getClass().getName())).addOnCompleteListener(new zzay(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private final <T> ListenerHolder<T> zzb(T t) {
        if (t == null) {
            return null;
        }
        return (ListenerHolder<T>) registerListener(t, t.getClass().getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzf(int i) {
        final int i2 = 1;
        zza(new zzbd(i2) { // from class: com.google.android.gms.nearby.messages.internal.zzat
            private final int zzhy;

            {
                this.zzhy = i2;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zzf(this.zzhy);
            }
        });
    }

    @Override // com.google.android.gms.common.api.GoogleApi
    protected final ClientSettings.Builder createClientSettingsBuilder() {
        ClientSettings.Builder builderCreateClientSettingsBuilder = super.createClientSettingsBuilder();
        if (getApiOptions() != null) {
            getApiOptions();
        }
        return builderCreateClientSettingsBuilder;
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final void handleIntent(Intent intent, MessageListener messageListener) {
        zzgw.zza(intent, messageListener);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> publish(Message message) {
        return publish(message, PublishOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> publish(final Message message, final PublishOptions publishOptions) {
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(publishOptions);
        ListenerHolder listenerHolderZzb = zzb(message);
        final zzav zzavVar = new zzav(this, zzb(publishOptions.getCallback()), listenerHolderZzb);
        return zza(listenerHolderZzb, new zzbd(this, message, zzavVar, publishOptions) { // from class: com.google.android.gms.nearby.messages.internal.zzal
            private final zzak zzho;
            private final Message zzhp;
            private final zzbe zzhq;
            private final PublishOptions zzhr;

            {
                this.zzho = this;
                this.zzhp = message;
                this.zzhq = zzavVar;
                this.zzhr = publishOptions;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                this.zzho.zza(this.zzhp, this.zzhq, this.zzhr, zzahVar, listenerHolder);
            }
        }, new zzbd(message) { // from class: com.google.android.gms.nearby.messages.internal.zzam
            private final Message zzhs;

            {
                this.zzhs = message;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zza((ListenerHolder<BaseImplementation.ResultHolder<Status>>) listenerHolder, zzaf.zza(this.zzhs));
            }
        });
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> registerStatusCallback(StatusCallback statusCallback) {
        Preconditions.checkNotNull(statusCallback);
        final ListenerHolder listenerHolderZzb = zzb(statusCallback);
        return zza(listenerHolderZzb, new zzbd(listenerHolderZzb) { // from class: com.google.android.gms.nearby.messages.internal.zzar
            private final ListenerHolder zzhv;

            {
                this.zzhv = listenerHolderZzb;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zzb(listenerHolder, this.zzhv);
            }
        }, new zzbd(listenerHolderZzb) { // from class: com.google.android.gms.nearby.messages.internal.zzas
            private final ListenerHolder zzhv;

            {
                this.zzhv = listenerHolderZzb;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zzc(listenerHolder, this.zzhv);
            }
        });
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> subscribe(PendingIntent pendingIntent) {
        return subscribe(pendingIntent, SubscribeOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> subscribe(final PendingIntent pendingIntent, final SubscribeOptions subscribeOptions) {
        Preconditions.checkNotNull(pendingIntent);
        Preconditions.checkNotNull(subscribeOptions);
        ListenerHolder listenerHolderZzb = zzb(subscribeOptions.getCallback());
        final zzbg zzbgVar = listenerHolderZzb == null ? null : new zzbg(listenerHolderZzb);
        return zza(new zzbd(this, pendingIntent, zzbgVar, subscribeOptions) { // from class: com.google.android.gms.nearby.messages.internal.zzap
            private final zzak zzho;
            private final zzbg zzht;
            private final SubscribeOptions zzhu;
            private final PendingIntent zzhw;

            {
                this.zzho = this;
                this.zzhw = pendingIntent;
                this.zzht = zzbgVar;
                this.zzhu = subscribeOptions;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                this.zzho.zza(this.zzhw, this.zzht, this.zzhu, zzahVar, listenerHolder);
            }
        });
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> subscribe(MessageListener messageListener) {
        return subscribe(messageListener, SubscribeOptions.DEFAULT);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> subscribe(MessageListener messageListener, final SubscribeOptions subscribeOptions) {
        Preconditions.checkNotNull(messageListener);
        Preconditions.checkNotNull(subscribeOptions);
        Preconditions.checkArgument(subscribeOptions.getStrategy().zzae() == 0, "Strategy.setBackgroundScanMode() is only supported by background subscribe (the version which takes a PendingIntent).");
        final ListenerHolder listenerHolderZzb = zzb(messageListener);
        final zzaw zzawVar = new zzaw(this, zzb(subscribeOptions.getCallback()), listenerHolderZzb);
        return zza(listenerHolderZzb, new zzbd(this, listenerHolderZzb, zzawVar, subscribeOptions) { // from class: com.google.android.gms.nearby.messages.internal.zzan
            private final ListenerHolder zzch;
            private final zzak zzho;
            private final zzbg zzht;
            private final SubscribeOptions zzhu;

            {
                this.zzho = this;
                this.zzch = listenerHolderZzb;
                this.zzht = zzawVar;
                this.zzhu = subscribeOptions;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                this.zzho.zza(this.zzch, this.zzht, this.zzhu, zzahVar, listenerHolder);
            }
        }, new zzbd(listenerHolderZzb) { // from class: com.google.android.gms.nearby.messages.internal.zzao
            private final ListenerHolder zzhv;

            {
                this.zzhv = listenerHolderZzb;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zza((ListenerHolder<BaseImplementation.ResultHolder<Status>>) listenerHolder, (ListenerHolder<MessageListener>) this.zzhv);
            }
        });
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> unpublish(Message message) {
        Preconditions.checkNotNull(message);
        return zza(message);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> unregisterStatusCallback(StatusCallback statusCallback) {
        Preconditions.checkNotNull(statusCallback);
        return zza(statusCallback);
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> unsubscribe(final PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent);
        return zza(new zzbd(pendingIntent) { // from class: com.google.android.gms.nearby.messages.internal.zzaq
            private final PendingIntent zzhx;

            {
                this.zzhx = pendingIntent;
            }

            @Override // com.google.android.gms.nearby.messages.internal.zzbd
            public final void zza(zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
                zzahVar.zza((ListenerHolder<BaseImplementation.ResultHolder<Status>>) listenerHolder, this.zzhx);
            }
        });
    }

    @Override // com.google.android.gms.nearby.messages.MessagesClient
    public final Task<Void> unsubscribe(MessageListener messageListener) {
        Preconditions.checkNotNull(messageListener);
        return zza(messageListener);
    }

    final /* synthetic */ void zza(PendingIntent pendingIntent, zzbg zzbgVar, SubscribeOptions subscribeOptions, zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
        zzahVar.zza((ListenerHolder<BaseImplementation.ResultHolder<Status>>) listenerHolder, pendingIntent, zzbgVar, subscribeOptions, this.zzhf);
    }

    final /* synthetic */ void zza(ListenerHolder listenerHolder, zzbg zzbgVar, SubscribeOptions subscribeOptions, zzah zzahVar, ListenerHolder listenerHolder2) throws RemoteException {
        zzahVar.zza(listenerHolder2, listenerHolder, zzbgVar, subscribeOptions, null, this.zzhf);
    }

    final /* synthetic */ void zza(Message message, zzbe zzbeVar, PublishOptions publishOptions, zzah zzahVar, ListenerHolder listenerHolder) throws RemoteException {
        zzahVar.zza((ListenerHolder<BaseImplementation.ResultHolder<Status>>) listenerHolder, zzaf.zza(message), zzbeVar, publishOptions, this.zzhf);
    }
}
