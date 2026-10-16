package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tapsdk.antiaddiction.entities.Prompt;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddictionui.R;
import com.tapsdk.antiaddictionui.constant.Constants;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;
import com.voxelbusters.nativeplugins.defines.Keys;

/* JADX INFO: loaded from: classes.dex */
public class DialogTapAuthorization extends AbsBaseDialogFragment implements View.OnClickListener {
    public static final String TAG = "DialogTapAuthorization";
    private AuthorizationCallback authorizationCallback;
    private String clientId;
    private boolean isTapUser;
    private ImageView ivClose;
    private ScrollView rootContainer;
    private TextView tvAgreeUseButton;
    private TextView tvDisagreeUseButton;
    private TextView tvTitle;
    private boolean useAgeRange;
    private boolean useManual;
    private String userIdentifier;
    private WebView webContent;

    public interface AuthorizationCallback {
        void navigateToRealName(boolean z);

        void navigateToUnderReview();

        void onCloseRealName();

        void onRealNameSuccess(IdentifyState identifyState);
    }

    public static DialogTapAuthorization getInstance(String str, String str2, boolean z, boolean z2, AuthorizationCallback authorizationCallback) {
        DialogTapAuthorization dialogTapAuthorization = new DialogTapAuthorization();
        Bundle bundle = new Bundle();
        bundle.putString("client_id", str);
        bundle.putString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER, str2);
        bundle.putBoolean(Constants.ExtraBundleKey.KEY_USE_TAP_USER, z);
        bundle.putBoolean(Constants.ExtraBundleKey.KEY_USE_MANUAL, z2);
        dialogTapAuthorization.setArguments(bundle);
        dialogTapAuthorization.authorizationCallback = authorizationCallback;
        return dialogTapAuthorization;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public int layoutResId() {
        return R.layout.antiaddictionui_dialog_tap_authorization;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.include_title_bar);
        this.tvTitle = (TextView) viewFindViewById.findViewById(R.id.tv_title);
        this.ivClose = (ImageView) viewFindViewById.findViewById(R.id.iv_close);
        this.rootContainer = (ScrollView) view;
        this.webContent = (WebView) view.findViewById(R.id.web_content);
        this.tvAgreeUseButton = (TextView) view.findViewById(R.id.tv_agree_use_button);
        this.tvDisagreeUseButton = (TextView) view.findViewById(R.id.tv_disagree_use_button);
        this.ivClose.setVisibility(0);
        this.ivClose.setOnClickListener(this);
        this.tvAgreeUseButton.setOnClickListener(this);
        this.tvDisagreeUseButton.setOnClickListener(this);
        this.webContent.setOnTouchListener(new View.OnTouchListener() { // from class: com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int historySize;
                if (motionEvent.getAction() == 2 && (historySize = motionEvent.getHistorySize()) > 0) {
                    boolean z = true;
                    int i = motionEvent.getHistoricalY(historySize - 1) > motionEvent.getHistoricalY(0) ? 1 : 0;
                    boolean zCanScrollVertically = DialogTapAuthorization.this.webContent.canScrollVertically(i);
                    boolean zCanScrollVertically2 = DialogTapAuthorization.this.rootContainer.canScrollVertically(i);
                    if (i > 0 && (DialogTapAuthorization.this.webContent.getScrollY() == 0 || DialogTapAuthorization.this.rootContainer.getScrollY() == 0)) {
                        ((WebView) view2).requestDisallowInterceptTouchEvent(DialogTapAuthorization.this.webContent.getScrollY() != 0);
                    } else {
                        WebView webView = (WebView) view2;
                        if (!zCanScrollVertically && zCanScrollVertically2) {
                            z = false;
                        }
                        webView.requestDisallowInterceptTouchEvent(z);
                    }
                }
                return false;
            }
        });
        Prompt prompt = AntiAddictionSettings.getInstance().getRealNameConfig().realNameText.tapAuthTip;
        if (prompt != null) {
            this.tvTitle.setText(prompt.title);
            String str = prompt.description;
            if (str == null) {
                str = "";
            }
            this.webContent.loadDataWithBaseURL(null, str, Keys.Mime.HTML_TEXT, "utf-8", null);
            this.tvAgreeUseButton.setText(prompt.positiveButton);
            this.tvDisagreeUseButton.setText(prompt.negativeButton);
            if (useManualIdentify()) {
                return;
            }
            this.tvDisagreeUseButton.setVisibility(8);
        }
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void handleArgumentsData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.clientId = arguments.getString("client_id");
            this.userIdentifier = arguments.getString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER);
            this.isTapUser = arguments.getBoolean(Constants.ExtraBundleKey.KEY_USE_TAP_USER, false);
            this.useManual = arguments.getBoolean(Constants.ExtraBundleKey.KEY_USE_MANUAL, false);
        }
    }

    private boolean useManualIdentify() {
        return !this.isTapUser && this.useManual;
    }

    private void authorizeFromTapTapAndFetchTapTapIdentifyInfo() {
        Activity activity = getActivity();
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        TapAuthorizationService.getInstance().authorizeFromTapTap(activity, this.clientId, this.userIdentifier, new TapAuthorizationService.TapAuthorizationCallback() { // from class: com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.2
            @Override // com.tapsdk.antiaddictionui.widget.TapAuthorizationService.TapAuthorizationCallback
            public void onSuccess(IdentifyState identifyState) {
                if (DialogTapAuthorization.this.authorizationCallback != null) {
                    DialogTapAuthorization.this.authorizationCallback.onRealNameSuccess(identifyState);
                }
                DialogTapAuthorization.this.dismissAllowingStateLoss();
            }

            @Override // com.tapsdk.antiaddictionui.widget.TapAuthorizationService.TapAuthorizationCallback
            public void onCancel() {
                DialogTapAuthorization.this.enableAgreeClickable(true);
            }

            @Override // com.tapsdk.antiaddictionui.widget.TapAuthorizationService.TapAuthorizationCallback
            public void onFail(Throwable th) {
                DialogTapAuthorization.this.enableAgreeClickable(true);
            }

            @Override // com.tapsdk.antiaddictionui.widget.TapAuthorizationService.TapAuthorizationCallback
            public void navigateToUnderReview() {
                if (DialogTapAuthorization.this.authorizationCallback != null) {
                    DialogTapAuthorization.this.authorizationCallback.navigateToUnderReview();
                }
                DialogTapAuthorization.this.dismissAllowingStateLoss();
            }

            @Override // com.tapsdk.antiaddictionui.widget.TapAuthorizationService.TapAuthorizationCallback
            public void navigateToRealName() {
                DialogTapAuthorization.this.navigateToManualIdentify(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToManualIdentify(boolean z) {
        AuthorizationCallback authorizationCallback = this.authorizationCallback;
        if (authorizationCallback != null) {
            authorizationCallback.navigateToRealName(z);
        }
        dismissAllowingStateLoss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            AuthorizationCallback authorizationCallback = this.authorizationCallback;
            if (authorizationCallback != null) {
                authorizationCallback.onCloseRealName();
            }
            dismissAllowingStateLoss();
            return;
        }
        if (id == R.id.tv_agree_use_button) {
            enableAgreeClickable(false);
            authorizeFromTapTapAndFetchTapTapIdentifyInfo();
        } else if (id == R.id.tv_disagree_use_button) {
            navigateToManualIdentify(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAgreeClickable(boolean z) {
        TextView textView = this.tvAgreeUseButton;
        if (textView == null || textView.getVisibility() != 0) {
            return;
        }
        this.tvAgreeUseButton.setClickable(z);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        try {
            WebView webView = this.webContent;
            if (webView != null) {
                webView.destroy();
                this.webContent = null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
