package com.google.android.gms.games.multiplayer.realtime;

/* JADX INFO: loaded from: classes.dex */
public abstract class RoomUpdateCallback implements RoomUpdateListener {
    @Override // com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener
    public abstract void onJoinedRoom(int i, Room room);

    @Override // com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener
    public abstract void onLeftRoom(int i, String str);

    @Override // com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener
    public abstract void onRoomConnected(int i, Room room);

    @Override // com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener
    public abstract void onRoomCreated(int i, Room room);
}
