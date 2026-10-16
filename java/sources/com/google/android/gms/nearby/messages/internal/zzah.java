package com.google.android.gms.nearby.messages.internal;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.nearby.zzgw;
import com.google.android.gms.internal.nearby.zzgy;
import com.google.android.gms.internal.nearby.zzhb;
import com.google.android.gms.internal.nearby.zzhd;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.StatusCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

/* JADX INFO: loaded from: classes.dex */
public final class zzah extends GmsClient<zzs> {
    private final int zzfh;
    private final ClientAppContext zzhi;
    private final zzhd<ListenerHolder.ListenerKey, IBinder> zzhl;

    zzah(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings, MessagesOptions messagesOptions) {
        ClientAppContext clientAppContext;
        int i;
        super(context, looper, 62, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzhl = new zzhd<>();
        String realClientPackageName = clientSettings.getRealClientPackageName();
        int iZzb = zzb(context);
        if (messagesOptions != null) {
            clientAppContext = new ClientAppContext(realClientPackageName, null, false, null, iZzb);
            this.zzhi = clientAppContext;
            i = messagesOptions.zzfh;
        } else {
            clientAppContext = new ClientAppContext(realClientPackageName, null, false, null, iZzb);
            this.zzhi = clientAppContext;
            i = -1;
        }
        this.zzfh = i;
        if (iZzb == 1 && PlatformVersion.isAtLeastIceCreamSandwich()) {
            Activity activity = (Activity) context;
            if (Log.isLoggable("NearbyMessagesClient", 2)) {
                Log.v("NearbyMessagesClient", String.format("Registering ClientLifecycleSafetyNet's ActivityLifecycleCallbacks for %s", activity.getPackageName()));
            }
            activity.getApplication().registerActivityLifecycleCallbacks(new zzaj(activity, this));
        }
    }

    static int zzb(Context context) {
        if (context instanceof Activity) {
            return 1;
        }
        if (context instanceof Application) {
            return 2;
        }
        return context instanceof Service ? 3 : 0;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesService");
        return iInterfaceQueryLocalInterface instanceof zzs ? (zzs) iInterfaceQueryLocalInterface : new zzt(iBinder);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final void disconnect() {
        try {
            zzf(2);
        } catch (RemoteException e) {
            if (Log.isLoggable("NearbyMessagesClient", 2)) {
                Log.v("NearbyMessagesClient", String.format("Failed to emit CLIENT_DISCONNECTED from override of GmsClient#disconnect(): %s", e));
            }
        }
        this.zzhl.clear();
        super.disconnect();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle getServiceRequestExtraArgs = super.getGetServiceRequestExtraArgs();
        getServiceRequestExtraArgs.putInt("NearbyPermissions", this.zzfh);
        getServiceRequestExtraArgs.putParcelable("ClientAppContext", this.zzhi);
        return getServiceRequestExtraArgs;
    }

    @Override // com.google.android.gms.common.internal.GmsClient, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.nearby.messages.internal.INearbyMessagesService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.nearby.messages.service.NearbyMessagesService.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final boolean requiresGooglePlayServices() {
        return Nearby.zza(getContext());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, PendingIntent pendingIntent) throws RemoteException {
        ((zzs) getService()).zza(new zzcg(null, new zzgy(listenerHolder), pendingIntent));
    }

    @Deprecated
    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, PendingIntent pendingIntent, zzab zzabVar, SubscribeOptions subscribeOptions) throws RemoteException {
        zza(listenerHolder, pendingIntent, zzabVar, subscribeOptions, this.zzhi.zzhf);
    }

    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, PendingIntent pendingIntent, zzab zzabVar, SubscribeOptions subscribeOptions, int i) throws RemoteException {
        ((zzs) getService()).zza(new SubscribeRequest(null, subscribeOptions.getStrategy(), new zzgy(listenerHolder), subscribeOptions.getFilter(), pendingIntent, null, zzabVar, subscribeOptions.zzgb, subscribeOptions.zzgc, this.zzhi.zzhf));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, ListenerHolder<MessageListener> listenerHolder2) throws RemoteException {
        zzgy zzgyVar = new zzgy(listenerHolder);
        if (!this.zzhl.containsKey(listenerHolder2.getListenerKey())) {
            zzgyVar.zza(new Status(0));
            return;
        }
        ((zzs) getService()).zza(new zzcg(this.zzhl.get(listenerHolder2.getListenerKey()), zzgyVar, null));
        this.zzhl.remove(listenerHolder2.getListenerKey());
    }

    @Deprecated
    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, ListenerHolder<MessageListener> listenerHolder2, zzab zzabVar, SubscribeOptions subscribeOptions, byte[] bArr) throws RemoteException {
        zza(listenerHolder, listenerHolder2, zzabVar, subscribeOptions, null, this.zzhi.zzhf);
    }

    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, ListenerHolder<MessageListener> listenerHolder2, zzab zzabVar, SubscribeOptions subscribeOptions, byte[] bArr, int i) throws RemoteException {
        if (!this.zzhl.containsKey(listenerHolder2.getListenerKey())) {
            this.zzhl.zza(listenerHolder2.getListenerKey(), new zzgw(listenerHolder2));
        }
        ((zzs) getService()).zza(new SubscribeRequest(this.zzhl.get(listenerHolder2.getListenerKey()), subscribeOptions.getStrategy(), new zzgy(listenerHolder), subscribeOptions.getFilter(), null, null, zzabVar, subscribeOptions.zzgb, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, zzaf zzafVar) throws RemoteException {
        ((zzs) getService()).zza(new zzce(zzafVar, new zzgy(listenerHolder)));
    }

    @Deprecated
    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, zzaf zzafVar, zzv zzvVar, PublishOptions publishOptions) throws RemoteException {
        zza(listenerHolder, zzafVar, zzvVar, publishOptions, this.zzhi.zzhf);
    }

    final void zza(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, zzaf zzafVar, zzv zzvVar, PublishOptions publishOptions, int i) throws RemoteException {
        ((zzs) getService()).zza(new zzbz(zzafVar, publishOptions.getStrategy(), new zzgy(listenerHolder), zzvVar, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, ListenerHolder<StatusCallback> listenerHolder2) throws RemoteException {
        if (!this.zzhl.containsKey(listenerHolder2.getListenerKey())) {
            this.zzhl.zza(listenerHolder2.getListenerKey(), new zzhb(listenerHolder2));
        }
        zzcb zzcbVar = new zzcb(new zzgy(listenerHolder), this.zzhl.get(listenerHolder2.getListenerKey()));
        zzcbVar.zzix = true;
        ((zzs) getService()).zza(zzcbVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(ListenerHolder<BaseImplementation.ResultHolder<Status>> listenerHolder, ListenerHolder<StatusCallback> listenerHolder2) throws RemoteException {
        zzgy zzgyVar = new zzgy(listenerHolder);
        if (!this.zzhl.containsKey(listenerHolder2.getListenerKey())) {
            zzgyVar.zza(new Status(0));
            return;
        }
        zzcb zzcbVar = new zzcb(zzgyVar, this.zzhl.get(listenerHolder2.getListenerKey()));
        zzcbVar.zzix = false;
        ((zzs) getService()).zza(zzcbVar);
        this.zzhl.remove(listenerHolder2.getListenerKey());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzf(int i) throws RemoteException {
        String str;
        if (i == 1) {
            str = "ACTIVITY_STOPPED";
        } else {
            if (i != 2) {
                if (Log.isLoggable("NearbyMessagesClient", 5)) {
                    Log.w("NearbyMessagesClient", String.format("Received unknown/unforeseen client lifecycle event %d, can't do anything with it.", Integer.valueOf(i)));
                    return;
                }
                return;
            }
            str = "CLIENT_DISCONNECTED";
        }
        if (!isConnected()) {
            if (Log.isLoggable("NearbyMessagesClient", 3)) {
                Log.d("NearbyMessagesClient", String.format("Failed to emit client lifecycle event %s due to GmsClient being disconnected", str));
            }
        } else {
            zzj zzjVar = new zzj(i);
            if (Log.isLoggable("NearbyMessagesClient", 3)) {
                Log.d("NearbyMessagesClient", String.format("Emitting client lifecycle event %s", str));
            }
            ((zzs) getService()).zza(zzjVar);
        }
    }
}
