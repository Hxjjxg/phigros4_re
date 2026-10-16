package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class zzc {
    public static int zza(Bundle bundle) {
        int size;
        int iHashCode;
        int iKeyAt;
        int iHashCode2;
        int iHashCode3;
        if (bundle == null || (size = bundle.size()) == 0) {
            return 0;
        }
        String[] strArr = (String[]) bundle.keySet().toArray(new String[size]);
        Arrays.sort(strArr);
        int i = 1;
        for (String str : strArr) {
            i *= 31;
            Object obj = bundle.get(str);
            if (obj != null) {
                if (obj instanceof Bundle) {
                    iHashCode = zza((Bundle) obj);
                } else if (obj instanceof byte[]) {
                    iHashCode = Arrays.hashCode((byte[]) obj);
                } else if (obj instanceof char[]) {
                    iHashCode = Arrays.hashCode((char[]) obj);
                } else if (obj instanceof short[]) {
                    iHashCode = Arrays.hashCode((short[]) obj);
                } else if (obj instanceof float[]) {
                    iHashCode = Arrays.hashCode((float[]) obj);
                } else if (obj instanceof CharSequence[]) {
                    iHashCode = Arrays.hashCode((CharSequence[]) obj);
                } else {
                    if (obj instanceof Object[]) {
                        iKeyAt = 1;
                        for (Object obj2 : (Object[]) obj) {
                            iKeyAt *= 31;
                            if (obj2 instanceof Bundle) {
                                iHashCode3 = zza((Bundle) obj2);
                            } else {
                                if (obj2 != null) {
                                    iHashCode3 = obj2.hashCode();
                                }
                            }
                            iKeyAt += iHashCode3;
                        }
                    } else if (obj instanceof SparseArray) {
                        SparseArray sparseArray = (SparseArray) obj;
                        int size2 = sparseArray.size();
                        iKeyAt = 1;
                        for (int i2 = 0; i2 < size2; i2++) {
                            iKeyAt = ((iKeyAt * 31) + sparseArray.keyAt(i2)) * 31;
                            Object objValueAt = sparseArray.valueAt(i2);
                            if (objValueAt instanceof Bundle) {
                                iHashCode2 = zza((Bundle) objValueAt);
                            } else {
                                if (objValueAt != null) {
                                    iHashCode2 = objValueAt.hashCode();
                                }
                            }
                            iKeyAt += iHashCode2;
                        }
                    } else {
                        iHashCode = obj.hashCode();
                    }
                    i += iKeyAt;
                }
                i += iHashCode;
            }
        }
        return i;
    }

    public static boolean zza(Bundle bundle, Bundle bundle2) {
        boolean z;
        boolean z2;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        Set<String> setKeySet = bundle.keySet();
        if (!setKeySet.equals(bundle2.keySet())) {
            return false;
        }
        for (String str : setKeySet) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if (obj == null) {
                if (obj2 != null) {
                    return false;
                }
            } else if (obj instanceof Bundle) {
                if (!(obj2 instanceof Bundle) || !zza((Bundle) obj, (Bundle) obj2)) {
                    return false;
                }
            } else if (obj instanceof byte[]) {
                if (!(obj2 instanceof byte[]) || !Arrays.equals((byte[]) obj, (byte[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof char[]) {
                if (!(obj2 instanceof char[]) || !Arrays.equals((char[]) obj, (char[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof short[]) {
                if (!(obj2 instanceof short[]) || !Arrays.equals((short[]) obj, (short[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof float[]) {
                if (!(obj2 instanceof float[]) || !Arrays.equals((float[]) obj, (float[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof CharSequence[]) {
                if (!(obj2 instanceof CharSequence[]) || !Arrays.equals((CharSequence[]) obj, (CharSequence[]) obj2)) {
                    return false;
                }
            } else {
                if (obj instanceof Object[]) {
                    if (obj2 instanceof Object[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        Parcelable[] parcelableArr2 = (Parcelable[]) obj2;
                        if (parcelableArr == parcelableArr2) {
                            z = true;
                        } else {
                            int length = parcelableArr.length;
                            if (parcelableArr2.length == length) {
                                int i = 0;
                                while (true) {
                                    if (i < length) {
                                        Parcelable parcelable = parcelableArr[i];
                                        Parcelable parcelable2 = parcelableArr2[i];
                                        if (parcelable == null) {
                                            if (parcelable2 == null) {
                                                i++;
                                            }
                                        } else if (parcelable instanceof Bundle) {
                                            if ((parcelable2 instanceof Bundle) && zza((Bundle) parcelable, (Bundle) parcelable2)) {
                                                i++;
                                            }
                                        } else if (parcelable.equals(parcelable2)) {
                                            i++;
                                        }
                                    } else {
                                        z = true;
                                    }
                                }
                            }
                            z = false;
                        }
                        if (!z) {
                        }
                    }
                    return false;
                }
                if (obj instanceof SparseArray) {
                    if (obj2 instanceof SparseArray) {
                        SparseArray sparseArray = (SparseArray) obj;
                        SparseArray sparseArray2 = (SparseArray) obj2;
                        if (sparseArray == sparseArray2) {
                            z2 = true;
                        } else {
                            int size = sparseArray.size();
                            if (sparseArray2.size() == size) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= size) {
                                        z2 = true;
                                    } else if (sparseArray.keyAt(i2) == sparseArray2.keyAt(i2)) {
                                        Object objValueAt = sparseArray.valueAt(i2);
                                        Object objValueAt2 = sparseArray2.valueAt(i2);
                                        if (objValueAt == null) {
                                            if (objValueAt2 == null) {
                                                i2++;
                                            }
                                        } else if (objValueAt instanceof Bundle) {
                                            if ((objValueAt2 instanceof Bundle) && zza((Bundle) objValueAt, (Bundle) objValueAt2)) {
                                                i2++;
                                            }
                                        } else if (objValueAt.equals(objValueAt2)) {
                                            i2++;
                                        }
                                    }
                                }
                            }
                            z2 = false;
                        }
                        if (!z2) {
                        }
                    }
                    return false;
                }
                if (!obj.equals(obj2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
