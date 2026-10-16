package com.digits.sdk.android;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import com.digits.sdk.android.concurrent.DigitsRetryPolicy;
import com.digits.sdk.android.concurrent.RetryExecutor;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class ContactsUploadService extends IntentService {
    private static final String THREAD_NAME = "UPLOAD_WORKER";
    private static final int TIMEOUT_IN_SECONDS = 300;
    public static final String UPLOAD_COMPLETE = "com.digits.sdk.android.UPLOAD_COMPLETE";
    public static final String UPLOAD_FAILED = "com.digits.sdk.android.UPLOAD_FAILED";
    private ContactsClient contactsClient;
    private RetryExecutor executor;
    private ContactsHelper helper;
    private ContactsPreferenceManager prefManager;

    public ContactsUploadService() {
        super(THREAD_NAME);
        init(Digits.getInstance().getContactsClient(), new ContactsHelper(this), new ContactsPreferenceManager(), new RetryExecutor(2, new DigitsRetryPolicy(1)));
    }

    ContactsUploadService(ContactsClient contactsClient, ContactsHelper contactsHelper, ContactsPreferenceManager contactsPreferenceManager, RetryExecutor retryExecutor) {
        super(THREAD_NAME);
        init(contactsClient, contactsHelper, contactsPreferenceManager, retryExecutor);
    }

    private void init(ContactsClient contactsClient, ContactsHelper contactsHelper, ContactsPreferenceManager contactsPreferenceManager, RetryExecutor retryExecutor) {
        this.contactsClient = contactsClient;
        this.helper = contactsHelper;
        this.prefManager = contactsPreferenceManager;
        this.executor = retryExecutor;
        setIntentRedelivery(true);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        this.prefManager.setContactImportPermissionGranted();
        try {
            List<String> allCards = getAllCards();
            int size = allCards.size();
            int numberOfPages = getNumberOfPages(size);
            for (int i = 0; i < numberOfPages; i++) {
                int i2 = i * 100;
                final Vcards vcards = new Vcards(allCards.subList(i2, Math.min(size, i2 + 100)));
                this.executor.execute(new Runnable() { // from class: com.digits.sdk.android.ContactsUploadService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ContactsUploadService.this.contactsClient.uploadContacts(vcards);
                    }
                });
            }
            this.executor.shutdown();
            if (this.executor.awaitTermination(300L, TimeUnit.SECONDS)) {
                this.prefManager.setContactsReadTimestamp(System.currentTimeMillis());
                this.prefManager.setContactsUploaded(size);
                sendSuccessBroadcast();
            } else {
                this.executor.shutdownNow();
                sendFailureBroadcast();
            }
        } catch (InterruptedException unused) {
            sendFailureBroadcast();
        } catch (Exception unused2) {
            sendFailureBroadcast();
        }
    }

    int getNumberOfPages(int i) {
        return ((i + 100) - 1) / 100;
    }

    List<String> getAllCards() {
        List list = Collections.EMPTY_LIST;
        Cursor contactsCursor = null;
        try {
            contactsCursor = this.helper.getContactsCursor();
            return this.helper.createContactList(contactsCursor);
        } finally {
            if (contactsCursor != null) {
                contactsCursor.close();
            }
        }
    }

    void sendFailureBroadcast() {
        sendBroadcast(new Intent(UPLOAD_FAILED));
    }

    void sendSuccessBroadcast() {
        sendBroadcast(new Intent(UPLOAD_COMPLETE));
    }
}
