package com.tapsdk.antiaddiction.models;

import com.tapsdk.antiaddiction.entities.IdentificationInfo;
import com.tapsdk.antiaddiction.entities.UserInfo;

/* JADX INFO: loaded from: classes.dex */
public class UpdateAccountAction {
    public final boolean enableStandaloneMode;
    public final IdentificationInfo identificationInfo;
    public final UserInfo userInfo;

    public UpdateAccountAction(UserInfo userInfo, IdentificationInfo identificationInfo) {
        this(userInfo, identificationInfo, false);
    }

    public UpdateAccountAction(UserInfo userInfo, IdentificationInfo identificationInfo, Boolean bool) {
        this.userInfo = userInfo;
        this.identificationInfo = identificationInfo;
        this.enableStandaloneMode = bool.booleanValue();
    }
}
