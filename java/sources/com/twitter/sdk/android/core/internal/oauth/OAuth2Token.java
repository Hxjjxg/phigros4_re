package com.twitter.sdk.android.core.internal.oauth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.tapsdk.antiaddiction.constants.Constants;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OAuth2Token extends AuthToken implements Parcelable {
    public static final Parcelable.Creator<OAuth2Token> CREATOR = new Parcelable.Creator<OAuth2Token>() { // from class: com.twitter.sdk.android.core.internal.oauth.OAuth2Token.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OAuth2Token createFromParcel(Parcel parcel) {
            return new OAuth2Token(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OAuth2Token[] newArray(int i) {
            return new OAuth2Token[i];
        }
    };
    public static final String TOKEN_TYPE_BEARER = "bearer";

    @SerializedName(Constants.CacheData.ACCESS_TOKEN)
    private final String accessToken;

    @SerializedName("token_type")
    private final String tokenType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.twitter.sdk.android.core.AuthToken
    public boolean isExpired() {
        return false;
    }

    public OAuth2Token(String str, String str2) {
        this.tokenType = str;
        this.accessToken = str2;
    }

    public OAuth2Token(String str, String str2, long j) {
        super(j);
        this.tokenType = str;
        this.accessToken = str2;
    }

    private OAuth2Token(Parcel parcel) {
        this.tokenType = parcel.readString();
        this.accessToken = parcel.readString();
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    @Override // com.twitter.sdk.android.core.internal.oauth.AuthHeaders
    public Map<String, String> getAuthHeaders(TwitterAuthConfig twitterAuthConfig, String str, String str2, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("Authorization", OAuth2Service.getAuthorizationHeader(this));
        return map2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tokenType);
        parcel.writeString(this.accessToken);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OAuth2Token oAuth2Token = (OAuth2Token) obj;
        String str = this.accessToken;
        if (str == null ? oAuth2Token.accessToken != null : !str.equals(oAuth2Token.accessToken)) {
            return false;
        }
        String str2 = this.tokenType;
        String str3 = oAuth2Token.tokenType;
        return str2 == null ? str3 == null : str2.equals(str3);
    }

    public int hashCode() {
        String str = this.tokenType;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.accessToken;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }
}
