package retrofit.mime;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class TypedString extends TypedByteArray {
    public TypedString(String str) {
        super("text/plain; charset=UTF-8", convertToBytes(str));
    }

    private static byte[] convertToBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
