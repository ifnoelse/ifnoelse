package com.ifnoelse.common;

import java.util.Collection;

public abstract class Assert {

	public static void notTrue(boolean expression, String message) {
		if (expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void notTrue(boolean expression) {
		notTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static void notEmpty(String text, String message) {
		if (!Validate.notEmpty(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(String text) {
		notEmpty(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (Validate.notEmpty(textToSearch) && Validate.notEmpty(substring) && textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}

	public static void notEmpty(Object[] array, String message) {
		if (!Validate.notEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	public static <T> void notEmpty(Collection<T> collection, String message) {
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T> void notEmpty(Collection<T> collection) {
		notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
		}
	}

	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

	public static void notEmpty(long[] theItemIds, String message) {
		if (!Validate.notEmpty(theItemIds)) {
			throw new IllegalArgumentException(message);
		}

	}

	public static void notEmpty(int[] theItemIds, String message) {
		if (!Validate.notEmpty(theItemIds)) {
			throw new IllegalArgumentException(message);
		}

	}

}
