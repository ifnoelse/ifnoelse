package com.ifnoelse.common;

import java.util.Collection;

public abstract class Validate {
	public static boolean notNull(Object obj) {
		return obj != null;
	}

	public static <T> boolean notEmpty(Collection<T> collection) {
		return notNull(collection) && collection.size() > 0;
	}

	public static <T> boolean isEmpty(Collection<T> collection) {
		return !notEmpty(collection);
	}

	public static boolean notEmpty(String str) {
		return notNull(str) && str.length() > 0;
	}

	public static boolean isEmpty(String str) {
		return !notEmpty(str);
	}

	public static boolean notEmpty(Object[] objs) {
		return notNull(objs) && objs.length > 0;
	}

	public static boolean notEmpty(String[] objs) {
		return notNull(objs) && objs.length > 0;
	}

	public static boolean isEmpty(String[] objs) {
		return !notEmpty(objs);
	}

	public static boolean isEmpty(Object[] objs) {
		return !notEmpty(objs);
	}

	public static boolean allNotNull(Object... obj) {
		if (!notNull(obj))
			return false;
		for (Object o : obj) {
			if (!notNull(o))
				return false;
		}
		return true;
	}

	public static boolean allNotEmpty(String... str) {
		if (!notNull(str))
			return false;
		for (String s : str) {
			if (isEmpty(s))
				return false;
		}
		return true;
	}

	public static boolean anyIsEmpty(String... str) {
		return !allNotEmpty(str);
	}

	public static boolean anyIsNull(Object... obj) {
		return !allNotNull(obj);
	}

	public static boolean containsValue(String[] arr, String value) {
		for (String s : arr) {
			if (allNotNull(arr, s, value) && s.equals(value))
				return true;
		}
		return false;
	}

	public static boolean isEmpty(long[] datas) {
		return !notEmpty(datas);
	}

	public static boolean notEmpty(long[] datas) {
		return notNull(datas) && datas.length > 0;
	}

	public static boolean notEmpty(int[] datas) {
		return notNull(datas) && datas.length > 0;

	}

	public static boolean isEmpty(int[] datas) {
		return !notEmpty(datas);

	}

}
