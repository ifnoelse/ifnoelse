package com.ifnoelse.common;

import java.io.*;
import java.util.Properties;

public class Conf {
    private static Properties p = null;

    static {
        p = new Properties();
        Reader reader = null;
        try {
            reader = new InputStreamReader(Conf.class.getClassLoader().getResourceAsStream("conf.properties"), "utf-8");
            p.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
