package com.voxelbusters.nativeplugins.features.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class UiHandler {
    private static UiHandler INSTANCE;
    private String currentDisplayedUiTag = null;
    private final HashMap<String, Bundle> uiElementsMap = new HashMap<>();
    private final ArrayList<String> queueList = new ArrayList<>();

    public static UiHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UiHandler();
        }
        return INSTANCE;
    }

    private UiHandler() {
    }

    public void showAlertDialogWithMultipleButtons(String str, String str2, String str3, String str4) {
        Bundle bundle = new Bundle();
        bundle.putString("title", str);
        bundle.putString("message", str2);
        bundle.putStringArray(Keys.BUTTON_LIST, StringUtility.convertJsonStringToStringArray(str3));
        bundle.putString("tag", str4);
        bundle.putInt("type", eUiType.ALERT_DIALOG.ordinal());
        pushToActivityQueue(bundle, str4);
    }

    public void showSingleFieldPromptDialog(String str, String str2, String str3, boolean z, String str4) {
        Bundle bundle = new Bundle();
        bundle.putString("title", str);
        bundle.putString("message", str2);
        bundle.putStringArray(Keys.BUTTON_LIST, StringUtility.convertJsonStringToStringArray(str4));
        bundle.putBoolean(Keys.IS_SECURE, z);
        bundle.putString(Keys.PLACE_HOLDER_TEXT_1, str3);
        bundle.putInt("type", eUiType.SINGLE_FIELD_PROMPT.ordinal());
        startActivity(bundle);
    }

    public void showLoginPromptDialog(String str, String str2, String str3, String str4, String str5) {
        Bundle bundle = new Bundle();
        bundle.putString("title", str);
        bundle.putString("message", str2);
        bundle.putStringArray(Keys.BUTTON_LIST, StringUtility.convertJsonStringToStringArray(str5));
        bundle.putString(Keys.PLACE_HOLDER_TEXT_1, str3);
        bundle.putString(Keys.PLACE_HOLDER_TEXT_2, str4);
        bundle.putInt("type", eUiType.LOGIN_PROMPT.ordinal());
        startActivity(bundle);
    }

    public void showToast(final String str, String str2) {
        final int i = !str2.equals("SHORT") ? 1 : 0;
        NativePluginHelper.executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.features.ui.UiHandler.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(NativePluginHelper.getCurrentContext(), str, i).show();
            }
        });
    }

    public void pushToActivityQueue(Bundle bundle, String str) {
        if (this.currentDisplayedUiTag != null) {
            Debug.log(CommonDefines.UI_TAG, "Queuing this ui element");
            this.queueList.add(str);
            this.uiElementsMap.put(str, bundle);
        } else {
            startActivity(bundle);
            this.currentDisplayedUiTag = str;
        }
    }

    void startActivity(Bundle bundle) {
        Intent intent = new Intent(NativePluginHelper.getCurrentContext(), (Class<?>) UiActivity.class);
        intent.putExtras(bundle);
        NativePluginHelper.startActivityOnUiThread(intent);
    }

    public void onFinish(String str) {
        this.currentDisplayedUiTag = null;
        if (this.queueList.size() > 0) {
            ArrayList<String> arrayList = this.queueList;
            String strRemove = arrayList.remove(arrayList.size() - 1);
            pushToActivityQueue(this.uiElementsMap.get(strRemove), strRemove);
            this.uiElementsMap.remove(strRemove);
        }
    }
}
