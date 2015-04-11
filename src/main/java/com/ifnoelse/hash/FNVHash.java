package com.ifnoelse.hash;

public final class FNVHash {

	private static final long FNV_64_INIT = 0xcbf29ce484222325L;
	private static final long FNV_64_PRIME = 0x100000001b3L;

	private static final int FNV_32_INIT = 0x811c9dc5;
	private static final int FNV_32_PRIME = 0x01000193;

	private FNVHash() {
	}

	public static int FNV1_Hash32(final byte[] k) {
		int rv = FNV_32_INIT;
		final int len = k.length;
		for (int i = 0; i < len; i++) {
			rv *= FNV_32_PRIME;
			rv ^= k[i];
		}
		return rv;
	}
	public static int FNV1A_Hash32(final byte[] k) {
		int rv = FNV_32_INIT;
		final int len = k.length;
		for (int i = 0; i < len; i++) {
			rv ^= k[i];
			rv *= FNV_32_PRIME;
		}
		return rv;
	}

	public static long FNV1_Hash64(final byte[] k) {
		long rv = FNV_64_INIT;
		final int len = k.length;
		for (int i = 0; i < len; i++) {
			rv *= FNV_64_PRIME;
			rv ^= k[i];
		}
		return rv;
	}
	public static long FNV1A_Hash64(final byte[] k) {
		long rv = FNV_64_INIT;
		final int len = k.length;
		for (int i = 0; i < len; i++) {
			rv ^= k[i];
			rv *= FNV_64_PRIME;
		}
		return rv;
	}

	public static int FNV1_Hash32(final String k) {
		int rv = FNV_32_INIT;
		final int len = k.length();
		for (int i = 0; i < len; i++) {
			rv *= FNV_32_PRIME;
			rv ^= k.charAt(i);
		}
		return rv;
	}
	public static int FNV1A_Hash32(final String k) {
		int rv = FNV_32_INIT;
		final int len = k.length();
		for (int i = 0; i < len; i++) {
			rv ^= k.charAt(i);
			rv *= FNV_32_PRIME;
		}
		return rv;
	}

	public static long FNV1_Hash64(final String k) {
		long rv = FNV_64_INIT;
		final int len = k.length();
		for (int i = 0; i < len; i++) {
			rv *= FNV_64_PRIME;
			rv ^= k.charAt(i);
		}
		return rv;
	}
	public static long FNV1A_Hash64(final String k) {
		long rv = FNV_64_INIT;
		final int len = k.length();
		for (int i = 0; i < len; i++) {
			rv ^= k.charAt(i);
			rv *= FNV_64_PRIME;
		}
		return rv;
	}

}