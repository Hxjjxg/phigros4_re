package com.unity3d.player;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.assetpacks.AssetPackException;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStates;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: renamed from: com.unity3d.player.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0047k implements OnCompleteListener {
    private IAssetPackManagerDownloadStatusCallback a;
    private Looper b = Looper.myLooper();
    private String c;

    public C0047k(String str, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        this.a = iAssetPackManagerDownloadStatusCallback;
        this.c = str;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task task) {
        int errorCode;
        try {
            AssetPackStates assetPackStates = (AssetPackStates) task.getResult();
            Map mapPackStates = assetPackStates.packStates();
            if (mapPackStates.size() == 0) {
                return;
            }
            for (AssetPackState assetPackState : mapPackStates.values()) {
                if (assetPackState.errorCode() != 0 || assetPackState.status() == 4 || assetPackState.status() == 5 || assetPackState.status() == 0) {
                    String strName = assetPackState.name();
                    int iStatus = assetPackState.status();
                    int iErrorCode = assetPackState.errorCode();
                    long j = assetPackStates.totalBytes();
                    new Handler(this.b).post(new RunnableC0039g(Collections.singleton(this.a), strName, iStatus, j, iStatus == 4 ? j : 0L, 0, iErrorCode));
                } else {
                    C0053n c0053n = C0053n.d;
                    String strName2 = assetPackState.name();
                    IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback = this.a;
                    Looper looper = this.b;
                    c0053n.getClass();
                    synchronized (C0053n.d) {
                        Object obj = c0053n.c;
                        if (obj == null) {
                            C0041h c0041h = new C0041h(c0053n, iAssetPackManagerDownloadStatusCallback, looper);
                            c0053n.a.registerListener(c0041h);
                            c0053n.c = c0041h;
                        } else {
                            ((C0041h) obj).a(iAssetPackManagerDownloadStatusCallback);
                        }
                        c0053n.b.add(strName2);
                        c0053n.a.fetch(Collections.singletonList(strName2));
                    }
                }
            }
        } catch (RuntimeExecutionException e) {
            e = e;
            String str = this.c;
            while (true) {
                if (e instanceof AssetPackException) {
                    errorCode = e.getErrorCode();
                    break;
                }
                e = e.getCause();
                if (e == null) {
                    errorCode = -100;
                    break;
                }
            }
            new Handler(this.b).post(new RunnableC0039g(Collections.singleton(this.a), str, 0, 0L, 0L, 0, errorCode));
        }
    }
}
