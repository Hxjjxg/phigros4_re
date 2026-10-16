package com.unity3d.player;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/* JADX INFO: loaded from: classes.dex */
final class A extends ConnectivityManager.NetworkCallback {
    final /* synthetic */ NetworkConnectivityNougat a;

    A(NetworkConnectivityNougat networkConnectivityNougat) {
        this.a = networkConnectivityNougat;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onAvailable(Network network) {
        super.onAvailable(network);
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        NetworkConnectivityNougat networkConnectivityNougat;
        int i;
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasTransport(0)) {
            networkConnectivityNougat = this.a;
            i = 1;
        } else {
            networkConnectivityNougat = this.a;
            i = 2;
        }
        networkConnectivityNougat.b = i;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLost(Network network) {
        super.onLost(network);
        this.a.b = 0;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onUnavailable() {
        super.onUnavailable();
        this.a.b = 0;
    }
}
