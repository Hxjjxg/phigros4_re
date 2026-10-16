package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.MediaController;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class a1 extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {
    public static final /* synthetic */ int A = 0;
    private final Context a;
    private final SurfaceView b;
    private final SurfaceHolder c;
    private final String d;
    private final int e;
    private final int f;
    private final boolean g;
    private final long h;
    private final long i;
    private final a1 j;
    private int k;
    private int l;
    private int m;
    private int n;
    private MediaPlayer o;
    private MediaController p;
    private boolean q;
    private boolean r;
    private int s;
    private boolean t;
    private int u;
    private boolean v;
    private C w;
    private Y0 x;
    private Z0 y;
    private volatile int z;

    protected a1(Context context, UnityPlayer unityPlayer, String str, int i, int i2, int i3, boolean z, long j, long j2, Y0 y0) {
        super(context);
        this.q = false;
        this.r = false;
        this.s = 0;
        this.t = false;
        this.u = 0;
        this.v = false;
        this.w = null;
        this.z = 0;
        this.x = y0;
        this.a = context;
        this.j = this;
        SurfaceView surfaceView = new SurfaceView(context);
        this.b = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.c = holder;
        holder.addCallback(this);
        setBackgroundColor(i);
        addView(surfaceView);
        this.d = str;
        this.e = i2;
        this.f = i3;
        this.g = z;
        this.h = j;
        this.i = j2;
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.w = D.a(unityPlayer.getActivity(), 1000000, new X0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        this.z = i;
        Y0 y0 = this.x;
        if (y0 != null) {
            ((c1) y0).a(this.z);
        }
    }

    final boolean a() {
        return this.t;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return true;
    }

    public void cancelOnPrepare() {
        a(2);
    }

    protected void destroyPlayer() {
        if (!this.t) {
            pause();
        }
        doCleanUp();
        C c = this.w;
        if (c != null) {
            c.unregisterOnBackPressedCallback();
            this.w = null;
        }
    }

    protected void doCleanUp() {
        Z0 z0 = this.y;
        if (z0 != null) {
            z0.b = true;
            this.y = null;
        }
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.o.release();
            this.o = null;
        }
        this.m = 0;
        this.n = 0;
        this.r = false;
        this.q = false;
    }

    public boolean exitOnKeypress(int i, KeyEvent keyEvent) {
        Runnable runnable;
        if (i != 4 && (this.e != 2 || i == 0 || keyEvent.isSystem())) {
            return false;
        }
        com.unity3d.player.a.c cVar = this.w.a;
        if ((cVar != null) && (runnable = ((B) cVar).a) != null) {
            runnable.run();
        }
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getAudioSessionId();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.g) {
            return this.s;
        }
        return 100;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getDuration();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        boolean z = this.r && this.q;
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return !z;
        }
        return mediaPlayer.isPlaying() || !z;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        this.s = i;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        destroyPlayer();
        a(3);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        MediaController mediaController = this.p;
        return mediaController != null ? mediaController.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (exitOnKeypress(i, keyEvent)) {
            return true;
        }
        MediaController mediaController = this.p;
        return mediaController != null ? mediaController.onKeyUp(i, keyEvent) : super.onKeyUp(i, keyEvent);
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        Z0 z0 = this.y;
        if (z0 != null) {
            z0.b = true;
            this.y = null;
        }
        int i = this.e;
        if (i == 0 || i == 1) {
            MediaController mediaController = new MediaController(this.a);
            this.p = mediaController;
            mediaController.setMediaPlayer(this);
            this.p.setAnchorView(this);
            this.p.setEnabled(true);
            Context context = this.a;
            if (context instanceof Activity) {
                this.p.setSystemUiVisibility(((Activity) context).getWindow().getDecorView().getSystemUiVisibility());
            }
            this.p.show();
        }
        this.r = true;
        if (!this.q || isPlaying()) {
            return;
        }
        a(1);
        updateVideoLayout();
        if (this.t) {
            return;
        }
        start();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.e != 2 || action != 0) {
            MediaController mediaController = this.p;
            return mediaController != null ? mediaController.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 0 || i2 == 0) {
            return;
        }
        this.q = true;
        this.m = i;
        this.n = i2;
        if (!this.r || isPlaying()) {
            return;
        }
        a(1);
        updateVideoLayout();
        if (this.t) {
            return;
        }
        start();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return;
        }
        if (this.v) {
            this.u = mediaPlayer.getCurrentPosition();
            this.o.pause();
        }
        this.t = true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i) {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seekTo(i);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        MediaPlayer mediaPlayer = this.o;
        if (mediaPlayer == null) {
            return;
        }
        if (this.v) {
            int i = this.u;
            if (i > 0) {
                mediaPlayer.seekTo(i);
            }
            this.o.start();
            this.u = 0;
        }
        this.t = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.k == i2 && this.l == i3) {
            return;
        }
        this.k = i2;
        this.l = i3;
        if (this.v) {
            updateVideoLayout();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        FileInputStream fileInputStream;
        this.v = true;
        if (this.t) {
            MediaPlayer mediaPlayer = this.o;
            if (mediaPlayer == null || this.u <= 0) {
                return;
            }
            mediaPlayer.setDisplay(this.c);
            this.o.seekTo(this.u);
            return;
        }
        MediaPlayer mediaPlayer2 = this.o;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setDisplay(this.c);
            if (this.t) {
                return;
            }
            int i = this.u;
            if (i > 0) {
                this.o.seekTo(i);
            }
            this.o.start();
            this.u = 0;
            return;
        }
        a(0);
        doCleanUp();
        try {
            MediaPlayer mediaPlayer3 = new MediaPlayer();
            this.o = mediaPlayer3;
            if (this.g) {
                mediaPlayer3.setDataSource(this.a, Uri.parse(this.d));
            } else {
                if (this.i != 0) {
                    fileInputStream = new FileInputStream(this.d);
                    this.o.setDataSource(fileInputStream.getFD(), this.h, this.i);
                } else {
                    try {
                        AssetFileDescriptor assetFileDescriptorOpenFd = getResources().getAssets().openFd(this.d);
                        this.o.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                        assetFileDescriptorOpenFd.close();
                    } catch (IOException unused) {
                        fileInputStream = new FileInputStream(this.d);
                        this.o.setDataSource(fileInputStream.getFD());
                        fileInputStream.close();
                    }
                }
                fileInputStream.close();
            }
            this.o.setDisplay(this.c);
            this.o.setScreenOnWhilePlaying(true);
            this.o.setOnBufferingUpdateListener(this);
            this.o.setOnCompletionListener(this);
            this.o.setOnPreparedListener(this);
            this.o.setOnVideoSizeChangedListener(this);
            this.o.setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(3).build());
            this.o.prepareAsync();
            this.y = new Z0(this);
            new Thread(this.y).start();
        } catch (Exception unused2) {
            a(2);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.v = false;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004b  */
    /* JADX WARN: Code duplicated, block: B:22:0x004f  */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:
    
        if (r8 == 0) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void updateVideoLayout() {
        /*
            r10 = this;
            android.media.MediaPlayer r0 = r10.o
            if (r0 != 0) goto L5
            return
        L5:
            android.content.Context r0 = r10.a
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.util.DisplayMetrics r1 = new android.util.DisplayMetrics
            r1.<init>()
            android.view.Display r0 = r0.getDefaultDisplay()
            r0.getMetrics(r1)
            int r0 = r1.widthPixels
            if (r0 <= 0) goto L27
            int r1 = r1.heightPixels
            if (r1 <= 0) goto L27
            r10.k = r0
            r10.l = r1
        L27:
            int r0 = r10.k
            int r1 = r10.l
            boolean r2 = r10.q
            if (r2 == 0) goto L55
            int r2 = r10.m
            float r3 = (float) r2
            int r4 = r10.n
            float r5 = (float) r4
            float r3 = r3 / r5
            float r5 = (float) r0
            float r6 = (float) r1
            float r7 = r5 / r6
            int r8 = r10.f
            r9 = 1
            if (r8 != r9) goto L44
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 > 0) goto L4f
            goto L4b
        L44:
            r9 = 2
            if (r8 != r9) goto L52
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 < 0) goto L4f
        L4b:
            float r5 = r5 / r3
            int r4 = (int) r5
            r2 = r0
            goto L57
        L4f:
            float r6 = r6 * r3
            int r2 = (int) r6
            goto L56
        L52:
            if (r8 != 0) goto L55
            goto L57
        L55:
            r2 = r0
        L56:
            r4 = r1
        L57:
            if (r0 != r2) goto L5b
            if (r1 == r4) goto L69
        L5b:
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r1 = 17
            r0.<init>(r2, r4, r1)
            com.unity3d.player.a1 r1 = r10.j
            android.view.SurfaceView r2 = r10.b
            r1.updateViewLayout(r2, r0)
        L69:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.a1.updateVideoLayout():void");
    }
}
