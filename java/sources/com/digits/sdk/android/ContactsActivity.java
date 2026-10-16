package com.digits.sdk.android;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class ContactsActivity extends Activity {
    ContactsActivityDelegateImpl delegate;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(getIntent().getIntExtra(ThemeUtils.THEME_RESOURCE_ID, R.style.Digits_default));
        super.onCreate(bundle);
        ContactsActivityDelegateImpl contactsActivityDelegateImpl = new ContactsActivityDelegateImpl(this);
        this.delegate = contactsActivityDelegateImpl;
        contactsActivityDelegateImpl.init();
    }
}
