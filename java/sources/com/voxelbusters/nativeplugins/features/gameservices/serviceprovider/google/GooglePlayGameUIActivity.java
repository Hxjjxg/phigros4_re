package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.games.Games;
import com.voxelbusters.androidnativeplugin.R;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.features.gameservices.GameServicesHandler;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util.BaseGameUtils;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.StringUtility;

/* JADX INFO: loaded from: classes.dex */
public class GooglePlayGameUIActivity extends Activity implements DialogInterface.OnDismissListener {
    GooglePlayGameService service;
    private final String TAG = "GooglePlayGameUIActivity";
    private final int REQUEST_CODE_UNKNOWN = -1;
    private final int REQUEST_CODE_CONNECTION_RESOLVE = 1000;
    private final int REQUEST_CODE_SHOW_ACHIEVEMENTS = 1001;
    private final int REQUEST_CODE_SHOW_LEADERBOARDS = 1002;
    private final int REQUEST_CODE_GET_SERVER_AUTH_CODE = 1003;
    private final int RESULT_CODE_UNKNOWN = -1;
    boolean hasActivityResult = false;
    int currentRequestCode = -1;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        Intent allLeaderboardsIntent;
        super.onCreate(bundle);
        GooglePlayGameService googlePlayGameService = GooglePlayGameService.getInstance();
        this.service = googlePlayGameService;
        if (googlePlayGameService == null) {
            finish();
            return;
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("type");
        if (stringExtra.equals(Keys.GameServices.ON_CONNECTION_FAILURE)) {
            ConnectionResult connectionResult = new ConnectionResult(intent.getIntExtra(Keys.GameServices.RESULT_CODE, 0), (PendingIntent) intent.getParcelableExtra(Keys.GameServices.PENDING_INTENT));
            String stringExtra2 = intent.getStringExtra(Keys.GameServices.DISPLAY_STRING);
            if (this.service.getApiService() == null || !BaseGameUtils.resolveConnectionFailure(this, this.service.getApiService(), connectionResult, 1000, stringExtra2)) {
                this.service.finishedResolvingConnectionFailure();
                this.service.reportConnectionFailed();
                finish();
                return;
            }
            return;
        }
        if (stringExtra.equals(Keys.GameServices.SHOW_ACHIEVEMENTS)) {
            launchActivityForResult(Games.Achievements.getAchievementsIntent(this.service.getApiService()), 1001);
            return;
        }
        if (stringExtra.equals(Keys.GameServices.SHOW_LEADERBOARDS)) {
            String stringExtra3 = intent.getStringExtra(Keys.GameServices.LEADERBOARD_ID);
            String stringExtra4 = intent.getStringExtra(Keys.GameServices.TIME_SPAN);
            if (!StringUtility.isNullOrEmpty(stringExtra3)) {
                allLeaderboardsIntent = Games.Leaderboards.getLeaderboardIntent(this.service.getApiService(), stringExtra3, Integer.parseInt(stringExtra4));
            } else {
                allLeaderboardsIntent = Games.Leaderboards.getAllLeaderboardsIntent(this.service.getApiService());
            }
            launchActivityForResult(allLeaderboardsIntent, 1002);
            return;
        }
        Debug.error("GooglePlayGameUIActivity", "Type unspecified for this intent");
    }

    void launchActivityForResult(Intent intent, int i) {
        this.currentRequestCode = i;
        startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        if (this.currentRequestCode != -1 && !this.hasActivityResult) {
            sendResults(-1);
        }
        super.onDestroy();
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0054  */
    /* JADX WARN: Code duplicated, block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Debug.log(CommonDefines.GAME_SERVICES_TAG, "Request Code : " + i + " Result Code : " + i2);
        boolean z = true;
        if (i == 1000) {
            GooglePlayGameService googlePlayGameService = this.service;
            if (googlePlayGameService != null) {
                googlePlayGameService.finishedResolvingConnectionFailure();
                if (i2 == -1) {
                    this.service.signIn();
                } else if (GameServicesHandler.getInstance().getShowDefaultErrorDialogs()) {
                    BaseGameUtils.showActivityResultError(this, i, i2, R.string.gameservices_sign_in_failed);
                } else {
                    this.service.reportConnectionFailed();
                }
            }
            if (z) {
            }
            finish();
        }
        this.hasActivityResult = true;
        sendResults(i2);
        z = false;
        if (z) {
            finish();
        }
    }

    void sendResults(int i) {
        int i2 = this.currentRequestCode;
        if (i2 == 1001) {
            Debug.log("GooglePlayGameUIActivity", "onActivityResult from Show Achievements");
            GooglePlayGameService googlePlayGameService = this.service;
            if (googlePlayGameService != null) {
                googlePlayGameService.reportAchievementsUIClosed();
                if (i == 10001) {
                    this.service.getApiService().disconnect();
                    return;
                }
                return;
            }
            return;
        }
        if (i2 == 1002) {
            Debug.log("GooglePlayGameUIActivity", "onActivityResult from Show specific Leaderboard/All Leaderboards");
            GooglePlayGameService googlePlayGameService2 = this.service;
            if (googlePlayGameService2 != null) {
                googlePlayGameService2.reportLeaderboardsUIClosed();
                if (i == 10001) {
                    this.service.signOut();
                    return;
                }
                return;
            }
            return;
        }
        Debug.log("GooglePlayGameUIActivity", "Unknown request code!");
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        GooglePlayGameService googlePlayGameService = this.service;
        if (googlePlayGameService != null) {
            googlePlayGameService.reportConnectionFailed();
        }
        finish();
    }
}
