package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0003zaa> zack;

    public zaa(Activity activity) {
        this(C0003zaa.zaa(activity));
    }

    private zaa(C0003zaa c0003zaa) {
        this.zack = new WeakReference<>(c0003zaa);
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0003zaa c0003zaa = this.zack.get();
        if (c0003zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c0003zaa.zaa(runnable);
        return this;
    }

    /* JADX INFO: renamed from: com.google.android.gms.common.api.internal.zaa$zaa, reason: collision with other inner class name */
    static class C0003zaa extends LifecycleCallback {
        private List<Runnable> zacl;

        /* JADX INFO: Access modifiers changed from: private */
        public static C0003zaa zaa(Activity activity) {
            C0003zaa c0003zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                c0003zaa = (C0003zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0003zaa.class);
                if (c0003zaa == null) {
                    c0003zaa = new C0003zaa(fragment);
                }
            }
            return c0003zaa;
        }

        private C0003zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacl = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacl.add(runnable);
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacl;
                this.zacl = new ArrayList();
            }
            Iterator<Runnable> it = list.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }
}
