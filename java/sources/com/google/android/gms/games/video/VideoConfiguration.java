package com.google.android.gms.games.video;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class VideoConfiguration extends AbstractSafeParcelable {
    public static final int CAPTURE_MODE_FILE = 0;
    public static final int CAPTURE_MODE_STREAM = 1;
    public static final int CAPTURE_MODE_UNKNOWN = -1;
    public static final Parcelable.Creator<VideoConfiguration> CREATOR = new zzb();
    public static final int NUM_CAPTURE_MODE = 2;
    public static final int NUM_QUALITY_LEVEL = 4;
    public static final int QUALITY_LEVEL_FULLHD = 3;
    public static final int QUALITY_LEVEL_HD = 1;
    public static final int QUALITY_LEVEL_SD = 0;
    public static final int QUALITY_LEVEL_UNKNOWN = -1;
    public static final int QUALITY_LEVEL_XHD = 2;
    private final int zzrq;
    private final int zzrz;
    private final boolean zzsa;

    public static final class Builder {
        private int zzrq;
        private int zzrz;
        private boolean zzsa = true;

        public Builder(int i, int i2) {
            this.zzrz = i;
            this.zzrq = i2;
        }

        public final VideoConfiguration build() {
            return new VideoConfiguration(this.zzrz, this.zzrq, this.zzsa);
        }

        public final Builder setCaptureMode(int i) {
            this.zzrq = i;
            return this;
        }

        public final Builder setQualityLevel(int i) {
            this.zzrz = i;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ValidCaptureModes {
    }

    public VideoConfiguration(int i, int i2, boolean z) {
        Preconditions.checkArgument(isValidQualityLevel(i, false));
        Preconditions.checkArgument(isValidCaptureMode(i2, false));
        this.zzrz = i;
        this.zzrq = i2;
        this.zzsa = z;
    }

    public static boolean isValidCaptureMode(int i, boolean z) {
        if (i != -1) {
            if (i == 0) {
                return true;
            }
            if (i != 1) {
                return false;
            }
        }
        return z;
    }

    public static boolean isValidQualityLevel(int i, boolean z) {
        if (i != -1) {
            z = true;
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                return false;
            }
        }
        return z;
    }

    public final int getCaptureMode() {
        return this.zzrq;
    }

    public final int getQualityLevel() {
        return this.zzrz;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getQualityLevel());
        SafeParcelWriter.writeInt(parcel, 2, getCaptureMode());
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzsa);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
