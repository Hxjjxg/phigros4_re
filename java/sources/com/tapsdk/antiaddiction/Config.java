package com.tapsdk.antiaddiction;

/* JADX INFO: loaded from: classes.dex */
public class Config {
    public final String clientId;
    public final Region region;
    public final boolean showSwitchAccount;
    public final boolean useAgeRange;

    @Deprecated
    public final boolean useTapLogin;

    private Config(Builder builder) {
        this.clientId = builder.clientId;
        this.useTapLogin = builder.useTapLogin;
        this.showSwitchAccount = builder.showSwitchAccount;
        this.region = builder.region;
        this.useAgeRange = builder.useAgeRange;
    }

    public static class Builder {
        private String clientId = "";
        private boolean useTapLogin = false;
        private boolean showSwitchAccount = false;
        private Region region = Region.China;
        private boolean useAgeRange = true;

        public Builder withClientId(String str) {
            this.clientId = str;
            return this;
        }

        @Deprecated
        public Builder enableTapLogin(boolean z) {
            this.useTapLogin = z;
            return this;
        }

        public Builder showSwitchAccount(boolean z) {
            this.showSwitchAccount = z;
            return this;
        }

        public Builder useAgeRange(boolean z) {
            this.useAgeRange = z;
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
