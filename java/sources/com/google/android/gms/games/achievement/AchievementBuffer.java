package com.google.android.gms.games.achievement;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class AchievementBuffer extends AbstractDataBuffer<Achievement> {
    public AchievementBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    public final Achievement get(int i) {
        return new AchievementRef(this.mDataHolder, i);
    }
}
