package retrofit;

/* JADX INFO: loaded from: classes.dex */
public final class Endpoints {
    private static final String DEFAULT_NAME = "default";

    private Endpoints() {
    }

    public static Endpoint newFixedEndpoint(String str) {
        return new FixedEndpoint(str, DEFAULT_NAME);
    }

    public static Endpoint newFixedEndpoint(String str, String str2) {
        return new FixedEndpoint(str, str2);
    }

    private static class FixedEndpoint implements Endpoint {
        private final String apiUrl;
        private final String name;

        FixedEndpoint(String str, String str2) {
            this.apiUrl = str;
            this.name = str2;
        }

        @Override // retrofit.Endpoint
        public String getUrl() {
            return this.apiUrl;
        }

        @Override // retrofit.Endpoint
        public String getName() {
            return this.name;
        }
    }
}
