package com.ifnoelse.collect;

/**
 * Created by Administrator on 2015/4/11.
 */
public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
    public final E e;

    public static <A, B, C, D, E> FiveTuple of(A a, B b, C c, D d, E e) {
        return new FiveTuple<>(a, b, c, d, e);
    }

    public FiveTuple(A a, B b, C c, D d, E e) {
        super(a, b, c, d);
        this.e = e;
    }
}
