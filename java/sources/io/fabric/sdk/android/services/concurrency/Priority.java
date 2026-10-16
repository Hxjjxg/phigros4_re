package io.fabric.sdk.android.services.concurrency;

/* JADX INFO: loaded from: classes.dex */
public enum Priority {
    LOW,
    NORMAL,
    HIGH,
    IMMEDIATE;

    static <Y> int compareTo(PriorityProvider priorityProvider, Y y) {
        Priority priority;
        if (y instanceof PriorityProvider) {
            priority = ((PriorityProvider) y).getPriority();
        } else {
            priority = NORMAL;
        }
        return priority.ordinal() - priorityProvider.getPriority().ordinal();
    }
}
