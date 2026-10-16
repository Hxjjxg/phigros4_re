package com.tapsdk.antiaddiction.skynet;

import com.tapsdk.antiaddiction.skynet.okhttp3.OkHttpClient;
import com.tapsdk.antiaddiction.skynet.retrofit2.Retrofit;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Skynet {
    public static String RETROFIT_FOR_ANTI_ADDICTION = "anti-addiction";
    public static String RETROFIT_FOR_TAPTAP_SERVICE = "taptap_service";
    final Map<String, OkHttpClient> okHttpClientMap;
    final Map<String, Retrofit> retrofitMap;

    private static class Holder {
        static Skynet INSTANCE = new Skynet();

        private Holder() {
        }
    }

    private Skynet() {
        this.retrofitMap = new HashMap();
        this.okHttpClientMap = new HashMap();
    }

    public static Skynet getInstance() {
        return Holder.INSTANCE;
    }

    public void registerRetrofit(String str, Retrofit retrofit2) {
        this.retrofitMap.put(str, retrofit2);
    }

    public Retrofit getRetrofit(String str) {
        return this.retrofitMap.get(str);
    }

    public static <T> T getService(String str, Class<T> cls) {
        return (T) getInstance().getRetrofit(str).create(cls);
    }
}
