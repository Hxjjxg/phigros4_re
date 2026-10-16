package com.unity3d.player;

import android.content.Context;
import android.net.ConnectivityManager;

/* JADX INFO: loaded from: classes.dex */
public class NetworkConnectivityNougat extends NetworkConnectivity {
    private int b;
    private final ConnectivityManager.NetworkCallback c;

    public NetworkConnectivityNougat(Context context) {
        super(context);
        this.b = 0;
        A a = new A(this);
        this.c = a;
        if (this.a == null) {
            return;
        }
        this.b = super.b();
        this.a.registerDefaultNetworkCallback(a);
    }

    @Override // com.unity3d.player.NetworkConnectivity
    public final void a() {
        ConnectivityManager connectivityManager = this.a;
        if (connectivityManager == null) {
            return;
        }
        connectivityManager.unregisterNetworkCallback(this.c);
    }

    @Override // com.unity3d.player.NetworkConnectivity
    public final int b() {
        return this.b;
    }
}
