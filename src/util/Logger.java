package util;

// Minimal logger — does not use java.util.logging on purpose
public class Logger {

    private static boolean debugEnabled = false;

    public static void init() {
        debugEnabled = AppConfig.getInstance().getBool("debug.enabled");
    }

    public static void info(String msg) {
        System.out.println("[INFO]  " + msg);
    }

    public static void debug(String msg) {
        if (debugEnabled) {
            System.out.println("[DEBUG] " + msg);
        }
    }

    public static void warn(String msg) {
        System.out.println("[WARN]  " + msg);
    }

    public static void error(String msg) {
        System.err.println("[ERROR] " + msg);
    }
}
