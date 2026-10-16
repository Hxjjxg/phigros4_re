package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;
import com.tapsdk.antiaddiction.constants.Constants;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UserEntities {

    @SerializedName(Constants.MsgExtraParams.DESCRIPTION)
    public final UrlEntities description;

    @SerializedName("url")
    public final UrlEntities url;

    public UserEntities(UrlEntities urlEntities, UrlEntities urlEntities2) {
        this.url = urlEntities;
        this.description = urlEntities2;
    }

    public static class UrlEntities {

        @SerializedName("urls")
        public final List<UrlEntity> urls;

        public UrlEntities(List<UrlEntity> list) {
            this.urls = getSafeList(list);
        }

        private <T> List<T> getSafeList(List<T> list) {
            if (list == null) {
                return Collections.EMPTY_LIST;
            }
            return Collections.unmodifiableList(list);
        }
    }
}
