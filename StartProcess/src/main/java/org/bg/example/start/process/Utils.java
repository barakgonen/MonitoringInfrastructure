package org.bg.example.start.process;

import org.apache.log4j.Level;

public class Utils {
    public static int getEnvInt(String key) {
        String value = System.getenv(key);
        if (value != null)
            return Integer.parseInt(System.getenv(key));
        return -1;
    }

    public static int getEnvIntWithDefalutValue(String key, int defaultValue) {
        String value = System.getenv(key);
        if (value != null)
            return Integer.parseInt(System.getenv(key));
        return defaultValue;
    }

    public static boolean getEnvBool(String key) {
        String value = System.getenv(key);
        if (value != null)
            return value.equals("true");
        return false;
    }

    public static String getEnvString(String key) {
        String value = System.getenv(key);
        if (value != null)
            return value;
        return null;
    }

    public static Level getEnvLogLevel() {
        String value = System.getenv("LOG_LEVEL");
        if (value != null) {
            Level level = Level.toLevel(value);
            return level;
        }

        return Level.INFO;
    }
}
