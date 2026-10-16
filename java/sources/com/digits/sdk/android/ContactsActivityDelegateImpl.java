package com.digits.sdk.android;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
class ContactsActivityDelegateImpl implements ContactsActivityDelegate {
    final Activity activity;
    final ContactsController controller;

    public ContactsActivityDelegateImpl(Activity activity) {
        this(activity, new ContactsControllerImpl());
    }

    public ContactsActivityDelegateImpl(Activity activity, ContactsController contactsController) {
        this.activity = activity;
        this.controller = contactsController;
    }

    @Override // com.digits.sdk.android.ContactsActivityDelegate
    public void init() {
        setContentView();
        setUpViews();
    }

    protected void setContentView() {
        this.activity.setContentView(R.layout.dgts__activity_contacts);
    }

    protected void setUpViews() {
        Button button = (Button) this.activity.findViewById(R.id.dgts__not_now);
        Button button2 = (Button) this.activity.findViewById(R.id.dgts__okay);
        setUpNotNowButton(button);
        setUpOkayButton(button2);
    }

    protected void setUpNotNowButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.ContactsActivityDelegateImpl.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ContactsActivityDelegateImpl.this.activity.finish();
            }
        });
    }

    protected void setUpOkayButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.ContactsActivityDelegateImpl.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ContactsActivityDelegateImpl.this.controller.startUploadService(ContactsActivityDelegateImpl.this.activity);
                ContactsActivityDelegateImpl.this.activity.finish();
            }
        });
    }
}
