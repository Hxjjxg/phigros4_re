package com.voxelbusters.nativeplugins.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class SharedPreferencesUtility {
    public static JSONArray getJsonArray(String str, int i, Context context, String str2) {
        return JSONUtility.getJSONArray(getSharedPreferences(str, i, context).getString(str2, ""));
    }

    public static SharedPreferences getSharedPreferences(String str, int i, Context context) {
        return context.getSharedPreferences(str, i);
    }

    public static void setJSONArray(String str, int i, Context context, String str2, JSONArray jSONArray) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(str, i, context).edit();
        editorEdit.putString(str2, jSONArray.toString());
        editorEdit.commit();
    }
}
