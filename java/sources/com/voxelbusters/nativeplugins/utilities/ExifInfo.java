package com.voxelbusters.nativeplugins.utilities;

/* JADX INFO: compiled from: FileUtility.java */
/* JADX INFO: loaded from: classes.dex */
class ExifInfo {
    private boolean isFlipped;
    private float rotation;

    public ExifInfo(float f, boolean z) {
        this.rotation = f;
        this.isFlipped = z;
    }

    public float getRotation() {
        return this.rotation;
    }

    public boolean isFlipped() {
        return this.isFlipped;
    }
}
