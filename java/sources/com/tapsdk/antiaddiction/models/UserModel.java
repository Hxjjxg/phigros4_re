package com.tapsdk.antiaddiction.models;

import com.tapsdk.antiaddiction.entities.IdentificationInfo;
import com.tapsdk.antiaddiction.entities.UserInfo;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.subjects.PublishSubject;

/* JADX INFO: loaded from: classes.dex */
public class UserModel {
    private IdentificationInfo identificationInfo = null;
    private volatile UserInfo userInfo = null;
    private final PublishSubject<Boolean> userLoginStatusChangedPublishSubject = PublishSubject.create();

    public Observable<Boolean> getUserLoginStatusChangedObservable() {
        return this.userLoginStatusChangedPublishSubject;
    }

    public void setIdentificationInfo(IdentificationInfo identificationInfo) {
        this.identificationInfo = identificationInfo;
        UserInfo userInfo = new UserInfo();
        userInfo.accessToken = identificationInfo.antiAddictionToken;
        setCurrentUser(userInfo);
    }

    public void setIdentifyState(IdentifyState identifyState, String str) {
        UserInfo userInfo = new UserInfo();
        userInfo.accessToken = identifyState.antiAddictionToken;
        userInfo.ageLimit = identifyState.ageLimit;
        userInfo.userId = str;
        setCurrentUser(userInfo);
    }

    public IdentificationInfo getIdentificationInfo() {
        return this.identificationInfo;
    }

    public void setCurrentUser(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.userLoginStatusChangedPublishSubject.onNext(true);
    }

    public UserInfo getCurrentUser() {
        return this.userInfo;
    }

    public void logout() {
        this.userLoginStatusChangedPublishSubject.onNext(false);
        this.userInfo = null;
    }
}
