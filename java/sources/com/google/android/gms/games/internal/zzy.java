package com.google.android.gms.games.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;

/* JADX INFO: loaded from: classes.dex */
public interface zzy extends IInterface {
    Bundle getConnectionHint() throws RemoteException;

    int zza(zzu zzuVar, byte[] bArr, String str, String str2) throws RemoteException;

    Intent zza(int i, int i2, boolean z) throws RemoteException;

    Intent zza(int i, byte[] bArr, int i2, String str) throws RemoteException;

    Intent zza(PlayerEntity playerEntity) throws RemoteException;

    Intent zza(RoomEntity roomEntity, int i) throws RemoteException;

    Intent zza(String str, boolean z, boolean z2, int i) throws RemoteException;

    Intent zza(int[] iArr) throws RemoteException;

    void zza(long j) throws RemoteException;

    void zza(IBinder iBinder, Bundle bundle) throws RemoteException;

    void zza(Contents contents) throws RemoteException;

    void zza(zzu zzuVar) throws RemoteException;

    void zza(zzu zzuVar, int i) throws RemoteException;

    void zza(zzu zzuVar, int i, int i2, int i3) throws RemoteException;

    void zza(zzu zzuVar, int i, int i2, String[] strArr, Bundle bundle) throws RemoteException;

    void zza(zzu zzuVar, int i, boolean z, boolean z2) throws RemoteException;

    void zza(zzu zzuVar, int i, int[] iArr) throws RemoteException;

    void zza(zzu zzuVar, long j) throws RemoteException;

    void zza(zzu zzuVar, Bundle bundle, int i, int i2) throws RemoteException;

    void zza(zzu zzuVar, IBinder iBinder, int i, String[] strArr, Bundle bundle, boolean z, long j) throws RemoteException;

    void zza(zzu zzuVar, IBinder iBinder, String str, boolean z, long j) throws RemoteException;

    void zza(zzu zzuVar, String str) throws RemoteException;

    void zza(zzu zzuVar, String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void zza(zzu zzuVar, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException;

    void zza(zzu zzuVar, String str, int i, boolean z, boolean z2) throws RemoteException;

    void zza(zzu zzuVar, String str, long j, String str2) throws RemoteException;

    void zza(zzu zzuVar, String str, IBinder iBinder, Bundle bundle) throws RemoteException;

    void zza(zzu zzuVar, String str, com.google.android.gms.games.snapshot.zze zzeVar, Contents contents) throws RemoteException;

    void zza(zzu zzuVar, String str, String str2) throws RemoteException;

    void zza(zzu zzuVar, String str, String str2, int i, int i2) throws RemoteException;

    void zza(zzu zzuVar, String str, String str2, com.google.android.gms.games.snapshot.zze zzeVar, Contents contents) throws RemoteException;

    void zza(zzu zzuVar, String str, boolean z) throws RemoteException;

    void zza(zzu zzuVar, String str, boolean z, int i) throws RemoteException;

    void zza(zzu zzuVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) throws RemoteException;

    void zza(zzu zzuVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) throws RemoteException;

    void zza(zzu zzuVar, boolean z) throws RemoteException;

    void zza(zzu zzuVar, boolean z, String[] strArr) throws RemoteException;

    void zza(zzu zzuVar, int[] iArr, int i, boolean z) throws RemoteException;

    void zza(zzu zzuVar, String[] strArr) throws RemoteException;

    void zza(zzu zzuVar, String[] strArr, boolean z) throws RemoteException;

    void zza(zzw zzwVar, long j) throws RemoteException;

    void zza(String str, int i) throws RemoteException;

    void zza(String str, IBinder iBinder, Bundle bundle) throws RemoteException;

    void zza(String str, zzu zzuVar) throws RemoteException;

    Intent zzag() throws RemoteException;

    Intent zzai() throws RemoteException;

    int zzak() throws RemoteException;

    String zzam() throws RemoteException;

    int zzao() throws RemoteException;

    Intent zzaq() throws RemoteException;

    int zzar() throws RemoteException;

    int zzas() throws RemoteException;

    int zzat() throws RemoteException;

    int zzav() throws RemoteException;

    boolean zzaz() throws RemoteException;

    int zzb(byte[] bArr, String str, String[] strArr) throws RemoteException;

    Intent zzb(String str, int i, int i2) throws RemoteException;

    void zzb(long j) throws RemoteException;

    void zzb(zzu zzuVar) throws RemoteException;

    void zzb(zzu zzuVar, int i) throws RemoteException;

    void zzb(zzu zzuVar, long j) throws RemoteException;

    void zzb(zzu zzuVar, String str) throws RemoteException;

    void zzb(zzu zzuVar, String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void zzb(zzu zzuVar, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException;

    void zzb(zzu zzuVar, String str, IBinder iBinder, Bundle bundle) throws RemoteException;

    void zzb(zzu zzuVar, String str, String str2) throws RemoteException;

    void zzb(zzu zzuVar, String str, boolean z) throws RemoteException;

    void zzb(zzu zzuVar, boolean z) throws RemoteException;

    void zzb(zzu zzuVar, String[] strArr) throws RemoteException;

    void zzb(String str, int i) throws RemoteException;

    void zzbd() throws RemoteException;

    String zzbf() throws RemoteException;

    DataHolder zzbg() throws RemoteException;

    DataHolder zzbh() throws RemoteException;

    Intent zzbi() throws RemoteException;

    Intent zzc(int i, int i2, boolean z) throws RemoteException;

    void zzc(long j) throws RemoteException;

    void zzc(zzu zzuVar) throws RemoteException;

    void zzc(zzu zzuVar, long j) throws RemoteException;

    void zzc(zzu zzuVar, String str) throws RemoteException;

    void zzc(zzu zzuVar, boolean z) throws RemoteException;

    Intent zzd(String str) throws RemoteException;

    void zzd(long j) throws RemoteException;

    void zzd(zzu zzuVar) throws RemoteException;

    void zzd(zzu zzuVar, long j) throws RemoteException;

    void zzd(zzu zzuVar, String str) throws RemoteException;

    void zzd(zzu zzuVar, boolean z) throws RemoteException;

    void zzd(String str, int i) throws RemoteException;

    void zze(long j) throws RemoteException;

    void zze(zzu zzuVar, long j) throws RemoteException;

    void zze(zzu zzuVar, String str) throws RemoteException;

    void zze(zzu zzuVar, boolean z) throws RemoteException;

    void zzf(long j) throws RemoteException;

    void zzf(zzu zzuVar, String str) throws RemoteException;

    void zzf(zzu zzuVar, boolean z) throws RemoteException;

    void zzf(String str) throws RemoteException;

    void zzg(zzu zzuVar, String str) throws RemoteException;

    void zzh(zzu zzuVar, String str) throws RemoteException;

    void zzk(int i) throws RemoteException;

    String zzp() throws RemoteException;

    Intent zzv() throws RemoteException;

    Intent zzx() throws RemoteException;

    Intent zzy() throws RemoteException;

    Intent zzz() throws RemoteException;
}
