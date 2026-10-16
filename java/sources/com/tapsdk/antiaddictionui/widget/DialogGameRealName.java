package com.tapsdk.antiaddictionui.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tapsdk.antiaddiction.entities.IdentificationInfo;
import com.tapsdk.antiaddiction.entities.Prompt;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.models.IdentifyModel;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.Subscription;
import com.tapsdk.antiaddiction.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tapsdk.antiaddiction.reactor.schedulers.Schedulers;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddictionui.R;
import com.tapsdk.antiaddictionui.constant.Constants;
import com.tapsdk.antiaddictionui.utils.ToastUtils;

/* JADX INFO: loaded from: classes.dex */
public class DialogGameRealName extends AbsBaseDialogFragment implements View.OnClickListener {
    private static final int DETAIL_TIPS_MAX_LINE = 3;
    public static final String TAG = "DialogGameRealName";
    private String clientId;
    private EditText etUserIdentification;
    private EditText etUserRealName;
    private boolean hasAuthRecord;
    private Subscription identificationCheckSubscription;
    private ImageView ivClose;
    private LinearLayout llDetailTips;
    private RealNameResultCallback realNameResultCallback;
    private TextView tvDetailInformationTips;
    private TextView tvFunctionButton;
    private TextView tvRealNameTips;
    private TextView tvTitle;
    private TextView tvUserAuthorizationVerifyingTips;
    private IdentificationInfo userEditIdentificationInfo;
    private String userIdentifier;
    private final String tipsSuffix = " 详细内容";
    private final IdentifyModel identifyModel = new IdentifyModel();
    private boolean isIdentificationInfoVerifying = false;

    public interface RealNameResultCallback {
        void onCloseRealName();

        void onRealNameSuccess(IdentifyState identifyState);
    }

