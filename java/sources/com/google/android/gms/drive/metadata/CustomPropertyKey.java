package com.google.android.gms.drive.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CustomPropertyKey extends AbstractSafeParcelable {
    public static final int PRIVATE = 1;
    public static final int PUBLIC = 0;
    private final int visibility;
    private final String zzij;
    public static final Parcelable.Creator<CustomPropertyKey> CREATOR = new zzc();
    private static final Pattern zzik = Pattern.compile("[\\w.!@$%^&*()/-]+");

    public CustomPropertyKey(String str, int i) {
        Preconditions.checkNotNull(str, "key");
        Preconditions.checkArgument(zzik.matcher(str).matches(), "key name characters must be alphanumeric or one of .!@$%^&*()-_/");
        boolean z = true;
        if (i != 0 && i != 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "visibility must be either PUBLIC or PRIVATE");
        this.zzij = str;
        this.visibility = i;
    }

    public static CustomPropertyKey fromJson(JSONObject jSONObject) throws JSONException {
        return new CustomPropertyKey(jSONObject.getString("key"), jSONObject.getInt("visibility"));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == getClass()) {
            CustomPropertyKey customPropertyKey = (CustomPropertyKey) obj;
            if (customPropertyKey.getKey().equals(this.zzij) && customPropertyKey.getVisibility() == this.visibility) {
                return true;
            }
        }
        return false;
    }

    public String getKey() {
        return this.zzij;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public int hashCode() {
        String str = this.zzij;
        return new StringBuilder(String.valueOf(str).length() + 11).append(str).append(this.visibility).toString().hashCode();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("key", getKey());
        jSONObject.put("visibility", getVisibility());
        return jSONObject;
    }

    public String toString() {
        String str = this.zzij;
        return new StringBuilder(String.valueOf(str).length() + 31).append("CustomPropertyKey(").append(str).append(",").append(this.visibility).append(")").toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzij, false);
        SafeParcelWriter.writeInt(parcel, 3, this.visibility);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
