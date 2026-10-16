package io.fabric.sdk.android.services.common;

/* JADX INFO: loaded from: classes.dex */
public abstract class Crash {
    private final String sessionId;

    public Crash(String str) {
        this.sessionId = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public static class LoggedException extends Crash {
        public LoggedException(String str) {
            super(str);
        }
    }

    public static class FatalException extends Crash {
        public FatalException(String str) {
            super(str);
        }
    }
}
