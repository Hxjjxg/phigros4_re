package com.twitter.sdk.android.core.internal.scribe;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ScribeEvent {
    private static final String CURRENT_FORMAT_VERSION = "2";

    @SerializedName("_category_")
    private final String category;

    @SerializedName("event_namespace")
    private final EventNamespace eventNamespace;

    @SerializedName("format_version")
    private final String formatVersion = "2";

    @SerializedName("ts")
    private final String timestamp;

    public ScribeEvent(String str, EventNamespace eventNamespace, long j) {
        this.category = str;
        this.eventNamespace = eventNamespace;
        this.timestamp = String.valueOf(j);
    }

    public String toString() {
        return "event_namespace=" + this.eventNamespace + ", ts=" + this.timestamp + ", format_version=" + this.formatVersion + ", _category_=" + this.category;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScribeEvent scribeEvent = (ScribeEvent) obj;
        String str = this.category;
        if (str == null ? scribeEvent.category != null : !str.equals(scribeEvent.category)) {
            return false;
        }
        EventNamespace eventNamespace = this.eventNamespace;
        if (eventNamespace == null ? scribeEvent.eventNamespace != null : !eventNamespace.equals(scribeEvent.eventNamespace)) {
            return false;
        }
        String str2 = this.formatVersion;
        if (str2 == null ? scribeEvent.formatVersion != null : !str2.equals(scribeEvent.formatVersion)) {
            return false;
        }
        String str3 = this.timestamp;
        String str4 = scribeEvent.timestamp;
        return str3 == null ? str4 == null : str3.equals(str4);
    }

    public int hashCode() {
        EventNamespace eventNamespace = this.eventNamespace;
        int iHashCode = (eventNamespace != null ? eventNamespace.hashCode() : 0) * 31;
        String str = this.timestamp;
        int iHashCode2 = (iHashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.formatVersion;
        int iHashCode3 = (iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.category;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public static class Transform implements EventTransform<ScribeEvent> {
        private final Gson gson;

        public Transform(Gson gson) {
            this.gson = gson;
        }

        @Override // io.fabric.sdk.android.services.events.EventTransform
        public byte[] toBytes(ScribeEvent scribeEvent) throws IOException {
            return this.gson.toJson(scribeEvent).getBytes("UTF-8");
        }
    }
}
