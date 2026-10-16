package com.unity3d.player;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.OnSuccessListener;

/* JADX INFO: renamed from: com.unity3d.player.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0045j implements OnSuccessListener {
    private IAssetPackManagerMobileDataConfirmationCallback a;
    private Looper b = Looper.myLooper();

    public C0045j(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
        this.a = iAssetPackManagerMobileDataConfirmationCallback;
    }

    @Override // com.google.android.gms.tasks.OnSuccessListener
    public final void onSuccess(Object obj) {
        Integer num = (Integer) obj;
        if (this.a != null) {
            new Handler(this.b).post(new RunnableC0043i(this.a, num.intValue() == -1));
        }
    }
}
