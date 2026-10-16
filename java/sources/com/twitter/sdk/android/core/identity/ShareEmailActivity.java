package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.TextView;
import com.twitter.sdk.android.core.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
public class ShareEmailActivity extends Activity {
    static final String EXTRA_RESULT_RECEIVER = "result_receiver";
    static final String EXTRA_SESSION_ID = "session_id";
    ShareEmailController controller;
    private TwitterSession session;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tw__activity_share_email);
        try {
            Intent intent = getIntent();
            ResultReceiver resultReceiver = getResultReceiver(intent);
            this.session = getSession(intent);
            this.controller = new ShareEmailController(new ShareEmailClient(this.session), resultReceiver);
            setUpShareEmailDesc(this, (TextView) findViewById(R.id.tw__share_email_desc));
        } catch (IllegalArgumentException e) {
            Fabric.getLogger().e(TwitterCore.TAG, "Failed to create ShareEmailActivity.", e);
            finish();
        }
    }

    private ResultReceiver getResultReceiver(Intent intent) {
        ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
        if (resultReceiver != null) {
            return resultReceiver;
        }
        throw new IllegalArgumentException("ResultReceiver must not be null. This activity should not be started directly.");
    }

    private TwitterSession getSession(Intent intent) {
        long longExtra = intent.getLongExtra("session_id", -1L);
        TwitterSession twitterSession = (TwitterSession) TwitterCore.getInstance().getSessionManager().getSession(longExtra);
        if (twitterSession != null) {
            return twitterSession;
        }
        throw new IllegalArgumentException("No TwitterSession for id:" + longExtra);
    }

    void setUpShareEmailDesc(Context context, TextView textView) {
        textView.setText(getResources().getString(R.string.tw__share_email_desc, context.getPackageManager().getApplicationLabel(context.getApplicationInfo()), this.session.getUserName()));
    }

    public void onClickNotNow(View view) {
        this.controller.cancelRequest();
        finish();
    }

    public void onClickAllow(View view) {
        this.controller.executeRequest();
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        this.controller.cancelRequest();
        super.onBackPressed();
    }
}
