package com.google.android.gms.drive;

/* JADX INFO: loaded from: classes.dex */
public final class zzp extends ExecutionOptions.Builder {
    private boolean zzar = true;

    @Override // com.google.android.gms.drive.ExecutionOptions.Builder
    public final /* synthetic */ ExecutionOptions build() {
        zzn();
        return new zzn(this.zzao, this.zzap, this.zzaq, this.zzar);
    }

    @Override // com.google.android.gms.drive.ExecutionOptions.Builder
    public final /* synthetic */ ExecutionOptions.Builder setConflictStrategy(int i) {
        super.setConflictStrategy(i);
        return this;
    }

    @Override // com.google.android.gms.drive.ExecutionOptions.Builder
    public final /* synthetic */ ExecutionOptions.Builder setNotifyOnCompletion(boolean z) {
        super.setNotifyOnCompletion(z);
        return this;
    }

    @Override // com.google.android.gms.drive.ExecutionOptions.Builder
    public final /* synthetic */ ExecutionOptions.Builder setTrackingTag(String str) {
        super.setTrackingTag(str);
        return this;
    }
}
