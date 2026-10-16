package com.taptap.sdk.net;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.tapsdk.antiaddiction.constants.Constants;
import com.taptap.sdk.Utils;
import com.taptap.sdk.exceptions.ServerError;
import com.tds.common.entities.AccessToken;
import com.voxelbusters.nativeplugins.defines.Keys;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Api {
    private static final int MAX_RETRY_COUNT = 1;
    private static int retryCount;

    public interface ApiCallback<T> {
        void onError(Throwable th);

        void onSuccess(T t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getUA() {
        return "TapTapAndroidSDK/3.30.3 " + System.getProperty("http.agent");
    }

    private static boolean isTimeError(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(AccessToken.ROOT_ELEMENT_NAME);
        return jSONObjectOptJSONObject != null && TextUtils.equals(jSONObjectOptJSONObject.optString("error"), Constants.ServerErrorType.INVALID_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doGet(String str, com.taptap.sdk.AccessToken accessToken, ApiCallback<JSONObject> apiCallback) throws JSONException, IOException {
        int i;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
        httpURLConnection.addRequestProperty("User-Agent", getUA());
        httpURLConnection.addRequestProperty("Authorization", getAuthorization(str, HttpRequest.METHOD_GET, accessToken.kid, accessToken.mac_key));
        httpURLConnection.setConnectTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
        httpURLConnection.setReadTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
        int responseCode = httpURLConnection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responseCode >= 200 && responseCode < 400) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line).append("\n");
                } else {
                    notifyResult(apiCallback, new JSONObject(sb.toString()));
                    return;
                }
            }
        } else {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            while (true) {
                String line2 = bufferedReader2.readLine();
                if (line2 == null) {
                    break;
                } else {
                    sb.append(line2).append("\n");
                }
            }
            JSONObject jSONObject = new JSONObject(sb.toString());
            if (isTimeError(jSONObject) && (i = retryCount) < 1) {
                retryCount = i + 1;
                checkTime(jSONObject.optLong("now"));
                doGet(str, accessToken, apiCallback);
            } else {
                retryCount = 0;
                notifyError(apiCallback, new ServerError(sb.toString(), responseCode));
            }
        }
    }

    public static void get(final String str, HashMap<String, String> map, final ApiCallback<JSONObject> apiCallback) {
        final com.taptap.sdk.AccessToken currentAccessToken = com.taptap.sdk.AccessToken.getCurrentAccessToken();
        if (currentAccessToken == null) {
            return;
        }
        Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Api.doGet(str, currentAccessToken, apiCallback);
                } catch (Exception e) {
                    Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            apiCallback.onError(e);
                        }
                    });
                }
            }
        });
    }

    public static void post(final String str, final HashMap<String, String> map, final ApiCallback<JSONObject> apiCallback) {
        Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.2
            /* JADX WARN: Code duplicated, block: B:103:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:104:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:105:? A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:41:0x0117 A[Catch: IOException -> 0x0140, TRY_ENTER, TRY_LEAVE, TryCatch #10 {IOException -> 0x0140, blocks: (B:41:0x0117, B:60:0x013c), top: B:87:0x0003 }] */
            /* JADX WARN: Code duplicated, block: B:60:0x013c A[Catch: IOException -> 0x0140, TRY_ENTER, TRY_LEAVE, TryCatch #10 {IOException -> 0x0140, blocks: (B:41:0x0117, B:60:0x013c), top: B:87:0x0003 }] */
            /* JADX WARN: Code duplicated, block: B:77:0x0132 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:79:0x0152 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:84:0x0148 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                OutputStream outputStream;
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                try {
                    try {
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
                            httpURLConnection.addRequestProperty("User-Agent", Api.getUA());
                            httpURLConnection.setConnectTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
                            httpURLConnection.setReadTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
                            httpURLConnection.setUseCaches(false);
                            outputStream = httpURLConnection.getOutputStream();
                            try {
                                HashMap map2 = map;
                                if (map2 != null && map2.size() > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    for (Map.Entry entry : map.entrySet()) {
                                        sb.append(((String) entry.getKey()) + "=" + Uri.encode((String) entry.getValue()));
                                        if (map.size() != 0) {
                                            sb.append("&");
                                        }
                                    }
                                    outputStream.write(sb.toString().getBytes("UTF-8"));
                                    outputStream.flush();
                                }
                                int responseCode = httpURLConnection.getResponseCode();
                                StringBuilder sb2 = new StringBuilder();
                                if (responseCode >= 200 && responseCode < 400) {
                                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                    while (true) {
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line == null) {
                                                break;
                                            } else {
                                                sb2.append(line).append("\n");
                                            }
                                        } catch (Exception e) {
                                            e = e;
                                            bufferedReader2 = bufferedReader;
                                            Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.2.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    apiCallback.onError(e);
                                                }
                                            });
                                            if (bufferedReader2 != null) {
                                                bufferedReader2.close();
                                            }
                                            if (outputStream != null) {
                                                return;
                                            } else {
                                                outputStream.close();
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            bufferedReader2 = bufferedReader;
                                            if (bufferedReader2 != null) {
                                                bufferedReader2.close();
                                            }
                                            if (outputStream != null) {
                                                outputStream.close();
                                                throw th;
                                            }
                                            throw th;
                                        }
                                    }
                                    Api.notifyResult(apiCallback, new JSONObject(sb2.toString()));
                                    bufferedReader.close();
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                } else {
                                    BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                                    while (true) {
                                        try {
                                            String line2 = bufferedReader3.readLine();
                                            if (line2 == null) {
                                                break;
                                            } else {
                                                sb2.append(line2).append("\n");
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            bufferedReader2 = bufferedReader3;
                                            Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.2.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    apiCallback.onError(e);
                                                }
                                            });
                                            if (bufferedReader2 != null) {
                                                try {
                                                    bufferedReader2.close();
                                                } catch (IOException e3) {
                                                    e3.printStackTrace();
                                                }
                                            }
                                            if (outputStream != null) {
                                                return;
                                            } else {
                                                outputStream.close();
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            bufferedReader2 = bufferedReader3;
                                            if (bufferedReader2 != null) {
                                                try {
                                                    bufferedReader2.close();
                                                } catch (IOException e4) {
                                                    e4.printStackTrace();
                                                }
                                            }
                                            if (outputStream != null) {
                                                try {
                                                    outputStream.close();
                                                    throw th;
                                                } catch (IOException e5) {
                                                    e5.printStackTrace();
                                                    throw th;
                                                }
                                            }
                                            throw th;
                                        }
                                    }
                                    Api.notifyError(apiCallback, new ServerError(sb2.toString(), responseCode));
                                    bufferedReader = bufferedReader3;
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                    }
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                }
                            } catch (Exception e7) {
                                e = e7;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (Exception e8) {
                        e = e8;
                        outputStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        outputStream = null;
                    }
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
        });
    }

    public static void getWithHeader(final String str, final Map<String, String> map, final Map<String, String> map2, final ApiCallback<JSONObject> apiCallback) {
        if (TextUtils.isEmpty(str)) {
            notifyError(apiCallback, new RuntimeException("invalid url =" + str));
            return;
        }
        final com.taptap.sdk.AccessToken currentAccessToken = com.taptap.sdk.AccessToken.getCurrentAccessToken();
        if (currentAccessToken == null) {
            notifyError(apiCallback, new RuntimeException("invalid token =" + str));
        } else {
            Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.3
                /* JADX WARN: Code duplicated, block: B:54:0x013c A[Catch: IOException -> 0x0124, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0124, blocks: (B:41:0x0120, B:54:0x013c), top: B:65:0x004b }] */
                /* JADX WARN: Code duplicated, block: B:62:0x0142 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:85:? A[RETURN, SYNTHETIC] */
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    BufferedReader bufferedReader;
                    Uri uriBuild = Uri.parse(str);
                    if (map2 != null) {
                        Uri.Builder builderBuildUpon = uriBuild.buildUpon();
                        for (Map.Entry entry : map2.entrySet()) {
                            if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                                builderBuildUpon.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
                            }
                        }
                        uriBuild = builderBuildUpon.build();
                    }
                    String string = uriBuild.toString();
                    BufferedReader bufferedReader2 = null;
                    try {
                        try {
                            try {
                                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(string).openConnection();
                                httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                                httpURLConnection.addRequestProperty("User-Agent", Api.getUA());
                                httpURLConnection.addRequestProperty("Authorization", Api.getAuthorization(string, HttpRequest.METHOD_GET, currentAccessToken.kid, currentAccessToken.mac_key));
                                httpURLConnection.setConnectTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
                                httpURLConnection.setReadTimeout(AbstractSpiCall.DEFAULT_TIMEOUT);
                                Map map3 = map;
                                if (map3 != null) {
                                    for (Map.Entry entry2 : map3.entrySet()) {
                                        if (!TextUtils.isEmpty((CharSequence) entry2.getKey())) {
                                            httpURLConnection.addRequestProperty((String) entry2.getKey(), (String) entry2.getValue());
                                        }
                                    }
                                }
                                int responseCode = httpURLConnection.getResponseCode();
                                StringBuilder sb = new StringBuilder();
                                if (responseCode >= 200 && responseCode < 400) {
                                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                    while (true) {
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line == null) {
                                                break;
                                            } else {
                                                sb.append(line).append("\n");
                                            }
                                        } catch (Exception e) {
                                            bufferedReader2 = bufferedReader;
                                            e = e;
                                            Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.3.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    apiCallback.onError(e);
                                                }
                                            });
                                            if (bufferedReader2 != null) {
                                                return;
                                            } else {
                                                bufferedReader2.close();
                                            }
                                        } catch (Throwable th) {
                                            bufferedReader2 = bufferedReader;
                                            th = th;
                                            if (bufferedReader2 != null) {
                                                bufferedReader2.close();
                                            }
                                            throw th;
                                        }
                                    }
                                    Api.notifyResult(apiCallback, new JSONObject(sb.toString()));
                                    bufferedReader.close();
                                } else {
                                    BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                                    while (true) {
                                        try {
                                            String line2 = bufferedReader3.readLine();
                                            if (line2 == null) {
                                                break;
                                            } else {
                                                sb.append(line2).append("\n");
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            bufferedReader2 = bufferedReader3;
                                            Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.3.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    apiCallback.onError(e);
                                                }
                                            });
                                            if (bufferedReader2 != null) {
                                                return;
                                            } else {
                                                bufferedReader2.close();
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            bufferedReader2 = bufferedReader3;
                                            if (bufferedReader2 != null) {
                                                try {
                                                    bufferedReader2.close();
                                                } catch (IOException e3) {
                                                    e3.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                    }
                                    Api.notifyError(apiCallback, new ServerError(sb.toString(), responseCode));
                                    bufferedReader = bufferedReader3;
                                    bufferedReader.close();
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        } catch (Exception e5) {
                            e = e5;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAuthorization(String str, String str2, String str3, String str4) {
        try {
            URL url = new URL(str);
            String time = getTime();
            String randomString = getRandomString(5);
            String host = url.getHost();
            return "MAC " + getAuthorizationParam("id", str3) + "," + getAuthorizationParam("ts", time) + "," + getAuthorizationParam("nonce", randomString) + "," + getAuthorizationParam("mac", sign(mergeSign(time, randomString, str2, str.substring(str.lastIndexOf(host) + host.length()), host, str.startsWith(Keys.Scheme.HTTPS) ? "443" : "80", ""), str4));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRandomString(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(36)));
        }
        return stringBuffer.toString();
    }

    private static String sign(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return new String(Base64.encode(mac.doFinal(str.getBytes("UTF-8")), 2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e2) {
            throw new IllegalStateException(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new IllegalStateException(e3);
        }
    }

    private static String mergeSign(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
            return null;
        }
        String str8 = str + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5 + "\n" + str6 + "\n";
        if (TextUtils.isEmpty(str7)) {
            return str8 + "\n";
        }
        return str8 + str7 + "\n";
    }

    private static String getAuthorizationParam(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return str + "=\"" + str2 + "\"";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyError(final ApiCallback apiCallback, final Throwable th) {
        Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.4
            @Override // java.lang.Runnable
            public void run() {
                apiCallback.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyResult(final ApiCallback apiCallback, final JSONObject jSONObject) {
        Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.5
            @Override // java.lang.Runnable
            public void run() {
                apiCallback.onSuccess(jSONObject);
            }
        });
    }

    private static void checkTime(long j) {
        if (j > 0) {
            try {
                TimeVerifier.setDelta((j * 1000) - System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getTime() {
        return String.format(Locale.US, "%010d", Long.valueOf(TimeVerifier.getCurrentTimeMillions() / 1000));
    }
}
