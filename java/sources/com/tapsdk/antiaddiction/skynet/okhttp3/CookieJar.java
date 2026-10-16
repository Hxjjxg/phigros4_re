package com.tapsdk.antiaddiction.skynet.okhttp3;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CookieJar {
    public static final CookieJar NO_COOKIES = new CookieJar() { // from class: com.tapsdk.antiaddiction.skynet.okhttp3.CookieJar.1
        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.CookieJar
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.CookieJar
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return Collections.emptyList();
        }
    };

    List<Cookie> loadForRequest(HttpUrl httpUrl);

    void saveFromResponse(HttpUrl httpUrl, List<Cookie> list);
}
