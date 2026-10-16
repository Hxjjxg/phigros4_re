package com.tds.common.net;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PlatformXUA {
    private String engineTdsId;
    Map<String, String> xuaMap;

    private static class Holder {
        private static final PlatformXUA INSTANCE = new PlatformXUA();

        private Holder() {
        }
    }

    private PlatformXUA() {
        this.engineTdsId = "";
    }

    public static PlatformXUA getInstance() {
        return Holder.INSTANCE;
    }

    public Map<String, String> getXuaMap() {
        return this.xuaMap;
    }

    public void setXuaMap(Map<String, String> map) {
        this.xuaMap = map;
    }

    public void setEngineTdsId(String str) {
        this.engineTdsId = str;
    }

    public String getEngineTdsId() {
        return this.engineTdsId;
    }

    public String getTrackUA() {
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = this.xuaMap;
        if (map != null && map.containsKey("Engine-Platform") && this.xuaMap.containsKey("Engine-Version")) {
            sb.append("TapSDK-").append(this.xuaMap.get("Engine-Platform")).append("/").append(this.xuaMap.get("Engine-Version")).append(" ");
        }
        sb.append("TapSDK-Android/3.30.3");
        return sb.toString();
    }

    public String getArtifact() {
        Map<String, String> map = this.xuaMap;
        return (map == null || !map.containsKey("Engine-Platform")) ? "Android" : this.xuaMap.get("Engine-Platform");
    }
}
