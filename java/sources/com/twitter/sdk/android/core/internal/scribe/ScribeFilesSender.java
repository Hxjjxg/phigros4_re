package com.twitter.sdk.android.core.internal.scribe;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import com.tds.common.tracker.constants.CommonParam;
import com.twitter.sdk.android.core.AuthenticatedClient;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.events.FilesSender;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLSocketFactory;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/* JADX INFO: loaded from: classes.dex */
class ScribeFilesSender implements FilesSender {
    private static final String SEND_FILE_FAILURE_ERROR = "Failed sending files";
    private final AtomicReference<RestAdapter> apiAdapter = new AtomicReference<>();
    private final TwitterAuthConfig authConfig;
    private final Context context;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final long ownerId;
    private final ScribeConfig scribeConfig;
    private final List<SessionManager<? extends Session>> sessionManagers;
    private final SSLSocketFactory sslSocketFactory;
    private static final byte[] START_JSON_ARRAY = {91};
    private static final byte[] COMMA = {44};
    private static final byte[] END_JSON_ARRAY = {93};

    interface ScribeService {
        @POST("/{version}/jot/{type}")
        @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
        @FormUrlEncoded
        Response upload(@Path(CommonParam.VERSION) String str, @Path("type") String str2, @Field("log[]") String str3);

        @POST("/scribe/{sequence}")
        @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
        @FormUrlEncoded
        Response uploadSequence(@Path("sequence") String str, @Field("log[]") String str2);
    }

    public ScribeFilesSender(Context context, ScribeConfig scribeConfig, long j, TwitterAuthConfig twitterAuthConfig, List<SessionManager<? extends Session>> list, SSLSocketFactory sSLSocketFactory, ExecutorService executorService, IdManager idManager) {
        this.context = context;
        this.scribeConfig = scribeConfig;
        this.ownerId = j;
        this.authConfig = twitterAuthConfig;
        this.sessionManagers = list;
        this.sslSocketFactory = sSLSocketFactory;
        this.executorService = executorService;
        this.idManager = idManager;
    }

    @Override // io.fabric.sdk.android.services.events.FilesSender
    public boolean send(List<File> list) throws Throwable {
        if (hasApiAdapter()) {
            try {
                String scribeEventsAsJsonArrayString = getScribeEventsAsJsonArrayString(list);
                CommonUtils.logControlled(this.context, scribeEventsAsJsonArrayString);
                if (upload(scribeEventsAsJsonArrayString).getStatus() == 200) {
                    return true;
                }
                CommonUtils.logControlledError(this.context, SEND_FILE_FAILURE_ERROR, null);
                return false;
            } catch (IOException e) {
                CommonUtils.logControlledError(this.context, SEND_FILE_FAILURE_ERROR, e);
                return false;
            } catch (RetrofitError e2) {
                CommonUtils.logControlledError(this.context, SEND_FILE_FAILURE_ERROR, e2);
                if (e2.getResponse() != null) {
                    return e2.getResponse().getStatus() == 500 || e2.getResponse().getStatus() == 400;
                }
                return false;
            }
        }
        CommonUtils.logControlled(this.context, "Cannot attempt upload at this time");
        return false;
    }

    String getScribeEventsAsJsonArrayString(List<File> list) throws Throwable {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        final boolean[] zArr = new boolean[1];
        byteArrayOutputStream.write(START_JSON_ARRAY);
        Iterator<File> it = list.iterator();
        while (it.hasNext()) {
            QueueFile queueFile = null;
            try {
                QueueFile queueFile2 = new QueueFile(it.next());
                try {
                    queueFile2.forEach(new QueueFile.ElementReader() { // from class: com.twitter.sdk.android.core.internal.scribe.ScribeFilesSender.1
                        @Override // io.fabric.sdk.android.services.common.QueueFile.ElementReader
                        public void read(InputStream inputStream, int i) throws IOException {
                            byte[] bArr = new byte[i];
                            inputStream.read(bArr);
                            boolean[] zArr2 = zArr;
                            if (zArr2[0]) {
                                byteArrayOutputStream.write(ScribeFilesSender.COMMA);
                            } else {
                                zArr2[0] = true;
                            }
                            byteArrayOutputStream.write(bArr);
                        }
                    });
                    CommonUtils.closeQuietly(queueFile2);
                } catch (Throwable th) {
                    th = th;
                    queueFile = queueFile2;
                    CommonUtils.closeQuietly(queueFile);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        byteArrayOutputStream.write(END_JSON_ARRAY);
        return byteArrayOutputStream.toString("UTF-8");
    }

    private boolean hasApiAdapter() {
        return getApiAdapter() != null;
    }

    void setApiAdapter(RestAdapter restAdapter) {
        this.apiAdapter.set(restAdapter);
    }

    synchronized RestAdapter getApiAdapter() {
        if (this.apiAdapter.get() == null) {
            Session session = getSession(this.ownerId);
            ConfigRequestInterceptor configRequestInterceptor = new ConfigRequestInterceptor(this.scribeConfig, this.idManager);
            if (isValidSession(session)) {
                zal$$ExternalSyntheticBackportWithForwarding0.m(this.apiAdapter, null, new RestAdapter.Builder().setEndpoint(this.scribeConfig.baseUrl).setExecutors(this.executorService, new MainThreadExecutor()).setRequestInterceptor(configRequestInterceptor).setClient(new AuthenticatedClient(this.authConfig, session, this.sslSocketFactory)).build());
            } else {
                CommonUtils.logControlled(this.context, "No valid session at this time");
            }
        }
        return this.apiAdapter.get();
    }

    private Session getSession(long j) {
        Iterator<SessionManager<? extends Session>> it = this.sessionManagers.iterator();
        Session session = null;
        while (it.hasNext() && (session = it.next().getSession(j)) == null) {
        }
        return session;
    }

    private boolean isValidSession(Session session) {
        return (session == null || session.getAuthToken() == null) ? false : true;
    }

    Response upload(String str) {
        ScribeService scribeService = (ScribeService) this.apiAdapter.get().create(ScribeService.class);
        if (!TextUtils.isEmpty(this.scribeConfig.sequence)) {
            return scribeService.uploadSequence(this.scribeConfig.sequence, str);
        }
        return scribeService.upload(this.scribeConfig.pathVersion, this.scribeConfig.pathType, str);
    }

    static class ConfigRequestInterceptor implements RequestInterceptor {
        private static final String CLIENT_UUID_HEADER = "X-Client-UUID";
        private static final String POLLING_HEADER = "X-Twitter-Polling";
        private static final String POLLING_HEADER_VALUE = "true";
        private static final String USER_AGENT_HEADER = "User-Agent";
        private final IdManager idManager;
        private final ScribeConfig scribeConfig;

        ConfigRequestInterceptor(ScribeConfig scribeConfig, IdManager idManager) {
            this.scribeConfig = scribeConfig;
            this.idManager = idManager;
        }

        @Override // retrofit.RequestInterceptor
        public void intercept(RequestInterceptor.RequestFacade requestFacade) {
            if (!TextUtils.isEmpty(this.scribeConfig.userAgent)) {
                requestFacade.addHeader("User-Agent", this.scribeConfig.userAgent);
            }
            if (!TextUtils.isEmpty(this.idManager.getDeviceUUID())) {
                requestFacade.addHeader(CLIENT_UUID_HEADER, this.idManager.getDeviceUUID());
            }
            requestFacade.addHeader(POLLING_HEADER, POLLING_HEADER_VALUE);
        }
    }
}
