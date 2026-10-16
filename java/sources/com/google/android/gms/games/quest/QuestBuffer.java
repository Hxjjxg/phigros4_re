package com.google.android.gms.games.quest;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.EntityBuffer;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class QuestBuffer extends EntityBuffer<Quest> {
    public QuestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.EntityBuffer
    protected final /* synthetic */ Quest getEntry(int i, int i2) {
        return new QuestRef(this.mDataHolder, i, i2);
    }

    @Override // com.google.android.gms.common.data.EntityBuffer
    protected final String getPrimaryDataMarkerColumn() {
        return "external_quest_id";
    }
}
