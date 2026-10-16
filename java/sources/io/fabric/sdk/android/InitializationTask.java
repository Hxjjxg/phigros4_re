package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;

/* JADX INFO: loaded from: classes.dex */
class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    @Override // io.fabric.sdk.android.services.concurrency.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        TimingMetric timingMetricCreateAndStartTimingMetric = createAndStartTimingMetric("onPreExecute");
        try {
            try {
                boolean zOnPreExecute = this.kit.onPreExecute();
                timingMetricCreateAndStartTimingMetric.stopMeasuring();
                if (zOnPreExecute) {
                    return;
                }
            } catch (UnmetDependencyException e) {
                throw e;
            } catch (Exception e2) {
                Fabric.getLogger().e(Fabric.TAG, "Failure onPreExecute()", e2);
                timingMetricCreateAndStartTimingMetric.stopMeasuring();
            }
            cancel(true);
        } catch (Throwable th) {
            timingMetricCreateAndStartTimingMetric.stopMeasuring();
            cancel(true);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.fabric.sdk.android.services.concurrency.AsyncTask
    public Result doInBackground(Void... voidArr) {
        TimingMetric timingMetricCreateAndStartTimingMetric = createAndStartTimingMetric("doInBackground");
        Result resultDoInBackground = !isCancelled() ? this.kit.doInBackground() : null;
        timingMetricCreateAndStartTimingMetric.stopMeasuring();
        return resultDoInBackground;
    }

    @Override // io.fabric.sdk.android.services.concurrency.AsyncTask
    protected void onPostExecute(Result result) {
        this.kit.onPostExecute(result);
        this.kit.initializationCallback.success(result);
    }

    @Override // io.fabric.sdk.android.services.concurrency.AsyncTask
    protected void onCancelled(Result result) {
        this.kit.onCancelled(result);
        this.kit.initializationCallback.failure(new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled"));
    }

    @Override // io.fabric.sdk.android.services.concurrency.PriorityAsyncTask, io.fabric.sdk.android.services.concurrency.PriorityProvider
    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric createAndStartTimingMetric(String str) {
        TimingMetric timingMetric = new TimingMetric(this.kit.getIdentifier() + "." + str, TIMING_METRIC_TAG);
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
