package com.google.android.gms.internal.drive;

import com.digits.sdk.vcard.VCardConfig;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public final class zziv {
    protected static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName(VCardConfig.DEFAULT_INTERMEDIATE_CHARSET);
    private static final Object zzne = new Object();

    public static void zza(zzir zzirVar, zzir zzirVar2) {
        if (zzirVar.zzmw != null) {
            zzirVar2.zzmw = (zzit) zzirVar.zzmw.clone();
        }
    }
}
