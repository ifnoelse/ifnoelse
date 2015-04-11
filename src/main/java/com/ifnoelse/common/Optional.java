package com.ifnoelse.common;

import java.util.Collection;

public abstract class Optional {
    public static <T> T orNull(T src, T defaultVal) {
        return src != null ? src : defaultVal;
    }

    public static String orEmpty(String target, String defaultVal) {
        return Validate.notEmpty(target) ? target : defaultVal;
    }

    public static <T extends Collection> T orEmpty(T target, T defaultVal) {
        return Validate.notEmpty(target) ? target : defaultVal;
    }

    public static <T> T[] orEmpty(T[] target, T[] defaultVal) {
        return Validate.notEmpty(target) ? target : defaultVal;
    }

    public static <T> T orObjectNull(Object src, T defaultVal) {
        return src != null ? (T) src : defaultVal;
    }
}
