package com.google.android.gms.nearby.connection;

/* JADX INFO: loaded from: classes.dex */
public abstract class EndpointDiscoveryCallback {
    public abstract void onEndpointFound(String str, DiscoveredEndpointInfo discoveredEndpointInfo);

    public abstract void onEndpointLost(String str);
}
