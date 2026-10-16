package com.voxelbusters.nativeplugins.extensions;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class FileProviderExtended extends FileProvider {
    @Override // androidx.core.content.FileProvider, android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Cursor cursorQuery = super.query(uri, strArr, str, strArr2, str2);
        String[] columnNames = cursorQuery.getColumnNames();
        String[] strArr3 = null;
        for (String str3 : columnNames) {
            if ("_data".equals(str3)) {
                strArr3 = columnNames;
            }
        }
        if (strArr3 == null) {
            strArr3 = (String[]) Arrays.copyOf(columnNames, columnNames.length + 1);
            strArr3[columnNames.length] = "_data";
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr3, cursorQuery.getCount());
        cursorQuery.moveToPosition(-1);
        while (cursorQuery.moveToNext()) {
            MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
            for (int i = 0; i < columnNames.length; i++) {
                rowBuilderNewRow.add(cursorQuery.getString(i));
            }
        }
        return matrixCursor;
    }
}
