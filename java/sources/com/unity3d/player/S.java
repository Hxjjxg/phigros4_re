package com.unity3d.player;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import com.digits.sdk.vcard.VCardConfig;

/* JADX INFO: loaded from: classes.dex */
abstract class S implements TextWatcher {
    protected Context a;
    protected UnityPlayer b;
    protected EditText c = createEditText(this);
    protected boolean d;
    protected boolean e;
    protected F f;
    protected G g;

    public S(Context context, UnityPlayer unityPlayer) {
        this.a = context;
        this.b = unityPlayer;
    }

    public final String a() {
        InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) this.a.getSystemService("input_method")).getCurrentInputMethodSubtype();
        if (currentInputMethodSubtype == null) {
            return null;
        }
        String strA = AbstractC0075y.a(currentInputMethodSubtype);
        if (strA != null && !strA.equals("")) {
            return strA;
        }
        return currentInputMethodSubtype.getMode() + " " + currentInputMethodSubtype.getExtraValue();
    }

    public void a(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5, boolean z6) {
        this.e = z6;
        setupTextInput(str, i, z, z2, z3, z4, str2, i2);
        a(z5);
    }

    public final void a(String str, boolean z) {
        this.c.setSelection(0, 0);
        this.b.reportSoftInputStr(str, 1, z);
    }

    public abstract void a(boolean z);

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        this.b.reportSoftInputStr(editable.toString(), 0, false);
        int selectionStart = this.c.getSelectionStart();
        this.b.reportSoftInputSelection(selectionStart, this.c.getSelectionEnd() - selectionStart);
    }

    public final String b() {
        EditText editText = this.c;
        if (editText == null) {
            return null;
        }
        return editText.getText().toString();
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public abstract void c();

    protected abstract EditText createEditText(S s);

    public boolean d() {
        return this.e;
    }

    public abstract void e();

    public final void f() {
        ((InputMethodManager) this.a.getSystemService("input_method")).showSoftInput(this.c, 0);
    }

    protected void invokeOnClose() {
        G g = this.g;
        if (g != null) {
            ((C0058p0) g).a();
        }
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    protected void setupTextInput(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2) {
        this.c.setOnEditorActionListener(new Q(this));
        this.c.setBackgroundColor(-1);
        this.c.setImeOptions(6);
        this.c.setText(str);
        this.c.setHint(str2);
        this.c.setHintTextColor(1627389952);
        EditText editText = this.c;
        int i3 = (z ? 32768 : 524288) | (z2 ? 131072 : 0) | (z3 ? 128 : 0);
        if (i >= 0 && i <= 11) {
            int i4 = new int[]{1, 16385, 12290, 17, 2, 3, 8289, 33, 1, 16417, 17, 8194}[i];
            if ((i4 & 2) != 0) {
                i3 = (z3 ? 16 : 0) | i4;
            } else {
                i3 |= i4;
            }
        }
        editText.setInputType(i3);
        this.c.setImeOptions(VCardConfig.FLAG_REFRAIN_PHONE_NUMBER_FORMATTING);
        if (i2 > 0) {
            this.c.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        }
        this.c.addTextChangedListener(this);
        EditText editText2 = this.c;
        editText2.setSelection(editText2.getText().length());
        this.c.setClickable(true);
    }
}
