package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class zzk {
    private static zzk zzal;
    private final Map<String, Set<ListenerHolder.ListenerKey<?>>> zzam = new HashMap();
    private final Set<ListenerHolder.ListenerKey<?>> zzan = new HashSet();
    private final Map<String, ListenerHolder<String>> zzao = new HashMap();

    private zzk() {
    }

    public static synchronized zzk zza() {
        if (zzal == null) {
            zzal = new zzk();
        }
        return zzal;
    }

    private final void zza(String str, ListenerHolder.ListenerKey<?> listenerKey) {
        Set<ListenerHolder.ListenerKey<?>> hashSet = this.zzam.get(str);
        if (hashSet == null) {
            hashSet = new HashSet<>();
            this.zzam.put(str, hashSet);
        }
        hashSet.add(listenerKey);
    }

    public final synchronized <T> ListenerHolder<T> zza(GoogleApi googleApi, T t, String str) {
        ListenerHolder<T> listenerHolderRegisterListener;
        listenerHolderRegisterListener = googleApi.registerListener(t, str);
        zza(str, listenerHolderRegisterListener.getListenerKey());
        return listenerHolderRegisterListener;
    }

    public final synchronized ListenerHolder<String> zza(GoogleApi googleApi, String str, String str2) {
        if (this.zzao.containsKey(str) && this.zzao.get(str).hasListener()) {
            return this.zzao.get(str);
        }
        ListenerHolder<String> listenerHolderRegisterListener = googleApi.registerListener(str, str2);
        zza(str2, listenerHolderRegisterListener.getListenerKey());
        this.zzao.put(str, listenerHolderRegisterListener);
        return listenerHolderRegisterListener;
    }

    public final synchronized Task<Boolean> zza(GoogleApi googleApi, ListenerHolder.ListenerKey<?> listenerKey) {
        this.zzan.remove(listenerKey);
        return googleApi.doUnregisterEventListener(listenerKey);
    }

    public final synchronized Task<Void> zza(GoogleApi googleApi, RegisterListenerMethod registerListenerMethod, UnregisterListenerMethod unregisterListenerMethod) {
        this.zzan.add(registerListenerMethod.getListenerKey());
        return googleApi.doRegisterEventListener(registerListenerMethod, unregisterListenerMethod).addOnFailureListener(new zzl(this, registerListenerMethod));
    }

    public final synchronized void zza(GoogleApi googleApi, String str) {
        Set<ListenerHolder.ListenerKey<?>> set = this.zzam.get(str);
        if (set == null) {
            return;
        }
        for (ListenerHolder.ListenerKey<?> listenerKey : set) {
            if (this.zzan.contains(listenerKey)) {
                zza(googleApi, listenerKey);
            }
        }
        this.zzam.remove(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final synchronized <T> ListenerHolder.ListenerKey<T> zzb(GoogleApi googleApi, T t, String str) {
        if (t instanceof String) {
            return (ListenerHolder.ListenerKey<T>) zza(googleApi, (String) t, str).getListenerKey();
        }
        return ListenerHolders.createListenerKey(t, str);
    }
}
