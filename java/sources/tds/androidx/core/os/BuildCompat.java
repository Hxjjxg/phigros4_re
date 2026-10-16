package tds.androidx.core.os;

import android.os.Build;
import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: loaded from: classes.dex */
public class BuildCompat {
    @Deprecated
    public static boolean isAtLeastN() {
        return true;
    }

    @Deprecated
    public static boolean isAtLeastNMR1() {
        return true;
    }

    @Deprecated
    public static boolean isAtLeastO() {
        return true;
    }

    private BuildCompat() {
    }

    @Deprecated
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @Deprecated
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @Deprecated
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30 || Build.VERSION.CODENAME.equals("R");
    }

    public static boolean isAtLeastS() {
        return Build.VERSION.CODENAME.equals(ExifInterface.LATITUDE_SOUTH);
    }
}
