package com.twitter.sdk.android.core;

import com.twitter.sdk.android.core.internal.TwitterRequestHeaders;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.TypedOutput;

/* JADX INFO: loaded from: classes.dex */
public class AuthenticatedClient extends DefaultClient {
    private static final String FAKE_URL = "https://twitter.com";
    private final TwitterAuthConfig authConfig;
    private final Session session;

    public AuthenticatedClient(TwitterAuthConfig twitterAuthConfig, Session session, SSLSocketFactory sSLSocketFactory) {
        super(sSLSocketFactory);
        this.authConfig = twitterAuthConfig;
        this.session = session;
    }

    @Override // com.twitter.sdk.android.core.DefaultClient, retrofit.client.Client
    public Response execute(Request request) throws IOException {
        return this.wrappedClient.execute(new Request(request.getMethod(), request.getUrl(), getAuthHeaders(request), request.getBody()));
    }

    protected List<Header> getAuthHeaders(Request request) throws IOException {
        TwitterRequestHeaders twitterRequestHeaders = new TwitterRequestHeaders(request.getMethod(), request.getUrl(), this.authConfig, this.session, null, getPostParams(request));
        ArrayList arrayList = new ArrayList(request.getHeaders());
        for (Map.Entry<String, String> entry : twitterRequestHeaders.getHeaders().entrySet()) {
            arrayList.add(new Header(entry.getKey(), entry.getValue()));
        }
        return arrayList;
    }

    protected Map<String, String> getPostParams(Request request) throws IOException {
        TreeMap treeMap = new TreeMap();
        if (HttpRequest.METHOD_POST.equals(request.getMethod().toUpperCase(Locale.US))) {
            TypedOutput body = request.getBody();
            if (body instanceof FormUrlEncodedTypedOutput) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                body.writeTo(byteArrayOutputStream);
                String string = byteArrayOutputStream.toString("UTF-8");
                if (string.length() > 0) {
                    for (NameValuePair nameValuePair : URLEncodedUtils.parse(URI.create("https://twitter.com/?" + string), "UTF-8")) {
                        treeMap.put(nameValuePair.getName(), nameValuePair.getValue());
                    }
                }
            }
        }
        return treeMap;
    }
}
