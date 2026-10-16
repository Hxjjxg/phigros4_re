package com.google.android.gms.internal.games;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzej {
    private Handler zzkr;
    private boolean zzks;
    private final Object zzkq = new Object();
    private HashMap<String, AtomicInteger> zzkt = new HashMap<>();
    private int zzku = 1000;

    public zzej(Looper looper, int i) {
        this.zzkr = new Handler(looper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzbl() {
        synchronized (this.zzkq) {
            this.zzks = false;
            flush();
        }
    }

    public final void flush() {
        synchronized (this.zzkq) {
            for (Map.Entry<String, AtomicInteger> entry : this.zzkt.entrySet()) {
                zzf(entry.getKey(), entry.getValue().get());
            }
            this.zzkt.clear();
        }
    }

    protected abstract void zzf(String str, int i);

    public final void zzg(String str, int i) {
        synchronized (this.zzkq) {
            if (!this.zzks) {
                this.zzks = true;
                this.zzkr.postDelayed(new zzek(this), this.zzku);
            }
            AtomicInteger atomicInteger = this.zzkt.get(str);
            if (atomicInteger == null) {
                atomicInteger = new AtomicInteger();
                this.zzkt.put(str, atomicInteger);
            }
            atomicInteger.addAndGet(i);
        }
    }
}
