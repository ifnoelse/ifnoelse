package com.ifnoelse.collect;

/**
 * Created by Administrator on 2015/4/11.
 */
public class Tuples {
    private Tuples() {
    }

    public static <A, B> TwoTuple of(A a, B b) {
        return TwoTuple.of(a, b);
    }

    public static <A, B, C> ThreeTuple of(A a, B b, C c) {
        return ThreeTuple.of(a, b, c);
    }

    public static <A, B, C, D> FourTuple of(A a, B b, C c, D d) {
        return FourTuple.of(a, b, c, d);
    }

    public static <A, B, C, D, E> FiveTuple of(A a, B b, C c, D d, E e) {
        return FiveTuple.of(a, b, c, d, e);
    }
}
