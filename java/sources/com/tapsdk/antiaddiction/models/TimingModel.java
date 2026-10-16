package com.tapsdk.antiaddiction.models;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.entities.CheckPlayResult;
import com.tapsdk.antiaddiction.entities.ThreeTuple;
import com.tapsdk.antiaddiction.entities.UserInfo;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.models.internal.TimingHandler;
import com.tapsdk.antiaddiction.reactor.functions.Action1;
import com.tapsdk.antiaddiction.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddiction.utils.TimeUtil;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TimingModel {
    private final Context context;
    private final TimingMessageListener timingMessageListener;
    private final UserModel userModel;
    private final Handler mainLooperHandler = new Handler(Looper.getMainLooper());
    public boolean inTiming = false;
    private int heartbeatInterval = 120;
    private HandlerThread mHandlerThread = null;
    private TimingHandler mHandler = null;
    private final TimingHandler.InteractiveOperation interactiveOperation = new TimingHandler.InteractiveOperation() { // from class: com.tapsdk.antiaddiction.models.TimingModel.5
        @Override // com.tapsdk.antiaddiction.models.internal.TimingHandler.InteractiveOperation
        public boolean countTime() {
            final CheckPlayResult checkPlayResultSyncTime;
            if (TimingModel.this.userModel.getCurrentUser() == null || (checkPlayResultSyncTime = TimingModel.this.syncTime()) == null) {
                return false;
            }
            AntiAddictionLogger.d("remain time =" + checkPlayResultSyncTime.remainTime);
            if (checkPlayResultSyncTime.remainTime <= TimingModel.this.heartbeatInterval && checkPlayResultSyncTime.remainTime > 0) {
                Message messageObtainMessage = TimingModel.this.mHandler.obtainMessage();
                messageObtainMessage.what = 1;
                TimingModel.this.mHandler.sendMessageDelayed(messageObtainMessage, 1000L);
                return false;
            }
            if (checkPlayResultSyncTime.remainTime > 0) {
                return true;
            }
            TimingModel.this.mainLooperHandler.post(new Runnable() { // from class: com.tapsdk.antiaddiction.models.TimingModel.5.1
                @Override // java.lang.Runnable
                public void run() {
                    TimingModel.this.timingMessageListener.onMessage(1030, AntiAddictionSettings.getInstance().generateAlertMessage(0, checkPlayResultSyncTime.title, checkPlayResultSyncTime.description));
                }
            });
            TimingModel.this.unbind();
            return false;
        }

        @Override // com.tapsdk.antiaddiction.models.internal.TimingHandler.InteractiveOperation
        public boolean countDown() {
            if (TimingModel.this.userModel.getCurrentUser() == null) {
                return true;
            }
            TimingModel.this.userModel.getCurrentUser().updateRemainTime(1);
            if (TimingModel.this.userModel.getCurrentUser().getRemainTime() > 0) {
                return false;
            }
            AntiAddictionLogger.d("remain time =" + TimingModel.this.userModel.getCurrentUser().getRemainTime() + " run out");
            Message messageObtainMessage = TimingModel.this.mHandler.obtainMessage();
            messageObtainMessage.what = 4;
            TimingModel.this.mHandler.sendMessage(messageObtainMessage);
            return true;
        }

        @Override // com.tapsdk.antiaddiction.models.internal.TimingHandler.InteractiveOperation
        public void childTimeRunOut() {
            if (TimingModel.this.userModel.getCurrentUser() == null) {
                return;
            }
            AntiAddictionLogger.d("sync time for  stop");
            final CheckPlayResult checkPlayResultSyncTime = TimingModel.this.syncTime();
            if (checkPlayResultSyncTime != null && checkPlayResultSyncTime.remainTime <= 0) {
                AntiAddictionLogger.d("stop result = " + checkPlayResultSyncTime);
                TimingModel.this.mainLooperHandler.post(new Runnable() { // from class: com.tapsdk.antiaddiction.models.TimingModel.5.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TimingModel.this.timingMessageListener.onMessage(1030, AntiAddictionSettings.getInstance().generateAlertMessage(checkPlayResultSyncTime.remainTime, checkPlayResultSyncTime.title, checkPlayResultSyncTime.description));
                    }
                });
                TimingModel.this.unbind();
                return;
            }
            countTime();
        }

        @Override // com.tapsdk.antiaddiction.models.internal.TimingHandler.InteractiveOperation
        public void logout() {
            TimingModel.this.timingMessageListener.onMessage(9001, null);
        }

        @Override // com.tapsdk.antiaddiction.models.internal.TimingHandler.InteractiveOperation
        public void syncServerError() {
            UserInfo currentUser = TimingModel.this.userModel.getCurrentUser();
            if (currentUser == null || TextUtils.isEmpty(currentUser.accessToken)) {
                return;
            }
            TimingModel.this.unbind();
            HashMap map = new HashMap();
            map.put(Constants.MsgExtraParams.DESCRIPTION, "网络异常，请稍后重试");
            TimingModel.this.timingMessageListener.onMessage(Constants.ANTI_ADDICTION_CALLBACK_CODE.INVALID_CLIENT_OR_NETWORK_ERROR, map);
        }
    };

    public interface TimingMessageListener {
        void onMessage(int i, Map<String, Object> map);
    }

    public TimingModel(UserModel userModel, Context context, TimingMessageListener timingMessageListener) {
        this.userModel = userModel;
        this.context = context;
        this.timingMessageListener = timingMessageListener;
        initLoginStatusChangedListener();
    }

    public void setHeartbeatInterval(int i) {
        this.heartbeatInterval = i;
    }

    private void initLoginStatusChangedListener() {
        this.userModel.getUserLoginStatusChangedObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() { // from class: com.tapsdk.antiaddiction.models.TimingModel.1
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Boolean bool) {
                if (bool.booleanValue()) {
                    return;
                }
                TimingModel.this.unbind();
            }
        }, new Action1<Throwable>() { // from class: com.tapsdk.antiaddiction.models.TimingModel.2
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Throwable th) {
            }
        });
    }

    public void bind() {
        unbind();
        AntiAddictionLogger.d("bind");
        this.mainLooperHandler.post(new Runnable() { // from class: com.tapsdk.antiaddiction.models.TimingModel.3
            @Override // java.lang.Runnable
            public void run() {
                if (TimingModel.this.userModel == null || TimingModel.this.userModel.getCurrentUser() == null) {
                    return;
                }
                TimingModel.this.mHandlerThread = new HandlerThread("AntiAddictionMonitor", 10);
                TimingModel.this.mHandlerThread.start();
                TimingModel.this.mHandler = new TimingHandler(TimingModel.this.mHandlerThread.getLooper(), TimingModel.this.heartbeatInterval, TimingModel.this.interactiveOperation);
                Message messageObtainMessage = TimingModel.this.mHandler.obtainMessage();
                messageObtainMessage.what = 0;
                messageObtainMessage.arg1 = 1;
                TimingModel.this.mHandler.sendMessage(messageObtainMessage);
                TimingModel.this.inTiming = true;
            }
        });
    }

    public void unbind() {
        AntiAddictionLogger.d("unbind");
        this.mainLooperHandler.post(new Runnable() { // from class: com.tapsdk.antiaddiction.models.TimingModel.4
            @Override // java.lang.Runnable
            public void run() {
                if (TimingModel.this.mHandlerThread != null) {
                    TimingModel.this.mHandlerThread.quit();
                    TimingModel.this.mHandlerThread = null;
                }
                if (TimingModel.this.mHandler != null) {
                    TimingModel.this.mHandler.removeCallbacksAndMessages(null);
                    TimingModel.this.mHandler = null;
                }
                TimingModel.this.inTiming = false;
            }
        });
    }

    private CheckPlayResult sendGameTimeToServerSync() {
        ThreeTuple<Boolean, CheckPlayResult, Boolean> threeTupleUploadPlayLogSync;
        UserInfo userInfoM7clone = this.userModel.getCurrentUser().m7clone();
        AntiAddictionLogger.d("-------sendGameTimeToServerSync-------");
        UserAntiConfig userAntiConfig = AntiAddictionSettings.getInstance().getUserAntiConfig();
        if (userAntiConfig.policy.active.equals(AntiAddictionSettings.TIME_RANGE_MODE) && userAntiConfig.policy.model.equals("local")) {
            threeTupleUploadPlayLogSync = PlayLogModel.getInstance().localCheckPlayable();
        } else {
            threeTupleUploadPlayLogSync = PlayLogModel.getInstance().uploadPlayLogSync(userInfoM7clone.clientId, userInfoM7clone.userId);
        }
        AntiAddictionLogger.d("after update serverTime:" + TimeUtil.getFullTime(ServerTimeModel.getRecentServerTime() * 1000));
        return threeTupleUploadPlayLogSync.secondParam;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CheckPlayResult syncTime() {
        try {
            CheckPlayResult checkPlayResultSendGameTimeToServerSync = sendGameTimeToServerSync();
            this.userModel.getCurrentUser().resetRemainTime(checkPlayResultSendGameTimeToServerSync.remainTime);
            return checkPlayResultSendGameTimeToServerSync;
        } catch (Exception e) {
            if ((e.getCause() instanceof AntiServerException) && ((AntiServerException) e.getCause()).isTokenExpired()) {
                Message messageObtainMessage = this.mHandler.obtainMessage();
                messageObtainMessage.what = 5;
                this.mHandler.sendMessage(messageObtainMessage);
                return null;
            }
            Message messageObtainMessage2 = this.mHandler.obtainMessage();
            messageObtainMessage2.what = 6;
            this.mHandler.sendMessage(messageObtainMessage2);
            return null;
        }
    }
}
