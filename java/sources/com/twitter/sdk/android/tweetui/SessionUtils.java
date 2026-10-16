package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class SessionUtils {
    private SessionUtils() {
    }

    public static Session getActiveSession(List<SessionManager<? extends Session>> list) {
        Iterator<SessionManager<? extends Session>> it = list.iterator();
        Session activeSession = null;
        while (it.hasNext() && (activeSession = it.next().getActiveSession()) == null) {
        }
        return activeSession;
    }
}
