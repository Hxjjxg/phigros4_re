package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class Fabric {
    static final boolean DEFAULT_DEBUGGABLE = false;
    static final Logger DEFAULT_LOGGER = new DefaultLogger();
    static final String ROOT_DIR = ".Fabric";
    public static final String TAG = "Fabric";
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private ActivityLifecycleManager activityLifecycleManager;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    final Logger logger;
    private final Handler mainHandler;
    private Onboarding onboarding;

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public String getVersion() {
        return "1.1.1.32";
    }

    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;
        private PriorityThreadPoolExecutor threadPoolExecutor;

        @Deprecated
        public Builder executorService(ExecutorService executorService) {
            return this;
        }

        @Deprecated
        public Builder handler(Handler handler) {
            return this;
        }

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder kits(Kit... kitArr) {
            if (kitArr == null) {
                throw new IllegalArgumentException("Kits must not be null.");
            }
            if (kitArr.length == 0) {
                throw new IllegalArgumentException("Kits must not be empty.");
            }
            if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.kits = kitArr;
            return this;
        }

        public Builder threadPoolExecutor(PriorityThreadPoolExecutor priorityThreadPoolExecutor) {
            if (priorityThreadPoolExecutor == null) {
                throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
            }
            if (this.threadPoolExecutor != null) {
                throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
            }
            this.threadPoolExecutor = priorityThreadPoolExecutor;
            return this;
        }

        public Builder logger(Logger logger) {
            if (logger == null) {
                throw new IllegalArgumentException("Logger must not be null.");
            }
            if (this.logger != null) {
                throw new IllegalStateException("Logger already set.");
            }
            this.logger = logger;
            return this;
        }

        public Builder appIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appIdentifier must not be null.");
            }
            if (this.appIdentifier != null) {
                throw new IllegalStateException("appIdentifier already set.");
            }
            this.appIdentifier = str;
            return this;
        }

        public Builder appInstallIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appInstallIdentifier must not be null.");
            }
            if (this.appInstallIdentifier != null) {
                throw new IllegalStateException("appInstallIdentifier already set.");
            }
            this.appInstallIdentifier = str;
            return this;
        }

        public Builder debuggable(boolean z) {
            this.debuggable = z;
            return this;
        }

        public Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
            if (initializationCallback == null) {
                throw new IllegalArgumentException("initializationCallback must not be null.");
            }
            if (this.initializationCallback != null) {
                throw new IllegalStateException("initializationCallback already set.");
            }
            this.initializationCallback = initializationCallback;
            return this;
        }

        public Fabric build() {
            if (this.kits == null) {
                throw new IllegalStateException("Kits must not be null.");
            }
            if (this.threadPoolExecutor == null) {
                this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                if (this.debuggable) {
                    this.logger = new DefaultLogger(3);
                } else {
                    this.logger = new DefaultLogger();
                }
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            Map kitMap = Fabric.getKitMap(Arrays.asList(this.kits));
            return new Fabric(this.context, kitMap, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, new IdManager(this.context, this.appIdentifier, this.appInstallIdentifier, kitMap.values()));
        }
    }

    static Fabric singleton() {
        if (singleton == null) {
            throw new IllegalStateException("Must Initialize Fabric before using singleton()");
        }
        return singleton;
    }

    Fabric(Context context, Map<Class<? extends Kit>, Kit> map, PriorityThreadPoolExecutor priorityThreadPoolExecutor, Handler handler, Logger logger, boolean z, InitializationCallback initializationCallback, IdManager idManager) {
        this.context = context;
        this.kits = map;
        this.executorService = priorityThreadPoolExecutor;
        this.mainHandler = handler;
        this.logger = logger;
        this.debuggable = z;
        this.initializationCallback = initializationCallback;
        this.kitInitializationCallback = createKitInitializationCallback(map.size());
        this.idManager = idManager;
    }

    public static Fabric with(Context context, Kit... kitArr) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(new Builder(context).kits(kitArr).build());
                }
            }
        }
        return singleton;
    }

    public static Fabric with(Fabric fabric) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(fabric);
                }
            }
        }
        return singleton;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference<>(activity);
        return this;
    }

    public Activity getCurrentActivity() {
        WeakReference<Activity> weakReference = this.activity;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void init() {
        setCurrentActivity(extractActivity(this.context));
        ActivityLifecycleManager activityLifecycleManager = new ActivityLifecycleManager(this.context);
        this.activityLifecycleManager = activityLifecycleManager;
        activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks() { // from class: io.fabric.sdk.android.Fabric.1
            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        initializeKits(this.context);
    }

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
    void initializeKits(Context context) {
        Collection<Kit> kits = getKits();
        this.onboarding = new Onboarding(kits);
        ArrayList<Kit> arrayList = new ArrayList(kits);
        Collections.sort(arrayList);
        this.onboarding.injectParameters(context, this, InitializationCallback.EMPTY, this.idManager);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Kit) it.next()).injectParameters(context, this, this.kitInitializationCallback, this.idManager);
        }
        this.onboarding.initialize();
        StringBuilder sbAppend = getLogger().isLoggable(TAG, 3) ? new StringBuilder("Initializing ").append(getIdentifier()).append(" [Version: ").append(getVersion()).append("], with the following kits:\n") : null;
        for (Kit kit : arrayList) {
            kit.initializationTask.addDependency(this.onboarding.initializationTask);
            addAnnotatedDependencies(this.kits, kit);
            kit.initialize();
            if (sbAppend != null) {
                sbAppend.append(kit.getIdentifier()).append(" [Version: ").append(kit.getVersion()).append("]\n");
            }
        }
        if (sbAppend != null) {
            getLogger().d(TAG, sbAppend.toString());
        }
    }

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
    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> map, Kit kit) {
        DependsOn dependsOn = (DependsOn) kit.getClass().getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            for (Class<?> cls : dependsOn.value()) {
                if (cls.isInterface()) {
                    for (Kit kit2 : map.values()) {
                        if (cls.isAssignableFrom(kit2.getClass())) {
                            kit.initializationTask.addDependency(kit2.initializationTask);
                        }
                    }
                } else {
                    if (map.get(cls) == null) {
                        throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                    }
                    kit.initializationTask.addDependency(map.get(cls).initializationTask);
                }
            }
        }
    }

    private Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.activityLifecycleManager;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public Handler getMainHandler() {
        return this.mainHandler;
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    public static <T extends Kit> T getKit(Class<T> cls) {
        return (T) singleton().kits.get(cls);
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return singleton.logger;
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return singleton.debuggable;
    }

    public static boolean isInitialized() {
        return singleton != null && singleton.initialized.get();
    }

    public String getAppIdentifier() {
        return this.idManager.getAppIdentifier();
    }

    public String getAppInstallIdentifier() {
        return this.idManager.getAppInstallIdentifier();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> collection) {
        HashMap map = new HashMap(collection.size());
        addToKitMap(map, collection);
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> collection) {
        for (Object obj : collection) {
            map.put(obj.getClass(), obj);
            if (obj instanceof KitGroup) {
                addToKitMap(map, ((KitGroup) obj).getKits());
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(int i) {
        return new InitializationCallback(i) { // from class: io.fabric.sdk.android.Fabric.2
            final CountDownLatch kitInitializedLatch;
            final /* synthetic */ int val$size;

            {
                this.val$size = i;
                this.kitInitializedLatch = new CountDownLatch(i);
            }

            @Override // io.fabric.sdk.android.InitializationCallback
            public void success(Object obj) {
                this.kitInitializedLatch.countDown();
                if (this.kitInitializedLatch.getCount() == 0) {
                    Fabric.this.initialized.set(true);
                    Fabric.this.initializationCallback.success(Fabric.this);
                }
            }

            @Override // io.fabric.sdk.android.InitializationCallback
            public void failure(Exception exc) {
                Fabric.this.initializationCallback.failure(exc);
            }
        };
    }
}
