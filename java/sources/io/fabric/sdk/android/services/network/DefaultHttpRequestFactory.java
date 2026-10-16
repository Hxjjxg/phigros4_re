package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class DefaultHttpRequestFactory implements HttpRequestFactory {
    private static final String HTTPS = "https";
    private boolean attemptedSslInit;
    private final Logger logger;
    private PinningInfoProvider pinningInfo;
    private SSLSocketFactory sslSocketFactory;

    public DefaultHttpRequestFactory() {
        this(new DefaultLogger());
    }

    public DefaultHttpRequestFactory(Logger logger) {
        this.logger = logger;
    }

    @Override // io.fabric.sdk.android.services.network.HttpRequestFactory
    public PinningInfoProvider getPinningInfoProvider() {
        return this.pinningInfo;
    }

    @Override // io.fabric.sdk.android.services.network.HttpRequestFactory
    public void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        if (this.pinningInfo != pinningInfoProvider) {
            this.pinningInfo = pinningInfoProvider;
            resetSSLSocketFactory();
        }
    }

    private synchronized void resetSSLSocketFactory() {
        this.attemptedSslInit = false;
        this.sslSocketFactory = null;
    }

    @Override // io.fabric.sdk.android.services.network.HttpRequestFactory
    public HttpRequest buildHttpRequest(HttpMethod httpMethod, String str) {
        return buildHttpRequest(httpMethod, str, Collections.emptyMap());
    }

    /* JADX INFO: renamed from: io.fabric.sdk.android.services.network.DefaultHttpRequestFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$fabric$sdk$android$services$network$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$io$fabric$sdk$android$services$network$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[HttpMethod.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[HttpMethod.PUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[HttpMethod.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // io.fabric.sdk.android.services.network.HttpRequestFactory
    public HttpRequest buildHttpRequest(HttpMethod httpMethod, String str, Map<String, String> map) {
        HttpRequest httpRequestPost;
        SSLSocketFactory sSLSocketFactory;
        int i = AnonymousClass1.$SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[httpMethod.ordinal()];
        if (i == 1) {
            httpRequestPost = HttpRequest.get((CharSequence) str, (Map<?, ?>) map, true);
        } else if (i == 2) {
            httpRequestPost = HttpRequest.post((CharSequence) str, (Map<?, ?>) map, true);
        } else if (i == 3) {
            httpRequestPost = HttpRequest.put(str);
        } else if (i == 4) {
            httpRequestPost = HttpRequest.delete(str);
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method!");
        }
        if (isHttps(str) && this.pinningInfo != null && (sSLSocketFactory = getSSLSocketFactory()) != null) {
            ((HttpsURLConnection) httpRequestPost.getConnection()).setSSLSocketFactory(sSLSocketFactory);
        }
        return httpRequestPost;
    }

    private boolean isHttps(String str) {
        return str != null && str.toLowerCase(Locale.US).startsWith("https");
    }

    private synchronized SSLSocketFactory getSSLSocketFactory() {
        if (this.sslSocketFactory == null && !this.attemptedSslInit) {
            this.sslSocketFactory = initSSLSocketFactory();
        }
        return this.sslSocketFactory;
    }

    private synchronized SSLSocketFactory initSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory;
        this.attemptedSslInit = true;
        try {
            sSLSocketFactory = NetworkUtils.getSSLSocketFactory(this.pinningInfo);
            this.logger.d(Fabric.TAG, "Custom SSL pinning enabled");
        } catch (Exception e) {
            this.logger.e(Fabric.TAG, "Exception while validating pinned certs", e);
            return null;
        }
        return sSLSocketFactory;
    }
}
