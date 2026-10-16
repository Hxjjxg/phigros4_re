package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/* JADX INFO: renamed from: com.unity3d.player.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0053n {
    private static C0053n d;
    private AssetPackManager a;
    private HashSet b;
    private Object c;

    private C0053n(Context context) {
        if (d != null) {
            throw new RuntimeException("AssetPackManagerWrapper should be created only once. Use getInstance() instead.");
        }
        this.a = AssetPackManagerFactory.getInstance(context);
        this.b = new HashSet();
    }

    public static C0053n a(Context context) {
        if (d == null) {
            d = new C0053n(context);
        }
        return d;
    }

    public final Object a(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        C0041h c0041h = new C0041h(this, iAssetPackManagerDownloadStatusCallback, Looper.myLooper());
        this.a.registerListener(c0041h);
        return c0041h;
    }

    public final String a(String str) {
        AssetPackLocation packLocation = this.a.getPackLocation(str);
        return packLocation == null ? "" : packLocation.assetsPath();
    }

    public final void a(Activity activity, IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
        this.a.showCellularDataConfirmation(activity).addOnSuccessListener(new C0045j(iAssetPackManagerMobileDataConfirmationCallback));
    }

    public final void a(Object obj) {
        if (obj instanceof C0041h) {
            this.a.unregisterListener((C0041h) obj);
        }
    }

    public final void a(String[] strArr) {
        this.a.cancel(Arrays.asList(strArr));
    }

    public final void a(String[] strArr, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        for (String str : strArr) {
            this.a.getPackStates(Collections.singletonList(str)).addOnCompleteListener(new C0047k(str, iAssetPackManagerDownloadStatusCallback));
        }
    }

    public final void a(String[] strArr, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        this.a.getPackStates(Arrays.asList(strArr)).addOnCompleteListener(new C0051m(strArr, iAssetPackManagerStatusQueryCallback));
    }

    public final void b(String str) {
        this.a.removePack(str);
    }
}
