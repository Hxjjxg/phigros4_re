package com.voxelbusters.nativeplugins.features.gameservices.core;

import android.content.Context;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BasicGameService implements IGameServices {
    protected Context context;
    protected ArrayList<IGameServicesAuthListener> authListeners = new ArrayList<>();
    protected ArrayList<IGameServicesPlayerListener> playerListeners = new ArrayList<>();
    protected eGameServiceState state = eGameServiceState.NONE;

    public enum eGameServiceState {
        NONE,
        SIGNING_IN,
        SIGNING_OUT,
        RESOLVING_ERROR
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public AchievementData getAchievementData(String str) {
        return null;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public boolean isAvailable() {
        return false;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public boolean isSignedIn() {
        return false;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void register(boolean z) {
    }

    public BasicGameService(Context context) {
        this.context = context;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void signIn() {
        this.state = eGameServiceState.SIGNING_IN;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void signOut() {
        this.state = eGameServiceState.SIGNING_OUT;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void reportProgress(String str, String str2, int i, boolean z) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadAllAchievements() {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadUserAchievements() {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void showAchievementsUi() {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void reportScore(String str, String str2, long j, boolean z) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void showLeaderboardsUi(String str, int i) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadUsers(String str, String[] strArr) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void requestLocalPlayerDetails() {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadLocalPlayerFriends(boolean z) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadPlayerCenteredScores(String str, String str2, int i, int i2, int i3) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadTopScores(String str, String str2, int i, int i2, int i3) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadMoreScrores(String str, String str2, int i, int i2) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadProfileImage(String str, String str2) {
        throw new UnsupportedOperationException("Implement in sub class");
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void addListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener) {
        if (iGameServicesAuthListener != null && !this.authListeners.contains(iGameServicesAuthListener)) {
            this.authListeners.add(iGameServicesAuthListener);
        }
        if (iGameServicesPlayerListener == null || this.playerListeners.contains(iGameServicesPlayerListener)) {
            return;
        }
        this.playerListeners.add(iGameServicesPlayerListener);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void removeListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener) {
        if (iGameServicesAuthListener != null && this.authListeners.contains(iGameServicesAuthListener)) {
            this.authListeners.remove(iGameServicesAuthListener);
        }
        if (iGameServicesPlayerListener == null || !this.playerListeners.contains(iGameServicesPlayerListener)) {
            return;
        }
        this.playerListeners.remove(iGameServicesPlayerListener);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void requestAuthToken(String str) {
        throw new UnsupportedOperationException("Implement in sub class");
    }
}
