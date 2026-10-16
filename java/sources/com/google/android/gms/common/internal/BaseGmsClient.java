package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGmsClient<T extends IInterface> {
    public static final int CONNECT_STATE_CONNECTED = 4;
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private int zzbt;
    private long zzbu;
    private long zzbv;
    private int zzbw;
    private long zzbx;
    private zzh zzby;
    private final Looper zzbz;
    private final GmsClientSupervisor zzca;
    private final GoogleApiAvailabilityLight zzcb;
    private final Object zzcc;
    private IGmsServiceBroker zzcd;
    protected ConnectionProgressReportCallbacks zzce;
    private T zzcf;
    private final ArrayList<zzc<?>> zzcg;
    private zze zzch;
    private int zzci;
    private final BaseConnectionCallbacks zzcj;
    private final BaseOnConnectionFailedListener zzck;
    private final int zzcl;
    private final String zzcm;
    private ConnectionResult zzcn;
    private boolean zzco;
    private volatile com.google.android.gms.common.internal.zzb zzcp;
    protected AtomicInteger zzcq;
    private static final Feature[] zzbs = new Feature[0];
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};

    public interface BaseConnectionCallbacks {
        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface BaseOnConnectionFailedListener {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public interface ConnectionProgressReportCallbacks {
        void onReportServiceBinding(ConnectionResult connectionResult);
    }

    protected class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        public LegacyClientCallbackAdapter() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public void onReportServiceBinding(ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzck != null) {
                BaseGmsClient.this.zzck.onConnectionFailed(connectionResult);
            }
        }
    }

    public interface SignOutCallbacks {
        void onSignOutComplete();
    }

    protected abstract T createServiceInterface(IBinder iBinder);

    public Account getAccount() {
        return null;
    }

    public Bundle getConnectionHint() {
        return null;
    }

    protected String getLocalStartServiceAction() {
        return null;
    }

    protected abstract String getServiceDescriptor();

    protected abstract String getStartServiceAction();

    protected String getStartServicePackage() {
        return "com.google.android.gms";
    }

    void onSetConnectState(int i, T t) {
    }

    public boolean providesSignIn() {
        return false;
    }

    public boolean requiresAccount() {
        return false;
    }

    public boolean requiresGooglePlayServices() {
        return true;
    }

    public boolean requiresSignIn() {
        return false;
    }

    protected BaseGmsClient(Context context, Looper looper, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailabilityLight.getInstance(), i, (BaseConnectionCallbacks) Preconditions.checkNotNull(baseConnectionCallbacks), (BaseOnConnectionFailedListener) Preconditions.checkNotNull(baseOnConnectionFailedListener), str);
    }

    final class zzb extends com.google.android.gms.internal.common.zze {
        public zzb(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ConnectionResult connectionResult;
            ConnectionResult connectionResult2;
            if (BaseGmsClient.this.zzcq.get() != message.arg1) {
                if (zzb(message)) {
                    zza(message);
                    return;
                }
                return;
            }
            if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !BaseGmsClient.this.isConnecting()) {
                zza(message);
                return;
            }
            if (message.what != 4) {
                if (message.what != 5) {
                    if (message.what == 3) {
                        ConnectionResult connectionResult3 = new ConnectionResult(message.arg2, message.obj instanceof PendingIntent ? (PendingIntent) message.obj : null);
                        BaseGmsClient.this.zzce.onReportServiceBinding(connectionResult3);
                        BaseGmsClient.this.onConnectionFailed(connectionResult3);
                        return;
                    }
                    if (message.what != 6) {
                        if (message.what == 2 && !BaseGmsClient.this.isConnected()) {
                            zza(message);
                            return;
                        } else if (zzb(message)) {
                            ((zzc) message.obj).zzo();
                            return;
                        } else {
                            Log.wtf("GmsClient", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
                            return;
                        }
                    }
                    BaseGmsClient.this.zza(5, (IInterface) null);
                    if (BaseGmsClient.this.zzcj != null) {
                        BaseGmsClient.this.zzcj.onConnectionSuspended(message.arg2);
                    }
                    BaseGmsClient.this.onConnectionSuspended(message.arg2);
                    BaseGmsClient.this.zza(5, 1, (IInterface) null);
                    return;
                }
                if (BaseGmsClient.this.zzcn == null) {
                    connectionResult = new ConnectionResult(8);
                } else {
                    connectionResult = BaseGmsClient.this.zzcn;
                }
                BaseGmsClient.this.zzce.onReportServiceBinding(connectionResult);
                BaseGmsClient.this.onConnectionFailed(connectionResult);
                return;
            }
            BaseGmsClient.this.zzcn = new ConnectionResult(message.arg2);
            if (BaseGmsClient.this.zzl() && !BaseGmsClient.this.zzco) {
                BaseGmsClient.this.zza(3, (IInterface) null);
                return;
            }
            if (BaseGmsClient.this.zzcn == null) {
                connectionResult2 = new ConnectionResult(8);
            } else {
                connectionResult2 = BaseGmsClient.this.zzcn;
            }
            BaseGmsClient.this.zzce.onReportServiceBinding(connectionResult2);
            BaseGmsClient.this.onConnectionFailed(connectionResult2);
        }

        private static void zza(Message message) {
            zzc zzcVar = (zzc) message.obj;
            zzcVar.zzn();
            zzcVar.unregister();
        }

        private static boolean zzb(Message message) {
            return message.what == 2 || message.what == 1 || message.what == 7;
        }
    }

    protected final class zzg extends zza {
        public zzg(int i, Bundle bundle) {
            super(i, null);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        protected final void zza(ConnectionResult connectionResult) {
            BaseGmsClient.this.zzce.onReportServiceBinding(connectionResult);
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        protected final boolean zzm() {
            BaseGmsClient.this.zzce.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
            return true;
        }
    }

    protected abstract class zzc<TListener> {
        private TListener zzct;
        private boolean zzcu = false;

        public zzc(TListener tlistener) {
            this.zzct = tlistener;
        }

        protected abstract void zza(TListener tlistener);

        protected abstract void zzn();

        public final void zzo() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.zzct;
                if (this.zzcu) {
                    String strValueOf = String.valueOf(this);
                    Log.w("GmsClient", new StringBuilder(String.valueOf(strValueOf).length() + 47).append("Callback proxy ").append(strValueOf).append(" being reused. This is not safe.").toString());
                }
            }
            if (tlistener != null) {
                try {
                    zza(tlistener);
                } catch (RuntimeException e) {
                    zzn();
                    throw e;
                }
            } else {
                zzn();
            }
            synchronized (this) {
                this.zzcu = true;
            }
            unregister();
        }

        public final void unregister() {
            removeListener();
            synchronized (BaseGmsClient.this.zzcg) {
                BaseGmsClient.this.zzcg.remove(this);
            }
        }

        public final void removeListener() {
            synchronized (this) {
                this.zzct = null;
            }
        }
    }

    public static final class zzd extends IGmsCallbacks.zza {
        private BaseGmsClient zzcv;
        private final int zzcw;

        public zzd(BaseGmsClient baseGmsClient, int i) {
            this.zzcv = baseGmsClient;
            this.zzcw = i;
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        public final void zza(int i, Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        public final void onPostInitComplete(int i, IBinder iBinder, Bundle bundle) {
            Preconditions.checkNotNull(this.zzcv, "onPostInitComplete can be called only once per call to getRemoteService");
            this.zzcv.onPostInitHandler(i, iBinder, bundle, this.zzcw);
            this.zzcv = null;
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        public final void zza(int i, IBinder iBinder, com.google.android.gms.common.internal.zzb zzbVar) {
            Preconditions.checkNotNull(this.zzcv, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
            Preconditions.checkNotNull(zzbVar);
            this.zzcv.zza(zzbVar);
            onPostInitComplete(i, iBinder, zzbVar.zzcz);
        }
    }

    public final class zze implements ServiceConnection {
        private final int zzcw;

        public zze(int i) {
            this.zzcw = i;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IGmsServiceBroker zzaVar;
            if (iBinder == null) {
                BaseGmsClient.this.zzb(16);
                return;
            }
            synchronized (BaseGmsClient.this.zzcc) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                if (iBinder == null) {
                    zzaVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGmsServiceBroker)) {
                        zzaVar = (IGmsServiceBroker) iInterfaceQueryLocalInterface;
                    } else {
                        zzaVar = new IGmsServiceBroker.Stub.zza(iBinder);
                    }
                }
                baseGmsClient.zzcd = zzaVar;
            }
            BaseGmsClient.this.zza(0, (Bundle) null, this.zzcw);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (BaseGmsClient.this.zzcc) {
                BaseGmsClient.this.zzcd = null;
            }
            BaseGmsClient.this.mHandler.sendMessage(BaseGmsClient.this.mHandler.obtainMessage(6, this.zzcw, 1));
        }
    }

    protected final class zzf extends zza {
        private final IBinder zzcx;

        public zzf(int i, IBinder iBinder, Bundle bundle) {
            super(i, bundle);
            this.zzcx = iBinder;
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        protected final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.zzck != null) {
                BaseGmsClient.this.zzck.onConnectionFailed(connectionResult);
            }
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        protected final boolean zzm() {
            try {
                String interfaceDescriptor = this.zzcx.getInterfaceDescriptor();
                if (!BaseGmsClient.this.getServiceDescriptor().equals(interfaceDescriptor)) {
                    String serviceDescriptor = BaseGmsClient.this.getServiceDescriptor();
                    Log.e("GmsClient", new StringBuilder(String.valueOf(serviceDescriptor).length() + 34 + String.valueOf(interfaceDescriptor).length()).append("service descriptor mismatch: ").append(serviceDescriptor).append(" vs. ").append(interfaceDescriptor).toString());
                    return false;
                }
                IInterface iInterfaceCreateServiceInterface = BaseGmsClient.this.createServiceInterface(this.zzcx);
                if (iInterfaceCreateServiceInterface == null || !(BaseGmsClient.this.zza(2, 4, iInterfaceCreateServiceInterface) || BaseGmsClient.this.zza(3, 4, iInterfaceCreateServiceInterface))) {
                    return false;
                }
                BaseGmsClient.this.zzcn = null;
                Bundle connectionHint = BaseGmsClient.this.getConnectionHint();
                if (BaseGmsClient.this.zzcj == null) {
                    return true;
                }
                BaseGmsClient.this.zzcj.onConnected(connectionHint);
                return true;
            } catch (RemoteException unused) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }
    }

    private abstract class zza extends zzc<Boolean> {
        private final int statusCode;
        private final Bundle zzcr;

        protected zza(int i, Bundle bundle) {
            super(true);
            this.statusCode = i;
            this.zzcr = bundle;
        }

        protected abstract void zza(ConnectionResult connectionResult);

        protected abstract boolean zzm();

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zzc
        protected final void zzn() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zzc
        protected final /* synthetic */ void zza(Boolean bool) {
            if (bool != null) {
                int i = this.statusCode;
                if (i == 0) {
                    if (zzm()) {
                        return;
                    }
                    BaseGmsClient.this.zza(1, (IInterface) null);
                    zza(new ConnectionResult(8, null));
                    return;
                }
                if (i != 10) {
                    BaseGmsClient.this.zza(1, (IInterface) null);
                    Bundle bundle = this.zzcr;
                    zza(new ConnectionResult(this.statusCode, bundle != null ? (PendingIntent) bundle.getParcelable(BaseGmsClient.KEY_PENDING_INTENT) : null));
                    return;
                }
                BaseGmsClient.this.zza(1, (IInterface) null);
                throw new IllegalStateException(String.format("A fatal developer error has occurred. Class name: %s. Start service action: %s. Service Descriptor: %s. ", getClass().getSimpleName(), BaseGmsClient.this.getStartServiceAction(), BaseGmsClient.this.getServiceDescriptor()));
            }
            BaseGmsClient.this.zza(1, (IInterface) null);
        }
    }

    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this.mLock = new Object();
        this.zzcc = new Object();
        this.zzcg = new ArrayList<>();
        this.zzci = 1;
        this.zzcn = null;
        this.zzco = false;
        this.zzcp = null;
        this.zzcq = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.zzbz = (Looper) Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzca = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcb = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.mHandler = new zzb(looper);
        this.zzcl = i;
        this.zzcj = baseConnectionCallbacks;
        this.zzck = baseOnConnectionFailedListener;
        this.zzcm = str;
    }

    protected BaseGmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.mLock = new Object();
        this.zzcc = new Object();
        this.zzcg = new ArrayList<>();
        this.zzci = 1;
        this.zzcn = null;
        this.zzco = false;
        this.zzcp = null;
        this.zzcq = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.mHandler = (Handler) Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzbz = handler.getLooper();
        this.zzca = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcb = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzcl = i;
        this.zzcj = baseConnectionCallbacks;
        this.zzck = baseOnConnectionFailedListener;
        this.zzcm = null;
    }

    private final String zzj() {
        String str = this.zzcm;
        return str == null ? this.mContext.getClass().getName() : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(com.google.android.gms.common.internal.zzb zzbVar) {
        this.zzcp = zzbVar;
    }

    public final Feature[] getAvailableFeatures() {
        com.google.android.gms.common.internal.zzb zzbVar = this.zzcp;
        if (zzbVar == null) {
            return null;
        }
        return zzbVar.zzda;
    }

    protected void onConnectedLocked(T t) {
        this.zzbv = System.currentTimeMillis();
    }

    protected void onConnectionSuspended(int i) {
        this.zzbt = i;
        this.zzbu = System.currentTimeMillis();
    }

    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzbw = connectionResult.getErrorCode();
        this.zzbx = System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(int i, T t) {
        zzh zzhVar;
        zzh zzhVar2;
        Preconditions.checkArgument((i == 4) == (t != null));
        synchronized (this.mLock) {
            this.zzci = i;
            this.zzcf = t;
            onSetConnectState(i, t);
            if (i != 1) {
                if (i == 2 || i == 3) {
                    if (this.zzch != null && (zzhVar2 = this.zzby) != null) {
                        String strZzt = zzhVar2.zzt();
                        String packageName = this.zzby.getPackageName();
                        Log.e("GmsClient", new StringBuilder(String.valueOf(strZzt).length() + 70 + String.valueOf(packageName).length()).append("Calling connect() while still connected, missing disconnect() for ").append(strZzt).append(" on ").append(packageName).toString());
                        this.zzca.zza(this.zzby.zzt(), this.zzby.getPackageName(), this.zzby.zzq(), this.zzch, zzj());
                        this.zzcq.incrementAndGet();
                    }
                    this.zzch = new zze(this.zzcq.get());
                    if (this.zzci == 3 && getLocalStartServiceAction() != null) {
                        zzhVar = new zzh(getContext().getPackageName(), getLocalStartServiceAction(), true, 129);
                    } else {
                        zzhVar = new zzh(getStartServicePackage(), getStartServiceAction(), false, 129);
                    }
                    this.zzby = zzhVar;
                    if (!this.zzca.zza(new GmsClientSupervisor.zza(zzhVar.zzt(), this.zzby.getPackageName(), this.zzby.zzq()), this.zzch, zzj())) {
                        String strZzt2 = this.zzby.zzt();
                        String packageName2 = this.zzby.getPackageName();
                        Log.e("GmsClient", new StringBuilder(String.valueOf(strZzt2).length() + 34 + String.valueOf(packageName2).length()).append("unable to connect to service: ").append(strZzt2).append(" on ").append(packageName2).toString());
                        zza(16, (Bundle) null, this.zzcq.get());
                    }
                } else if (i == 4) {
                    onConnectedLocked(t);
                }
            } else if (this.zzch != null) {
                this.zzca.zza(getStartServiceAction(), getStartServicePackage(), 129, this.zzch, zzj());
                this.zzch = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zza(int i, int i2, T t) {
        synchronized (this.mLock) {
            if (this.zzci != i) {
                return false;
            }
            zza(i2, t);
            return true;
        }
    }

    public void checkAvailabilityAndConnect() {
        int iIsGooglePlayServicesAvailable = this.zzcb.isGooglePlayServicesAvailable(this.mContext, getMinApkVersion());
        if (iIsGooglePlayServicesAvailable != 0) {
            zza(1, (IInterface) null);
            triggerNotAvailable(new LegacyClientCallbackAdapter(), iIsGooglePlayServicesAvailable, null);
        } else {
            connect(new LegacyClientCallbackAdapter());
        }
    }

    public void connect(ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzce = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        zza(2, (IInterface) null);
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzci == 4;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            int i = this.zzci;
            z = i == 2 || i == 3;
        }
        return z;
    }

    private final boolean zzk() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzci == 3;
        }
        return z;
    }

    public void disconnect() {
        this.zzcq.incrementAndGet();
        synchronized (this.zzcg) {
            int size = this.zzcg.size();
            for (int i = 0; i < size; i++) {
                this.zzcg.get(i).removeListener();
            }
            this.zzcg.clear();
        }
        synchronized (this.zzcc) {
            this.zzcd = null;
        }
        zza(1, (IInterface) null);
    }

    public void triggerConnectionSuspended(int i) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(6, this.zzcq.get(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(int i) {
        int i2;
        if (zzk()) {
            this.zzco = true;
            i2 = 5;
        } else {
            i2 = 4;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(i2, this.zzcq.get(), 16));
    }

    protected void triggerNotAvailable(ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i, PendingIntent pendingIntent) {
        this.zzce = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, this.zzcq.get(), i, pendingIntent));
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzbz;
    }

    public Feature[] getApiFeatures() {
        return zzbs;
    }

    protected Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    protected void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1, i2, -1, new zzf(i, iBinder, bundle)));
    }

    protected final void zza(int i, Bundle bundle, int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzg(i, null)));
    }

    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T getService() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzci == 5) {
                throw new DeadObjectException();
            }
            checkConnected();
            Preconditions.checkState(this.zzcf != null, "Client is connected but service is null");
            t = this.zzcf;
        }
        return t;
    }

    public void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzcl);
        getServiceRequest.zzdh = this.mContext.getPackageName();
        getServiceRequest.zzdk = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzdj = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn()) {
            getServiceRequest.zzdl = getAccount() != null ? getAccount() : new Account("<<default account>>", AccountType.GOOGLE);
            if (iAccountAccessor != null) {
                getServiceRequest.zzdi = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.zzdl = getAccount();
        }
        getServiceRequest.zzdm = zzbs;
        getServiceRequest.zzdn = getApiFeatures();
        try {
            synchronized (this.zzcc) {
                IGmsServiceBroker iGmsServiceBroker = this.zzcd;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzcq.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(1);
        } catch (RemoteException e2) {
            e = e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzcq.get());
        } catch (SecurityException e3) {
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzcq.get());
        }
    }

    public void onUserSignOut(SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected Set<Scope> getScopes() {
        return Collections.EMPTY_SET;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        T t;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.mLock) {
            i = this.zzci;
            t = this.zzcf;
        }
        synchronized (this.zzcc) {
            iGmsServiceBroker = this.zzcd;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        if (i == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i == 4) {
            printWriter.print("CONNECTED");
        } else if (i == 5) {
            printWriter.print("DISCONNECTING");
        } else {
            printWriter.print("UNKNOWN");
        }
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzbv > 0) {
            PrintWriter printWriterAppend = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzbv;
            String str2 = simpleDateFormat.format(new Date(this.zzbv));
            printWriterAppend.println(new StringBuilder(String.valueOf(str2).length() + 21).append(j).append(" ").append(str2).toString());
        }
        if (this.zzbu > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i2 = this.zzbt;
            if (i2 == 1) {
                printWriter.append("CAUSE_SERVICE_DISCONNECTED");
            } else if (i2 == 2) {
                printWriter.append("CAUSE_NETWORK_LOST");
            } else {
                printWriter.append((CharSequence) String.valueOf(i2));
            }
            PrintWriter printWriterAppend2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzbu;
            String str3 = simpleDateFormat.format(new Date(this.zzbu));
            printWriterAppend2.println(new StringBuilder(String.valueOf(str3).length() + 21).append(j2).append(" ").append(str3).toString());
        }
        if (this.zzbx > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzbw));
            PrintWriter printWriterAppend3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzbx;
            String str4 = simpleDateFormat.format(new Date(this.zzbx));
            printWriterAppend3.println(new StringBuilder(String.valueOf(str4).length() + 21).append(j3).append(" ").append(str4).toString());
        }
    }

    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzcc) {
            IGmsServiceBroker iGmsServiceBroker = this.zzcd;
            if (iGmsServiceBroker == null) {
                return null;
            }
            return iGmsServiceBroker.asBinder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzl() {
        if (this.zzco || TextUtils.isEmpty(getServiceDescriptor()) || TextUtils.isEmpty(getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public String getEndpointPackageName() {
        zzh zzhVar;
        if (isConnected() && (zzhVar = this.zzby) != null) {
            return zzhVar.getPackageName();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }
}
