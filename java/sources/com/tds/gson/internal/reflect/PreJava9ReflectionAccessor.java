package com.tds.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;

/* JADX INFO: loaded from: classes.dex */
final class PreJava9ReflectionAccessor extends ReflectionAccessor {
    PreJava9ReflectionAccessor() {
    }

    @Override // com.tds.gson.internal.reflect.ReflectionAccessor
    public void makeAccessible(AccessibleObject accessibleObject) {
        accessibleObject.setAccessible(true);
    }
}
