package com.ifnoelse.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Conf {
    private static Properties p = null;

    static {
        try (InputStream in = Conf.class.getClassLoader().getResourceAsStream("uncia.properties");
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            p.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new RuntimeException("找不到配置文件：uncia.properties");
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
