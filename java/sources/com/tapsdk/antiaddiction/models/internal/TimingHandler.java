package com.tapsdk.antiaddiction.models.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;

/* JADX INFO: loaded from: classes.dex */
public class TimingHandler extends Handler {
    public static final int COUNT_DOWN_PERIOD = 1000;
    public static final int MESSAGE_CHILD_TIME_RUN_OUT = 4;
    public static final int MESSAGE_COUNT_DOWN = 1;
    public static final int MESSAGE_COUNT_TIME = 0;
    public static final int MESSAGE_LOGOUT = 5;
    public static final int MESSAGE_SEND_TIME = 2;
    public static final int MESSAGE_UPLOAD_FAILED = 6;
    public int heartbeatInterval;
    private final InteractiveOperation operation;

    public interface InteractiveOperation {
        void childTimeRunOut();

        boolean countDown();

        boolean countTime();

        void logout();

        void syncServerError();
    }

    public TimingHandler(Looper looper, int i, InteractiveOperation interactiveOperation) {
        super(looper);
        this.operation = interactiveOperation;
        this.heartbeatInterval = i;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        try {
            int i = message.what;
            if (i == 0) {
                boolean zCountTime = this.operation.countTime();
                AntiAddictionLogger.d("MESSAGE_COUNT_TIME:" + this.heartbeatInterval);
                if (zCountTime) {
                    Message messageObtain = Message.obtain(message);
                    messageObtain.arg1 = 1;
                    sendMessageDelayed(messageObtain, ((long) this.heartbeatInterval) * 1000);
                }
            } else if (i != 1) {
                if (i == 4) {
                    this.operation.childTimeRunOut();
                } else if (i == 5) {
                    this.operation.logout();
                } else if (i == 6) {
                    this.operation.syncServerError();
                }
            } else if (!this.operation.countDown()) {
                sendMessageDelayed(Message.obtain(message), 1000L);
            }
        } catch (Exception e) {
            AntiAddictionLogger.e("TransactionHandler handleMessage error");
            AntiAddictionLogger.printStackTrace(e);
        } catch (Throwable th) {
            AntiAddictionLogger.printStackTrace(th);
        }
    }
}
