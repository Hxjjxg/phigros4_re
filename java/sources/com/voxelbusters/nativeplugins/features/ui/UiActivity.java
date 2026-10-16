package com.voxelbusters.nativeplugins.features.ui;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.defines.UnityDefines;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class UiActivity extends Activity {
    AlertDialog alertDialog;
    Bundle bundleInfo;
    boolean paused;

    private int getThemeStyle() {
        return R.style.Theme.Material.Light.Dialog;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.bundleInfo == null) {
            this.bundleInfo = getIntent().getExtras();
        }
        eUiType euitype = eUiType.values()[this.bundleInfo.getInt("type")];
        if (euitype == eUiType.ALERT_DIALOG) {
            showAlertDialog(this.bundleInfo);
        } else if (euitype == eUiType.SINGLE_FIELD_PROMPT) {
            showSinglePrompt(this.bundleInfo);
        } else if (euitype == eUiType.LOGIN_PROMPT) {
            showLoginPrompt(this.bundleInfo);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.paused) {
            finish();
            new Handler().postDelayed(new Runnable() { // from class: com.voxelbusters.nativeplugins.features.ui.UiActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    UiActivity uiActivity = UiActivity.this;
                    uiActivity.startActivity(uiActivity.getIntent());
                }
            }, 10L);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.paused = true;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.alertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.alertDialog = null;
        }
    }

    private void showAlertDialog(Bundle bundle) {
        final String string = bundle.getString("tag");
        final String[] stringArray = bundle.getStringArray(Keys.BUTTON_LIST);
        this.alertDialog = getDialogWithDefaultDetails(bundle);
        int length = stringArray.length <= 3 ? stringArray.length : 3;
        for (final int i = 0; i < length; i++) {
            this.alertDialog.setButton((-1) - i, stringArray[i], new DialogInterface.OnClickListener() { // from class: com.voxelbusters.nativeplugins.features.ui.UiActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    String str = stringArray[i];
                    String str2 = string;
                    HashMap map = new HashMap();
                    map.put(Keys.Ui.BUTTON_PRESSED, str);
                    map.put(Keys.Ui.CALLER, str2);
                    NativePluginHelper.sendMessage(UnityDefines.Ui.ALERT_DIALOG_CLOSED, map);
                    UiHandler.getInstance().onFinish(str2);
                    UiActivity.this.finish();
                }
            });
        }
        this.alertDialog.show();
    }

    private void showSinglePrompt(Bundle bundle) {
        final String[] stringArray = bundle.getStringArray(Keys.BUTTON_LIST);
        this.alertDialog = getDialogWithDefaultDetails(bundle);
        final EditText editText = new EditText(this);
        this.alertDialog.setView(editText);
        boolean z = bundle.getBoolean(Keys.IS_SECURE);
        String string = bundle.getString(Keys.PLACE_HOLDER_TEXT_1);
        if (z) {
            editText.setTransformationMethod(new PasswordTransformationMethod());
        }
        if (string != null) {
            editText.setHint(string);
        }
        int length = stringArray.length <= 3 ? stringArray.length : 3;
        for (final int i = 0; i < length; i++) {
            this.alertDialog.setButton((-1) - i, stringArray[i], new DialogInterface.OnClickListener() { // from class: com.voxelbusters.nativeplugins.features.ui.UiActivity.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    String string2 = editText.getText().toString();
                    HashMap map = new HashMap();
                    map.put(Keys.Ui.BUTTON_PRESSED, stringArray[i]);
                    map.put(Keys.Ui.INPUT, string2);
                    NativePluginHelper.sendMessage(UnityDefines.Ui.SINGLE_FIELD_PROMPT_DIALOG_CLOSED, map);
                    UiActivity.this.finish();
                }
            });
        }
        this.alertDialog.show();
    }

    private void showLoginPrompt(Bundle bundle) {
        final String[] stringArray = bundle.getStringArray(Keys.BUTTON_LIST);
        setContentView(new LinearLayout(this));
        this.alertDialog = getDialogWithDefaultDetails(bundle);
        final EditText editText = new EditText(this);
        final EditText editText2 = new EditText(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.addView(editText);
        linearLayout.addView(editText2);
        this.alertDialog.setView(linearLayout);
        String string = bundle.getString(Keys.PLACE_HOLDER_TEXT_1);
        String string2 = bundle.getString(Keys.PLACE_HOLDER_TEXT_2);
        editText2.setTransformationMethod(new PasswordTransformationMethod());
        editText.setHint(string);
        editText2.setHint(string2);
        int length = stringArray.length <= 3 ? stringArray.length : 3;
        for (int i = 0; i < length; i++) {
            final int i2 = i;
            this.alertDialog.setButton((-1) - i, stringArray[i], new DialogInterface.OnClickListener() { // from class: com.voxelbusters.nativeplugins.features.ui.UiActivity.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    HashMap map = new HashMap();
                    map.put(Keys.Ui.BUTTON_PRESSED, stringArray[i2]);
                    map.put(Keys.Ui.USER_NAME, editText.getText().toString());
                    map.put(Keys.Ui.PASSWORD, editText2.getText().toString());
                    NativePluginHelper.sendMessage(UnityDefines.Ui.LOGIN_PROMPT_DIALOG_CLOSED, map);
                    UiActivity.this.finish();
                }
            });
        }
        this.alertDialog.show();
    }

    private AlertDialog getDialogWithDefaultDetails(Bundle bundle) {
        String string = bundle.getString("title");
        String string2 = bundle.getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, getThemeStyle()));
        if (!StringUtility.isNullOrEmpty(string)) {
            builder.setTitle(string);
        }
        if (!StringUtility.isNullOrEmpty(string2)) {
            builder.setMessage(string2);
        }
        builder.setCancelable(false);
        return builder.create();
    }
}
