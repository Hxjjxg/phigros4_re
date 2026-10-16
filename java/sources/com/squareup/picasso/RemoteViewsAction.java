package com.squareup.picasso;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

/* JADX INFO: loaded from: classes.dex */
abstract class RemoteViewsAction extends Action<RemoteViewsTarget> {
    final RemoteViews remoteViews;
    final int viewId;

    abstract void update();

    RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, boolean z, String str) {
        super(picasso, new RemoteViewsTarget(remoteViews, i), request, z, false, i2, null, str);
        this.remoteViews = remoteViews;
        this.viewId = i;
    }

    @Override // com.squareup.picasso.Action
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        this.remoteViews.setImageViewBitmap(this.viewId, bitmap);
        update();
    }

    @Override // com.squareup.picasso.Action
    public void error() {
        if (this.errorResId != 0) {
            setImageResource(this.errorResId);
        }
    }

    void setImageResource(int i) {
        this.remoteViews.setImageViewResource(this.viewId, i);
        update();
    }

    static class RemoteViewsTarget {
        final RemoteViews remoteViews;
        final int viewId;

        RemoteViewsTarget(RemoteViews remoteViews, int i) {
            this.remoteViews = remoteViews;
            this.viewId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RemoteViewsTarget remoteViewsTarget = (RemoteViewsTarget) obj;
            return this.viewId == remoteViewsTarget.viewId && this.remoteViews.equals(remoteViewsTarget.remoteViews);
        }

        public int hashCode() {
            return (this.remoteViews.hashCode() * 31) + this.viewId;
        }
    }

    static class AppWidgetAction extends RemoteViewsAction {
        private final int[] appWidgetIds;

        AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int[] iArr, boolean z, int i2, String str) {
            super(picasso, request, remoteViews, i, i2, z, str);
            this.appWidgetIds = iArr;
        }

        @Override // com.squareup.picasso.RemoteViewsAction
        void update() {
            AppWidgetManager.getInstance(this.picasso.context).updateAppWidget(this.appWidgetIds, this.remoteViews);
        }
    }

    static class NotificationAction extends RemoteViewsAction {
        private final Notification notification;
        private final int notificationId;

        NotificationAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, Notification notification, boolean z, int i3, String str) {
            super(picasso, request, remoteViews, i, i3, z, str);
            this.notificationId = i2;
            this.notification = notification;
        }

        @Override // com.squareup.picasso.RemoteViewsAction
        void update() {
            ((NotificationManager) Utils.getService(this.picasso.context, "notification")).notify(this.notificationId, this.notification);
        }
    }
}
