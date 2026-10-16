package com.google.android.gms.internal.nearby;

import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.Task;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzbd extends ConnectionsClient {
    private static final Api.AbstractClientBuilder<zzx, Api.ApiOptions.NoOptions> CLIENT_BUILDER;
    private static final Api.ClientKey<zzx> CLIENT_KEY;
    private static final Api<Api.ApiOptions.NoOptions> CONNECTIONS_API;
    private final zzk zzcd;

    static {
        Api.ClientKey<zzx> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzbm zzbmVar = new zzbm();
        CLIENT_BUILDER = zzbmVar;
        CONNECTIONS_API = new Api<>("Nearby.CONNECTIONS_API", zzbmVar, clientKey);
    }

    public zzbd(Activity activity) {
        super(activity, CONNECTIONS_API, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zzcd = zzk.zza();
    }

    public zzbd(Context context) {
        super(context, CONNECTIONS_API, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zzcd = zzk.zza();
    }

    private final Task<Void> zza(zzbw zzbwVar) {
        return doWrite(new zzbv(this, zzbwVar));
    }

    private final Task<Void> zza(zzbz zzbzVar) {
        return doWrite(new zzbn(this, zzbzVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(String str) {
        ListenerHolder<String> listenerHolderZza = this.zzcd.zza((GoogleApi) this, str, "connection");
        this.zzcd.zza(this, new zzbt(this, listenerHolderZza), new zzbu(this, listenerHolderZza.getListenerKey()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(String str) {
        zzk zzkVar = this.zzcd;
        zzkVar.zza(this, zzkVar.zzb(this, str, "connection"));
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> acceptConnection(final String str, PayloadCallback payloadCallback) {
        final ListenerHolder<L> listenerHolderRegisterListener = registerListener(payloadCallback, PayloadCallback.class.getName());
        return zza(new zzbw(str, listenerHolderRegisterListener) { // from class: com.google.android.gms.internal.nearby.zzbf
            private final String zzce;
            private final ListenerHolder zzch;

            {
                this.zzce = str;
                this.zzch = listenerHolderRegisterListener;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, this.zzce, (ListenerHolder<PayloadCallback>) this.zzch);
            }
        });
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> cancelPayload(final long j) {
        return zza(new zzbw(j) { // from class: com.google.android.gms.internal.nearby.zzbj
            private final long zzck;

            {
                this.zzck = j;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, this.zzck);
            }
        });
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final void disconnectFromEndpoint(final String str) {
        zza(new zzbz(str) { // from class: com.google.android.gms.internal.nearby.zzbk
            private final String zzce;

            {
                this.zzce = str;
            }

            @Override // com.google.android.gms.internal.nearby.zzbz
            public final void zzb(zzx zzxVar) throws RemoteException {
                zzxVar.disconnectFromEndpoint(this.zzce);
            }
        });
        zzc(str);
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> rejectConnection(final String str) {
        return zza(new zzbw(str) { // from class: com.google.android.gms.internal.nearby.zzbg
            private final String zzce;

            {
                this.zzce = str;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, this.zzce);
            }
        });
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> requestConnection(final String str, final String str2, ConnectionLifecycleCallback connectionLifecycleCallback) {
        final ListenerHolder<L> listenerHolderRegisterListener = registerListener(new zzbx(this, connectionLifecycleCallback), ConnectionLifecycleCallback.class.getName());
        zzb(str2);
        return zza(new zzbw(str, str2, listenerHolderRegisterListener) { // from class: com.google.android.gms.internal.nearby.zzbe
            private final String zzce;
            private final String zzcf;
            private final ListenerHolder zzcg;

            {
                this.zzce = str;
                this.zzcf = str2;
                this.zzcg = listenerHolderRegisterListener;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, this.zzce, this.zzcf, (ListenerHolder<ConnectionLifecycleCallback>) this.zzcg);
            }
        }).addOnFailureListener(new zzbs(this, str2));
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> sendPayload(final String str, final Payload payload) {
        return zza(new zzbw(str, payload) { // from class: com.google.android.gms.internal.nearby.zzbh
            private final String zzce;
            private final Payload zzci;

            {
                this.zzce = str;
                this.zzci = payload;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, new String[]{this.zzce}, this.zzci, false);
            }
        });
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> sendPayload(final List<String> list, final Payload payload) {
        return zza(new zzbw(list, payload) { // from class: com.google.android.gms.internal.nearby.zzbi
            private final Payload zzci;
            private final List zzcj;

            {
                this.zzcj = list;
                this.zzci = payload;
            }

            @Override // com.google.android.gms.internal.nearby.zzbw
            public final void zza(zzx zzxVar, BaseImplementation.ResultHolder resultHolder) throws RemoteException {
                zzxVar.zza((BaseImplementation.ResultHolder<Status>) resultHolder, (String[]) this.zzcj.toArray(new String[0]), this.zzci, false);
            }
        });
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> startAdvertising(String str, String str2, ConnectionLifecycleCallback connectionLifecycleCallback, AdvertisingOptions advertisingOptions) {
        ListenerHolder<L> listenerHolderRegisterListener = registerListener(new zzbx(this, connectionLifecycleCallback), ConnectionLifecycleCallback.class.getName());
        ListenerHolder listenerHolderZza = this.zzcd.zza(this, new Object(), "advertising");
        return this.zzcd.zza(this, new zzbo(this, listenerHolderZza, str, str2, listenerHolderRegisterListener, advertisingOptions), new zzbp(this, listenerHolderZza.getListenerKey()));
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final Task<Void> startDiscovery(String str, EndpointDiscoveryCallback endpointDiscoveryCallback, DiscoveryOptions discoveryOptions) {
        ListenerHolder listenerHolderZza = this.zzcd.zza(this, endpointDiscoveryCallback, "discovery");
        return this.zzcd.zza(this, new zzbq(this, listenerHolderZza, str, listenerHolderZza, discoveryOptions), new zzbr(this, listenerHolderZza.getListenerKey()));
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final void stopAdvertising() {
        this.zzcd.zza(this, "advertising");
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final void stopAllEndpoints() {
        stopAdvertising();
        stopDiscovery();
        zza(zzbl.zzcl);
        this.zzcd.zza(this, "connection");
    }

    @Override // com.google.android.gms.nearby.connection.ConnectionsClient
    public final void stopDiscovery() {
        this.zzcd.zza(this, "discovery");
    }
}
