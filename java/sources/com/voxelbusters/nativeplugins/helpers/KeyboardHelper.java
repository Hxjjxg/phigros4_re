package com.voxelbusters.nativeplugins.helpers;

import android.R;
import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.voxelbusters.nativeplugins.helpers.interfaces.IKeyboardListener;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.MiscUtilities;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class KeyboardHelper {
    final float KEYBOARD_VISIBLE_THRESHOLD_IN_DP = 100.0f;
    ArrayList<IKeyboardListener> listeners = new ArrayList<>();
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    public KeyboardHelper(Activity activity) {
        int i = activity.getWindow().getAttributes().softInputMode;
        if (16 != i && i != 0) {
            Debug.warning("KeyboardHelper", "Need to set ADJUST_RESIZE to get the callbacks");
        }
        registerViewObserver(activity);
    }

    public void addListener(IKeyboardListener iKeyboardListener) {
        if (this.listeners.contains(iKeyboardListener)) {
            return;
        }
        this.listeners.add(iKeyboardListener);
    }

    public void removeListener(IKeyboardListener iKeyboardListener) {
        if (this.listeners.contains(iKeyboardListener)) {
            return;
        }
        this.listeners.remove(iKeyboardListener);
    }

    private void registerViewObserver(Activity activity) {
        View childAt = ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0);
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(activity, childAt) { // from class: com.voxelbusters.nativeplugins.helpers.KeyboardHelper.1
            final /* synthetic */ Activity val$rootActivity;
            final /* synthetic */ View val$rootView;
            private final int visibleThreshold;
            private final Rect rect = new Rect();
            private boolean oldVisibilityState = false;

            {
                this.val$rootActivity = activity;
                this.val$rootView = childAt;
                this.visibleThreshold = MiscUtilities.convertDpToPixels(activity, 100.0f);
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                this.val$rootView.getWindowVisibleDisplayFrame(this.rect);
                boolean z = this.val$rootView.getRootView().getHeight() - this.rect.height() > this.visibleThreshold;
                if (z != this.oldVisibilityState) {
                    KeyboardHelper.this.notifyListeners(z);
                    this.oldVisibilityState = z;
                }
            }
        };
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListeners(boolean z) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).onKeyboardVisibilityChange(z);
        }
    }
}
