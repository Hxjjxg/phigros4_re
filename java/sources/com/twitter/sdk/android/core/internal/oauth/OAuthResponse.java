package com.twitter.sdk.android.core.internal.oauth;

import android.os.Parcel;
import android.os.Parcelable;
import com.twitter.sdk.android.core.TwitterAuthToken;

/* JADX INFO: loaded from: classes.dex */
public class OAuthResponse implements Parcelable {
    public static final Parcelable.Creator<OAuthResponse> CREATOR = new Parcelable.Creator<OAuthResponse>() { // from class: com.twitter.sdk.android.core.internal.oauth.OAuthResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OAuthResponse createFromParcel(Parcel parcel) {
            return new OAuthResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OAuthResponse[] newArray(int i) {
            return new OAuthResponse[i];
        }
    };
    public final TwitterAuthToken authToken;
    public final long userId;
    public final String userName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OAuthResponse(TwitterAuthToken twitterAuthToken, String str, long j) {
        this.authToken = twitterAuthToken;
        this.userName = str;
        this.userId = j;
    }

    private OAuthResponse(Parcel parcel) {
        this.authToken = (TwitterAuthToken) parcel.readParcelable(TwitterAuthToken.class.getClassLoader());
        this.userName = parcel.readString();
        this.userId = parcel.readLong();
    }

    public String toString() {
        return "authToken=" + this.authToken + ",userName=" + this.userName + ",userId=" + this.userId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.authToken, i);
        parcel.writeString(this.userName);
        parcel.writeLong(this.userId);
    }
}
