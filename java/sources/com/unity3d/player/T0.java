package com.unity3d.player;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
final class T0 implements Handler.Callback {
    final /* synthetic */ U0 a;

    T0(U0 u0) {
        this.a = u0;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00e1 A[PHI: r7
  0x00e1: PHI (r7v11 com.unity3d.player.U0) = (r7v7 com.unity3d.player.U0), (r7v14 com.unity3d.player.U0) binds: [B:58:0x00df, B:47:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        U0 u0;
        if (message.what != 2269) {
            return false;
        }
        S0 s0 = (S0) message.obj;
        S0 s1 = S0.h;
        if (s0 == s1) {
            U0 u1 = this.a;
            u1.e--;
            u1.i.executeGLThreadJobs();
            U0 u2 = this.a;
            if (!u2.b) {
                return true;
            }
            if (u2.i.getHaveAndroidWindowSupport() && !this.a.c) {
                return true;
            }
            U0 u3 = this.a;
            int i = u3.h;
            if (i >= 0) {
                if (i == 0) {
                    if (u3.i.getSplashEnabled()) {
                        this.a.i.DisableStaticSplashScreen();
                    }
                    UnityPlayer unityPlayer = this.a.i;
                    if (unityPlayer.mActivity != null && unityPlayer.getAutoReportFullyDrawnEnabled()) {
                        this.a.i.mActivity.reportFullyDrawn();
                    }
                }
                this.a.h--;
            }
            if (!this.a.i.isFinishing() && !this.a.i.nativeRender()) {
                this.a.i.finish();
            }
        } else if (s0 == S0.c) {
            Looper.myLooper().quit();
        } else if (s0 == S0.b) {
            this.a.b = true;
        } else if (s0 == S0.a) {
            this.a.b = false;
        } else if (s0 == S0.d) {
            this.a.c = false;
        } else if (s0 == S0.e) {
            u0 = this.a;
            u0.c = true;
            if (u0.d == 3) {
                u0.i.nativeFocusChanged(true);
                this.a.d = 1;
            }
        } else if (s0 == S0.f) {
            U0 u4 = this.a;
            if (u4.d == 1) {
                u4.i.nativeFocusChanged(false);
            }
            this.a.d = 2;
        } else if (s0 == S0.g) {
            u0 = this.a;
            u0.d = 3;
            if (u0.c) {
                u0.i.nativeFocusChanged(true);
                this.a.d = 1;
            }
        } else if (s0 == S0.i) {
            UnityPlayer unityPlayer2 = this.a.i;
            unityPlayer2.nativeSetLaunchURL(unityPlayer2.getLaunchURL());
        } else if (s0 == S0.j) {
            U0 u5 = this.a;
            u5.i.nativeOrientationChanged(u5.f, u5.g);
        }
        U0 u6 = this.a;
        if (u6.b && u6.e <= 0) {
            Message.obtain(u6.a, 2269, s1).sendToTarget();
            this.a.e++;
        }
        return true;
    }
}
