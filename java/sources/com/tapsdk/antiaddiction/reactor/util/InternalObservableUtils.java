package com.tapsdk.antiaddiction.reactor.util;

import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorNotImplementedException;
import com.tapsdk.antiaddiction.reactor.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public enum InternalObservableUtils {
    ;

    public static final Action1<Throwable> ERROR_NOT_IMPLEMENTED = new Action1<Throwable>() { // from class: com.tapsdk.antiaddiction.reactor.util.InternalObservableUtils.ErrorNotImplementedAction
        @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
        public void call(Throwable th) {
            throw new OnErrorNotImplementedException(th);
        }
    };
}
