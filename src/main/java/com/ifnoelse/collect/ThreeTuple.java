package com.ifnoelse.collect;

/**
 * Created by Administrator on 2015/4/11.
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public final C c;

    public static <A, B, C> ThreeTuple of(A a, B b, C c) {
        return new ThreeTuple<>(a, b, c);
    }

    public ThreeTuple(A a, B b, C c) {
        super(a, b);
        this.c = c;
    }
}
