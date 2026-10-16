package com.tds.common.tracker.model;

import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ActionModel implements BaseTrackModel {
    public static final int ACTION_CLICK = 0;
    public static final int ACTION_COLLECT = 3;
    public static final int ACTION_COMMENT = 2;
    public static final int ACTION_IMPRESSION = 4;
    public static final int ACTION_LIKE = 1;
    public static final String PARAM_NAME_CLICK = "click";
    public static final String PARAM_NAME_COLLECT = "collect";
    public static final String PARAM_NAME_COMMENT = "comment";
    public static final String PARAM_NAME_IMPRESSION = "impression";
    public static final String PARAM_NAME_LIKE = "like";
    private String targetViewName = "";
    private int actionType = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public ActionModel clickWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 0;
        return this;
    }

    public ActionModel likeWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 1;
        return this;
    }

    public ActionModel commentWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 2;
        return this;
    }

    public ActionModel collectWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 3;
        return this;
    }

    public ActionModel impressionWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 4;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() {
        String str;
        HashMap map = new HashMap();
        int i = this.actionType;
        if (i == 0) {
            str = PARAM_NAME_CLICK;
        } else if (i == 1) {
            str = PARAM_NAME_LIKE;
        } else if (i == 2) {
            str = PARAM_NAME_COMMENT;
        } else if (i != 3) {
            str = i != 4 ? "" : PARAM_NAME_IMPRESSION;
        } else {
            str = PARAM_NAME_COLLECT;
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(this.targetViewName)) {
            map.put(str, this.targetViewName);
        }
        return map;
    }
}
