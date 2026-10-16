package android.util;

/**
 * 编译期桩 —— 设备上由 Android 运行时提供，不会被打进 dex。
 * 只为让 javac 通过。
 */
public class Log {
    public static int i(String tag, String msg) { return 0; }
    public static int w(String tag, String msg) { return 0; }
}
