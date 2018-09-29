package com.ifnoelse.hash;

import java.util.Map;

public final class SimHash {

	private static final int HASH_32 = 32;
	private static final int HASH_64 = 64;

	public interface Splitter {
		public String next();
	}

	public interface Hasher {
		public int hash32(String s);

		public long hash64(String s);
	}

	/**
	 * 
	 * @param s
	 *            需要格式化的字符串
	 * @param sep
	 *            分隔符
	 * @param radix
	 *            按多少位分
	 * @return 返回按分隔符分隔之后的字符串
	 */
	public static String prettyBinaryString(String s, String sep, long radix) {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			temp.append(s.charAt(i));
			if ((i + 1) % radix == 0) {
				temp.append(sep);
			}
		}
		return temp.toString();
	}

	public static String prettyBinaryStringInverse(String s, String sep,
			long radix) {
		StringBuilder temp = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			temp.append(s.charAt(i));
			if ((s.length() - i) % radix == 0 && i != 0) {
				temp.append(sep);
			}
		}
		return temp.toString();
	}

	public static String prettyBinaryStringInverse(long n, String sep,
			long radix) {
		if (n > Integer.MAX_VALUE || n < Integer.MIN_VALUE) {
			return prettyBinaryStringInverse(Long.toBinaryString(n), sep, radix);
		} else {
			return prettyBinaryStringInverse(Integer.toBinaryString((int) n),
					sep, radix);
		}
	}

	public static String prettyBinaryString(long n, String sep, int radix) {
		return prettyBinaryString(Long.toBinaryString(n), sep, radix);
	}

	/**
	 * 计算 字符串数据的simhash值
	 * 
	 * @param strArr
	 *            需要计算simhash的字符串数组
	 * @return 为strArr返回bit位的simhash值
	 */
	public static int simHash32(String[] strArr) {

		int[] rv = new int[HASH_32];
		for (String s : strArr) {
			computeDimension(hash32(s), rv);
		}
		return norm32(rv);

	}

	public static int simHash32(String str) {
		String[] strArr = new String[str.length()];
		int n = 0;
		for (char c : str.toCharArray()) {
			strArr[n++] = c + "";
		}
		return simHash32(strArr);

	}

	public static int simHash32(String[] strArr, Hasher hasher) {

		int[] rv = new int[HASH_32];
		for (String s : strArr) {
			computeDimension(hasher.hash32(s), rv);
		}
		return norm32(rv);

	}

	public static long simHash64(String[] strArr) {

		int[] rv = new int[HASH_64];
		for (String s : strArr) {
			computeDimension(hash64(s), rv);
		}
		return norm64(rv);

	}

	public static long simHash64(String[] strArr, Hasher hasher) {

		int[] rv = new int[HASH_64];
		for (String s : strArr) {
			computeDimension(hasher.hash64(s), rv);
		}
		return norm64(rv);

	}

	/**
	 * 
	 * @param s
	 *            需要计算哈希值的字符串
	 * @return 为字符串s返回bit位的哈希值
	 */
	public static int hash32(String s) {
		final byte[] data = s.getBytes();
		final int length = data.length;
		final int seed = 0x9747b28c;
		final int m = 0x5bd1e995;
		final int r = 24;
		// Initialize the hash to a random value
		int h = seed ^ length;
		int length4 = length / 4;

		for (int i = 0; i < length4; i++) {
			final int i4 = i * 4;
			int k = (data[i4 + 0] & 0xff) + ((data[i4 + 1] & 0xff) << 8)
					+ ((data[i4 + 2] & 0xff) << 16)
					+ ((data[i4 + 3] & 0xff) << 24);
			k *= m;
			k ^= k >>> r;
			k *= m;
			h *= m;
			h ^= k;
		}

		// Handle the last few bytes of the input array
		switch (length % 4) {
		case 3:
			h ^= (data[(length & ~3) + 2] & 0xff) << 16;
		case 2:
			h ^= (data[(length & ~3) + 1] & 0xff) << 8;
		case 1:
			h ^= (data[length & ~3] & 0xff);
			h *= m;
		}

		h ^= h >>> 13;
		h *= m;
		h ^= h >>> 15;

		return h;
	}

	public static long hash64(String s) {
		final byte[] data = s.getBytes();
		final int length = data.length;
		final int seed = 0xe17a1465;
		final long m = 0xc6a4a7935bd1e995L;
		final int r = 47;

		long h = (seed & 0xffffffffl) ^ (length * m);

		int length8 = length / 8;

		for (int i = 0; i < length8; i++) {
			final int i8 = i * 8;
			long k = ((long) data[i8 + 0] & 0xff)
					+ (((long) data[i8 + 1] & 0xff) << 8)
					+ (((long) data[i8 + 2] & 0xff) << 16)
					+ (((long) data[i8 + 3] & 0xff) << 24)
					+ (((long) data[i8 + 4] & 0xff) << 32)
					+ (((long) data[i8 + 5] & 0xff) << 40)
					+ (((long) data[i8 + 6] & 0xff) << 48)
					+ (((long) data[i8 + 7] & 0xff) << 56);
			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		switch (length % 8) {
		case 7:
			h ^= (long) (data[(length & ~7) + 6] & 0xff) << 48;
		case 6:
			h ^= (long) (data[(length & ~7) + 5] & 0xff) << 40;
		case 5:
			h ^= (long) (data[(length & ~7) + 4] & 0xff) << 32;
		case 4:
			h ^= (long) (data[(length & ~7) + 3] & 0xff) << 24;
		case 3:
			h ^= (long) (data[(length & ~7) + 2] & 0xff) << 16;
		case 2:
			h ^= (long) (data[(length & ~7) + 1] & 0xff) << 8;
		case 1:
			h ^= (long) (data[length & ~7] & 0xff);
			h *= m;
		}
		;

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		return h;
	}

	private static int norm32(int[] rv) {

		int norm = 0;
		for (int i = 0; i < rv.length; i++) {
			if (rv[i] >= 0) {
				norm ^= 1 << i;
			}
		}
		return norm;
	}

	private static long norm64(int[] rv) {

		long norm = 0;
		for (int i = 0; i < rv.length; i++) {
			if (rv[i] >= 0) {
				norm ^= 1L << i;
			}
		}
		return norm;
	}

	/**
	 * 
	 * @param v
	 * @param rv
	 */
	private static void computeDimension(long v, int[] rv) {

		for (int i = 0; i < rv.length; i++) {
			if ((v >> i & 1) == 1) {
				rv[i] += 1;
			} else {
				rv[i] -= 1;
			}
		}
	}

	public static int simHash32(Splitter splitter) {

		int[] rv = new int[HASH_32];

		for (String s = splitter.next(); s != null; s = splitter.next()) {
			computeDimension(hash32(s), rv);
		}
		return norm32(rv);

	}

	public static int simHash32(Splitter splitter, Hasher hasher) {

		int[] rv = new int[HASH_32];

		for (String s = splitter.next(); s != null; s = splitter.next()) {
			computeDimension(hasher.hash32(s), rv);
		}
		return norm32(rv);

	}

	public static long simHash64(Splitter splitter) {

		int[] rv = new int[HASH_64];

		for (String s = splitter.next(); s != null; s = splitter.next()) {
			computeDimension(hash64(s), rv);
		}
		return norm64(rv);

	}

	public static long simHash64(Splitter splitter, Hasher hasher) {

		int[] rv = new int[HASH_64];

		for (String s = splitter.next(); s != null; s = splitter.next()) {
			computeDimension(hasher.hash64(s), rv);
		}
		return norm64(rv);

	}

	public static int simHash32(Map<String, Double> map) {

		if (map == null || map.size() == 0) {
			return 0;
		}

		double[] rv = new double[HASH_32];
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			computeDimension(hash32(entry.getKey()), entry.getValue(), rv);
		}

		return norm32(rv);

	}

	public static int simHash32(Map<String, Double> map, Hasher hasher) {

		if (map == null || map.size() == 0) {
			return 0;
		}

		double[] rv = new double[HASH_32];
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			computeDimension(hasher.hash32(entry.getKey()), entry.getValue(),
					rv);
		}

		return norm32(rv);

	}

	public static long simHash64(Map<String, Double> map) {

		if (map == null || map.size() == 0) {
			return 0;
		}

		double[] rv = new double[HASH_64];
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			computeDimension(hash64(entry.getKey()), entry.getValue(), rv);
		}

		return norm64(rv);

	}

	public static long simHash64(Map<String, Double> map, Hasher hasher) {

		if (map == null || map.size() == 0) {
			return 0;
		}

		double[] rv = new double[HASH_64];
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			computeDimension(hasher.hash64(entry.getKey()), entry.getValue(),
					rv);
		}

		return norm64(rv);

	}

	private static int norm32(double[] rv) {
		int norm = 0;
		for (int i = 0; i < rv.length; i++) {
			if (rv[i] >= 0) {
				norm ^= 1L << i;
			}
		}
		return norm;
	}

	private static long norm64(double[] rv) {
		long norm = 0;
		for (int i = 0; i < rv.length; i++) {
			if (rv[i] >= 0) {
				norm ^= 1L << i;
			}
		}
		return norm;
	}

	private static void computeDimension(long v, double weight, double[] rv) {
		for (int i = 0; i < rv.length; i++) {
			if ((v >> i & 1) == 1) {
				rv[i] += weight * weight;
			} else {
				rv[i] -= weight * weight;
			}
		}
	}

	public static int simHash32(String str, String regex) {
		return simHash32(str.split(regex));
	}

	public static int simHash32(String str, String regex, Hasher hasher) {
		return simHash32(str.split(regex), hasher);
	}

	public static long simHash64(String str, String regex) {
		return simHash64(str.split(regex));
	}

	public static String simHashToString32(String str, String regex) {
		return simHashToString32(str.split(regex));

	}

	public static String simHashToString32(String str, String regex,
			Hasher hasher) {
		return simHashToString32(str.split(regex));

	}

	public static String simHashToString64(String str, String regex) {
		return simHashToString64(str.split(regex));

	}

	public static String simHashToString64(String str, String regex,
			Hasher hasher) {
		return simHashToString64(str.split(regex), hasher);

	}

	/**
	 * 计算字符串数组的相似性哈希，并把结果表示位二进制的字符串
	 * 
	 * @param strArr
	 *            需要计算相似性哈希的字符串
	 * @return 返回strArr相似性哈希的字符串形式
	 */
	public static String simHashToString32(String[] strArr) {
		int simHash = simHash32(strArr);
		return Long.toBinaryString(simHash);

	}

	public static String simHashToString32(String[] strArr, Hasher hasher) {
		long simHash = simHash32(strArr, hasher);
		return Long.toBinaryString(simHash);

	}

	public static String simHashToString64(String[] strArr) {
		long simHash = simHash64(strArr);
		return Long.toBinaryString(simHash);

	}

	public static String simHashToString64(String[] strArr, Hasher hasher) {
		long simHash = simHash64(strArr, hasher);
		return Long.toBinaryString(simHash);

	}

	/**
	 * 计算汉明距离，按位计算连个整数a,b之间的海明距离 两个等长字符串之间的汉明距离是两个字符串对应位置的不同字符的个数，例如：
	 * 1011101 与 1001001 之间的汉明距离是 2。
	 * 2143896 与 2233796 之间的汉明距离是 3。
	 * "toned" 与 "roses" 之间的汉明距离是 3。
	 * 
	 * @param a 整数
	 * @param b 整数
	 * @return 返回a,b之间的汉明距离
	 */
	public static int hammingDistance(long a, long b) {
		int distance = 0;
		long c = a ^ b;
		for (int i = 0; i < HASH_64; i++) {
			if ((c >>> i & 1) > 0) {
				distance++;
			}
			// if (((a >>> i & 1) ^ (b >>> i & 1)) == 1) {
			// distance++;
			// }
		}
		return distance;

	}

	public static int hammingDistance(int a, int b) {
		int distance = 0;
		int c = a ^ b;
		for (int i = 0; i < HASH_32; i++) {
			if ((c >>> i & 1) > 0) {
				distance++;
			}
			// if (((a >>> i & 1) ^ (b >>> i & 1)) == 1) {
			// distance++;
			// }
		}
		return distance;

	}

	public static double cosineSimilarity(long a, long b) {
		double ab = vectorDot(a, b);
		double ab_norm = ab_norm(a, b);
		return ab / ab_norm;
	}

	public static double cosineSimilarity(int a, int b) {
		double ab = vectorDot(a, b);
		double ab_norm = ab_norm(a, b);
		return ab / ab_norm;
	}

	private static double ab_norm(long a, long b) {
		double a_norm = 0;
		double b_norm = 0;
		for (int i = 0; i < HASH_64; i++) {
			a_norm += a >>> i & 1;
			b_norm += b >>> i & 1;
		}
		return Math.sqrt(a_norm * b_norm);

	}

	private static double ab_norm(int a, int b) {
		double a_norm = 0;
		double b_norm = 0;
		for (int i = 0; i < HASH_32; i++) {
			a_norm += a >>> i & 1;
			b_norm += b >>> i & 1;
		}
		return Math.sqrt(a_norm * b_norm);

	}

	private static double vectorDot(long a, long b) {
		long c = a & b;
		long ab = 0;
		for (int i = 0; i < HASH_64; i++) {
			ab += c >>> i & 1;
		}
		return ab;
	}

	private static double vectorDot(int a, int b) {
		int c = a & b;
		int ab = 0;
		for (int i = 0; i < HASH_32; i++) {
			ab += c >>> i & 1;
		}
		return ab;
	}

	public static int[] intToKeys(int h, int radix) {
		int m = 0;
		for (int i = 0; i < radix; i++) {
			m = m << 1 ^ 1;
		}
		int seps = 32 / radix;
		int[] keys = new int[seps];
		int count = 0;
		while (count < seps) {
			long key = h >>> count * radix & m ^ count << radix;
			keys[count++] = (int) key;
		}
		return keys;
	}

	public static int[] longToKeys(long h, int radix) {
		int m = 0;
		for (int i = 0; i < radix; i++) {
			m = m << 1 ^ 1;
		}
		int seps = 64 / radix;
		int[] keys = new int[seps];
		int count = 0;
		while (count < seps) {
			long key = h >>> count * radix & m ^ count << radix;
			keys[count++] = (int) key;
		}
		return keys;
	}

	public static boolean testPair(int[] aKeys, int[] bKeys) {
		if (aKeys.length == bKeys.length) {
			for (int i = 0; i < aKeys.length; i++) {
				if (aKeys[i] == bKeys[i]) {
					return true;
				}
			}
		}
		return false;
	}

}
