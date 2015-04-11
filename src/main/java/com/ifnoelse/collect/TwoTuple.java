package com.ifnoelse.collect;

/**
 * Created by Administrator on 2015/4/11.
 */
public class TwoTuple<A, B> {
    public final A a;
    public final B b;

    public static <A, B> TwoTuple of(A a, B b) {
        return new TwoTuple<>(a, b);
    }

    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }


}
