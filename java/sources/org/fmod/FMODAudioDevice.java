package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class FMODAudioDevice implements Runnable {
    private volatile Thread a = null;
    private volatile boolean b = false;
    private AudioTrack c = null;
    private boolean d = false;
    private ByteBuffer e = null;
    private byte[] f = null;
    private volatile a g;

    private native int fmodGetInfo(int i);

    private native int fmodProcess(ByteBuffer byteBuffer);

    private void releaseAudioTrack() {
        AudioTrack audioTrack = this.c;
        if (audioTrack != null) {
            if (audioTrack.getState() == 1) {
                this.c.stop();
            }
            this.c.release();
            this.c = null;
        }
        this.e = null;
        this.f = null;
        this.d = false;
    }

    public synchronized void close() {
        stop();
    }

    native int fmodProcessMicData(ByteBuffer byteBuffer, int i);

    public boolean isRunning() {
        return this.a != null && this.a.isAlive();
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = 3;
        while (this.b) {
            if (!this.d && i > 0) {
                releaseAudioTrack();
                int iFmodGetInfo = fmodGetInfo(0);
                int i2 = fmodGetInfo(4) == 1 ? 4 : 12;
                int minBufferSize = AudioTrack.getMinBufferSize(iFmodGetInfo, i2, 2);
                int iFmodGetInfo2 = fmodGetInfo(4) * 2;
                int iRound = Math.round(minBufferSize * 1.1f) & (~(iFmodGetInfo2 - 1));
                int iFmodGetInfo3 = fmodGetInfo(1);
                int iFmodGetInfo4 = fmodGetInfo(2) * iFmodGetInfo3 * iFmodGetInfo2;
                AudioTrack audioTrack = new AudioTrack(3, iFmodGetInfo, i2, 2, iFmodGetInfo4 > iRound ? iFmodGetInfo4 : iRound, 1);
                this.c = audioTrack;
                boolean z = audioTrack.getState() == 1;
                this.d = z;
                if (z) {
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iFmodGetInfo3 * iFmodGetInfo2);
                    this.e = byteBufferAllocateDirect;
                    this.f = new byte[byteBufferAllocateDirect.capacity()];
                    this.c.play();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioTrack failed to initialize (status " + this.c.getState() + ")");
                    releaseAudioTrack();
                    i--;
                }
            }
            if (this.d) {
                if (fmodGetInfo(3) == 1) {
                    fmodProcess(this.e);
                    ByteBuffer byteBuffer = this.e;
                    byteBuffer.get(this.f, 0, byteBuffer.capacity());
                    this.c.write(this.f, 0, this.e.capacity());
                    this.e.position(0);
                } else {
                    releaseAudioTrack();
                }
            }
        }
        releaseAudioTrack();
    }

    public synchronized void start() {
        if (this.a != null) {
            stop();
        }
        this.a = new Thread(this, "FMODAudioDevice");
        this.a.setPriority(10);
        this.b = true;
        this.a.start();
        if (this.g != null) {
            this.g.c();
        }
    }

    public synchronized int startAudioRecord(int i, int i2, int i3) {
        if (this.g == null) {
            this.g = new a(this, i, i2);
            this.g.c();
        }
        return this.g.a();
    }

    public synchronized void stop() {
        while (this.a != null) {
            this.b = false;
            try {
                this.a.join();
                this.a = null;
            } catch (InterruptedException unused) {
            }
        }
        if (this.g != null) {
            this.g.d();
        }
    }

    public synchronized void stopAudioRecord() {
        if (this.g != null) {
            this.g.d();
            this.g = null;
        }
    }
}
