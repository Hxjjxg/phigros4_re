package io.fabric.sdk.android.services.persistence;

import android.content.Context;
import android.os.Environment;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class FileStoreImpl implements FileStore {
    private final String contentPath;
    private final Context context;
    private final String legacySupport;

    public FileStoreImpl(Kit kit) {
        if (kit.getContext() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        Context context = kit.getContext();
        this.context = context;
        this.contentPath = kit.getPath();
        this.legacySupport = "Android/" + context.getPackageName();
    }

    @Override // io.fabric.sdk.android.services.persistence.FileStore
    public File getCacheDir() {
        return prepare(this.context.getCacheDir());
    }

    @Override // io.fabric.sdk.android.services.persistence.FileStore
    public File getExternalCacheDir() {
        return prepare(isExternalStorageAvailable() ? this.context.getExternalCacheDir() : null);
    }

    @Override // io.fabric.sdk.android.services.persistence.FileStore
    public File getFilesDir() {
        return prepare(this.context.getFilesDir());
    }

    @Override // io.fabric.sdk.android.services.persistence.FileStore
    public File getExternalFilesDir() {
        return prepare(isExternalStorageAvailable() ? this.context.getExternalFilesDir(null) : null);
    }

    File prepare(File file) {
        if (file != null) {
            if (file.exists() || file.mkdirs()) {
                return file;
            }
            Fabric.getLogger().w(Fabric.TAG, "Couldn't create file");
            return null;
        }
        Fabric.getLogger().d(Fabric.TAG, "Null File");
        return null;
    }

    boolean isExternalStorageAvailable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        Fabric.getLogger().w(Fabric.TAG, "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
        return false;
    }
}
