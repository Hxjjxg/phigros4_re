package com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform;

import com.tapsdk.antiaddiction.skynet.okhttp3.Protocol;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
final class Jdk9Platform extends Platform {
    final Method getProtocolMethod;
    final Method setProtocolMethod;

    Jdk9Platform(Method method, Method method2) {
        this.setProtocolMethod = method;
        this.getProtocolMethod = method2;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform.Platform
    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
        try {
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            List<String> listAlpnProtocolNames = alpnProtocolNames(list);
            this.setProtocolMethod.invoke(sSLParameters, listAlpnProtocolNames.toArray(new String[listAlpnProtocolNames.size()]));
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw Util.assertionError("unable to set ssl parameters", e);
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform.Platform
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        try {
            String str = (String) this.getProtocolMethod.invoke(sSLSocket, new Object[0]);
            if (str == null || str.equals("")) {
                return null;
            }
            return str;
        } catch (IllegalAccessException e) {
            throw Util.assertionError("failed to get ALPN selected protocol", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof UnsupportedOperationException) {
                return null;
            }
            throw Util.assertionError("failed to get ALPN selected protocol", e2);
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform.Platform
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+");
    }

    public static Jdk9Platform buildIfSupported() {
        try {
            return new Jdk9Platform(SSLParameters.class.getMethod("setApplicationProtocols", String[].class), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }
}
