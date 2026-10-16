package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzir;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzir<M extends zzir<M>> extends zzix {
    protected zzit zzmw;

    @Override // com.google.android.gms.internal.drive.zzix
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzir zzirVar = (zzir) super.clone();
        zziv.zza(this, zzirVar);
        return zzirVar;
    }

    @Override // com.google.android.gms.internal.drive.zzix
    public void zza(zzip zzipVar) throws IOException {
        if (this.zzmw == null) {
            return;
        }
        for (int i = 0; i < this.zzmw.size(); i++) {
            this.zzmw.zzs(i).zza(zzipVar);
        }
    }

    protected final boolean zza(zzio zzioVar, int i) throws IOException {
        zziu zziuVarZzr;
        int position = zzioVar.getPosition();
        if (!zzioVar.zzk(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zziz zzizVar = new zziz(i, zzioVar.zza(position, zzioVar.getPosition() - position));
        zzit zzitVar = this.zzmw;
        if (zzitVar == null) {
            this.zzmw = new zzit();
            zziuVarZzr = null;
        } else {
            zziuVarZzr = zzitVar.zzr(i2);
        }
        if (zziuVarZzr == null) {
            zziuVarZzr = new zziu();
            this.zzmw.zza(i2, zziuVarZzr);
        }
        zziuVarZzr.zza(zzizVar);
        return true;
    }

    @Override // com.google.android.gms.internal.drive.zzix
    protected int zzaq() {
        if (this.zzmw == null) {
            return 0;
        }
        int iZzaq = 0;
        for (int i = 0; i < this.zzmw.size(); i++) {
            iZzaq += this.zzmw.zzs(i).zzaq();
        }
        return iZzaq;
    }

    @Override // com.google.android.gms.internal.drive.zzix
    /* JADX INFO: renamed from: zzbi */
    public final /* synthetic */ zzix clone() throws CloneNotSupportedException {
        return (zzir) clone();
    }
}
