package com.google.android.gms.common.images;

/* JADX INFO: loaded from: classes.dex */
public final class Size {
    private final int zand;
    private final int zane;

    public Size(int i, int i2) {
        this.zand = i;
        this.zane = i2;
    }

    public final int getWidth() {
        return this.zand;
    }

    public final int getHeight() {
        return this.zane;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            if (this.zand == size.zand && this.zane == size.zane) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        int i = this.zand;
        return new StringBuilder(23).append(i).append("x").append(this.zane).toString();
    }

    private static NumberFormatException zah(String str) {
        throw new NumberFormatException(new StringBuilder(String.valueOf(str).length() + 16).append("Invalid Size: \"").append(str).append("\"").toString());
    }

    public static Size parseSize(String str) throws NumberFormatException {
        if (str == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int iIndexOf = str.indexOf(42);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(120);
        }
        if (iIndexOf < 0) {
            throw zah(str);
        }
        try {
            return new Size(Integer.parseInt(str.substring(0, iIndexOf)), Integer.parseInt(str.substring(iIndexOf + 1)));
        } catch (NumberFormatException unused) {
            throw zah(str);
        }
    }

    public final int hashCode() {
        int i = this.zane;
        int i2 = this.zand;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }
}
