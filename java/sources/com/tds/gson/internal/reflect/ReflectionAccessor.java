package com.tds.gson.internal.reflect;

import com.tds.gson.internal.JavaVersion;
import java.lang.reflect.AccessibleObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class ReflectionAccessor {
    private static final ReflectionAccessor instance;

    public abstract void makeAccessible(AccessibleObject accessibleObject);

    static {
        instance = JavaVersion.getMajorJavaVersion() < 9 ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor();
    }

    public static ReflectionAccessor getInstance() {
        return instance;
    }
}
