package com.digits.sdk.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.ContactsContract;
import com.digits.sdk.vcard.VCardBuilder;
import com.digits.sdk.vcard.VCardConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ContactsHelper {
    private static final int MAX_CONTACTS = 2500;
    private static final String[] allProjectionColumns = {"mimetype", "lookup", "data2", "data3", "is_primary", "data1", "data1", "data2", "data3", "is_primary", "data1", "data2", "data3"};
    private static final String[] selectionArgs = {"vnd.android.cursor.item/phone_v2", "vnd.android.cursor.item/email_v2", "vnd.android.cursor.item/name"};
    private static final String selectionQuery = "mimetype=? OR mimetype=? OR mimetype=?";
    private final Context context;

    ContactsHelper(Context context) {
        this.context = context;
    }

    public Cursor getContactsCursor() {
        HashSet hashSet = new HashSet(Arrays.asList(allProjectionColumns));
        String[] strArr = (String[]) hashSet.toArray(new String[hashSet.size()]);
        return this.context.getContentResolver().query(ContactsContract.Data.CONTENT_URI.buildUpon().appendQueryParameter("limit", Integer.toString(MAX_CONTACTS)).build(), strArr, selectionQuery, selectionArgs, null);
    }

    public List<String> createContactList(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return Collections.EMPTY_LIST;
        }
        int columnIndex = cursor.getColumnIndex("mimetype");
        int columnIndex2 = cursor.getColumnIndex("lookup");
        HashMap map = new HashMap();
        while (cursor.moveToNext()) {
            String string = cursor.getString(columnIndex);
            ContentValues contentValues = new ContentValues();
            contentValues.put("mimetype", string);
            string.hashCode();
            switch (string) {
                case "vnd.android.cursor.item/email_v2":
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data1");
                    DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "data2");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data3");
                    DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "is_primary");
                    break;
                case "vnd.android.cursor.item/name":
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data1");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data2");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data3");
                    break;
                case "vnd.android.cursor.item/phone_v2":
                    DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "data2");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data3");
                    DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "is_primary");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data1");
                    break;
                default:
                    continue;
                    break;
            }
            String string2 = cursor.getString(columnIndex2);
            List<ContentValues> arrayList = map.get(string2);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                map.put(string2, arrayList);
            }
            arrayList.add(contentValues);
        }
        return processContactsMap(map);
    }

    private List<String> processContactsMap(Map<String, List<ContentValues>> map) {
        ArrayList arrayList = new ArrayList();
        HashMap map2 = new HashMap();
        VCardBuilder vCardBuilder = new VCardBuilder(VCardConfig.VCARD_TYPE_V30_GENERIC, "UTF-8");
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            List<ContentValues> list = map.get(it.next());
            map2.clear();
            vCardBuilder.clear();
            boolean z = false;
            for (ContentValues contentValues : list) {
                String asString = contentValues.getAsString("mimetype");
                if ("vnd.android.cursor.item/phone_v2".equals(asString) || "vnd.android.cursor.item/email_v2".equals(asString)) {
                    z = true;
                }
                List arrayList2 = (List) map2.get(asString);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    map2.put(asString, arrayList2);
                }
                arrayList2.add(contentValues);
            }
            if (z) {
                vCardBuilder.appendNameProperties((List) map2.get("vnd.android.cursor.item/name")).appendPhones((List) map2.get("vnd.android.cursor.item/phone_v2"), null).appendEmails((List) map2.get("vnd.android.cursor.item/email_v2"));
                arrayList.add(vCardBuilder.toString());
            }
        }
        return arrayList;
    }
}
