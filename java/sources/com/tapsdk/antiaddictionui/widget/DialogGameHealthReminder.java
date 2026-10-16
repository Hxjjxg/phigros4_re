package com.tapsdk.antiaddictionui.widget;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.tapsdk.antiaddiction.AntiAddictionKit;
import com.tapsdk.antiaddiction.entities.Prompt;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddictionui.R;
import com.tapsdk.antiaddictionui.constant.Constants;

/* JADX INFO: loaded from: classes.dex */
public class DialogGameHealthReminder extends AbsBaseDialogFragment implements View.OnClickListener {
    public static final String TAG = "DialogAccountLimit";
    public static final int TYPE_DURING_CERTIFICATION = 1002;
    public static final int TYPE_OUT_OF_PLAYABLE_TIME = 1003;
    public static final int TYPE_PAY_LIMIT = 1005;
    public static final int TYPE_REMAIN_TIME_TIPS = 1004;
    public static final int TYPE_TIME_LIMIT = 1001;
    private String clientId;
    private int currentLimitType;
    private String description;
    private String negativeButtonStr;
    private String positiveButtonStr;
    private Callback reminderCallback;
    private String title;
    private TextView tvFunctionButton;
    private TextView tvLimitTips;
    private TextView tvSwitchAccountButton;
    private TextView tvTitle;
    private String userIdentifier;

    public interface Callback {
        void checkPending();

        void confirmNotice();

        void enterGame();
    }

    public static DialogGameHealthReminder getInstance(String str, String str2, int i, String str3, String str4, String str5, String str6, Callback callback) {
        DialogGameHealthReminder dialogGameHealthReminder = new DialogGameHealthReminder();
        Bundle bundle = new Bundle();
        bundle.putString("client_id", str);
        bundle.putString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER, str2);
        bundle.putInt(Constants.ExtraBundleKey.KEY_LIMIT_TYPE, i);
        bundle.putString(Constants.ExtraBundleKey.KEY_TITLE, str3);
        bundle.putString(Constants.ExtraBundleKey.KEY_DESCRIPTION, str4);
        bundle.putString(Constants.ExtraBundleKey.KEY_NEGATIVE_BUTTON, str5);
        bundle.putString(Constants.ExtraBundleKey.KEY_POSITIVE_BUTTON, str6);
        dialogGameHealthReminder.setArguments(bundle);
        dialogGameHealthReminder.reminderCallback = callback;
        return dialogGameHealthReminder;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public int layoutResId() {
        return R.layout.antiaddictionui_dialog_game_health_reminder;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void initView(View view) {
        Prompt prompt;
        this.tvTitle = (TextView) view.findViewById(R.id.include_title_bar).findViewById(R.id.tv_title);
        this.tvLimitTips = (TextView) view.findViewById(R.id.tv_limit_tips);
        this.tvSwitchAccountButton = (TextView) view.findViewById(R.id.tv_switch_account_button);
        this.tvFunctionButton = (TextView) view.findViewById(R.id.tv_function_button);
        this.tvLimitTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.tvSwitchAccountButton.setOnClickListener(this);
        this.tvFunctionButton.setOnClickListener(this);
        updateButtonView();
        if (this.tvSwitchAccountButton.getVisibility() == 0 && !AntiAddictionKit.showSwitchAccount()) {
            this.tvSwitchAccountButton.setVisibility(8);
        }
        if (this.tvSwitchAccountButton.getVisibility() != 0) {
            this.tvFunctionButton.setBackgroundResource(R.drawable.antiaddictionui_alert_positive_bg);
            this.tvFunctionButton.setTextColor(-1);
        }
        if (this.currentLimitType == 1002) {
            prompt = AntiAddictionSettings.getInstance().getRealNameConfig().realNameText.authWaitingTip;
        } else {
            prompt = new Prompt();
            prompt.title = this.title;
            prompt.description = this.description;
            prompt.negativeButton = this.negativeButtonStr;
            prompt.positiveButton = this.positiveButtonStr;
        }
        if (prompt != null) {
            this.tvTitle.setText(prompt.title);
            this.tvLimitTips.setText(Html.fromHtml(prompt.description));
            this.tvFunctionButton.setText(prompt.positiveButton);
        }
    }

    private void updateButtonView() {
        this.tvFunctionButton.setVisibility(0);
        int i = this.currentLimitType;
        if (i == 1004 || i == 1005) {
            this.tvSwitchAccountButton.setVisibility(8);
        } else {
            this.tvSwitchAccountButton.setVisibility(0);
        }
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void handleArgumentsData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.clientId = arguments.getString("client_id");
            this.userIdentifier = arguments.getString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER);
            this.currentLimitType = arguments.getInt(Constants.ExtraBundleKey.KEY_LIMIT_TYPE);
            this.title = arguments.getString(Constants.ExtraBundleKey.KEY_TITLE);
            this.description = arguments.getString(Constants.ExtraBundleKey.KEY_DESCRIPTION);
            this.negativeButtonStr = arguments.getString(Constants.ExtraBundleKey.KEY_NEGATIVE_BUTTON);
            this.positiveButtonStr = arguments.getString(Constants.ExtraBundleKey.KEY_POSITIVE_BUTTON);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_switch_account_button) {
            AntiAddictionKit.switchAccount();
            dismissAllowingStateLoss();
            return;
        }
        if (id == R.id.tv_function_button) {
            dismissAllowingStateLoss();
            int i = this.currentLimitType;
            if (i == 1002) {
                Callback callback = this.reminderCallback;
                if (callback != null) {
                    callback.checkPending();
                    return;
                }
                return;
            }
            if (i == 1003) {
                getActivity().finishAffinity();
                System.exit(0);
                return;
            }
            if (i == 1004) {
                Callback callback2 = this.reminderCallback;
                if (callback2 != null) {
                    callback2.enterGame();
                    return;
                }
                return;
            }
            if (i == 1005) {
                dismissAllowingStateLoss();
            } else if (i == 1001) {
                getActivity().finishAffinity();
                System.exit(0);
            }
        }
    }
}
