package com.voxelbusters.nativeplugins.utilities;

import android.media.ExifInterface;

/* JADX INFO: loaded from: classes.dex */
public class BitmapUtility {
    public static int exifToDegrees(int i) {
        if (i == 6) {
            return 90;
        }
        if (i == 3) {
            return 180;
        }
        return i == 8 ? 270 : 0;
    }

    public static int getExifRotationForFile(String str) {
        try {
            return exifToDegrees(new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
        } catch (Exception unused) {
            return 0;
        }
    }
}
