package com.google.android.gms.internal.drive;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzix {
    protected volatile int zznf = -1;

    public static final <T extends zzix> T zza(T t, byte[] bArr, int i, int i2) throws zziw {
        try {
            zzio zzioVarZza = zzio.zza(bArr, 0, i2);
            t.zza(zzioVarZza);
            zzioVarZza.zzj(0);
            return t;
        } catch (zziw e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public static final byte[] zza(zzix zzixVar) {
        int iZzaq = zzixVar.zzaq();
        zzixVar.zznf = iZzaq;
        byte[] bArr = new byte[iZzaq];
        try {
            zzip zzipVarZzb = zzip.zzb(bArr, 0, iZzaq);
            zzixVar.zza(zzipVarZzb);
            zzipVarZzb.zzbh();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public String toString() {
        return zziy.zzb(this);
    }

    public abstract zzix zza(zzio zzioVar) throws IOException;

    public void zza(zzip zzipVar) throws IOException {
    }

    protected int zzaq() {
        return 0;
    }

    @Override // 
    /* JADX INFO: renamed from: zzbi, reason: merged with bridge method [inline-methods] */
    public zzix clone() throws CloneNotSupportedException {
        return (zzix) super.clone();
    }
}
