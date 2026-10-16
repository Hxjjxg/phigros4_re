package com.twitter.sdk.android.core;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.AuthToken;

/* JADX INFO: loaded from: classes.dex */
public class Session<T extends AuthToken> {

    @SerializedName("auth_token")
    private final T authToken;

    @SerializedName("id")
    private final long id;

    public Session(T t, long j) {
        this.authToken = t;
        this.id = j;
    }

    public T getAuthToken() {
        return this.authToken;
    }

    public long getId() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Session session = (Session) obj;
        if (this.id != session.id) {
            return false;
        }
        T t = this.authToken;
        T t2 = session.authToken;
        return t == null ? t2 == null : t.equals(t2);
    }

    public int hashCode() {
        T t = this.authToken;
        int iHashCode = t != null ? t.hashCode() : 0;
        long j = this.id;
        return (iHashCode * 31) + ((int) (j ^ (j >>> 32)));
    }
}
