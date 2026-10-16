package com.google.android.gms.internal.nearby;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.nearby.connection.Payload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class zzfl {
    static Pair<zzfh, Pair<ParcelFileDescriptor, ParcelFileDescriptor>> zza(Payload payload) throws IOException {
        int type = payload.getType();
        if (type == 1) {
            return Pair.create(new zzfj().zzb(payload.getId()).zzd(payload.getType()).zzb(payload.asBytes()).zzr(), null);
        }
        if (type == 2) {
            return Pair.create(new zzfj().zzb(payload.getId()).zzd(payload.getType()).zzc(payload.asFile().asParcelFileDescriptor()).zze(payload.asFile().asJavaFile() == null ? null : payload.asFile().asJavaFile().getAbsolutePath()).zzc(payload.asFile().getSize()).zzr(), null);
        }
        if (type != 3) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.format("Outgoing Payload %d has unknown type %d", Long.valueOf(payload.getId()), Integer.valueOf(payload.getType())));
            Log.wtf("NearbyConnections", "Unknown payload type!", illegalArgumentException);
            throw illegalArgumentException;
        }
        try {
            ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
            ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe2 = ParcelFileDescriptor.createPipe();
            return Pair.create(new zzfj().zzb(payload.getId()).zzd(payload.getType()).zzc(parcelFileDescriptorArrCreatePipe[0]).zzd(parcelFileDescriptorArrCreatePipe2[0]).zzr(), Pair.create(parcelFileDescriptorArrCreatePipe[1], parcelFileDescriptorArrCreatePipe2[1]));
        } catch (IOException e) {
            Log.e("NearbyConnections", String.format("Unable to create PFD pipe for streaming payload %d from client to service.", Long.valueOf(payload.getId())), e);
            throw e;
        }
    }

    static Payload zza(zzfh zzfhVar) {
        long id = zzfhVar.getId();
        int type = zzfhVar.getType();
        if (type == 1) {
            return Payload.zza(zzfhVar.getBytes(), id);
        }
        if (type != 2) {
            if (type == 3) {
                return Payload.zza(Payload.Stream.zzb(zzfhVar.zzo()), id);
            }
            Log.w("NearbyConnections", String.format("Incoming ParcelablePayload %d has unknown type %d", Long.valueOf(zzfhVar.getId()), Integer.valueOf(zzfhVar.getType())));
            return null;
        }
        String strZzp = zzfhVar.zzp();
        if (strZzp != null) {
            try {
                return Payload.zza(Payload.File.zza(new File(strZzp), zzfhVar.zzq()), id);
            } catch (FileNotFoundException e) {
                String strValueOf = String.valueOf(strZzp);
                Log.w("NearbyConnections", strValueOf.length() != 0 ? "Failed to create Payload from ParcelablePayload: Java file not found at ".concat(strValueOf) : new String("Failed to create Payload from ParcelablePayload: Java file not found at "), e);
            }
        }
        return Payload.zza(Payload.File.zza(zzfhVar.zzo()), id);
    }
}
