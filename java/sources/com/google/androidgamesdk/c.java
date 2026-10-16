package com.google.androidgamesdk;

import android.view.Window;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ SwappyDisplayManager b;

    c(SwappyDisplayManager swappyDisplayManager, int i) {
        this.b = swappyDisplayManager;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Window window = this.b.mActivity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.preferredDisplayModeId = this.a;
        window.setAttributes(attributes);
    }
}
