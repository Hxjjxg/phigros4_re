package com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection;

import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class RouteException extends RuntimeException {
    private IOException firstException;
    private IOException lastException;

    public RouteException(IOException iOException) {
        super(iOException);
        this.firstException = iOException;
        this.lastException = iOException;
    }

    public IOException getFirstConnectException() {
        return this.firstException;
    }

    public IOException getLastConnectException() {
        return this.lastException;
    }

    public void addConnectException(IOException iOException) {
        Util.addSuppressedIfPossible(this.firstException, iOException);
        this.lastException = iOException;
    }
}
