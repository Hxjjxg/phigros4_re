package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.AbstractDataBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantBuffer extends AbstractDataBuffer<Participant> {
    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    public final Participant get(int i) {
        return new ParticipantRef(this.mDataHolder, i);
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    public final /* synthetic */ Object get(int i) {
        throw new NoSuchMethodError();
    }
}
