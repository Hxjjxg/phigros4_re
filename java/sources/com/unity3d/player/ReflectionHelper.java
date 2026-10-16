package com.unity3d.player;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class ReflectionHelper {
    protected static boolean LOG = false;
    protected static final boolean LOGV = false;
    private static M[] a = new M[4096];
    private static long b = 0;
    private static long c = 0;
    private static boolean d = false;

    /* JADX WARN: Code duplicated, block: B:29:0x0049  */
    /* JADX WARN: Code duplicated, block: B:45:0x0078  */
    private static float a(Class cls, Class[] clsArr, Class[] clsArr2) {
        float f;
        float f2;
        float f3 = 0.1f;
        if (clsArr2.length == 0) {
            return 0.1f;
        }
        int i = 0;
        if ((clsArr == null ? 0 : clsArr.length) + 1 != clsArr2.length) {
            return 0.0f;
        }
        if (clsArr != null) {
            int length = clsArr.length;
            int i2 = 0;
            f = 1.0f;
            while (i < length) {
                Class cls2 = clsArr[i];
                int i3 = i2 + 1;
                Class cls3 = clsArr2[i2];
                if (cls2.equals(cls3)) {
                    f2 = 1.0f;
                } else if (cls2.isPrimitive() || cls3.isPrimitive()) {
                    f2 = 0.0f;
                } else {
                    try {
                        if (cls2.asSubclass(cls3) != null) {
                            f2 = 0.5f;
                        } else {
                            try {
                                if (cls3.asSubclass(cls2) != null) {
                                    f2 = 0.1f;
                                } else {
                                    f2 = 0.0f;
                                }
                            } catch (ClassCastException unused) {
                            }
                        }
                    } catch (ClassCastException unused2) {
                    }
                }
                f *= f2;
                i++;
                i2 = i3;
            }
        } else {
            f = 1.0f;
        }
        Class cls4 = clsArr2[clsArr2.length - 1];
        if (cls.equals(cls4)) {
            f3 = 1.0f;
        } else if (cls.isPrimitive() || cls4.isPrimitive()) {
            f3 = 0.0f;
        } else {
            try {
                if (cls.asSubclass(cls4) != null) {
                    f3 = 0.5f;
                } else {
                    try {
                        if (cls4.asSubclass(cls) == null) {
                            f3 = 0.0f;
                        }
                    } catch (ClassCastException unused3) {
                    }
                }
            } catch (ClassCastException unused4) {
            }
        }
        return f * f3;
    }

    private static Class a(String str, int[] iArr) {
        while (iArr[0] < str.length()) {
            int i = iArr[0];
            iArr[0] = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt != '(' && cCharAt != ')') {
                if (cCharAt == 'L') {
                    int iIndexOf = str.indexOf(59, iArr[0]);
                    if (iIndexOf == -1) {
                        return null;
                    }
                    String strSubstring = str.substring(iArr[0], iIndexOf);
                    iArr[0] = iIndexOf + 1;
                    try {
                        return Class.forName(strSubstring.replace('/', '.'));
                    } catch (ClassNotFoundException unused) {
                        return null;
                    }
                }
                if (cCharAt == 'Z') {
                    return Boolean.TYPE;
                }
                if (cCharAt == 'I') {
                    return Integer.TYPE;
                }
                if (cCharAt == 'F') {
                    return Float.TYPE;
                }
                if (cCharAt == 'V') {
                    return Void.TYPE;
                }
                if (cCharAt == 'B') {
                    return Byte.TYPE;
                }
                if (cCharAt == 'C') {
                    return Character.TYPE;
                }
                if (cCharAt == 'S') {
                    return Short.TYPE;
                }
                if (cCharAt == 'J') {
                    return Long.TYPE;
                }
                if (cCharAt == 'D') {
                    return Double.TYPE;
                }
                if (cCharAt == '[') {
                    return Array.newInstance((Class<?>) a(str, iArr), 0).getClass();
                }
                AbstractC0077z.Log(5, "! parseType; " + cCharAt + " is not known!");
                return null;
            }
        }
        return null;
    }

    private static synchronized boolean a(M m) {
        M m2 = a[m.d & 4095];
        if (!m.equals(m2)) {
            return false;
        }
        m.e = m2.e;
        return true;
    }

    private static Class[] a(String str) {
        Class clsA;
        int i = 0;
        int[] iArr = {0};
        ArrayList arrayList = new ArrayList();
        while (iArr[0] < str.length() && (clsA = a(str, iArr)) != null) {
            arrayList.add(clsA);
        }
        Class[] clsArr = new Class[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            clsArr[i] = (Class) it.next();
            i++;
        }
        return clsArr;
    }

    protected static synchronized boolean beginProxyCall(long j) {
        if (j != b) {
            return false;
        }
        c++;
        return true;
    }

    protected static Object createInvocationError(long j, boolean z) {
        return new N(j, z);
    }

    protected static synchronized void endProxyCall() {
        long j = c - 1;
        c = j;
        if (0 == j && d) {
            ReflectionHelper.class.notifyAll();
        }
    }

    protected static synchronized void endUnityLaunch() {
        try {
            b++;
            d = true;
            while (c > 0) {
                ReflectionHelper.class.wait();
            }
        } catch (InterruptedException unused) {
            AbstractC0077z.Log(6, "Interrupted while waiting for all proxies to exit.");
        }
        d = false;
    }

    protected static Constructor getConstructorID(Class cls, String str) {
        Constructor<?> constructor;
        M m = new M(cls, "", str);
        if (a(m)) {
            constructor = (Constructor) m.e;
        } else {
            Class[] clsArrA = a(str);
            Constructor<?> constructor2 = null;
            float f = 0.0f;
            for (Constructor<?> constructor3 : cls.getConstructors()) {
                float fA = a(Void.TYPE, constructor3.getParameterTypes(), clsArrA);
                if (fA > f) {
                    if (fA == 1.0f) {
                        constructor2 = constructor3;
                        break;
                    }
                    constructor2 = constructor3;
                    f = fA;
                }
            }
            synchronized (ReflectionHelper.class) {
                m.e = constructor2;
                a[m.d & 4095] = m;
            }
            constructor = constructor2;
        }
        if (constructor != null) {
            return constructor;
        }
        throw new NoSuchMethodError("<init>" + str + " in class " + cls.getName());
    }

    protected static Field getFieldID(Class cls, String str, String str2, boolean z) {
        Field field;
        Class superclass = cls;
        M m = new M(superclass, str, str2);
        if (a(m)) {
            field = (Field) m.e;
        } else {
            Class[] clsArrA = a(str2);
            float f = 0.0f;
            Field field2 = null;
            while (superclass != null) {
                for (Field field3 : superclass.getDeclaredFields()) {
                    if (z == Modifier.isStatic(field3.getModifiers()) && field3.getName().compareTo(str) == 0) {
                        float fA = a(field3.getType(), null, clsArrA);
                        if (fA > f) {
                            field2 = field3;
                            if (fA == 1.0f) {
                                f = fA;
                                break;
                            }
                            f = fA;
                        } else {
                            continue;
                        }
                    }
                }
                if (f == 1.0f || superclass.isPrimitive() || superclass.isInterface() || superclass.equals(Object.class) || superclass.equals(Void.TYPE)) {
                    break;
                }
                superclass = superclass.getSuperclass();
            }
            synchronized (ReflectionHelper.class) {
                m.e = field2;
                a[m.d & 4095] = m;
            }
            field = field2;
        }
        if (field != null) {
            return field;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = superclass.getName();
        throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", objArr));
    }

    protected static String getFieldSignature(Field field) {
        Class<?> type = field.getType();
        if (!type.isPrimitive()) {
            return type.isArray() ? type.getName().replace('.', '/') : "L" + type.getName().replace('.', '/') + ";";
        }
        String name = type.getName();
        if ("boolean".equals(name)) {
            return "Z";
        }
        if ("byte".equals(name)) {
            return "B";
        }
        if ("char".equals(name)) {
            return "C";
        }
        if ("double".equals(name)) {
            return "D";
        }
        if ("float".equals(name)) {
            return "F";
        }
        if ("int".equals(name)) {
            return "I";
        }
        if ("long".equals(name)) {
            return "J";
        }
        return "short".equals(name) ? ExifInterface.LATITUDE_SOUTH : name;
    }

    protected static Method getMethodID(Class cls, String str, String str2, boolean z) {
        Method method;
        M m = new M(cls, str, str2);
        if (a(m)) {
            method = (Method) m.e;
        } else {
            Class[] clsArrA = a(str2);
            Method method2 = null;
            float f = 0.0f;
            while (cls != null) {
                for (Method method3 : cls.getDeclaredMethods()) {
                    if (z == Modifier.isStatic(method3.getModifiers()) && method3.getName().compareTo(str) == 0) {
                        float fA = a(method3.getReturnType(), method3.getParameterTypes(), clsArrA);
                        if (fA <= f) {
                            continue;
                        } else {
                            if (fA == 1.0f) {
                                method2 = method3;
                                f = fA;
                                break;
                            }
                            method2 = method3;
                            f = fA;
                        }
                    }
                }
                if (f == 1.0f || cls.isPrimitive() || cls.isInterface() || cls.equals(Object.class) || cls.equals(Void.TYPE)) {
                    break;
                }
                cls = cls.getSuperclass();
            }
            synchronized (ReflectionHelper.class) {
                m.e = method2;
                a[m.d & 4095] = m;
            }
            method = method2;
        }
        if (method != null) {
            return method;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = cls.getName();
        throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyFinalize(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native Object nativeProxyInvoke(long j, String str, Object[] objArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyJNIFreeGCHandle(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyLogJNIInvokeException(long j);

    protected static Object newProxyInstance(UnityPlayer unityPlayer, long j, Class cls) {
        return newProxyInstance(unityPlayer, j, new Class[]{cls});
    }

    protected static Object newProxyInstance(UnityPlayer unityPlayer, long j, Class[] clsArr) {
        return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), clsArr, new L(unityPlayer, j));
    }
}
