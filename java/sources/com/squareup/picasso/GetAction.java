package com.squareup.picasso;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
class GetAction extends Action<Void> {
    @Override // com.squareup.picasso.Action
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
    }

    @Override // com.squareup.picasso.Action
    public void error() {
    }

    GetAction(Picasso picasso, Request request, boolean z, String str) {
        super(picasso, null, request, z, false, 0, null, str);
    }
}
