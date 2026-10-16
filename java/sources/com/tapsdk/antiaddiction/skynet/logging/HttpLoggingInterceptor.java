package com.tapsdk.antiaddiction.skynet.logging;

import com.tapsdk.antiaddiction.skynet.okhttp3.Connection;
import com.tapsdk.antiaddiction.skynet.okhttp3.Headers;
import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.MediaType;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.HttpHeaders;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform.Platform;
import com.tapsdk.antiaddiction.skynet.okio.Buffer;
import com.tapsdk.antiaddiction.skynet.okio.BufferedSource;
import com.tapsdk.antiaddiction.skynet.okio.GzipSource;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.EOFException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private volatile Set<String> headersToRedact;
    private volatile Level level;
    private final Logger logger;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        public static final Logger DEFAULT = new Logger() { // from class: com.tapsdk.antiaddiction.skynet.logging.HttpLoggingInterceptor.Logger.1
            @Override // com.tapsdk.antiaddiction.skynet.logging.HttpLoggingInterceptor.Logger
            public void log(String str) {
                Platform.get().log(4, str, null);
            }
        };

        void log(String str);
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.headersToRedact = Collections.emptySet();
        this.level = Level.NONE;
        this.logger = logger;
    }

    public void redactHeader(String str) {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        treeSet.addAll(this.headersToRedact);
        treeSet.add(str);
        this.headersToRedact = treeSet;
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return this.level;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        Level level = this.level;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean z = level == Level.BODY;
        boolean z2 = z || level == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        boolean z3 = requestBodyBody != null;
        Connection connection = chain.connection();
        String str = "--> " + request.method() + ' ' + request.url() + (connection != null ? " " + connection.protocol() : "");
        if (!z2 && z3) {
            str = str + " (" + requestBodyBody.contentLength() + "-byte body)";
        }
        this.logger.log(str);
        if (z2) {
            if (z3) {
                if (requestBodyBody.contentType() != null) {
                    this.logger.log("Content-Type: " + requestBodyBody.contentType());
                }
                if (requestBodyBody.contentLength() != -1) {
                    this.logger.log("Content-Length: " + requestBodyBody.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            int i = 0;
            while (i < size) {
                String strName = headers.name(i);
                int i2 = size;
                if (!"Content-Type".equalsIgnoreCase(strName) && !HttpRequest.HEADER_CONTENT_LENGTH.equalsIgnoreCase(strName)) {
                    logHeader(headers, i);
                }
                i++;
                size = i2;
            }
            if (!z || !z3) {
                this.logger.log("--> END " + request.method());
            } else if (bodyHasUnknownEncoding(request.headers())) {
                this.logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBodyBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType mediaTypeContentType = requestBodyBody.contentType();
                if (mediaTypeContentType != null) {
                    charset = mediaTypeContentType.charset(charset);
                }
                this.logger.log("");
                if (isPlaintext(buffer)) {
                    this.logger.log(buffer.readString(charset));
                    this.logger.log("--> END " + request.method() + " (" + requestBodyBody.contentLength() + "-byte body)");
                } else {
                    this.logger.log("--> END " + request.method() + " (binary " + requestBodyBody.contentLength() + "-byte body omitted)");
                }
            }
        }
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
            ResponseBody responseBodyBody = responseProceed.body();
            long jContentLength = responseBodyBody.contentLength();
            this.logger.log("<-- " + responseProceed.code() + (responseProceed.message().isEmpty() ? "" : " " + responseProceed.message()) + ' ' + responseProceed.request().url() + " (" + millis + "ms" + (!z2 ? ", " + (jContentLength != -1 ? jContentLength + "-byte" : "unknown-length") + " body" : "") + ')');
            if (z2) {
                Headers headers2 = responseProceed.headers();
                int size2 = headers2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    logHeader(headers2, i3);
                }
                if (!z || !HttpHeaders.hasBody(responseProceed)) {
                    this.logger.log("<-- END HTTP");
                } else if (bodyHasUnknownEncoding(responseProceed.headers())) {
                    this.logger.log("<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource bufferedSourceSource = responseBodyBody.source();
                    bufferedSourceSource.request(Long.MAX_VALUE);
                    Buffer buffer2 = bufferedSourceSource.buffer();
                    Long l = null;
                    GzipSource gzipSource = null;
                    if (HttpRequest.ENCODING_GZIP.equalsIgnoreCase(headers2.get(HttpRequest.HEADER_CONTENT_ENCODING))) {
                        Long lValueOf = Long.valueOf(buffer2.size());
                        try {
                            GzipSource gzipSource2 = new GzipSource(buffer2.m9clone());
                            try {
                                buffer2 = new Buffer();
                                buffer2.writeAll(gzipSource2);
                                gzipSource2.close();
                                l = lValueOf;
                            } catch (Throwable th) {
                                th = th;
                                gzipSource = gzipSource2;
                                if (gzipSource != null) {
                                    gzipSource.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    Charset charset2 = UTF8;
                    MediaType mediaTypeContentType2 = responseBodyBody.contentType();
                    if (mediaTypeContentType2 != null) {
                        charset2 = mediaTypeContentType2.charset(charset2);
                    }
                    if (!isPlaintext(buffer2)) {
                        this.logger.log("");
                        this.logger.log("<-- END HTTP (binary " + buffer2.size() + "-byte body omitted)");
                        return responseProceed;
                    }
                    if (jContentLength != 0) {
                        this.logger.log("");
                        this.logger.log(buffer2.m9clone().readString(charset2));
                    }
                    if (l != null) {
                        this.logger.log("<-- END HTTP (" + buffer2.size() + "-byte, " + l + "-gzipped-byte body)");
                    } else {
                        this.logger.log("<-- END HTTP (" + buffer2.size() + "-byte body)");
                    }
                }
            }
            return responseProceed;
        } catch (Exception e) {
            this.logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    private void logHeader(Headers headers, int i) {
        this.logger.log(headers.name(i) + ": " + (this.headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i)));
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i = 0; i < 16 && !buffer2.exhausted(); i++) {
                int utf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(utf8CodePoint) && !Character.isWhitespace(utf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String str = headers.get(HttpRequest.HEADER_CONTENT_ENCODING);
        return (str == null || str.equalsIgnoreCase("identity") || str.equalsIgnoreCase(HttpRequest.ENCODING_GZIP)) ? false : true;
    }
}
