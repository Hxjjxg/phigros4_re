package com.tapsdk.antiaddiction.entities;

import com.digits.sdk.android.DigitsClient;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UserInfo implements Serializable {

    @SerializedName(Constants.CacheData.ACCESS_TOKEN)
    public String accessToken;

    @SerializedName("ageLimit")
    public int ageLimit;

    @SerializedName(NetworkStateModel.PARAM_CODE)
    public String code;
    public String game;

    @SerializedName(DigitsClient.EXTRA_USER_ID)
    public String userId;
    public String clientId = "";
    private int remainTime = 0;
    public long saveTimeStamp = 0;

    public synchronized void updateRemainTime(int i) {
        int i2 = this.remainTime - i;
        this.remainTime = i2;
        if (i2 < 0) {
            this.remainTime = 0;
        }
    }

    public void resetRemainTime(int i) {
        this.remainTime = i;
    }

    public int getRemainTime() {
        return this.remainTime;
    }

    public boolean isAdult() {
        int i = this.ageLimit;
        return i == 18 || i == 110;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public UserInfo m7clone() {
        UserInfo userInfo = new UserInfo();
        userInfo.code = this.code;
        userInfo.clientId = this.clientId;
        userInfo.userId = this.userId;
        userInfo.ageLimit = this.ageLimit;
        userInfo.remainTime = this.remainTime;
        userInfo.saveTimeStamp = this.saveTimeStamp;
        userInfo.accessToken = this.accessToken;
        userInfo.game = this.game;
        return userInfo;
    }
}
