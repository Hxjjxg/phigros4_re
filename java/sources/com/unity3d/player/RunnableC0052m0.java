package com.unity3d.player;

import android.content.Context;
import java.util.concurrent.Semaphore;

/* JADX INFO: renamed from: com.unity3d.player.m0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0052m0 implements Runnable {
    final /* synthetic */ UnityPlayer a;
    final /* synthetic */ String b;
    final /* synthetic */ int c;
    final /* synthetic */ boolean d;
    final /* synthetic */ boolean e;
    final /* synthetic */ boolean f;
    final /* synthetic */ boolean g;
    final /* synthetic */ String h;
    final /* synthetic */ int i;
    final /* synthetic */ boolean j;
    final /* synthetic */ boolean k;
    final /* synthetic */ Semaphore l;
    final /* synthetic */ UnityPlayer m;

    RunnableC0052m0(UnityPlayer unityPlayer, UnityPlayer unityPlayer2, String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5, boolean z6, Semaphore semaphore) {
        this.m = unityPlayer;
        this.a = unityPlayer2;
        this.b = str;
        this.c = i;
        this.d = z;
        this.e = z2;
        this.f = z3;
        this.g = z4;
        this.h = str2;
        this.i = i2;
        this.j = z5;
        this.k = z6;
        this.l = semaphore;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            try {
                UnityPlayer unityPlayer = this.m;
                if (unityPlayer.mSoftInput != null) {
                    unityPlayer.dismissSoftInput();
                }
                UnityPlayer unityPlayer2 = this.m;
                int iA = SoftInputProvider.a();
                Context context = this.m.mContext;
                UnityPlayer unityPlayer3 = this.a;
                unityPlayer2.mSoftInput = com.unity3d.player.a.a.a(iA) != 2 ? new C0032c0(context, unityPlayer3) : new X(context, unityPlayer3);
                this.m.mSoftInput.a(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
                S s = this.m.mSoftInput;
                s.f = new C0050l0(this);
                s.e();
                this.m.nativeReportKeyboardConfigChanged();
            } catch (Exception e) {
                AbstractC0077z.Log(6, "Exception when opening Softinput " + e);
            }
        } finally {
            this.l.release();
        }
    }
}
