package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.view.Surface;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: com.unity3d.player.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0071w {
    private static CameraManager B;
    private static String[] C;
    private static Semaphore D = new Semaphore(1);
    private com.unity3d.player.a.b a;
    private CameraDevice b;
    private HandlerThread c;
    private Handler d;
    private Rect e;
    private Rect f;
    private int g;
    private int h;
    private int k;
    private int l;
    private Range n;
    private Image p;
    private CaptureRequest.Builder q;
    private int t;
    private SurfaceTexture u;
    private float i = -1.0f;
    private float j = -1.0f;
    private boolean m = false;
    private ImageReader o = null;
    private CameraCaptureSession r = null;
    private Object s = new Object();
    private Surface v = null;
    private int A = 3;
    private CameraCaptureSession.CaptureCallback w = new C0061r(this);
    private final CameraDevice.StateCallback x = new C0065t(this);
    private final ImageReader.OnImageAvailableListener y = new C0067u(this);
    private final SurfaceTexture.OnFrameAvailableListener z = new C0069v(this);

    protected C0071w(com.unity3d.player.a.b bVar) {
        this.a = null;
        this.a = bVar;
        e();
    }

    public static int a(Context context) {
        return b(context).length;
    }

    public static int a(Context context, int i) {
        try {
            CameraCharacteristics cameraCharacteristics = c(context).getCameraCharacteristics(b(context)[i]);
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            if (fArr.length > 0) {
                return (int) ((fArr[0] * 36.0f) / sizeF.getWidth());
            }
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CameraDevice cameraDevice) {
        synchronized (this.s) {
            this.r = null;
        }
        cameraDevice.close();
        this.b = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj) {
        if (obj != "Focus") {
            if (obj == "Cancel focus") {
                synchronized (this.s) {
                    if (this.r != null) {
                        g();
                    }
                }
                return;
            }
            return;
        }
        this.m = false;
        synchronized (this.s) {
            if (this.r != null) {
                try {
                    this.q.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                    this.q.setTag("Regular");
                    this.r.setRepeatingRequest(this.q.build(), this.w, this.d);
                } catch (CameraAccessException e) {
                    AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
                }
            }
        }
    }

    private void b() {
        try {
            Semaphore semaphore = D;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (!semaphore.tryAcquire(4L, timeUnit)) {
                AbstractC0077z.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.b.close();
            try {
                if (!D.tryAcquire(4L, timeUnit)) {
                    AbstractC0077z.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e) {
                AbstractC0077z.Log(6, "Camera2: Interrupted while waiting to close camera " + e);
            }
            this.b = null;
            D.release();
        } catch (InterruptedException e2) {
            AbstractC0077z.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002f  */
    /* JADX WARN: Code duplicated, block: B:17:0x0038 A[LOOP:0: B:15:0x0035->B:17:0x0038, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:19:0x0050 A[RETURN] */
    public static int[] b(Context context, int i) {
        Size[] outputSizes;
        int[] iArr;
        int i2;
        try {
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) c(context).getCameraCharacteristics(b(context)[i]).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (streamConfigurationMap != null) {
                outputSizes = streamConfigurationMap.getOutputSizes(35);
                if (outputSizes == null || outputSizes.length == 0) {
                }
                if (outputSizes != null) {
                    return null;
                }
                iArr = new int[outputSizes.length * 2];
                for (i2 = 0; i2 < outputSizes.length; i2++) {
                    int i3 = i2 * 2;
                    iArr[i3] = outputSizes[i2].getWidth();
                    iArr[i3 + 1] = outputSizes[i2].getHeight();
                }
                return iArr;
            }
            AbstractC0077z.Log(6, "Camera2: configuration map is not available.");
            outputSizes = null;
            if (outputSizes != null) {
                return null;
            }
            iArr = new int[outputSizes.length * 2];
            while (i2 < outputSizes.length) {
                int i4 = i2 * 2;
                iArr[i4] = outputSizes[i2].getWidth();
                iArr[i4 + 1] = outputSizes[i2].getHeight();
            }
            return iArr;
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
            return null;
        }
    }

    private static String[] b(Context context) {
        if (C == null) {
            try {
                C = c(context).getCameraIdList();
            } catch (CameraAccessException e) {
                AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
                C = new String[0];
            }
        }
        return C;
    }

    public static int c(Context context, int i) {
        try {
            return ((Integer) c(context).getCameraCharacteristics(b(context)[i]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
            return 0;
        }
    }

    private static CameraManager c(Context context) {
        if (B == null) {
            B = (CameraManager) context.getSystemService("camera");
        }
        return B;
    }

    public static boolean d(Context context, int i) {
        try {
            return ((Integer) c(context).getCameraCharacteristics(b(context)[i]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() > 0;
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    private void e() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.c = handlerThread;
        handlerThread.start();
        this.d = new Handler(this.c.getLooper());
    }

    public static boolean e(Context context, int i) {
        try {
            return ((Integer) c(context).getCameraCharacteristics(b(context)[i]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    private void f() {
        try {
            CameraCaptureSession cameraCaptureSession = this.r;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.stopRepeating();
                this.q.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.q.set(CaptureRequest.CONTROL_AF_MODE, 0);
                this.q.setTag("Cancel focus");
                this.r.capture(this.q.build(), this.w, this.d);
            }
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:14:0x00bc A[Catch: CameraAccessException -> 0x00e1, TryCatch #0 {CameraAccessException -> 0x00e1, blocks: (B:2:0x0000, B:4:0x0004, B:8:0x0011, B:13:0x001d, B:14:0x00bc, B:16:0x00d3), top: B:22:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x00d3 A[Catch: CameraAccessException -> 0x00e1, TRY_LEAVE, TryCatch #0 {CameraAccessException -> 0x00e1, blocks: (B:2:0x0000, B:4:0x0004, B:8:0x0011, B:13:0x001d, B:14:0x00bc, B:16:0x00d3), top: B:22:0x0000 }] */
    public void g() {
        CameraCaptureSession cameraCaptureSession;
        try {
            if (this.h != 0) {
                float f = this.i;
                if (f < 0.0f || f > 1.0f) {
                    this.q.set(CaptureRequest.CONTROL_AF_MODE, 4);
                    this.q.setTag("Regular");
                    cameraCaptureSession = this.r;
                    if (cameraCaptureSession != null) {
                        cameraCaptureSession.setRepeatingRequest(this.q.build(), this.w, this.d);
                    }
                } else {
                    float f2 = this.j;
                    if (f2 < 0.0f || f2 > 1.0f) {
                        this.q.set(CaptureRequest.CONTROL_AF_MODE, 4);
                        this.q.setTag("Regular");
                        cameraCaptureSession = this.r;
                        if (cameraCaptureSession != null) {
                            cameraCaptureSession.setRepeatingRequest(this.q.build(), this.w, this.d);
                        }
                    } else {
                        this.m = true;
                        int iWidth = this.f.width();
                        int i = this.k;
                        int i2 = (int) (((iWidth - (i * 2)) * this.i) + i);
                        int iHeight = this.f.height();
                        int i3 = this.l;
                        int i4 = (int) (((1.0d - ((double) this.j)) * ((double) (iHeight - (i3 * 2)))) + ((double) i3));
                        int iMax = Math.max(this.g + 1, Math.min(i2, (this.f.width() - this.g) - 1));
                        int iMax2 = Math.max(this.g + 1, Math.min(i4, (this.f.height() - this.g) - 1));
                        CaptureRequest.Builder builder = this.q;
                        CaptureRequest.Key key = CaptureRequest.CONTROL_AF_REGIONS;
                        int i5 = this.g;
                        int i6 = i5 * 2;
                        builder.set(key, new MeteringRectangle[]{new MeteringRectangle(iMax - i5, iMax2 - i5, i6, i6, 999)});
                        this.q.set(CaptureRequest.CONTROL_AF_MODE, 1);
                        this.q.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                        this.q.setTag("Focus");
                        this.r.capture(this.q.build(), this.w, this.d);
                    }
                }
            } else {
                this.q.set(CaptureRequest.CONTROL_AF_MODE, 4);
                this.q.setTag("Regular");
                cameraCaptureSession = this.r;
                if (cameraCaptureSession != null) {
                    cameraCaptureSession.setRepeatingRequest(this.q.build(), this.w, this.d);
                }
            }
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    public final void a() {
        if (this.b != null) {
            i();
            b();
            this.w = null;
            this.v = null;
            this.u = null;
            Image image = this.p;
            if (image != null) {
                image.close();
                this.p = null;
            }
            ImageReader imageReader = this.o;
            if (imageReader != null) {
                imageReader.close();
                this.o = null;
            }
        }
        this.c.quit();
        try {
            this.c.join(4000L);
            this.c = null;
            this.d = null;
        } catch (InterruptedException e) {
            this.c.interrupt();
            AbstractC0077z.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e);
        }
    }

    public final boolean a(float f, float f2) {
        if (this.h <= 0) {
            return false;
        }
        if (this.m) {
            AbstractC0077z.Log(5, "Camera2: Setting manual focus point already started.");
            return false;
        }
        this.i = f;
        this.j = f2;
        synchronized (this.s) {
            if (this.r != null && this.A != 2) {
                f();
            }
        }
        return true;
    }

    public final boolean a(Context context, int i, int i2, int i3, int i4, int i5, Surface surface) {
        int iIntValue = i4;
        try {
            CameraCharacteristics cameraCharacteristics = B.getCameraCharacteristics(b(context)[i]);
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                AbstractC0077z.Log(5, "Camera2: only LEGACY hardware level is supported.");
            }
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] sizeArr = null;
            if (streamConfigurationMap == null) {
                AbstractC0077z.Log(6, "Camera2: configuration map is not available.");
            } else {
                Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
                if (outputSizes != null && outputSizes.length != 0) {
                    sizeArr = outputSizes;
                }
            }
            if (sizeArr == null || sizeArr.length == 0) {
                return false;
            }
            double d = i2;
            double d2 = i3;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            double d3 = Double.MAX_VALUE;
            while (i6 < sizeArr.length) {
                int width = sizeArr[i6].getWidth();
                int height = sizeArr[i6].getHeight();
                CameraCharacteristics cameraCharacteristics2 = cameraCharacteristics;
                double d4 = d;
                double dAbs = Math.abs(Math.log(d2 / ((double) height))) + Math.abs(Math.log(d / ((double) width)));
                if (dAbs < d3) {
                    i7 = height;
                    d3 = dAbs;
                    i8 = width;
                }
                i6++;
                cameraCharacteristics = cameraCharacteristics2;
                d = d4;
            }
            CameraCharacteristics cameraCharacteristics3 = cameraCharacteristics;
            this.e = new Rect(0, 0, i8, i7);
            Range[] rangeArr = (Range[]) cameraCharacteristics3.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            if (rangeArr == null || rangeArr.length == 0) {
                AbstractC0077z.Log(6, "Camera2: target FPS ranges are not avialable.");
                return false;
            }
            int i9 = -1;
            int i10 = 0;
            double d5 = Double.MAX_VALUE;
            while (true) {
                if (i10 >= rangeArr.length) {
                    iIntValue = ((Integer) (iIntValue > ((Integer) rangeArr[i9].getUpper()).intValue() ? rangeArr[i9].getUpper() : rangeArr[i9].getLower())).intValue();
                    break;
                }
                int iIntValue2 = ((Integer) rangeArr[i10].getLower()).intValue();
                int iIntValue3 = ((Integer) rangeArr[i10].getUpper()).intValue();
                float f = iIntValue;
                if (f + 0.1f > iIntValue2 && f - 0.1f < iIntValue3) {
                    break;
                }
                double dMin = Math.min(Math.abs(iIntValue - iIntValue2), Math.abs(iIntValue - iIntValue3));
                if (dMin < d5) {
                    i9 = i10;
                    d5 = dMin;
                }
                i10++;
            }
            this.n = new Range(Integer.valueOf(iIntValue), Integer.valueOf(iIntValue));
            try {
                Semaphore semaphore = D;
                TimeUnit timeUnit = TimeUnit.SECONDS;
                if (!semaphore.tryAcquire(4L, timeUnit)) {
                    AbstractC0077z.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                    return false;
                }
                try {
                    B.openCamera(b(context)[i], this.x, this.d);
                    try {
                        if (!D.tryAcquire(4L, timeUnit)) {
                            AbstractC0077z.Log(5, "Camera2: Timeout waiting to open camera.");
                            return false;
                        }
                        D.release();
                        this.t = i5;
                        this.v = surface;
                        int iIntValue4 = ((Integer) cameraCharacteristics3.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
                        this.h = iIntValue4;
                        if (iIntValue4 > 0) {
                            Rect rect = (Rect) cameraCharacteristics3.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                            this.f = rect;
                            float fWidth = rect.width() / this.f.height();
                            float fWidth2 = this.e.width() / this.e.height();
                            if (fWidth2 > fWidth) {
                                this.k = 0;
                                this.l = (int) ((this.f.height() - (this.f.width() / fWidth2)) / 2.0f);
                            } else {
                                this.l = 0;
                                this.k = (int) ((this.f.width() - (this.f.height() * fWidth2)) / 2.0f);
                            }
                            this.g = Math.min(this.f.width(), this.f.height()) / 20;
                        }
                        return this.b != null;
                    } catch (InterruptedException e) {
                        AbstractC0077z.Log(6, "Camera2: Interrupted while waiting to open camera " + e);
                    }
                } catch (CameraAccessException e2) {
                    AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e2);
                    D.release();
                    return false;
                }
            } catch (InterruptedException e3) {
                AbstractC0077z.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e3);
                return false;
            }
        } catch (CameraAccessException e4) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e4);
            return false;
        }
    }

    public final Rect c() {
        return this.e;
    }

    public final void d() {
        synchronized (this.s) {
            CameraCaptureSession cameraCaptureSession = this.r;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.stopRepeating();
                    this.A = 2;
                } catch (CameraAccessException e) {
                    AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
                }
            }
        }
    }

    public void h() {
        Surface surface;
        if (this.t != 0) {
            if (this.v == null) {
                SurfaceTexture surfaceTexture = new SurfaceTexture(this.t);
                this.u = surfaceTexture;
                surfaceTexture.setDefaultBufferSize(this.e.width(), this.e.height());
                this.u.setOnFrameAvailableListener(this.z, this.d);
                surface = new Surface(this.u);
                this.v = surface;
            }
        } else if (this.v == null && this.o == null) {
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.e.width(), this.e.height(), 35, 2);
            this.o = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(this.y, this.d);
            this.p = null;
            surface = this.o.getSurface();
            this.v = surface;
        }
        try {
            CameraCaptureSession cameraCaptureSession = this.r;
            if (cameraCaptureSession == null) {
                this.b.createCaptureSession(Arrays.asList(this.v), new C0063s(this), this.d);
            } else if (this.A == 2) {
                cameraCaptureSession.setRepeatingRequest(this.q.build(), this.w, this.d);
            }
            this.A = 1;
        } catch (CameraAccessException e) {
            AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    public final void i() {
        synchronized (this.s) {
            CameraCaptureSession cameraCaptureSession = this.r;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.abortCaptures();
                } catch (CameraAccessException e) {
                    AbstractC0077z.Log(6, "Camera2: CameraAccessException " + e);
                }
                this.r.close();
                this.r = null;
                this.A = 3;
            }
        }
    }
}
