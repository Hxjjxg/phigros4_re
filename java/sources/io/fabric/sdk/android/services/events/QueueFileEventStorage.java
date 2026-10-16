package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class QueueFileEventStorage implements EventsStorage {
    private final Context context;
    private QueueFile queueFile;
    private File targetDirectory;
    private final String targetDirectoryName;
    private final File workingDirectory;
    private final File workingFile;

    public QueueFileEventStorage(Context context, File file, String str, String str2) throws IOException {
        this.context = context;
        this.workingDirectory = file;
        this.targetDirectoryName = str2;
        File file2 = new File(file, str);
        this.workingFile = file2;
        this.queueFile = new QueueFile(file2);
        createTargetDirectory();
    }

    private void createTargetDirectory() {
        File file = new File(this.workingDirectory, this.targetDirectoryName);
        this.targetDirectory = file;
        if (file.exists()) {
            return;
        }
        this.targetDirectory.mkdirs();
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public void add(byte[] bArr) throws IOException {
        this.queueFile.add(bArr);
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public int getWorkingFileUsedSizeInBytes() {
        return this.queueFile.usedBytes();
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public void rollOver(String str) throws Throwable {
        this.queueFile.close();
        move(this.workingFile, new File(this.targetDirectory, str));
        this.queueFile = new QueueFile(this.workingFile);
    }

    private void move(File file, File file2) throws Throwable {
        OutputStream outputStream;
        FileInputStream fileInputStream = null;
        OutputStream moveOutputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                moveOutputStream = getMoveOutputStream(file2);
                CommonUtils.copyStream(fileInputStream2, moveOutputStream, new byte[1024]);
                CommonUtils.closeOrLog(fileInputStream2, "Failed to close file input stream");
                CommonUtils.closeOrLog(moveOutputStream, "Failed to close output stream");
                file.delete();
            } catch (Throwable th) {
                th = th;
                outputStream = moveOutputStream;
                fileInputStream = fileInputStream2;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream");
                CommonUtils.closeOrLog(outputStream, "Failed to close output stream");
                file.delete();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            outputStream = null;
        }
    }

    public OutputStream getMoveOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public File getWorkingDirectory() {
        return this.workingDirectory;
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public File getRollOverDirectory() {
        return this.targetDirectory;
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public List<File> getBatchOfFilesToSend(int i) {
        ArrayList arrayList = new ArrayList();
        for (File file : this.targetDirectory.listFiles()) {
            arrayList.add(file);
            if (arrayList.size() >= i) {
                break;
            }
        }
        return arrayList;
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public void deleteFilesInRollOverDirectory(List<File> list) {
        for (File file : list) {
            CommonUtils.logControlled(this.context, String.format("deleting sent analytics file %s", file.getName()));
            file.delete();
        }
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public List<File> getAllFilesInRollOverDirectory() {
        return Arrays.asList(this.targetDirectory.listFiles());
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public void deleteWorkingFile() {
        try {
            this.queueFile.close();
        } catch (IOException unused) {
        }
        this.workingFile.delete();
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public boolean isWorkingFileEmpty() {
        return this.queueFile.isEmpty();
    }

    @Override // io.fabric.sdk.android.services.events.EventsStorage
    public boolean canWorkingFileStore(int i, int i2) {
        return this.queueFile.hasSpaceFor(i, i2);
    }
}
