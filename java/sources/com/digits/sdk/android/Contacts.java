package com.digits.sdk.android;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Contacts {

    @SerializedName("next_cursor")
    public String nextCursor;

    @SerializedName("users")
    public ArrayList<User> users;
}
