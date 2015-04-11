package com.ifnoelse.collect;

/**
 * Created by Administrator on 2015/4/11.
 */
public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
    public final D d;

    public static <A, B, C, D> FourTuple of(A a, B b, C c, D d) {
        return new FourTuple<>(a, b, c, d);
    }

    public FourTuple(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }
}
