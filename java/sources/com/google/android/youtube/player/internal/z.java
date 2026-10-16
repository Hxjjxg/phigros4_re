package com.google.android.youtube.player.internal;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public final class z {
    private static final Uri a = Uri.parse("http://play.google.com/store/apps/details");
    private static final String[] b = {"com.google.android.youtube", "com.google.android.youtube.tv", "com.google.android.youtube.googletv", "com.google.android.gms", null};

    public static Intent a(String str) {
        Uri uriFromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(uriFromParts);
        return intent;
    }

    public static String a() {
        return new StringBuilder(35).append("1.2.1").toString();
    }

    public static String a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        for (String str : b) {
            ResolveInfo resolveInfoResolveService = packageManager.resolveService(new Intent("com.google.android.youtube.api.service.START").setPackage(str), 0);
            if (resolveInfoResolveService != null && resolveInfoResolveService.serviceInfo != null && resolveInfoResolveService.serviceInfo.packageName != null) {
                return resolveInfoResolveService.serviceInfo.packageName;
            }
        }
        if (packageManager.hasSystemFeature("android.software.leanback")) {
            return "com.google.android.youtube.tv";
        }
        return packageManager.hasSystemFeature("com.google.android.tv") ? "com.google.android.youtube.googletv" : "com.google.android.youtube";
    }

    public static boolean a(Context context, String str) {
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(str);
            if (str.equals("com.google.android.youtube.googletvdev")) {
                str = "com.google.android.youtube.googletv";
            }
            int identifier = resourcesForApplication.getIdentifier("youtube_api_version_code", "integer", str);
            return identifier == 0 || 12 > resourcesForApplication.getInteger(identifier) / 100;
        } catch (PackageManager.NameNotFoundException unused) {
            return true;
        }
    }

    public static boolean a(PackageManager packageManager) {
        return packageManager.hasSystemFeature("com.google.android.tv");
    }

    public static Context b(Context context) {
        try {
            return context.createPackageContext(a(context), 3);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static Intent b(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(a.buildUpon().appendQueryParameter("id", str).build());
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    public static boolean b(PackageManager packageManager) {
        return packageManager.hasSystemFeature("android.software.leanback");
    }

    public static int c(Context context) {
        Context contextB = b(context);
        int identifier = contextB != null ? contextB.getResources().getIdentifier("clientTheme", "style", a(context)) : 0;
        return identifier == 0 ? R.style.Theme.DeviceDefault : identifier;
    }

    public static String d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("Cannot retrieve calling Context's PackageInfo", e);
        }
    }
}
