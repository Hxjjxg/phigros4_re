package com.unity3d.player;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

/* JADX INFO: renamed from: com.unity3d.player.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0059q {
    private final Context a;
    private final AudioManager b;
    private C0055o c;

    public C0059q(Context context) {
        this.a = context;
        this.b = (AudioManager) context.getSystemService("audio");
    }

    public final void a() {
        if (this.c != null) {
            this.a.getContentResolver().unregisterContentObserver(this.c);
            this.c = null;
        }
    }

    public final void a(InterfaceC0057p interfaceC0057p) {
        this.c = new C0055o(new Handler(Looper.getMainLooper()), this.b, interfaceC0057p);
        this.a.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.c);
    }
}
