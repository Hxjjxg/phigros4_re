package io.fabric.sdk.android.services.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import io.fabric.sdk.android.Kit;

/* JADX INFO: loaded from: classes.dex */
public class PreferenceStoreImpl implements PreferenceStore {
    private final Context context;
    private final String preferenceName;
    private final SharedPreferences sharedPreferences;

    public PreferenceStoreImpl(Context context, String str) {
        if (context == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.context = context;
        this.preferenceName = str;
        this.sharedPreferences = context.getSharedPreferences(str, 0);
    }

    public PreferenceStoreImpl(Kit kit) {
        this(kit.getContext(), kit.getClass().getName());
    }

    @Override // io.fabric.sdk.android.services.persistence.PreferenceStore
    public SharedPreferences get() {
        return this.sharedPreferences;
    }

    @Override // io.fabric.sdk.android.services.persistence.PreferenceStore
    public SharedPreferences.Editor edit() {
        return this.sharedPreferences.edit();
    }

    @Override // io.fabric.sdk.android.services.persistence.PreferenceStore
    public boolean save(SharedPreferences.Editor editor) {
        editor.apply();
        return true;
    }
}
