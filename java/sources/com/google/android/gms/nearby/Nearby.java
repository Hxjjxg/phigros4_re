package com.google.android.gms.nearby;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.PermissionChecker;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.nearby.zzbd;
import com.google.android.gms.internal.nearby.zzca;
import com.google.android.gms.internal.nearby.zze;
import com.google.android.gms.internal.nearby.zzg;
import com.google.android.gms.internal.nearby.zzhe;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.messages.Messages;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.internal.zzak;
import com.google.android.gms.nearby.messages.internal.zzbi;
import com.google.android.gms.nearby.messages.internal.zzby;
import com.google.android.gms.nearby.messages.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class Nearby {

    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> CONNECTIONS_API = new Api<>("Nearby.CONNECTIONS_API", zzca.CLIENT_BUILDER, zzca.CLIENT_KEY);

    @Deprecated
    public static final Connections Connections = new zzca();

    @Deprecated
    public static final Api<MessagesOptions> MESSAGES_API = new Api<>("Nearby.MESSAGES_API", zzbi.CLIENT_BUILDER, zzbi.CLIENT_KEY);

    @Deprecated
    public static final Messages Messages = zzbi.zzij;
    private static final zzd zze = new zzby();
    private static final Api<Api.ApiOptions.NoOptions> zzf = new Api<>("Nearby.BOOTSTRAP_API", zzg.CLIENT_BUILDER, zzg.CLIENT_KEY);
    private static final zze zzg = new zzg();

    private Nearby() {
    }

    public static final ConnectionsClient getConnectionsClient(Activity activity) {
        Preconditions.checkNotNull(activity, "Activity must not be null");
        return new zzbd(activity);
    }

    public static final ConnectionsClient getConnectionsClient(Context context) {
        Preconditions.checkNotNull(context, "Context must not be null");
        return new zzbd(context);
    }

    public static final MessagesClient getMessagesClient(Activity activity) {
        Preconditions.checkNotNull(activity, "Activity must not be null");
        return new zzak(activity, (MessagesOptions) null);
    }

    public static final MessagesClient getMessagesClient(Activity activity, MessagesOptions messagesOptions) {
        Preconditions.checkNotNull(activity, "Activity must not be null");
        Preconditions.checkNotNull(messagesOptions, "Options must not be null");
        return new zzak(activity, messagesOptions);
    }

    public static final MessagesClient getMessagesClient(Context context) {
        Preconditions.checkNotNull(context, "Context must not be null");
        return new zzak(context, (MessagesOptions) null);
    }

    public static final MessagesClient getMessagesClient(Context context, MessagesOptions messagesOptions) {
        Preconditions.checkNotNull(context, "Context must not be null");
        Preconditions.checkNotNull(messagesOptions, "Options must not be null");
        return new zzak(context, messagesOptions);
    }

    public static boolean zza(Context context) {
        if (PermissionChecker.checkCallingOrSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0) {
            return zzhe.zza(context.getContentResolver(), "gms:nearby:requires_gms_check", true);
        }
        return true;
    }
}
