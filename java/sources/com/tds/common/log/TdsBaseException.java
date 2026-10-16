package com.tds.common.log;

/* JADX INFO: loaded from: classes.dex */
public class TdsBaseException extends Exception {
    public static final int SERVER_ERROR = 500;
    public static final int UNAUTHORIZED = 401;
    public static final String VERSION = " Version: 3.30.3";
    public final int errorCode;

    public TdsBaseException(String str) {
        super(str + VERSION);
        this.errorCode = 1000;
    }

    public TdsBaseException(String str, int i) {
        super(str + " Version: 3.30.3 errorCode: " + i);
        this.errorCode = i;
    }

    public TdsBaseException(String str, Throwable th) {
        super(str + VERSION, th);
        this.errorCode = 1000;
    }

    public TdsBaseException(Throwable th) {
        super(VERSION, th);
        this.errorCode = 1000;
    }

    public TdsBaseException(Throwable th, int i) {
        super(" Version: 3.30.3 errorCode: " + i, th);
        this.errorCode = i;
    }

    @Override // java.lang.Throwable
    public String toString() {
        Throwable cause = getCause();
        if (cause == null) {
            return super.toString();
        }
        return super.toString() + "\n" + cause.toString();
    }
}
