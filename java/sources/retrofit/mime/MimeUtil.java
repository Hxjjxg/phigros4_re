package retrofit.mime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class MimeUtil {
    private static final Pattern CHARSET = Pattern.compile("\\Wcharset=([^\\s;]+)", 2);

    public static String parseCharset(String str) {
        Matcher matcher = CHARSET.matcher(str);
        return matcher.find() ? matcher.group(1).replaceAll("[\"\\\\]", "") : "UTF-8";
    }

    private MimeUtil() {
    }
}
