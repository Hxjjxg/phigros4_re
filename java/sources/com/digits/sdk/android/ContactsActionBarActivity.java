package com.digits.sdk.android;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarActivity;

/* JADX INFO: loaded from: classes.dex */
public class ContactsActionBarActivity extends ActionBarActivity {
    ContactsActivityDelegateImpl delegate;

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        setTheme(getIntent().getIntExtra(ThemeUtils.THEME_RESOURCE_ID, R.style.Digits_default));
        super.onCreate(bundle);
        ContactsActivityDelegateImpl contactsActivityDelegateImpl = new ContactsActivityDelegateImpl(this);
        this.delegate = contactsActivityDelegateImpl;
        contactsActivityDelegateImpl.init();
    }
}
