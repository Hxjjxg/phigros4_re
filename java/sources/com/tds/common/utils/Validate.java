package com.tds.common.utils;

import com.tds.common.log.Logger;

/* JADX INFO: loaded from: classes.dex */
public class Validate {
    private static final Logger LOG = Logger.getCommonLogger();

    public static void notNull(Object obj, String str) {
        if (obj != null) {
            return;
        }
        LOG.e("validate", "Argument '" + str + "' can not be null");
        throw new NullPointerException("Argument '" + str + "' can not be null");
    }
}
