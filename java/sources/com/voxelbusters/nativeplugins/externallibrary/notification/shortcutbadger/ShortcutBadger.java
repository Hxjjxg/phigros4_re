package com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.AdwHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.ApexHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.AsusHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.DefaultBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.EverythingMeHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.HuaweiHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.NewHtcHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.NovaHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.OPPOHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.SamsungHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.SonyHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.VivoHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.ZTEHomeBadger;
import com.voxelbusters.nativeplugins.externallibrary.notification.shortcutbadger.impl.ZukHomeBadger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ShortcutBadger {
    private static final List<Class<? extends Badger>> BADGERS;
    private static final String LOG_TAG = "ShortcutBadger";
    private static final int SUPPORTED_CHECK_ATTEMPTS = 3;
    private static ComponentName sComponentName;
    private static final Object sCounterSupportedLock;
    private static volatile Boolean sIsBadgeCounterSupported;
    private static Badger sShortcutBadger;

    static {
        LinkedList linkedList = new LinkedList();
        BADGERS = linkedList;
        sCounterSupportedLock = new Object();
        linkedList.add(AdwHomeBadger.class);
        linkedList.add(ApexHomeBadger.class);
        linkedList.add(DefaultBadger.class);
        linkedList.add(NewHtcHomeBadger.class);
        linkedList.add(NovaHomeBadger.class);
        linkedList.add(SonyHomeBadger.class);
        linkedList.add(AsusHomeBadger.class);
        linkedList.add(HuaweiHomeBadger.class);
        linkedList.add(OPPOHomeBadger.class);
        linkedList.add(SamsungHomeBadger.class);
        linkedList.add(ZukHomeBadger.class);
        linkedList.add(VivoHomeBadger.class);
        linkedList.add(ZTEHomeBadger.class);
        linkedList.add(EverythingMeHomeBadger.class);
    }

    public static boolean applyCount(Context context, int i) {
        try {
            applyCountOrThrow(context, i);
            return true;
        } catch (ShortcutBadgeException e) {
            if (!Log.isLoggable(LOG_TAG, 3)) {
                return false;
            }
            Log.d(LOG_TAG, "Unable to execute badge", e);
            return false;
        }
    }

    public static void applyCountOrThrow(Context context, int i) throws ShortcutBadgeException {
        if (sShortcutBadger == null && !initBadger(context)) {
            throw new ShortcutBadgeException("No default launcher available");
        }
        try {
            sShortcutBadger.executeBadge(context, sComponentName, i);
        } catch (Exception e) {
            throw new ShortcutBadgeException("Unable to execute badge", e);
        }
    }

    public static boolean removeCount(Context context) {
        return applyCount(context, 0);
    }

    public static void removeCountOrThrow(Context context) throws ShortcutBadgeException {
        applyCountOrThrow(context, 0);
    }

    public static boolean isBadgeCounterSupported(Context context) {
        if (sIsBadgeCounterSupported == null) {
            synchronized (sCounterSupportedLock) {
                if (sIsBadgeCounterSupported == null) {
                    String message = null;
                    for (int i = 0; i < 3; i++) {
                        try {
                            Log.i(LOG_TAG, "Checking if platform supports badge counters, attempt " + String.format("%d/%d.", Integer.valueOf(i + 1), 3));
                            if (initBadger(context)) {
                                sShortcutBadger.executeBadge(context, sComponentName, 0);
                                sIsBadgeCounterSupported = true;
                                Log.i(LOG_TAG, "Badge counter is supported in this platform.");
                                break;
                            }
                            message = "Failed to initialize the badge counter.";
                        } catch (Exception e) {
                            message = e.getMessage();
                        }
                    }
                    if (sIsBadgeCounterSupported == null) {
                        Log.w(LOG_TAG, "Badge counter seems not supported for this platform: " + message);
                        sIsBadgeCounterSupported = false;
                    }
                }
            }
        }
        return sIsBadgeCounterSupported.booleanValue();
    }

    public static void applyNotification(Context context, Notification notification, int i) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            try {
                Object obj = notification.getClass().getDeclaredField("extraNotification").get(notification);
                obj.getClass().getDeclaredMethod("setMessageCount", Integer.TYPE).invoke(obj, Integer.valueOf(i));
            } catch (Exception e) {
                if (Log.isLoggable(LOG_TAG, 3)) {
                    Log.d(LOG_TAG, "Unable to execute badge", e);
                }
            }
        }
    }

    private static boolean initBadger(Context context) {
        Badger badgerNewInstance;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            Log.e(LOG_TAG, "Unable to find launch intent for package " + context.getPackageName());
            return false;
        }
        sComponentName = launchIntentForPackage.getComponent();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo.name.toLowerCase().contains("resolver")) {
            return false;
        }
        String str = resolveInfoResolveActivity.activityInfo.packageName;
        Iterator<Class<? extends Badger>> it = BADGERS.iterator();
        while (it.hasNext()) {
            try {
                badgerNewInstance = it.next().newInstance();
            } catch (Exception unused) {
                badgerNewInstance = null;
            }
            if (badgerNewInstance != null && badgerNewInstance.getSupportLaunchers().contains(str)) {
                sShortcutBadger = badgerNewInstance;
                break;
            }
        }
        if (sShortcutBadger != null) {
            return true;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("ZUK")) {
            sShortcutBadger = new ZukHomeBadger();
            return true;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
            sShortcutBadger = new OPPOHomeBadger();
            return true;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("VIVO")) {
            sShortcutBadger = new VivoHomeBadger();
            return true;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("ZTE")) {
            sShortcutBadger = new ZTEHomeBadger();
            return true;
        }
        sShortcutBadger = new DefaultBadger();
        return true;
    }

    private ShortcutBadger() {
    }
}
