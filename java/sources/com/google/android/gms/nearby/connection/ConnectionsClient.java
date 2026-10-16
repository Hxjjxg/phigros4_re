package com.google.android.gms.nearby.connection;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ConnectionsClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    public static int MAX_BYTES_DATA_SIZE = 32768;

    protected ConnectionsClient(Activity activity, Api<Api.ApiOptions.NoOptions> api, GoogleApi.Settings settings) {
        super(activity, (Api<Api.ApiOptions>) api, (Api.ApiOptions) null, settings);
    }

    protected ConnectionsClient(Context context, Api<Api.ApiOptions.NoOptions> api, GoogleApi.Settings settings) {
        super(context, api, (Api.ApiOptions) null, settings);
    }

    public abstract Task<Void> acceptConnection(String str, PayloadCallback payloadCallback);

    public abstract Task<Void> cancelPayload(long j);

    public abstract void disconnectFromEndpoint(String str);

    public abstract Task<Void> rejectConnection(String str);

    public abstract Task<Void> requestConnection(String str, String str2, ConnectionLifecycleCallback connectionLifecycleCallback);

    public abstract Task<Void> sendPayload(String str, Payload payload);

    public abstract Task<Void> sendPayload(List<String> list, Payload payload);

    public abstract Task<Void> startAdvertising(String str, String str2, ConnectionLifecycleCallback connectionLifecycleCallback, AdvertisingOptions advertisingOptions);

    public abstract Task<Void> startDiscovery(String str, EndpointDiscoveryCallback endpointDiscoveryCallback, DiscoveryOptions discoveryOptions);

    public abstract void stopAdvertising();

    public abstract void stopAllEndpoints();

    public abstract void stopDiscovery();
}
