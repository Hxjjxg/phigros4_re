package bitter.jnibridge;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
final class a implements InvocationHandler {
    private Object[] a = new Object[0];
    private long b;
    private Constructor c;

    public a(long j) {
        this.b = j;
        try {
            Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
            this.c = declaredConstructor;
            declaredConstructor.setAccessible(true);
        } catch (NoClassDefFoundError | NoSuchMethodException unused) {
            this.c = null;
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        synchronized (this.a) {
            long j = this.b;
            if (j == 0) {
                return null;
            }
            try {
                return JNIBridge.invoke(j, method.getDeclaringClass(), method, objArr);
            } catch (NoSuchMethodError e) {
                if (this.c == null) {
                    System.err.println("JNIBridge error: Java interface default methods are only supported since Android Oreo");
                    throw e;
                }
                if ((method.getModifiers() & 1024) != 0) {
                    throw e;
                }
                if (objArr == null) {
                    objArr = new Object[0];
                }
                Class<?> declaringClass = method.getDeclaringClass();
                return ((MethodHandles.Lookup) this.c.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
            }
        }
    }
}
