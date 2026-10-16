package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class Request {
    private static final long TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Bitmap.Config config;
    public final boolean hasRotationPivot;
    int id;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    private Request(Uri uri, int i, List<Transformation> list, int i2, int i3, boolean z, boolean z2, float f, float f2, float f3, boolean z3, Bitmap.Config config) {
        this.uri = uri;
        this.resourceId = i;
        if (list == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(list);
        }
        this.targetWidth = i2;
        this.targetHeight = i3;
        this.centerCrop = z;
        this.centerInside = z2;
        this.rotationDegrees = f;
        this.rotationPivotX = f2;
        this.rotationPivotY = f3;
        this.hasRotationPivot = z3;
        this.config = config;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        int i = this.resourceId;
        if (i > 0) {
            sb.append(i);
        } else {
            sb.append(this.uri);
        }
        List<Transformation> list = this.transformations;
        if (list != null && !list.isEmpty()) {
            Iterator<Transformation> it = this.transformations.iterator();
            while (it.hasNext()) {
                sb.append(' ').append(it.next().key());
            }
        }
        if (this.targetWidth > 0) {
            sb.append(" resize(").append(this.targetWidth).append(',').append(this.targetHeight).append(')');
        }
        if (this.centerCrop) {
            sb.append(" centerCrop");
        }
        if (this.centerInside) {
            sb.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            sb.append(" rotation(").append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                sb.append(" @ ").append(this.rotationPivotX).append(',').append(this.rotationPivotY);
            }
            sb.append(')');
        }
        if (this.config != null) {
            sb.append(' ').append(this.config);
        }
        sb.append('}');
        return sb.toString();
    }

    String logId() {
        long jNanoTime = System.nanoTime() - this.started;
        if (jNanoTime > TOO_LONG_LOG) {
            return plainId() + '+' + TimeUnit.NANOSECONDS.toSeconds(jNanoTime) + 's';
        }
        return plainId() + '+' + TimeUnit.NANOSECONDS.toMillis(jNanoTime) + "ms";
    }

    String plainId() {
        return "[R" + this.id + ']';
    }

    String getName() {
        Uri uri = this.uri;
        if (uri != null) {
            return uri.getPath();
        }
        return Integer.toHexString(this.resourceId);
    }

    public boolean hasSize() {
        return this.targetWidth != 0;
    }

    boolean needsTransformation() {
        return needsMatrixTransform() || hasCustomTransformations();
    }

    boolean needsMatrixTransform() {
        return (this.targetWidth == 0 && this.rotationDegrees == 0.0f) ? false : true;
    }

    boolean hasCustomTransformations() {
        return this.transformations != null;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public static final class Builder {
        private boolean centerCrop;
        private boolean centerInside;
        private Bitmap.Config config;
        private boolean hasRotationPivot;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        public Builder(Uri uri) {
            setUri(uri);
        }

        public Builder(int i) {
            setResourceId(i);
        }

        Builder(Uri uri, int i) {
            this.uri = uri;
            this.resourceId = i;
        }

        private Builder(Request request) {
            this.uri = request.uri;
            this.resourceId = request.resourceId;
            this.targetWidth = request.targetWidth;
            this.targetHeight = request.targetHeight;
            this.centerCrop = request.centerCrop;
            this.centerInside = request.centerInside;
            this.rotationDegrees = request.rotationDegrees;
            this.rotationPivotX = request.rotationPivotX;
            this.rotationPivotY = request.rotationPivotY;
            this.hasRotationPivot = request.hasRotationPivot;
            if (request.transformations != null) {
                this.transformations = new ArrayList(request.transformations);
            }
            this.config = request.config;
        }

        boolean hasImage() {
            return (this.uri == null && this.resourceId == 0) ? false : true;
        }

        boolean hasSize() {
            return this.targetWidth != 0;
        }

        public Builder setUri(Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.uri = uri;
            this.resourceId = 0;
            return this;
        }

        public Builder setResourceId(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.resourceId = i;
            this.uri = null;
            return this;
        }

        public Builder resize(int i, int i2) {
            if (i <= 0) {
                throw new IllegalArgumentException("Width must be positive number.");
            }
            if (i2 <= 0) {
                throw new IllegalArgumentException("Height must be positive number.");
            }
            this.targetWidth = i;
            this.targetHeight = i2;
            return this;
        }

        public Builder clearResize() {
            this.targetWidth = 0;
            this.targetHeight = 0;
            this.centerCrop = false;
            this.centerInside = false;
            return this;
        }

        public Builder centerCrop() {
            if (this.centerInside) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.centerCrop = true;
            return this;
        }

        public Builder clearCenterCrop() {
            this.centerCrop = false;
            return this;
        }

        public Builder centerInside() {
            if (this.centerCrop) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.centerInside = true;
            return this;
        }

        public Builder clearCenterInside() {
            this.centerInside = false;
            return this;
        }

        public Builder rotate(float f) {
            this.rotationDegrees = f;
            return this;
        }

        public Builder rotate(float f, float f2, float f3) {
            this.rotationDegrees = f;
            this.rotationPivotX = f2;
            this.rotationPivotY = f3;
            this.hasRotationPivot = true;
            return this;
        }

        public Builder clearRotation() {
            this.rotationDegrees = 0.0f;
            this.rotationPivotX = 0.0f;
            this.rotationPivotY = 0.0f;
            this.hasRotationPivot = false;
            return this;
        }

        public Builder config(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            }
            if (this.transformations == null) {
                this.transformations = new ArrayList(2);
            }
            this.transformations.add(transformation);
            return this;
        }

        public Request build() {
            boolean z = this.centerInside;
            if (z && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            }
            if (this.centerCrop && this.targetWidth == 0) {
                throw new IllegalStateException("Center crop requires calling resize.");
            }
            if (z && this.targetWidth == 0) {
                throw new IllegalStateException("Center inside requires calling resize.");
            }
            return new Request(this.uri, this.resourceId, this.transformations, this.targetWidth, this.targetHeight, this.centerCrop, this.centerInside, this.rotationDegrees, this.rotationPivotX, this.rotationPivotY, this.hasRotationPivot, this.config);
        }
    }
}
