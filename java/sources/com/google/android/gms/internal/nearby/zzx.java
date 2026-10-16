package com.google.android.gms.internal.nearby;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import androidx.collection.ArraySet;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class zzx extends GmsClient<zzdu> {
    private final long zzaz;
    private final Set<zzak> zzba;
    private final Set<zzav> zzbb;
    private final Set<zzz> zzbc;
    private zzff zzbd;

    public zzx(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 54, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzba = new ArraySet();
        this.zzbb = new ArraySet();
        this.zzbc = new ArraySet();
        this.zzaz = hashCode();
    }

    private final void reset() {
        Iterator<zzak> it = this.zzba.iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        Iterator<zzav> it2 = this.zzbb.iterator();
        while (it2.hasNext()) {
            it2.next().shutdown();
        }
        Iterator<zzz> it3 = this.zzbc.iterator();
        while (it3.hasNext()) {
            it3.next().shutdown();
        }
        this.zzba.clear();
        this.zzbb.clear();
        this.zzbc.clear();
        zzff zzffVar = this.zzbd;
        if (zzffVar != null) {
            zzffVar.shutdown();
            this.zzbd = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Status zza(int i) {
        return new Status(i, ConnectionsStatusCodes.getStatusCodeString(i));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
        return iInterfaceQueryLocalInterface instanceof zzdu ? (zzdu) iInterfaceQueryLocalInterface : new zzdv(iBinder);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final void disconnect() {
        if (isConnected()) {
            try {
                ((zzdu) getService()).zza(new zzv().zzd());
            } catch (RemoteException e) {
                Log.w("NearbyConnectionsClient", "Failed to notify client disconnect.", e);
            }
        }
        reset();
        super.disconnect();
    }

    public final void disconnectFromEndpoint(String str) throws RemoteException {
        ((zzdu) getService()).zza(new zzdb().zzd(str).zzf());
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", this.zzaz);
        return bundle;
    }

    @Override // com.google.android.gms.common.internal.GmsClient, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.nearby.internal.connection.INearbyConnectionService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.nearby.connection.service.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ void onConnectedLocked(IInterface iInterface) {
        super.onConnectedLocked((zzdu) iInterface);
        this.zzbd = new zzff();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final void onConnectionSuspended(int i) {
        if (i == 1) {
            reset();
        }
        super.onConnectionSuspended(i);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final boolean requiresGooglePlayServices() {
        return Nearby.zza(getContext());
    }

    public final void stopAdvertising() throws RemoteException {
        ((zzdu) getService()).zza(new zzgh().zzx());
    }

    public final void stopAllEndpoints() throws RemoteException {
        ((zzdu) getService()).zza(new zzgk().zzy());
    }

    public final void stopDiscovery() throws RemoteException {
        ((zzdu) getService()).zza(new zzgn().zzz());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, long j) throws RemoteException {
        ((zzdu) getService()).zza(new zzs().zzb(new zzba(resultHolder)).zza(j).zzc());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str) throws RemoteException {
        ((zzdu) getService()).zza(new zzfo().zzc(new zzba(resultHolder)).zzf(str).zzs());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str, ListenerHolder<PayloadCallback> listenerHolder) throws RemoteException {
        zzav zzavVar = new zzav(listenerHolder);
        this.zzbb.add(zzavVar);
        ((zzdu) getService()).zza(new zzo().zza(new zzba(resultHolder)).zza(str).zza(zzavVar).zzb());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str, ListenerHolder<EndpointDiscoveryCallback> listenerHolder, DiscoveryOptions discoveryOptions) throws RemoteException {
        zzak zzakVar = new zzak(listenerHolder);
        this.zzba.add(zzakVar);
        ((zzdu) getService()).zza(new zzge().zzf(new zzba(resultHolder)).zzk(str).zze(discoveryOptions).zza(zzakVar).zzw());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str, String str2, ListenerHolder<ConnectionLifecycleCallback> listenerHolder) throws RemoteException {
        zzz zzzVar = new zzz(listenerHolder);
        this.zzbc.add(zzzVar);
        ((zzdu) getService()).zza(new zzfs().zzd(new zzba(resultHolder)).zzg(str).zzh(str2).zza(zzzVar).zzt());
    }

    public final void zza(BaseImplementation.ResultHolder<Connections.StartAdvertisingResult> resultHolder, String str, String str2, ListenerHolder<ConnectionLifecycleCallback> listenerHolder, AdvertisingOptions advertisingOptions) throws RemoteException {
        zzz zzzVar = new zzz(listenerHolder);
        this.zzbc.add(zzzVar);
        ((zzdu) getService()).zza(new zzga().zza(new zzbc(resultHolder)).zzi(str).zzj(str2).zzg(advertisingOptions).zzb(zzzVar).zzv());
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String[] strArr, Payload payload, boolean z) throws RemoteException {
        try {
            Pair<zzfh, Pair<ParcelFileDescriptor, ParcelFileDescriptor>> pairZza = zzfl.zza(payload);
            ((zzdu) getService()).zza(new zzfw().zze(new zzba(resultHolder)).zza(strArr).zzb((zzfh) pairZza.first).zzu());
            if (pairZza.second != null) {
                Pair pair = (Pair) pairZza.second;
                this.zzbd.zza(payload.asStream().asInputStream(), new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor) pair.first), new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor) pair.second), payload.getId());
            }
        } catch (IOException unused) {
            resultHolder.setResult(zza(ConnectionsStatusCodes.STATUS_PAYLOAD_IO_ERROR));
        }
    }
}
