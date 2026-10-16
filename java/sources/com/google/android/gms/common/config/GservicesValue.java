package com.google.android.gms.common.config;

import android.content.Context;
import android.os.Binder;
import android.os.StrictMode;
import android.util.Log;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class GservicesValue<T> {
    private static final Object sLock = new Object();
    private static zza zzbl;
    private static int zzbm;
    private static Context zzbn;
    private static HashSet<String> zzbo;
    protected final String mKey;
    protected final T zzbp;
    private T zzbq = null;

    /* JADX INFO: Access modifiers changed from: private */
    interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zza(String str, Float f);

        Integer zza(String str, Integer num);
    }

    public static boolean isInitialized() {
        synchronized (sLock) {
        }
        return false;
    }

    protected abstract T zzd(String str);

    private static boolean zzi() {
        synchronized (sLock) {
        }
        return false;
    }

    protected GservicesValue(String str, T t) {
        this.mKey = str;
        this.zzbp = t;
    }

    public void override(T t) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzbq = t;
        synchronized (sLock) {
            zzi();
        }
    }

    public void resetOverride() {
        this.zzbq = null;
    }

    public final T get() {
        T t = this.zzbq;
        if (t != null) {
            return t;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        Object obj = sLock;
        synchronized (obj) {
        }
        synchronized (obj) {
            zzbo = null;
            zzbn = null;
            try {
            } catch (Throwable th) {
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                throw th;
            }
        }
        try {
            T tZzd = zzd(this.mKey);
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return tZzd;
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                T tZzd2 = zzd(this.mKey);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return tZzd2;
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw th2;
            }
        }
    }

    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    public static GservicesValue<Boolean> value(String str, boolean z) {
        return new com.google.android.gms.common.config.zza(str, Boolean.valueOf(z));
    }

    public static GservicesValue<Long> value(String str, Long l) {
        return new zzb(str, l);
    }

    public static GservicesValue<Integer> value(String str, Integer num) {
        return new zzc(str, num);
    }

    public static GservicesValue<Float> value(String str, Float f) {
        return new zzd(str, f);
    }

    public static GservicesValue<String> value(String str, String str2) {
        return new zze(str, str2);
    }
}
