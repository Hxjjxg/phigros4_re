package com.tds.common.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.tds.common.TapCommon;
import com.tds.common.entities.AccessToken;
import com.tds.common.entities.TapConfig;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.localize.TapLanguage;

/* JADX INFO: loaded from: classes.dex */
public class CurrentConfigProvider extends ContentProvider {
    private static final int URI_CODE_CLIENT_ID = 1;
    private static final int URI_CODE_CLIENT_SERVER = 4;
    private static final int URI_CODE_CLIENT_TOKEN = 3;
    private static final int URI_CODE_LANG = 2;
    static final UriMatcher uriMatcher = new UriMatcher(-1);

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        UriMatcher uriMatcher2 = uriMatcher;
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_id", 1);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/lang", 2);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_token", 3);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_server", 4);
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return buildCursor(uriMatcher.match(uri));
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("insert is not supported" + uri);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("update is not supported" + uri);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("delete is not supported" + uri);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0031  */
    private Cursor buildCursor(int i) {
        String preferredLanguageString;
        try {
            TapConfig tapConfig = TapCommon.getTapConfig();
            if (i != 1) {
                if (i == 2) {
                    preferredLanguageString = LocalizeManager.getGameSelectedLang() == TapLanguage.AUTO ? "" : LocalizeManager.getPreferredLanguageString();
                } else if (i != 3) {
                    if (i == 4 && tapConfig != null) {
                        preferredLanguageString = tapConfig.serverUrl;
                    } else {
                        preferredLanguageString = null;
                    }
                } else if (tapConfig != null) {
                    preferredLanguageString = tapConfig.clientToken;
                } else {
                    preferredLanguageString = null;
                }
            } else if (tapConfig != null) {
                preferredLanguageString = tapConfig.clientId;
            } else {
                preferredLanguageString = null;
            }
            if (preferredLanguageString == null) {
                return null;
            }
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{AccessToken.ROOT_ELEMENT_NAME});
            matrixCursor.addRow(new Object[]{preferredLanguageString});
            return matrixCursor;
        } catch (Exception unused) {
            return null;
        }
    }
}
