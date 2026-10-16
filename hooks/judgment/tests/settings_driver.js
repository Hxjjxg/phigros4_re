// Test-only Android UI driver. Walks actual windows and invokes actual widgets.
// No production configuration or save handler is bypassed.
rpc.exports.panel = function (operation) {
    return new Promise((resolve, reject) => Java.perform(() => Java.scheduleOnMainThread(() => {
        try {
            const View = Java.use('android.view.View'), Group = Java.use('android.view.ViewGroup');
            const Text = Java.use('android.widget.TextView'), Check = Java.use('android.widget.CheckBox');
            const StringClass = Java.use('java.lang.String');
            const CharSequence = Java.use('java.lang.CharSequence');
            const roots = Java.use('android.view.WindowManagerGlobal').getInstance().mViews.value;
            const found = {}, texts = [];
            function walk(view) {
                const description = view.getContentDescription();
                if (description) found[Java.cast(description, CharSequence).toString()] = view;
                if (Text.class.isInstance(view)) {
                    const value = Java.cast(view, Text).getText();
                    if (value) texts.push(Java.cast(value, CharSequence).toString());
                }
                if (Group.class.isInstance(view)) {
                    const group = Java.cast(view, Group);
                    for (let i = 0; i < group.getChildCount(); i++) walk(group.getChildAt(i));
                }
            }
            for (let i = 0; i < roots.size(); i++) walk(Java.cast(roots.get(i), View));
            if (operation.set) Object.keys(operation.set).forEach(key => {
                const view = found[key.includes('.') && key.startsWith('dglab.') ? key : 'judgment.' + key];
                if (!view) throw new Error('Missing UI field ' + key);
                if (typeof operation.set[key] === 'boolean') Java.cast(view, Check).setChecked(operation.set[key]);
                else Text.setText.overload('java.lang.CharSequence').call(Java.cast(view, Text), StringClass.$new(operation.set[key]));
            });
            if (operation.click) {
                const view = found[operation.click.startsWith('dglab.') ? operation.click : 'judgment.' + operation.click];
                if (!view) throw new Error('Missing UI button ' + operation.click);
                view.performClick();
            }
            const snapshot = {fields: Object.keys(found), texts: texts};
            setImmediate(() => resolve(snapshot));
        } catch (e) { setImmediate(() => reject(e)); }
    })));
};
