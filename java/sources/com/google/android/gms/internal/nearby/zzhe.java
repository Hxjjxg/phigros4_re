package com.google.android.gms.internal.nearby;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class zzhe {
    private static HashMap<String, String> zzjr;
    private static Object zzjw;
    private static boolean zzjx;
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzjn = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static final Pattern zzjo = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static final Pattern zzjp = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzjq = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzjs = new HashMap<>();
    private static final HashMap<String, Integer> zzjt = new HashMap<>();
    private static final HashMap<String, Long> zzju = new HashMap<>();
    private static final HashMap<String, Float> zzjv = new HashMap<>();
    private static String[] zzjy = new String[0];

    private static <T> T zza(HashMap<String, T> map, String str, T t) {
        synchronized (zzhe.class) {
            if (!map.containsKey(str)) {
                return null;
            }
            T t2 = map.get(str);
            if (t2 != null) {
                t = t2;
            }
            return t;
        }
    }

    private static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzhe.class) {
            zza(contentResolver);
            Object obj = zzjw;
            if (zzjr.containsKey(str)) {
                String str3 = zzjr.get(str);
                return str3 != null ? str3 : null;
            }
            for (String str4 : zzjy) {
                if (str.startsWith(str4)) {
                    if (!zzjx || zzjr.isEmpty()) {
                        zzjr.putAll(zza(contentResolver, zzjy));
                        zzjx = true;
                        if (zzjr.containsKey(str)) {
                            String str5 = zzjr.get(str);
                            return str5 != null ? str5 : null;
                        }
                    }
                    return null;
                }
            }
            Cursor cursorQuery = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(1);
                        if (string != null && string.equals(null)) {
                            string = null;
                        }
                        zza(obj, str, string);
                        return string != null ? string : null;
                    }
                } finally {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            }
            zza(obj, str, (String) null);
            return null;
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor cursorQuery = contentResolver.query(zzjn, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (cursorQuery == null) {
            return treeMap;
        }
        while (cursorQuery.moveToNext()) {
            try {
                treeMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        cursorQuery.close();
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzjr == null) {
            zzjq.set(false);
            zzjr = new HashMap<>();
            zzjw = new Object();
            zzjx = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzhf(null));
            return;
        }
        if (zzjq.getAndSet(false)) {
            zzjr.clear();
            zzjs.clear();
            zzjt.clear();
            zzju.clear();
            zzjv.clear();
            zzjw = new Object();
            zzjx = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzhe.class) {
            if (obj == zzjw) {
                zzjr.put(str, str2);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object objZzb = zzb(contentResolver);
        HashMap<String, Boolean> map = zzjs;
        boolean z2 = true;
        Boolean bool = true;
        Boolean bool2 = (Boolean) zza(map, str, bool);
        if (bool2 != null) {
            return bool2.booleanValue();
        }
        String strZza = zza(contentResolver, str, (String) null);
        if (strZza == null || strZza.equals("")) {
            bool = bool2;
        } else if (!zzjo.matcher(strZza).matches()) {
            if (zzjp.matcher(strZza).matches()) {
                z2 = false;
                bool = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + strZza + "\") as boolean");
                bool = bool2;
            }
        }
        synchronized (zzhe.class) {
            if (objZzb == zzjw) {
                map.put(str, bool);
                zzjr.remove(str);
            }
        }
        return z2;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzhe.class) {
            zza(contentResolver);
            obj = zzjw;
        }
        return obj;
    }
}
