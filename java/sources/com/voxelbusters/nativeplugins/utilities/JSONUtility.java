package com.voxelbusters.nativeplugins.utilities;

import android.os.Bundle;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JSONUtility {
    public static JSONObject getJSONfromBundle(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, bundle.get(str));
            } catch (JSONException e) {
                e.printStackTrace();
                Debug.error(CommonDefines.JSON_UTILS_TAG, "Exception while entering key " + str);
            }
        }
        return jSONObject;
    }

    public static String[] getKeys(JSONObject jSONObject) {
        JSONArray jSONArrayNames = jSONObject.names();
        String[] strArr = new String[jSONArrayNames.length()];
        int length = jSONArrayNames.length();
        for (int i = 0; i < length; i++) {
            try {
                strArr[i] = jSONArrayNames.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strArr;
    }

    public static JSONArray getJSONArray(String str) {
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static JSONArray removeIndex(JSONArray jSONArray, int i) {
        JSONArray jSONArray2 = new JSONArray();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                if (i2 != i) {
                    jSONArray2.put(jSONArray.get(i2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jSONArray2;
    }

    public static int findString(JSONArray jSONArray, String str) {
        String string;
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                string = jSONArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
                string = null;
            }
            if (string != null && string.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public static String getJSONString(HashMap map) {
        return new JSONObject(map).toString();
    }

    public static JSONObject getJSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
