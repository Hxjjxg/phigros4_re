package com.digits.sdk.android;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class CountryListSpinner extends TextView implements View.OnClickListener, CountryListLoadTask.Listener {
    private CountryListAdapter countryListAdapter;
    private DialogPopup dialogPopup;
    private View.OnClickListener listener;
    private int selectedId;
    private String textFormat;

    public CountryListSpinner(Context context) {
        this(context, null, R.attr.spinnerStyle);
    }

    public CountryListSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spinnerStyle);
    }

    public CountryListSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    void setDialogPopup(DialogPopup dialogPopup) {
        this.dialogPopup = dialogPopup;
    }

    private void init() {
        super.setOnClickListener(this);
        this.countryListAdapter = new CountryListAdapter(getContext());
        this.dialogPopup = new DialogPopup(this.countryListAdapter);
        this.textFormat = getResources().getString(R.string.dgts__country_spinner_format);
        this.selectedId = 0;
        setSpinnerText(1, Locale.US.getDisplayCountry());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSpinnerText(int i, String str) {
        setText(String.format(this.textFormat, str, Integer.valueOf(i)));
        setTag(Integer.valueOf(i));
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.dialogPopup.isShowing()) {
            this.dialogPopup.dismiss();
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        CommonUtils.hideKeyboard(getContext(), this);
        loadCountryList();
        this.dialogPopup.show(this.selectedId);
        executeUserClickListener(view);
    }

    private void loadCountryList() {
        if (this.countryListAdapter.getCount() == 0) {
            new CountryListLoadTask(this).executeOnExecutor(Digits.getInstance().getExecutorService(), new Void[0]);
        }
    }

    private void executeUserClickListener(View view) {
        View.OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    @Override // com.digits.sdk.android.CountryListLoadTask.Listener
    public void onLoadComplete(List<CountryInfo> list) {
        this.countryListAdapter.setData(list);
    }

    public class DialogPopup implements DialogInterface.OnClickListener {
        private static final long DELAY_MILLIS = 100;
        private AlertDialog dialog;
        private final CountryListAdapter listAdapter;

        DialogPopup(CountryListAdapter countryListAdapter) {
            this.listAdapter = countryListAdapter;
        }

        public void dismiss() {
            this.dialog.dismiss();
            this.dialog = null;
        }

        public boolean isShowing() {
            AlertDialog alertDialog = this.dialog;
            if (alertDialog != null) {
                return alertDialog.isShowing();
            }
            return false;
        }

        public void show(final int i) {
            if (this.listAdapter == null) {
                return;
            }
            AlertDialog alertDialogCreate = new AlertDialog.Builder(CountryListSpinner.this.getContext()).setSingleChoiceItems(this.listAdapter, 0, this).create();
            this.dialog = alertDialogCreate;
            alertDialogCreate.setCanceledOnTouchOutside(true);
            final ListView listView = this.dialog.getListView();
            listView.setFastScrollEnabled(true);
            listView.postDelayed(new Runnable() { // from class: com.digits.sdk.android.CountryListSpinner.DialogPopup.1
                @Override // java.lang.Runnable
                public void run() {
                    listView.setSelection(i);
                }
            }, DELAY_MILLIS);
            this.dialog.show();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            CountryListSpinner.this.selectedId = i;
            CountryInfo item = this.listAdapter.getItem(i);
            CountryListSpinner.this.setSpinnerText(item.countryCode, item.country);
            dismiss();
        }
    }
}
