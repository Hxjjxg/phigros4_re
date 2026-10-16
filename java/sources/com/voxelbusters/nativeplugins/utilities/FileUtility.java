package com.voxelbusters.nativeplugins.utilities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class FileUtility {
    public static final int IMAGE_QUALITY = 100;

    private static float getScaleFactor(int i, int i2, int i3) {
        if (i3 <= 0 || i2 <= 0 || i <= 0) {
            return 1.0f;
        }
        if (i2 <= i && i3 <= i) {
            return 1.0f;
        }
        float f = i2;
        float f2 = i3 * 1.0f;
        return f / f2 > 1.0f ? i / (f * 1.0f) : i / f2;
    }

    public static String getSavedFile(byte[] bArr, int i, File file, String str, boolean z) {
        return getSavedFile(bArr, i, file, str, z, true);
    }

    private static String getSavedFile(byte[] bArr, int i, File file, String str, boolean z, boolean z2) {
        String absolutePath;
        createDirectoriesIfUnAvailable(file.getAbsolutePath());
        File file2 = new File(file, str);
        if (file2.exists()) {
            file2.delete();
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (z2) {
            file2.setReadable(true, false);
            file2.setWritable(true, false);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            if (bArr != null) {
                fileOutputStream.write(bArr);
            }
            fileOutputStream.close();
            absolutePath = file2.getAbsolutePath();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            absolutePath = null;
        } catch (IOException e3) {
            e3.printStackTrace();
            absolutePath = null;
        }
        return (absolutePath == null || !z) ? absolutePath : "file://" + absolutePath;
    }

    private static void createDirectoriesIfUnAvailable(String str) {
        File file = new File(str);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static ByteArrayOutputStream getBitmapStreamFromLocalDirectory(String str) {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(new File(str).getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapDecodeFile.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    private static void createPathIfUnAvailable(File file, File file2) {
        if (file2.exists()) {
            return;
        }
        try {
            file.mkdirs();
            file2.createNewFile();
        } catch (IOException e) {
            Debug.error(CommonDefines.FILE_UTILS_TAG, "Creating file failed!");
            e.printStackTrace();
        }
    }

    public static String createFileFromStream(InputStream inputStream, File file, String str) {
        File file2 = new File(file, str);
        createPathIfUnAvailable(file, file2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file2.getAbsolutePath();
    }

    public static String getScaledImagePathFromBitmap(Bitmap bitmap, File file, String str, float f) {
        return getScaledImagePathFromBitmap(bitmap, file, str, f, new ExifInfo(0.0f, false));
    }

    public static String getScaledImagePathFromBitmap(Bitmap bitmap, File file, String str, float f, ExifInfo exifInfo) {
        String strSaveBitmap = null;
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != 0 && height != 0) {
            Matrix matrix = new Matrix();
            matrix.postScale(f, f);
            matrix.postRotate(exifInfo.getRotation());
            matrix.postScale(exifInfo.isFlipped() ? -1.0f : 1.0f, 1.0f);
            try {
                strSaveBitmap = saveBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true), file, str, true);
            } catch (Throwable th) {
                Debug.error("FileUtility", "Error while creating bitmap" + th);
            }
        } else {
            Debug.error(CommonDefines.FILE_UTILS_TAG, "Width and height should be greater than zero. Returning null reference");
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return strSaveBitmap;
    }

    public static void grantPermissions(Context context, Uri uri, int i) {
        context.grantUriPermission(context.getPackageName(), uri, i);
    }

    private static String getScaledImagePath(String str, File file, String str2, float f, boolean z) {
        File file2 = new File(str);
        String scaledImagePathFromBitmap = getScaledImagePathFromBitmap(BitmapFactory.decodeFile(file2.getAbsolutePath()), file, str2, f);
        if (z) {
            file2.delete();
        }
        return scaledImagePathFromBitmap;
    }

    public static String getSavedLocalFileFromUri(Context context, Uri uri, String str, String str2) {
        return getSavedFileFromUri(context, uri, context.getDir(str, 0), str2);
    }

    private static String getSavedFileFromUri(Context context, Uri uri, File file, String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] byteArray;
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[1024];
                while (inputStreamOpenInputStream.read(bArr) != -1) {
                    byteArrayOutputStream.write(bArr);
                }
                byteArrayOutputStream.flush();
                byteArray = byteArrayOutputStream.toByteArray();
            } catch (FileNotFoundException e) {
                e = e;
                e.printStackTrace();
                byteArray = null;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                byteArray = null;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            byteArrayOutputStream = null;
        } catch (IOException e4) {
            e = e4;
            byteArrayOutputStream = null;
        }
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
        if (byteArray != null) {
            return getSavedFile(byteArray, byteArray.length, file, str, true);
        }
        return null;
    }

    public static Uri createSharingFileUri(Context context, byte[] bArr, int i, String str, String str2) {
        ApplicationUtility.hasExternalStorageWritable(context);
        String savedFile = getSavedFile(bArr, i, ApplicationUtility.getLocalSaveDirectory(context, str), str2, false);
        Debug.log(CommonDefines.SHARING_TAG, "Saving temp at " + savedFile);
        if (StringUtility.isNullOrEmpty(savedFile)) {
            return null;
        }
        Uri uriForFile = FileProvider.getUriForFile(context, ApplicationUtility.getFileProviderAuthoityName(context), new File(savedFile));
        context.grantUriPermission(ApplicationUtility.getPackageName(context), uriForFile, 3);
        return uriForFile;
    }

    public static File getFileProviderUriFile(Context context, String str, String str2) {
        return new File(ApplicationUtility.getLocalSaveDirectory(context, str), str2);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0092 A[Catch: IOException -> 0x008e, TRY_LEAVE, TryCatch #4 {IOException -> 0x008e, blocks: (B:34:0x0081, B:36:0x0087, B:40:0x0092), top: B:46:0x0081 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v3 */
    public static String saveBitmap(Bitmap bitmap, File file, String str, boolean z) throws Throwable {
        FileOutputStream fileOutputStream;
        IOException e;
        File file2 = new File(file, (String) str);
        createPathIfUnAvailable(file, file2);
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    try {
                        if (!bitmap.hasAlpha()) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        } else {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        }
                        if (z && !bitmap.isRecycled()) {
                            bitmap.recycle();
                            System.gc();
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        Debug.error(CommonDefines.FILE_UTILS_TAG, "Error creating scaled bitmap " + e.getMessage());
                        e.printStackTrace();
                        if (z && !bitmap.isRecycled()) {
                            bitmap.recycle();
                            System.gc();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (IOException e4) {
                fileOutputStream = null;
                e = e4;
            } catch (Throwable th) {
                str = 0;
                th = th;
                if (z) {
                    try {
                        if (!bitmap.isRecycled()) {
                            bitmap.recycle();
                            System.gc();
                        }
                        if (str != 0) {
                            str.flush();
                            str.close();
                        }
                    } catch (IOException e5) {
                        e5.printStackTrace();
                        throw th;
                    }
                } else if (str != 0) {
                    str.flush();
                    str.close();
                }
                throw th;
            }
            return file2.getAbsolutePath();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x002d  */
    public static void limitImageToMaxResolution(Context context, int i, Uri uri, Uri uri2) {
        int iFloor;
        try {
            String path = uri.getPath();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            if (i2 > i || i3 > i) {
                float f = i;
                iFloor = (int) Math.floor(i2 / f);
                int iFloor2 = (int) Math.floor(i3 / f);
                if (iFloor <= 1 && iFloor2 <= 1) {
                    iFloor = 1;
                } else if (iFloor2 > iFloor) {
                    iFloor = iFloor2;
                }
            } else {
                iFloor = 1;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = Math.max((iFloor / 2) * 2, 1);
            File file = new File(uri.getPath());
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            if (bitmapDecodeStream != null) {
                getScaledImagePathFromBitmap(bitmapDecodeStream, file.getParentFile(), file.getName(), getScaleFactor(i, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight()), getBitmapOrientation(context, uri2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0019. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    private static ExifInfo getBitmapOrientation(Context context, Uri uri) {
        boolean z;
        int orientationFromMediaStore;
        int i = 0;
        try {
            z = 1;
            int attributeInt = new ExifInterface(context.getContentResolver().openInputStream(uri)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 0) {
                orientationFromMediaStore = getOrientationFromMediaStore(context, uri);
            } else {
                switch (attributeInt) {
                    case 2:
                        break;
                    case 4:
                        i = 1;
                    case 3:
                        orientationFromMediaStore = 180;
                        break;
                    case 5:
                        i = 1;
                    case 6:
                        orientationFromMediaStore = 90;
                        break;
                    case 7:
                        i = 1;
                    case 8:
                        orientationFromMediaStore = 270;
                        break;
                    default:
                        Debug.log("FileUtility", "Unknown Orientation in Exif : " + attributeInt);
                        z = 0;
                        break;
                }
                return new ExifInfo(i, z);
            }
            z = i;
            i = orientationFromMediaStore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ExifInfo(i, z);
    }

    public static int getOrientationFromMediaStore(Context context, Uri uri) {
        String[] strArr = {"_data", Keys.ORIENTATION};
        Cursor cursorQuery = context.getContentResolver().query(uri, strArr, null, null, null);
        if (cursorQuery == null) {
            return 0;
        }
        cursorQuery.moveToFirst();
        int i = cursorQuery.getInt(cursorQuery.getColumnIndex(strArr[1]));
        cursorQuery.close();
        return i;
    }

    public static String getMimeType(byte[] bArr, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, 0, i, options);
        return options.outMimeType;
    }
}
