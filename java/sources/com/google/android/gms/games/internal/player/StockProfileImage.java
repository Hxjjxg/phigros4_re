package com.google.android.gms.games.internal.player;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;

/* JADX INFO: loaded from: classes.dex */
public interface StockProfileImage extends Parcelable, Freezable<StockProfileImage> {
    String getImageUrl();

    Uri zzbz();
}
