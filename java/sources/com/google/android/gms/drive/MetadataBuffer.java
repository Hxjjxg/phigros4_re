package com.google.android.gms.drive;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzhp;

/* JADX INFO: loaded from: classes.dex */
public final class MetadataBuffer extends AbstractDataBuffer<Metadata> {
    private zza zzas;

    static class zza extends Metadata {
        private final int row;
        private final DataHolder zzat;
        private final int zzau;

        public zza(DataHolder dataHolder, int i) {
            this.zzat = dataHolder;
            this.row = i;
            this.zzau = dataHolder.getWindowIndex(i);
        }

        @Override // com.google.android.gms.common.data.Freezable
        public final /* synthetic */ Metadata freeze() {
            MetadataBundle metadataBundleZzaw = MetadataBundle.zzaw();
            for (MetadataField<?> metadataField : com.google.android.gms.drive.metadata.internal.zzf.zzau()) {
                if (metadataField != zzhp.zzka) {
                    metadataField.zza(this.zzat, metadataBundleZzaw, this.row, this.zzau);
                }
            }
            return new zzaa(metadataBundleZzaw);
        }

        @Override // com.google.android.gms.common.data.Freezable
        public final boolean isDataValid() {
            return !this.zzat.isClosed();
        }

        @Override // com.google.android.gms.drive.Metadata
        public final <T> T zza(MetadataField<T> metadataField) {
            return metadataField.zza(this.zzat, this.row, this.zzau);
        }
    }

    public MetadataBuffer(DataHolder dataHolder) {
        super(dataHolder);
        dataHolder.getMetadata().setClassLoader(MetadataBuffer.class.getClassLoader());
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    public final Metadata get(int i) {
        zza zzaVar = this.zzas;
        if (zzaVar != null && zzaVar.row == i) {
            return zzaVar;
        }
        zza zzaVar2 = new zza(this.mDataHolder, i);
        this.zzas = zzaVar2;
        return zzaVar2;
    }

    @Deprecated
    public final String getNextPageToken() {
        return null;
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer, com.google.android.gms.common.api.Releasable
    public final void release() {
        if (this.mDataHolder != null) {
            com.google.android.gms.drive.metadata.internal.zzf.zza(this.mDataHolder);
        }
        super.release();
    }
}
