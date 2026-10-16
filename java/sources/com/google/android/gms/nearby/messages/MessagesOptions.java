package com.google.android.gms.nearby.messages;

import com.google.android.gms.common.api.Api;

/* JADX INFO: loaded from: classes.dex */
public class MessagesOptions implements Api.ApiOptions.Optional {
    public final String zzff;
    public final boolean zzfg;
    public final int zzfh;
    public final String zzfi;
    public final String zzfj;

    public static class Builder {
        private int zzfh = -1;

        public MessagesOptions build() {
            return new MessagesOptions(this);
        }

        public Builder setPermissions(int i) {
            this.zzfh = i;
            return this;
        }
    }

    private MessagesOptions(Builder builder) {
        this.zzff = null;
        this.zzfg = false;
        this.zzfh = builder.zzfh;
        this.zzfi = null;
        this.zzfj = null;
    }
}
