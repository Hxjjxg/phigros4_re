package com.tapsdk.antiaddiction.constants;

/* JADX INFO: loaded from: classes.dex */
public class Constants {

    public static class ANTI_ADDICTION_CALLBACK_CODE {
        public static final int AGE_RESTRICT = 1100;
        public static final int DURATION_LIMIT = 1050;
        public static final int EXITED = 1000;
        public static final int INVALID_CLIENT_OR_NETWORK_ERROR = 1200;
        public static final int LOGIN_SUCCESS = 500;

        @Deprecated
        public static final int LOGOUT = 1000;

        @Deprecated
        public static final int NIGHT_STRICT = 1030;

        @Deprecated
        public static final int OPEN_ALERT_TIP = 1095;
        public static final int PERIOD_RESTRICT = 1030;
        public static final int REAL_NAME_STOP = 9002;
        public static final int SWITCH_ACCOUNT = 1001;

        @Deprecated
        public static final int TIME_LIMIT = 1050;
        public static final int TOKEN_EXPIRED = 9001;
    }

    public static class API {
        public static final String ANTI_ADDICTION_BASE_URL = "https://tds-tapsdk.cn.tapapis.com";
    }

    public static class AgeLimit {
        public static final int ADULT = 18;
        public static final int CHILD = 0;
        public static final int TEEN = 8;
        public static final int UNKNOWN_AGE_RANGE = 100;
        public static final int UNKNOWN_AGE_RANGE_ADULT = 110;
        public static final int UNREALNAME = -1;
        public static final int YOUNG = 16;
    }

    public static class CacheData {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ACCESS_TOKEN_V2 = "access_token_v2";
        public static final String AGE_LIMIT = "age_limit";
        public static final String AUTH_INPUT_INFO = "auth_input_info";
        public static final String TIMING_FILE_SUFFIX = "timing_file_suffix";
        public static final String TIMING_SHARED_PREFERENCE_NAME = "timing_shared_preference_name";
    }

    public static class FetchAuthType {
        public static final int TYPE_LOCAL_OLD_TOKEN = 1;
        public static final int TYPE_LOCAL_TOKEN = 0;
        public static final int TYPE_TAP_TOKEN = 2;
        public static final int TYPE_USER_ID = 3;
    }

    public static class IdentifyState {
        public static final int FAIL = 2;
        public static final int SUCCESS = 0;
        public static final int UNDEFINED = -1;
        public static final int VERIFYING = 1;
    }

    public static class MsgExtraParams {
        public static final String DESCRIPTION = "description";
        public static final String IS_LOGIN_TIP = "is_login_tip";
        public static final String TITLE = "title";
    }

    public static class ServerErrorType {
        public static final String BUSINESS_ERROR = "business_code_error";
        public static final String INVALID_TIME = "invalid_time";
    }
}
