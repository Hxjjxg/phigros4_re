package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.Set;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class DriveSpace extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<DriveSpace> CREATOR = new zzm();
    public static final DriveSpace zzaf;
    public static final DriveSpace zzag;
    public static final DriveSpace zzah;
    private static final Set<DriveSpace> zzai;
    private static final String zzaj;
    private static final Pattern zzak;
    private final String name;

    static {
        DriveSpace driveSpace = new DriveSpace("DRIVE");
        zzaf = driveSpace;
        DriveSpace driveSpace2 = new DriveSpace("APP_DATA_FOLDER");
        zzag = driveSpace2;
        DriveSpace driveSpace3 = new DriveSpace("PHOTOS");
        zzah = driveSpace3;
        Set<DriveSpace> of = CollectionUtils.setOf(driveSpace, driveSpace2, driveSpace3);
        zzai = of;
        zzaj = TextUtils.join(",", of.toArray());
        zzak = Pattern.compile("[A-Z0-9_]*");
    }

    DriveSpace(String str) {
        this.name = (String) Preconditions.checkNotNull(str);
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != DriveSpace.class) {
            return false;
        }
        return this.name.equals(((DriveSpace) obj).name);
    }

    public int hashCode() {
        return this.name.hashCode() ^ 1247068382;
    }

    public String toString() {
        return this.name;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
