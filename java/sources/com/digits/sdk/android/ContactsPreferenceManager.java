package com.digits.sdk.android;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

/* JADX INFO: loaded from: classes.dex */
class ContactsPreferenceManager {
    static final String PREFKEY_CONTACTS_IMPORT_PERMISSION = "CONTACTS_IMPORT_PERMISSION";
    static final String PREFKEY_CONTACTS_READ_TIMESTAMP = "CONTACTS_READ_TIMESTAMP";
    static final String PREFKEY_CONTACTS_UPLOADED = "CONTACTS_CONTACTS_UPLOADED";
    private final PreferenceStore prefStore = new PreferenceStoreImpl(Fabric.getKit(Digits.class));

    ContactsPreferenceManager() {
    }

    protected boolean hasContactImportPermissionGranted() {
        return this.prefStore.get().getBoolean(PREFKEY_CONTACTS_IMPORT_PERMISSION, false);
    }

    protected void setContactImportPermissionGranted() {
        PreferenceStore preferenceStore = this.prefStore;
        preferenceStore.save(preferenceStore.edit().putBoolean(PREFKEY_CONTACTS_IMPORT_PERMISSION, true));
    }

    protected void clearContactImportPermissionGranted() {
        PreferenceStore preferenceStore = this.prefStore;
        preferenceStore.save(preferenceStore.edit().remove(PREFKEY_CONTACTS_IMPORT_PERMISSION));
    }

    protected void setContactsReadTimestamp(long j) {
        PreferenceStore preferenceStore = this.prefStore;
        preferenceStore.save(preferenceStore.edit().putLong(PREFKEY_CONTACTS_READ_TIMESTAMP, j));
    }

    protected void setContactsUploaded(int i) {
        PreferenceStore preferenceStore = this.prefStore;
        preferenceStore.save(preferenceStore.edit().putInt(PREFKEY_CONTACTS_UPLOADED, i));
    }
}
