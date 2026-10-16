package retrofit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/* JADX INFO: loaded from: classes.dex */
final class Utils {
    private static final int BUFFER_SIZE = 4096;

    static byte[] streamToBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (inputStream != null) {
            byte[] bArr = new byte[4096];
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    static Request readBodyToBytesIfNecessary(Request request) throws IOException {
        TypedOutput body = request.getBody();
        if (body == null || (body instanceof TypedByteArray)) {
            return request;
        }
        String strMimeType = body.mimeType();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        body.writeTo(byteArrayOutputStream);
        return new Request(request.getMethod(), request.getUrl(), request.getHeaders(), new TypedByteArray(strMimeType, byteArrayOutputStream.toByteArray()));
    }

    static Response readBodyToBytesIfNecessary(Response response) throws IOException {
        TypedInput body = response.getBody();
        if (body == null || (body instanceof TypedByteArray)) {
            return response;
        }
        String strMimeType = body.mimeType();
        InputStream inputStreamIn = body.in();
        try {
            return replaceResponseBody(response, new TypedByteArray(strMimeType, streamToBytes(inputStreamIn)));
        } finally {
            if (inputStreamIn != null) {
                try {
                    inputStreamIn.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    static Response replaceResponseBody(Response response, TypedInput typedInput) {
        return new Response(response.getUrl(), response.getStatus(), response.getReason(), response.getHeaders(), typedInput);
    }

    static <T> void validateServiceClass(Class<T> cls) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
        }
        if (cls.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
        }
    }

    static class SynchronousExecutor implements Executor {
        SynchronousExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    private Utils() {
    }
}
