package com.digits.sdk.android;

import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;

/* JADX INFO: loaded from: classes.dex */
public class DigitsException extends RuntimeException {
    private final int errorCode;

    private static boolean isUnrecoverable(int i) {
        return i == 286 || i == 269 || i == 235 || i == 237 || i == 299 || i == 284;
    }

    DigitsException(String str) {
        this(str, -1);
    }

    DigitsException(String str, int i) {
        super(str);
        this.errorCode = i;
    }

    static DigitsException create(ErrorCodes errorCodes, TwitterException twitterException) {
        if (twitterException instanceof TwitterApiException) {
            TwitterApiException twitterApiException = (TwitterApiException) twitterException;
            return createException(twitterApiException.getErrorCode(), getMessageForApiError(errorCodes, twitterApiException));
        }
        return new DigitsException(errorCodes.getDefaultMessage());
    }

    private static DigitsException createException(int i, String str) {
        if (i == 285) {
            return new AlreadyRegisteredException(str, i);
        }
        if (isUnrecoverable(i)) {
            return new UnrecoverableException(str, i);
        }
        return new DigitsException(str, i);
    }

    private static String getMessageForApiError(ErrorCodes errorCodes, TwitterApiException twitterApiException) {
        if (twitterApiException.getRetrofitError().isNetworkError()) {
            return errorCodes.getNetworkError();
        }
        return errorCodes.getMessage(twitterApiException.getErrorCode());
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
