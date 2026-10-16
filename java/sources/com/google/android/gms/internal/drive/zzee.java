package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.events.DriveEvent;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzee extends zzet {
    private static final GmsLogger zzbx = new GmsLogger("EventCallback", "");
    private final com.google.android.gms.drive.events.zzi zzgr;
    private final zzeg zzgs;
    private final List<Integer> zzgt = new ArrayList();
    private final int zzcy = 1;

    public zzee(Looper looper, Context context, int i, com.google.android.gms.drive.events.zzi zziVar) {
        this.zzgr = zziVar;
        this.zzgs = new zzeg(looper, context);
    }

    @Override // com.google.android.gms.internal.drive.zzes
    public final void zzc(zzfj zzfjVar) throws RemoteException {
        DriveEvent driveEventZzak = zzfjVar.zzak();
        Preconditions.checkState(this.zzcy == driveEventZzak.getType());
        Preconditions.checkState(this.zzgt.contains(Integer.valueOf(driveEventZzak.getType())));
        zzeg zzegVar = this.zzgs;
        zzegVar.sendMessage(zzegVar.obtainMessage(1, new Pair(this.zzgr, driveEventZzak)));
    }

    public final void zzf(int i) {
        this.zzgt.add(1);
    }

    public final boolean zzg(int i) {
        return this.zzgt.contains(1);
    }
}
