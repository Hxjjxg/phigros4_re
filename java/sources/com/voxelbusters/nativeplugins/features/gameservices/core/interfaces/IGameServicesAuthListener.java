package com.voxelbusters.nativeplugins.features.gameservices.core.interfaces;

import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.User;

/* JADX INFO: loaded from: classes.dex */
public interface IGameServicesAuthListener {
    void onConnected(User user, String str);

    void onConnectionFailure();

    void onConnectionSuspended();

    void onDisConnected();

    void onReceivingExternalAuthenticationDetails(String str, String str2);

    void onSignOut(String str);
}
