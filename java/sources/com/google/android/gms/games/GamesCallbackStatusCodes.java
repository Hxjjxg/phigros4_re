package com.google.android.gms.games;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class GamesCallbackStatusCodes {

    @Deprecated
    public static final int CLIENT_RECONNECT_REQUIRED = 2;
    public static final int INTERNAL_ERROR = 1;
    public static final int MULTIPLAYER_DISABLED = 6003;
    public static final int OK = 0;
    public static final int REAL_TIME_CONNECTION_FAILED = 7000;
    public static final int REAL_TIME_MESSAGE_SEND_FAILED = 7001;
    public static final int REAL_TIME_ROOM_NOT_JOINED = 7004;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnJoinedRoomStatusCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnLeftRoomStatusCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnRealTimeMessageSentStatusCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnRoomConnectedStatusCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnRoomCreatedStatusCodes {
    }

    private GamesCallbackStatusCodes() {
    }

    public static String getStatusCodeString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "INTERNAL_ERROR";
        }
        if (i == 2) {
            return "CLIENT_RECONNECT_REQUIRED";
        }
        if (i == 6003) {
            return "MULTIPLAYER_DISABLED";
        }
        if (i == 7004) {
            return "REAL_TIME_ROOM_NOT_JOINED";
        }
        if (i != 7000) {
            return i != 7001 ? new StringBuilder(47).append("unknown games callback status code: ").append(i).toString() : "REAL_TIME_MESSAGE_SEND_FAILED";
        }
        return "REAL_TIME_CONNECTION_FAILED";
    }
}
