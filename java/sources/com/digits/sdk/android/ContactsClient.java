package com.digits.sdk.android;

import android.content.Context;
import android.content.Intent;
import com.twitter.sdk.android.core.AuthenticatedClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.NewHtcHomeBadger;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/* JADX INFO: loaded from: classes.dex */
public class ContactsClient {
    public static final int MAX_PAGE_SIZE = 100;
    private ActivityClassManagerFactory activityClassManagerFactory;
    private ContactsService contactsService;
    private final ContactsPreferenceManager prefManager;
    private final TwitterCore twitterCore;

    interface ContactsService {
        @POST("/1.1/contacts/destroy/all.json")
        void deleteAll(ContactsCallback<Response> contactsCallback);

        @POST("/1.1/contacts/upload.json")
        UploadResponse upload(@Body Vcards vcards);

        @GET("/1.1/contacts/users_and_uploaded_by.json")
        void usersAndUploadedBy(@Query("next_cursor") String str, @Query(NewHtcHomeBadger.COUNT) Integer num, ContactsCallback<Contacts> contactsCallback);
    }

    ContactsClient() {
        this(TwitterCore.getInstance(), new ContactsPreferenceManager(), new ActivityClassManagerFactory(), null);
    }

    ContactsClient(TwitterCore twitterCore, ContactsPreferenceManager contactsPreferenceManager, ActivityClassManagerFactory activityClassManagerFactory, ContactsService contactsService) {
        if (twitterCore == null) {
            throw new IllegalArgumentException("twitter must not be null");
        }
        if (contactsPreferenceManager == null) {
            throw new IllegalArgumentException("preference manager must not be null");
        }
        if (activityClassManagerFactory == null) {
            throw new IllegalArgumentException("activityClassManagerFactory must not be null");
        }
        this.twitterCore = twitterCore;
        this.prefManager = contactsPreferenceManager;
        this.activityClassManagerFactory = activityClassManagerFactory;
        this.contactsService = contactsService;
    }

    public void startContactsUpload() {
        startContactsUpload(R.style.Digits_default);
    }

    public void startContactsUpload(int i) {
        startContactsUpload(this.twitterCore.getContext(), i);
    }

    protected void startContactsUpload(Context context, int i) {
        if (!this.prefManager.hasContactImportPermissionGranted()) {
            startContactsActivity(context, i);
        } else {
            startContactsService(context);
        }
    }

    private void startContactsActivity(Context context, int i) {
        Intent intent = new Intent(context, this.activityClassManagerFactory.createActivityClassManager(context, i).getContactsActivity());
        intent.putExtra(ThemeUtils.THEME_RESOURCE_ID, i);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private void startContactsService(Context context) {
        context.startService(new Intent(context, (Class<?>) ContactsUploadService.class));
    }

    private ContactsService getContactsService() {
        ContactsService contactsService = this.contactsService;
        if (contactsService != null) {
            return contactsService;
        }
        ContactsService contactsService2 = (ContactsService) new RestAdapter.Builder().setEndpoint(new TwitterApi().getBaseHostUrl()).setClient(new AuthenticatedClient(this.twitterCore.getAuthConfig(), Digits.getSessionManager().getActiveSession(), this.twitterCore.getSSLSocketFactory())).build().create(ContactsService.class);
        this.contactsService = contactsService2;
        return contactsService2;
    }

    public void deleteAllContacts(ContactsCallback<Response> contactsCallback) {
        getContactsService().deleteAll(contactsCallback);
    }

    public void getContactMatches(String str, Integer num, ContactsCallback<Contacts> contactsCallback) {
        if (num == null || num.intValue() < 1 || num.intValue() > 100) {
            getContactsService().usersAndUploadedBy(str, null, contactsCallback);
        } else {
            getContactsService().usersAndUploadedBy(str, num, contactsCallback);
        }
    }

    protected UploadResponse uploadContacts(Vcards vcards) {
        return getContactsService().upload(vcards);
    }
}
