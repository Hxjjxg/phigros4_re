package com.google.android.gms.nearby.messages;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;

/* JADX INFO: loaded from: classes.dex */
public abstract class MessagesClient extends GoogleApi<MessagesOptions> {
    protected MessagesClient(Activity activity, Api<MessagesOptions> api, MessagesOptions messagesOptions, GoogleApi.Settings settings) {
        super(activity, api, messagesOptions, settings);
    }

    protected MessagesClient(Context context, Api<MessagesOptions> api, MessagesOptions messagesOptions, GoogleApi.Settings settings) {
        super(context, api, messagesOptions, settings);
    }

    public abstract void handleIntent(Intent intent, MessageListener messageListener);

    public abstract Task<Void> publish(Message message);

    public abstract Task<Void> publish(Message message, PublishOptions publishOptions);

    public abstract Task<Void> registerStatusCallback(StatusCallback statusCallback);

    public abstract Task<Void> subscribe(PendingIntent pendingIntent);

    public abstract Task<Void> subscribe(PendingIntent pendingIntent, SubscribeOptions subscribeOptions);

    public abstract Task<Void> subscribe(MessageListener messageListener);

    public abstract Task<Void> subscribe(MessageListener messageListener, SubscribeOptions subscribeOptions);

    public abstract Task<Void> unpublish(Message message);

    public abstract Task<Void> unregisterStatusCallback(StatusCallback statusCallback);

    public abstract Task<Void> unsubscribe(PendingIntent pendingIntent);

    public abstract Task<Void> unsubscribe(MessageListener messageListener);
}
