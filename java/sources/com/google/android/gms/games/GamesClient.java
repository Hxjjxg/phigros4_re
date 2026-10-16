package com.google.android.gms.games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.tasks.Task;

/* JADX INFO: loaded from: classes.dex */
public class GamesClient extends com.google.android.gms.internal.games.zzu {
    GamesClient(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    GamesClient(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }

    public Task<Bundle> getActivationHint() {
        return doRead(new zzs(this));
    }

    public Task<String> getAppId() {
        return doRead(new zzq(this));
    }

    public Task<String> getCurrentAccountName() {
        return doRead(new zzp(this));
    }

    public Task<Integer> getSdkVariant() {
        return doRead(new zzt(this));
    }

    public Task<Intent> getSettingsIntent() {
        return doRead(new zzr(this));
    }

    public Task<Void> setGravityForPopups(int i) {
        return doWrite(new zzn(this, i));
    }

    public Task<Void> setViewForPopups(View view) {
        return doWrite(new zzo(this, view));
    }
}
