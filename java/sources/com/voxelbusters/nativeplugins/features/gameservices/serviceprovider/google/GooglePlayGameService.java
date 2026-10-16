package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.User;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util.DataConversionUtils;
import com.voxelbusters.nativeplugins.utilities.ApplicationUtility;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.FileUtility;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class GooglePlayGameService extends BasicGameService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int REQUEST_CODE_SIGN_IN_CONNECTION_FAILURE = 1000;
    public static GooglePlayGameService instance;
    private final String TAG;
    GooglePlayAchievements achievements;
    private GoogleApiClient apiService;
    GooglePlayLeaderboards leaderboards;
    IGameServicesPlayerListener playerListener;
    boolean resolveAvailabilityError;
    boolean useCloudServices;

    String getErrorCodeDescription(int i) {
        switch (i) {
            case 0:
                return "The connection was successful";
            case 1:
                return "Google Play services is missing on this device.";
            case 2:
                return "The installed version of Google Play services is out of date.";
            case 3:
                return "The installed version of Google Play services has been disabled on this device.";
            case 4:
                return "The client attempted to connect to the service but the user is not signed in.";
            case 5:
                return "The client attempted to connect to the service with an invalid account name specified.";
            case 6:
                return "Completing the connection requires some form of resolution.";
            case 7:
                return "A network error occurred.";
            case 8:
                return "An internal error occurred.";
            case 9:
                return "The version of the Google Play services installed on this device is not authentic.";
            case 10:
                return "The application is misconfigured.";
            case 11:
                return "The application is not licensed to the user.";
            case 12:
            default:
                return "Unknown Error!";
            case 13:
                return "The connection was canceled.";
            case 14:
                return "The timeout was exceeded while waiting for the connection to complete.";
            case 15:
                return "An interrupt occurred while waiting for the connection complete.";
            case 16:
                return "One of the API components you attempted to connect to is not available.";
            case 17:
                return "The client attempted to connect to the service but the user is not signed in.";
            case 18:
                return "Google Play service is currently being updated on this device.";
            case 19:
                return "Google Play service doesn't have one or more required permissions.";
            case 20:
                return "The current user profile is restricted and cannot use authenticated features.";
        }
    }

    public static GooglePlayGameService getInstance() {
        return instance;
    }

    public static GooglePlayGameService getInstance(Context context) {
        GooglePlayGameService googlePlayGameService = instance;
        return googlePlayGameService != null ? googlePlayGameService : new GooglePlayGameService(context);
    }

    private GooglePlayGameService(Context context) {
        super(context);
        this.TAG = "GooglePlayGameService";
        this.resolveAvailabilityError = false;
        this.useCloudServices = false;
        instance = this;
        this.apiService = null;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void register(boolean z) {
        this.useCloudServices = z;
        this.resolveAvailabilityError = false;
        if (isAvailable()) {
            registerAPIClient();
        }
        this.resolveAvailabilityError = true;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void addListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener) {
        super.addListener(iGameServicesAuthListener, iGameServicesPlayerListener);
    }

    void registerAPIClient() {
        GoogleApiClient.Builder builderAddScope = new GoogleApiClient.Builder(this.context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Games.API, Games.GamesOptions.builder().setShowConnectingPopup(true).build()).addScope(Games.SCOPE_GAMES);
        if (this.useCloudServices) {
            builderAddScope = builderAddScope.addScope(Drive.SCOPE_APPFOLDER);
        }
        this.apiService = builderAddScope.build();
        this.achievements = new GooglePlayAchievements(this.apiService);
        this.leaderboards = new GooglePlayLeaderboards(this.apiService);
        IGameServicesPlayerListener playerListener = getPlayerListener();
        this.playerListener = playerListener;
        if (playerListener != null) {
            this.achievements.setListener(playerListener);
            this.leaderboards.setListener(this.playerListener);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void removeListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener) {
        super.removeListener(iGameServicesAuthListener, iGameServicesPlayerListener);
        if (iGameServicesPlayerListener != null) {
            this.achievements.removeListener(iGameServicesPlayerListener);
            this.leaderboards.removeListener(iGameServicesPlayerListener);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public boolean isAvailable() {
        return ApplicationUtility.isGooglePlayServicesAvailable(this.context, false);
    }

    private boolean isAvailable(boolean z) {
        return ApplicationUtility.isGooglePlayServicesAvailable(this.context, z);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void signIn() {
        if (isAvailable(this.resolveAvailabilityError)) {
            if (getApiService() == null) {
                registerAPIClient();
            }
            super.signIn();
            this.apiService.connect();
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void signOut() {
        if (isAvailable()) {
            super.signOut();
            GoogleApiClient googleApiClient = this.apiService;
            if (googleApiClient != null && googleApiClient.isConnected()) {
                Games.signOut(this.apiService);
                this.apiService.disconnect();
            }
            this.apiService = null;
            onSignOut(null);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public boolean isSignedIn() {
        GoogleApiClient googleApiClient = this.apiService;
        return googleApiClient != null && googleApiClient.isConnected();
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void reportProgress(String str, String str2, int i, boolean z) {
        if (isAvailable()) {
            this.achievements.reportProgress(str, str2, i, z);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadAllAchievements() {
        if (isAvailable()) {
            this.achievements.loadAchievementsDetails(true);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadUserAchievements() {
        if (isAvailable()) {
            this.achievements.loadAchievementsDetails(false);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public AchievementData getAchievementData(String str) {
        if (isAvailable()) {
            return this.achievements.getAchievementData(str);
        }
        return null;
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void showAchievementsUi() {
        if (isAvailable()) {
            this.achievements.showUI(this.context);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void reportScore(String str, String str2, long j, boolean z) {
        if (isAvailable()) {
            this.leaderboards.reportScore(str, str2, j, z);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void showLeaderboardsUi(String str, int i) {
        if (isAvailable()) {
            this.leaderboards.showUI(this.context, str, i);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadPlayerCenteredScores(String str, String str2, int i, int i2, int i3) {
        this.leaderboards.loadPlayerCenteredScores(str, str2, i, i2, i3);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadTopScores(String str, String str2, int i, int i2, int i3) {
        this.leaderboards.loadTopScores(str, str2, i, i2, i3);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadMoreScrores(String str, String str2, int i, int i2) {
        this.leaderboards.loadMoreScores(str, str2, i, i2);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService$1] */
    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadUsers(final String str, String[] strArr) {
        if (isAvailable()) {
            new AsyncTask<String[], Void, ArrayList<User>>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public ArrayList<User> doInBackground(String[]... strArr2) {
                    String[] strArr3 = strArr2[0];
                    ArrayList<User> arrayList = new ArrayList<>();
                    for (String str2 : strArr3) {
                        PlayerBuffer playerBuffer = GooglePlayGameService.this.getPlayerBuffer(Games.Players.loadPlayer(GooglePlayGameService.this.apiService, str2));
                        if (playerBuffer != null) {
                            if (playerBuffer.getCount() > 0) {
                                arrayList.add(DataConversionUtils.getUser(playerBuffer.get(0)));
                            }
                            playerBuffer.release();
                        }
                    }
                    return arrayList;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(ArrayList<User> arrayList) {
                    String str2 = arrayList == null ? "Error loading user friend details." : null;
                    if (GooglePlayGameService.this.playerListener != null) {
                        GooglePlayGameService.this.playerListener.onLoadUserProfiles(str, arrayList, str2);
                    }
                }
            }.execute(strArr);
        }
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void requestLocalPlayerDetails() {
        Player currentPlayer = Games.Players.getCurrentPlayer(this.apiService);
        IGameServicesPlayerListener iGameServicesPlayerListener = this.playerListener;
        if (iGameServicesPlayerListener != null) {
            iGameServicesPlayerListener.onLoadLocalUserDetails(DataConversionUtils.getUser(currentPlayer));
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService$2] */
    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadLocalPlayerFriends(final boolean z) {
        if (isAvailable()) {
            new AsyncTask<Void, Void, ArrayList<User>>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public ArrayList<User> doInBackground(Void... voidArr) {
                    PlayerBuffer playerBuffer = GooglePlayGameService.this.getPlayerBuffer(Games.Players.loadConnectedPlayers(GooglePlayGameService.this.getApiService(), z));
                    if (playerBuffer == null) {
                        return null;
                    }
                    ArrayList<User> arrayList = new ArrayList<>();
                    for (int i = 0; i < playerBuffer.getCount(); i++) {
                        arrayList.add(DataConversionUtils.getUser(playerBuffer.get(i)));
                    }
                    playerBuffer.release();
                    return arrayList;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(ArrayList<User> arrayList) {
                    String str = arrayList == null ? "Error loading user friend details." : null;
                    if (GooglePlayGameService.this.playerListener != null) {
                        GooglePlayGameService.this.playerListener.onLoadLocalUserFriendsDetails(arrayList, str);
                    }
                }
            }.execute(new Void[0]);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String string;
        Debug.log(CommonDefines.GAME_SERVICES_TAG, "onConnectionFailed : " + getErrorCodeDescription(connectionResult.getErrorCode()));
        if (connectionResult != null) {
            string = connectionResult.toString();
            Debug.error(CommonDefines.GAME_SERVICES_TAG, string);
        } else {
            string = "";
        }
        if (this.state == BasicGameService.eGameServiceState.RESOLVING_ERROR) {
            return;
        }
        if (this.apiService != null) {
            Debug.warning(CommonDefines.GAME_SERVICES_TAG, "Attempting resolution if any...");
            this.state = BasicGameService.eGameServiceState.RESOLVING_ERROR;
            Intent intent = new Intent(this.context, (Class<?>) GooglePlayGameUIActivity.class);
            intent.putExtra("type", Keys.GameServices.ON_CONNECTION_FAILURE);
            intent.putExtra(Keys.GameServices.RESULT_CODE, connectionResult.getErrorCode());
            intent.putExtra(Keys.GameServices.PENDING_INTENT, connectionResult.getResolution());
            intent.putExtra(Keys.GameServices.DISPLAY_STRING, string);
            this.context.startActivity(intent);
            return;
        }
        Debug.error(CommonDefines.GAME_SERVICES_TAG, "Google API client instance creation failed. Check if service isAvailable()");
        finishedResolvingConnectionFailure();
        reportConnectionFailed();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnected(Bundle bundle) {
        Debug.log("GooglePlayGameService", "onConnected(): connected to Google APIs");
        onConnected(DataConversionUtils.getUser(Games.Players.getCurrentPlayer(getApiService())), null);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnectionSuspended(int i) {
        Debug.log("GooglePlayGameService", "onConnectionSuspended(): attempting to reconnect");
        this.apiService.connect();
        onConnectionSuspended();
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void loadProfileImage(final String str, String str2) {
        ImageManager.create(this.context).loadImage(new ImageManager.OnImageLoadedListener() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService.3
            @Override // com.google.android.gms.common.images.ImageManager.OnImageLoadedListener
            public void onImageLoaded(Uri uri, Drawable drawable, boolean z) {
                try {
                    String scaledImagePathFromBitmap = FileUtility.getScaledImagePathFromBitmap(((BitmapDrawable) drawable).getBitmap(), new File(GooglePlayGameService.this.context.getFilesDir().getPath() + "/temp/"), "Profile_Picture_" + str + ".jpg", 1.0f);
                    if (GooglePlayGameService.this.playerListener != null) {
                        GooglePlayGameService.this.playerListener.onLoadProfilePicture(str, scaledImagePathFromBitmap, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String str3 = "Exception while loading image : " + e.toString();
                    Debug.error(CommonDefines.GAME_SERVICES_TAG, str3);
                    if (GooglePlayGameService.this.playerListener != null) {
                        GooglePlayGameService.this.playerListener.onLoadProfilePicture(str, null, str3);
                    }
                }
            }
        }, Uri.parse(str2));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService$4] */
    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService, com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices
    public void requestAuthToken(final String str) {
        new AsyncTask<Void, Void, String>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                Games.GetServerAuthCodeResult getServerAuthCodeResult = (Games.GetServerAuthCodeResult) Games.getGamesServerAuthCode(GooglePlayGameService.this.getApiService(), str).await();
                return getServerAuthCodeResult.getStatus().isSuccess() ? getServerAuthCodeResult.getCode() : "";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str2) {
                GooglePlayGameService.this.onReceivingServerAuthCode(StringUtility.getBase64EncodedString(str2), StringUtility.isNullOrEmpty(str2) ? "Error loading server auth code details. Cross check your server client id." : "");
            }
        }.execute(new Void[0]);
    }

    public void finishedResolvingConnectionFailure() {
        if (this.state == BasicGameService.eGameServiceState.RESOLVING_ERROR) {
            this.state = BasicGameService.eGameServiceState.NONE;
        }
    }

    public GoogleApiClient getApiService() {
        return this.apiService;
    }

    PlayerBuffer getPlayerBuffer(PendingResult<Players.LoadPlayersResult> pendingResult) {
        Players.LoadPlayersResult loadPlayersResult = (Players.LoadPlayersResult) pendingResult.await();
        int statusCode = loadPlayersResult.getStatus().getStatusCode();
        if (statusCode == 0 || statusCode == 3) {
            return loadPlayersResult.getPlayers();
        }
        Debug.error(CommonDefines.GAME_SERVICES_TAG, "Failed loading top scores with error code : " + statusCode + " Message: " + GamesStatusCodes.getStatusString(statusCode));
        return null;
    }

    public void reportConnectionFailed() {
        onConnectionFailure();
    }

    public void reportAchievementsUIClosed() {
        IGameServicesPlayerListener iGameServicesPlayerListener = this.playerListener;
        if (iGameServicesPlayerListener != null) {
            iGameServicesPlayerListener.onAchievementsUIClosed();
        }
    }

    public void reportLeaderboardsUIClosed() {
        IGameServicesPlayerListener iGameServicesPlayerListener = this.playerListener;
        if (iGameServicesPlayerListener != null) {
            iGameServicesPlayerListener.onLeaderboardsUIClosed();
        }
    }

    public String getSignedInPlayerId() {
        return isSignedIn() ? Games.Players.getCurrentPlayer(this.apiService).getPlayerId() : "default-id";
    }

    public void onReceivingServerAuthCode(String str, String str2) {
        if (this.authListeners.size() > 0) {
            Iterator<IGameServicesAuthListener> it = this.authListeners.iterator();
            while (it.hasNext()) {
                it.next().onReceivingExternalAuthenticationDetails(str, str2);
            }
        }
    }

    IGameServicesPlayerListener getPlayerListener() {
        if (this.playerListeners.size() > 0) {
            return this.playerListeners.get(0);
        }
        return null;
    }

    void onConnected(User user, String str) {
        if (this.authListeners.size() > 0) {
            Iterator<IGameServicesAuthListener> it = this.authListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnected(user, str);
            }
        }
    }

    void onConnectionSuspended() {
        if (this.authListeners.size() > 0) {
            Iterator<IGameServicesAuthListener> it = this.authListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionSuspended();
            }
        }
    }

    void onConnectionFailure() {
        if (this.authListeners.size() > 0) {
            Iterator<IGameServicesAuthListener> it = this.authListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionFailure();
            }
        }
    }

    void onSignOut(String str) {
        if (this.authListeners.size() > 0) {
            Iterator<IGameServicesAuthListener> it = this.authListeners.iterator();
            while (it.hasNext()) {
                it.next().onSignOut(str);
            }
        }
    }
}
