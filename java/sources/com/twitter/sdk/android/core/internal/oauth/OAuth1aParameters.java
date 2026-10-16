package com.twitter.sdk.android.core.internal.oauth;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
class OAuth1aParameters {
    private static final SecureRandom RAND = new SecureRandom();
    private static final String SIGNATURE_METHOD = "HMAC-SHA1";
    private static final String VERSION = "1.0";
    private final TwitterAuthConfig authConfig;
    private final TwitterAuthToken authToken;
    private final String callback;
    private final String method;
    private final Map<String, String> postParams;
    private final String url;

    public OAuth1aParameters(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken, String str, String str2, String str3, Map<String, String> map) {
        this.authConfig = twitterAuthConfig;
        this.authToken = twitterAuthToken;
        this.callback = str;
        this.method = str2;
        this.url = str3;
        this.postParams = map;
    }

    public String getAuthorizationHeader() {
        String nonce = getNonce();
        String timestamp = getTimestamp();
        return constructAuthorizationHeader(nonce, timestamp, calculateSignature(constructSignatureBase(nonce, timestamp)));
    }

    private String getNonce() {
        return String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(RAND.nextLong()));
    }

    private String getTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    String constructSignatureBase(String str, String str2) {
        URI uriCreate = URI.create(this.url);
        TreeMap<String, String> queryParams = UrlUtils.getQueryParams(uriCreate, true);
        Map<String, String> map = this.postParams;
        if (map != null) {
            queryParams.putAll(map);
        }
        String str3 = this.callback;
        if (str3 != null) {
            queryParams.put(OAuthConstants.PARAM_CALLBACK, str3);
        }
        queryParams.put(OAuthConstants.PARAM_CONSUMER_KEY, this.authConfig.getConsumerKey());
        queryParams.put(OAuthConstants.PARAM_NONCE, str);
        queryParams.put(OAuthConstants.PARAM_SIGNATURE_METHOD, SIGNATURE_METHOD);
        queryParams.put(OAuthConstants.PARAM_TIMESTAMP, str2);
        TwitterAuthToken twitterAuthToken = this.authToken;
        if (twitterAuthToken != null && twitterAuthToken.token != null) {
            queryParams.put(OAuthConstants.PARAM_TOKEN, this.authToken.token);
        }
        queryParams.put(OAuthConstants.PARAM_VERSION, "1.0");
        return this.method.toUpperCase(Locale.ENGLISH) + '&' + UrlUtils.percentEncode(uriCreate.getScheme() + "://" + uriCreate.getHost() + uriCreate.getPath()) + '&' + getEncodedQueryParams(queryParams);
    }

    private String getEncodedQueryParams(TreeMap<String, String> treeMap) {
        StringBuilder sb = new StringBuilder();
        int size = treeMap.size();
        int i = 0;
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            sb.append(UrlUtils.percentEncode(UrlUtils.percentEncode(entry.getKey()))).append("%3D").append(UrlUtils.percentEncode(UrlUtils.percentEncode(entry.getValue())));
            i++;
            if (i < size) {
                sb.append("%26");
            }
        }
        return sb.toString();
    }

    String calculateSignature(String str) {
        try {
            String signingKey = getSigningKey();
            byte[] bytes = str.getBytes(UrlUtils.UTF8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(signingKey.getBytes(UrlUtils.UTF8), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            byte[] bArrDoFinal = mac.doFinal(bytes);
            return new String(HttpRequest.Base64.encodeBytesToBytes(bArrDoFinal, 0, bArrDoFinal.length), UrlUtils.UTF8);
        } catch (UnsupportedEncodingException e) {
            Fabric.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e);
            return "";
        } catch (InvalidKeyException e2) {
            Fabric.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e2);
            return "";
        } catch (NoSuchAlgorithmException e3) {
            Fabric.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e3);
            return "";
        }
    }

    private String getSigningKey() {
        TwitterAuthToken twitterAuthToken = this.authToken;
        return UrlUtils.urlEncode(this.authConfig.getConsumerSecret()) + '&' + UrlUtils.urlEncode(twitterAuthToken != null ? twitterAuthToken.secret : null);
    }

    String constructAuthorizationHeader(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("OAuth");
        appendParameter(sb, OAuthConstants.PARAM_CALLBACK, this.callback);
        appendParameter(sb, OAuthConstants.PARAM_CONSUMER_KEY, this.authConfig.getConsumerKey());
        appendParameter(sb, OAuthConstants.PARAM_NONCE, str);
        appendParameter(sb, OAuthConstants.PARAM_SIGNATURE, str3);
        appendParameter(sb, OAuthConstants.PARAM_SIGNATURE_METHOD, SIGNATURE_METHOD);
        appendParameter(sb, OAuthConstants.PARAM_TIMESTAMP, str2);
        TwitterAuthToken twitterAuthToken = this.authToken;
        appendParameter(sb, OAuthConstants.PARAM_TOKEN, twitterAuthToken != null ? twitterAuthToken.token : null);
        appendParameter(sb, OAuthConstants.PARAM_VERSION, "1.0");
        return sb.substring(0, sb.length() - 1);
    }

    private void appendParameter(StringBuilder sb, String str, String str2) {
        if (str2 != null) {
            sb.append(' ').append(UrlUtils.percentEncode(str)).append("=\"").append(UrlUtils.percentEncode(str2)).append("\",");
        }
    }
}
