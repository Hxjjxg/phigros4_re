package io.fabric.sdk.android.services.network;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* JADX INFO: loaded from: classes.dex */
public final class NetworkUtils {
    private NetworkUtils() {
    }

    public static final SSLSocketFactory getSSLSocketFactory(PinningInfoProvider pinningInfoProvider) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, new TrustManager[]{new PinningTrustManager(new SystemKeyStore(pinningInfoProvider.getKeyStoreStream(), pinningInfoProvider.getKeyStorePassword()), pinningInfoProvider)}, null);
        return sSLContext.getSocketFactory();
    }
}
