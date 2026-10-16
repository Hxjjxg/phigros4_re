package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import javax.annotation.CheckReturnValue;

/* JADX INFO: loaded from: classes.dex */
@CheckReturnValue
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier zzal;
    private final Context mContext;
    private volatile String zzam;

    private GoogleSignatureVerifier(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zzal == null) {
                zzc.zza(context);
                zzal = new GoogleSignatureVerifier(context);
            }
        }
        return zzal;
    }

    public boolean isUidGoogleSigned(int i) {
        zzm zzmVarZzb;
        String[] packagesForUid = Wrappers.packageManager(this.mContext).getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            zzmVarZzb = zzm.zzb("no pkgs");
        } else {
            zzmVarZzb = null;
            for (String str : packagesForUid) {
                zzmVarZzb = zza(str, i);
                if (zzmVarZzb.zzac) {
                    break;
                }
            }
        }
        zzmVarZzb.zzf();
        return zzmVarZzb.zzac;
    }

    public boolean isPackageGoogleSigned(String str) {
        zzm zzmVarZzc = zzc(str);
        zzmVarZzc.zzf();
        return zzmVarZzc.zzac;
    }

    public static boolean zza(PackageInfo packageInfo, boolean z) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z ? zza(packageInfo, zzh.zzx) : zza(packageInfo, zzh.zzx[0])) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    private final zzm zza(String str, int i) {
        try {
            return zza(Wrappers.packageManager(this.mContext).zza(str, 64, i));
        } catch (PackageManager.NameNotFoundException unused) {
            String strValueOf = String.valueOf(str);
            return zzm.zzb(strValueOf.length() != 0 ? "no pkg ".concat(strValueOf) : new String("no pkg "));
        }
    }

    private final zzm zzc(String str) {
        if (str == null) {
            return zzm.zzb("null pkg");
        }
        if (str.equals(this.zzam)) {
            return zzm.zze();
        }
        try {
            zzm zzmVarZza = zza(Wrappers.packageManager(this.mContext).getPackageInfo(str, 64));
            if (zzmVarZza.zzac) {
                this.zzam = str;
            }
            return zzmVarZza;
        } catch (PackageManager.NameNotFoundException unused) {
            String strValueOf = String.valueOf(str);
            return zzm.zzb(strValueOf.length() != 0 ? "no pkg ".concat(strValueOf) : new String("no pkg "));
        }
    }

    private final zzm zza(PackageInfo packageInfo) {
        boolean zHonorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
        if (packageInfo == null) {
            return zzm.zzb("null pkg");
        }
        if (packageInfo.signatures.length != 1) {
            return zzm.zzb("single cert required");
        }
        zzf zzfVar = new zzf(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        zzm zzmVarZza = zzc.zza(str, zzfVar, zHonorsDebugCertificates);
        return (!zzmVarZza.zzac || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0 || (zHonorsDebugCertificates && !zzc.zza(str, (zze) zzfVar, false).zzac)) ? zzmVarZza : zzm.zzb("debuggable release cert app rejected");
    }

    private static zze zza(PackageInfo packageInfo, zze... zzeVarArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzf zzfVar = new zzf(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zzeVarArr.length; i++) {
            if (zzeVarArr[i].equals(zzfVar)) {
                return zzeVarArr[i];
            }
        }
        return null;
    }
}
