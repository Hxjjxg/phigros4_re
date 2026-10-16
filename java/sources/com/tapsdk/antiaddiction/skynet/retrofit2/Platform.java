package com.tapsdk.antiaddiction.skynet.retrofit2;

import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class Platform {
    private static final Platform PLATFORM = findPlatform();

    Executor defaultCallbackExecutor() {
        return null;
    }

    boolean isDefaultMethod(Method method) {
        return false;
    }

    Platform() {
    }

    static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            try {
                Class.forName("android.os.Build");
                return new Android();
            } catch (ClassNotFoundException unused) {
                return new Platform();
            }
        } catch (ClassNotFoundException unused2) {
            Class.forName("java.util.Optional");
            return new Java8();
        }
    }

    CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
        if (executor != null) {
            return new ExecutorCallAdapterFactory(executor);
        }
        return DefaultCallAdapterFactory.INSTANCE;
    }

    Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
        throw new UnsupportedOperationException();
    }

    static class Java8 extends Platform {
        Java8() {
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Platform
        boolean isDefaultMethod(Method method) {
            return method.isDefault();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Platform
        Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
            Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return ((MethodHandles.Lookup) declaredConstructor.newInstance(cls, -1)).unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
        }
    }

    static class Android extends Platform {
        Android() {
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Platform
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Platform
        CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
            if (executor == null) {
                throw new AssertionError();
            }
            return new ExecutorCallAdapterFactory(executor);
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            MainThreadExecutor() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                this.handler.post(runnable);
            }
        }
    }
}
