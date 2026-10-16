package com.google.android.gms.games.internal.experience;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;

/* JADX INFO: loaded from: classes.dex */
public interface ExperienceEvent extends Parcelable, Freezable<ExperienceEvent> {
    Game getGame();

    Uri getIconImageUri();

    @Deprecated
    String getIconImageUrl();

    int getType();

    String zzbm();

    String zzbn();

    String zzbo();

    long zzbp();

    long zzbq();

    long zzbr();

    int zzbs();
}