    public static DialogGameRealName getInstance(String str, String str2, boolean z, boolean z2, RealNameResultCallback realNameResultCallback) {
        DialogGameRealName dialogGameRealName = new DialogGameRealName();
        Bundle bundle = new Bundle();
        bundle.putString("client_id", str);
        bundle.putString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER, str2);
        bundle.putBoolean(Constants.ExtraBundleKey.KEY_HAS_AUTH_RECORD, z);
        bundle.putBoolean(Constants.ExtraBundleKey.KEY_USE_FALLBACK, z2);
        dialogGameRealName.setArguments(bundle);
        dialogGameRealName.realNameResultCallback = realNameResultCallback;
        return dialogGameRealName;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public int layoutResId() {
        return R.layout.antiaddictionui_dialog_game_real_name;
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.include_title_bar);
        this.tvTitle = (TextView) viewFindViewById.findViewById(R.id.tv_title);
        this.ivClose = (ImageView) viewFindViewById.findViewById(R.id.iv_close);
        this.tvRealNameTips = (TextView) view.findViewById(R.id.tv_real_name_tips);
        this.etUserRealName = (EditText) view.findViewById(R.id.et_user_real_name);
        this.etUserIdentification = (EditText) view.findViewById(R.id.et_user_identification);
        this.tvFunctionButton = (TextView) view.findViewById(R.id.tv_function_button);
        this.tvUserAuthorizationVerifyingTips = (TextView) view.findViewById(R.id.tv_user_authorization_verifying_tips);
        this.llDetailTips = (LinearLayout) view.findViewById(R.id.ll_detail_tips);
        this.tvDetailInformationTips = (TextView) view.findViewById(R.id.tv_detail_information_tips);
        Prompt prompt = this.hasAuthRecord ? AntiAddictionSettings.getInstance().getRealNameConfig().realNameText.manualAuthFailedTip : AntiAddictionSettings.getInstance().getRealNameConfig().realNameText.manualAuthTip;
        if (prompt != null) {
            this.tvTitle.setText(prompt.title);
            Spanned spannedFromHtml = Html.fromHtml(prompt.description);
            this.tvRealNameTips.setText(spannedFromHtml);
            this.tvDetailInformationTips.setText(spannedFromHtml);
            this.tvFunctionButton.setText(prompt.positiveButton);
        }
        if (!this.hasAuthRecord) {
            this.tvRealNameTips.post(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.DialogGameRealName.1
                @Override // java.lang.Runnable
                public void run() {
                    if (DialogGameRealName.this.tvRealNameTips.getLineCount() > 3) {
                        String str = DialogGameRealName.this.tvRealNameTips.getText().toString().substring(0, (DialogGameRealName.this.tvRealNameTips.getLayout().getLineEnd(2) - 5) - 3) + "... 详细内容";
                        SpannableString spannableString = new SpannableString(str);
                        int iIndexOf = str.indexOf(" 详细内容");
                        int i = iIndexOf + 5;
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00D9C5")), iIndexOf, i, 33);
                        spannableString.setSpan(new StyleSpan(1), iIndexOf, i, 33);
                        DialogGameRealName.this.tvRealNameTips.setText(spannableString);
                    }
                }
            });
        }
        this.ivClose.setVisibility(0);
        this.ivClose.setOnClickListener(this);
        this.tvRealNameTips.setOnClickListener(this);
        this.tvFunctionButton.setOnClickListener(this);
        this.tvDetailInformationTips.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override // com.tapsdk.antiaddictionui.widget.AbsBaseDialogFragment
    public void handleArgumentsData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.clientId = arguments.getString("client_id");
            this.userIdentifier = arguments.getString(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER);
            this.hasAuthRecord = arguments.getBoolean(Constants.ExtraBundleKey.KEY_HAS_AUTH_RECORD);
        }
    }

    private Observable<IdentifyState> chooseIdentifyObservable(String str, String str2, String str3, String str4) {
        return this.identifyModel.identifyUserManually(str, str2, str3, str4);
    }

    private void identifyUserReal(String str, String str2, String str3, String str4) {
        Subscription subscription = this.identificationCheckSubscription;
        if (subscription == null || subscription.isUnsubscribed()) {
            this.identificationCheckSubscription = chooseIdentifyObservable(str, str2, str3, str4).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<IdentifyState>() { // from class: com.tapsdk.antiaddictionui.widget.DialogGameRealName.2
                @Override // com.tapsdk.antiaddiction.reactor.Observer
                public void onCompleted() {
                }

                @Override // com.tapsdk.antiaddiction.reactor.Observer
                public void onError(Throwable th) {
                    boolean z = th instanceof AntiServerException;
                    if (z && ((AntiServerException) th).statusCode >= 500) {
                        AntiAddictionLogger.e("auth service failed");
                        ToastUtils.safeToast(DialogGameRealName.this.getActivity(), "网络异常，请稍后重试");
                    } else {
                        if (z) {
                            AntiServerException antiServerException = (AntiServerException) th;
                            if (!TextUtils.isEmpty(antiServerException.errorType) && !TextUtils.isEmpty(antiServerException.errorMsg)) {
                                ToastUtils.safeToast(DialogGameRealName.this.getActivity(), antiServerException.errorMsg);
                                return;
                            } else {
                                ToastUtils.safeToast(DialogGameRealName.this.getActivity(), "认证未通过，请提交真实信息");
                                return;
                            }
                        }
                        ToastUtils.safeToast(DialogGameRealName.this.getActivity(), "网络异常，请稍后重试");
                    }
                }

                @Override // com.tapsdk.antiaddiction.reactor.Observer
                public void onNext(IdentifyState identifyState) {
                    DialogGameRealName.this.processIdentifyResult(identifyState);
                }
            });
        }
    }

    private void request2CheckIdentificationInfo() {
        IdentificationInfo identificationInfo = this.userEditIdentificationInfo;
        if (identificationInfo == null) {
            return;
        }
        identifyUserReal(this.clientId, this.userIdentifier, identificationInfo.name, this.userEditIdentificationInfo.idCard);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processIdentifyResult(IdentifyState identifyState) {
        if (identifyState == null) {
            this.isIdentificationInfoVerifying = false;
            ToastUtils.safeToast(getActivity(), "认证未通过，请提交真实信息");
            return;
        }
        if (identifyState.identifyState.equals(IdentifyState.STATUS_PASS)) {
            ToastUtils.safeToast(getActivity(), "提交成功");
            RealNameResultCallback realNameResultCallback = this.realNameResultCallback;
            if (realNameResultCallback != null) {
                realNameResultCallback.onRealNameSuccess(identifyState);
            }
            dismissAllowingStateLoss();
            return;
        }
        if (identifyState.identifyState.equals(IdentifyState.STATUS_WAITING)) {
            this.isIdentificationInfoVerifying = true;
            this.tvUserAuthorizationVerifyingTips.setVisibility(0);
            this.tvRealNameTips.setVisibility(8);
            this.etUserRealName.setVisibility(8);
            this.etUserIdentification.setVisibility(8);
            Prompt prompt = AntiAddictionSettings.getInstance().getRealNameConfig().realNameText.authWaitingTip;
            if (prompt != null) {
                this.tvUserAuthorizationVerifyingTips.setText(Html.fromHtml(prompt.description));
                this.tvFunctionButton.setText(prompt.positiveButton);
                return;
            }
            return;
        }
        this.isIdentificationInfoVerifying = false;
        ToastUtils.safeToast(getActivity(), "认证未通过，请提交真实信息");
    }

    private boolean checkIdentificationInfo() {
        String string = this.etUserRealName.getEditableText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils.safeToast(getActivity(), "姓名不能为空");
            return false;
        }
        String string2 = this.etUserIdentification.getEditableText().toString();
        if (TextUtils.isEmpty(string2)) {
            ToastUtils.safeToast(getActivity(), "身份信息不能为空");
            return false;
        }
        if (this.userEditIdentificationInfo == null) {
            this.userEditIdentificationInfo = new IdentificationInfo();
        }
        this.userEditIdentificationInfo.name = string;
        this.userEditIdentificationInfo.idCard = string2;
        return true;
    }

    private boolean isDetailTipsShowing() {
        if (this.llDetailTips.getVisibility() != 0) {
            return false;
        }
        this.llDetailTips.setVisibility(8);
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (isDetailTipsShowing()) {
            return;
        }
        int id = view.getId();
        if (id == R.id.iv_close) {
            RealNameResultCallback realNameResultCallback = this.realNameResultCallback;
            if (realNameResultCallback != null) {
                realNameResultCallback.onCloseRealName();
            }
            dismissAllowingStateLoss();
            return;
        }
        if (id == R.id.tv_real_name_tips) {
            if (this.hasAuthRecord) {
                return;
            }
            this.llDetailTips.setVisibility(0);
        } else if (id == R.id.tv_function_button) {
            if (!this.isIdentificationInfoVerifying) {
                if (checkIdentificationInfo()) {
                    request2CheckIdentificationInfo();
                    return;
                }
                return;
            }
            dismissAllowingStateLoss();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Subscription subscription = this.identificationCheckSubscription;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
