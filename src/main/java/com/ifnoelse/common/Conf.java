package com.ifnoelse.common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Conf {
    private static Properties p = null;

    static {
        p = new Properties();
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("conf.properties").getPath();
            p.load(new FileReader(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String $(String key, String defaultValue) {
        String v = p.getProperty(key);
        return v == null ? defaultValue : v;
    }

    public static int $(String key, int defaultValue) {
        return Integer.valueOf($(key, defaultValue + ""));
    }

    public static double $(String key, double defaultValue) {
        return Double.valueOf($(key, defaultValue + ""));
    }

    public static boolean $(String key, boolean defaultValue) {
        return Boolean.valueOf($(key, defaultValue + ""));
    }

    public static String $(String key) {
        return $(key, null);
    }

}
