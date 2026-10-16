package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.query.internal.zzf;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SortOrder extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SortOrder> CREATOR = new zzc();
    private final List<zzf> zzlg;
    private final boolean zzlh;

    public static class Builder {
        private final List<zzf> zzlg = new ArrayList();
        private boolean zzlh = false;

        public Builder addSortAscending(SortableMetadataField sortableMetadataField) {
            this.zzlg.add(new zzf(sortableMetadataField.getName(), true));
            return this;
        }

        public Builder addSortDescending(SortableMetadataField sortableMetadataField) {
            this.zzlg.add(new zzf(sortableMetadataField.getName(), false));
            return this;
        }

        public SortOrder build() {
            return new SortOrder(this.zzlg, false);
        }
    }

    SortOrder(List<zzf> list, boolean z) {
        this.zzlg = list;
        this.zzlh = z;
    }

    public String toString() {
        return String.format(Locale.US, "SortOrder[%s, %s]", TextUtils.join(",", this.zzlg), Boolean.valueOf(this.zzlh));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzlg, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzlh);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
