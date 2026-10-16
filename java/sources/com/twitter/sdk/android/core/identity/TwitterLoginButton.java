package com.twitter.sdk.android.core.identity;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class TwitterLoginButton extends Button {
    static final String ERROR_MSG_NO_ACTIVITY = "TwitterLoginButton requires an activity. Override getActivity to provide the activity for this button.";
    static final String TAG = "Twitter";
    final WeakReference<Activity> activityRef;
    volatile TwitterAuthClient authClient;
    Callback<TwitterSession> callback;
    View.OnClickListener onClickListener;

    public TwitterLoginButton(Context context) {
        this(context, null);
    }

    public TwitterLoginButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public TwitterLoginButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, null);
    }

    TwitterLoginButton(Context context, AttributeSet attributeSet, int i, TwitterAuthClient twitterAuthClient) {
        super(context, attributeSet, i);
        this.activityRef = new WeakReference<>(getActivity());
        this.authClient = twitterAuthClient;
        setupButton();
        checkTwitterCoreAndEnable();
    }

    private void setupButton() {
        Resources resources = getResources();
        super.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(com.twitter.sdk.android.core.R.drawable.tw__ic_logo_default), (Drawable) null, (Drawable) null, (Drawable) null);
        super.setCompoundDrawablePadding(resources.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_drawable_padding));
        super.setText(com.twitter.sdk.android.core.R.string.tw__login_btn_txt);
        super.setTextColor(resources.getColor(com.twitter.sdk.android.core.R.color.tw__solid_white));
        super.setTextSize(0, resources.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_text_size));
        super.setTypeface(Typeface.DEFAULT_BOLD);
        super.setPadding(resources.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_left_padding), 0, resources.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_right_padding), 0);
        super.setBackgroundResource(com.twitter.sdk.android.core.R.drawable.tw__login_btn);
        super.setOnClickListener(new LoginClickListener());
        super.setAllCaps(false);
    }

    public void setCallback(Callback<TwitterSession> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null");
        }
        this.callback = callback;
    }

    public Callback<TwitterSession> getCallback() {
        return this.callback;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == getTwitterAuthClient().getRequestCode()) {
            getTwitterAuthClient().onActivityResult(i, i2, intent);
        }
    }

    protected Activity getActivity() {
        if (getContext() instanceof Activity) {
            return (Activity) getContext();
        }
        if (isInEditMode()) {
            return null;
        }
        throw new IllegalStateException(ERROR_MSG_NO_ACTIVITY);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private class LoginClickListener implements View.OnClickListener {
        private LoginClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            checkCallback(TwitterLoginButton.this.callback);
            checkActivity(TwitterLoginButton.this.activityRef.get());
            TwitterLoginButton.this.getTwitterAuthClient().authorize(TwitterLoginButton.this.activityRef.get(), TwitterLoginButton.this.callback);
            if (TwitterLoginButton.this.onClickListener != null) {
                TwitterLoginButton.this.onClickListener.onClick(view);
            }
        }

        private void checkCallback(Callback callback) {
            if (callback == null) {
                CommonUtils.logOrThrowIllegalStateException("Twitter", "Callback must not be null, did you call setCallback?");
            }
        }

        private void checkActivity(Activity activity) {
            if (activity == null || activity.isFinishing()) {
                CommonUtils.logOrThrowIllegalStateException("Twitter", TwitterLoginButton.ERROR_MSG_NO_ACTIVITY);
            }
        }
    }

    TwitterAuthClient getTwitterAuthClient() {
        if (this.authClient == null) {
            synchronized (TwitterLoginButton.class) {
                if (this.authClient == null) {
                    this.authClient = new TwitterAuthClient();
                }
            }
        }
        return this.authClient;
    }

    private void checkTwitterCoreAndEnable() {
        if (isInEditMode()) {
            return;
        }
        try {
            TwitterCore.getInstance();
        } catch (IllegalStateException e) {
            Fabric.getLogger().e("Twitter", e.getMessage());
            setEnabled(false);
        }
    }
}
