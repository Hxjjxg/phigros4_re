package com.digits.sdk.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarActivity;

/* JADX INFO: loaded from: classes.dex */
public abstract class DigitsActionBarActivity extends ActionBarActivity {
    static final int REQUEST_CODE = 140;
    static final int RESULT_FINISH_DIGITS = 200;
    DigitsController controller;

    abstract int getLayoutId();

    abstract DigitsController init(Bundle bundle);

    abstract boolean isValid(Bundle bundle);

    public void onCreate(Bundle bundle) {
        setTheme(Digits.getInstance().getTheme());
        super.onCreate(bundle);
        setContentView(getLayoutId());
        Bundle extras = getIntent().getExtras();
        if (isValid(extras)) {
            this.controller = init(extras);
        } else {
            finish();
            throw new IllegalAccessError("This activity can only be started from Digits");
        }
    }

    public void onResume() {
        super.onResume();
        this.controller.onResume();
    }

    void setUpSendButton(StateButton stateButton) {
        stateButton.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActionBarActivity.1
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActionBarActivity.this.controller.clearError();
                DigitsActionBarActivity.this.controller.executeRequest(DigitsActionBarActivity.this);
            }
        });
    }

    void setUpPhoneEditText(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.digits.sdk.android.DigitsActionBarActivity.2
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 5) {
                    return false;
                }
                DigitsActionBarActivity.this.controller.clearError();
                DigitsActionBarActivity.this.controller.executeRequest(DigitsActionBarActivity.this);
                return true;
            }
        });
        editText.addTextChangedListener(this.controller.getTextWatcher());
    }

    void setUpTermsText(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActionBarActivity.3
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActionBarActivity.this.controller.clearError();
                DigitsActionBarActivity.this.controller.showTOS(DigitsActionBarActivity.this);
            }
        });
    }

    void setUpResendText(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActionBarActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActionBarActivity.this.finish();
            }
        });
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == 200 && i == 140) {
            finish();
        }
    }
}
