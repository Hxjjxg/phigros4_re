package com.tapsdk.antiaddiction.models;

/* JADX INFO: loaded from: classes.dex */
public interface PromptType {
    public static final int DAILY_FIRST_LOGIN = 7;
    public static final int DAILY_FIRST_LOGIN_IN_NIGHT = 8;
    public static final int IN_NIGHT_STRICT = 5;
    public static final int LOGIN_IN_NIGHT_WITH_NO_REMAINING_TIME = 10;
    public static final int LOGIN_WITH_NO_REMAINING_TIME = 9;
    public static final int NIGHT_REMAINING_TIME = 1;
    public static final int NIGHT_STRICT_BUBBLE = 13;
    public static final int NOT_FIRST_LOGIN_WITH_REMAINING_TIME = 11;
    public static final int TIME_EXHAUSTED = 6;
    public static final int TIME_LIMIT_BUBBLE = 12;
    public static final int TIME_LIMIT_REMAINING_TIME = 2;

    public @interface Type {
    }
}
