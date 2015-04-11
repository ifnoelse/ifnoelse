package com.ifnoelse.collect;


import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ifnoelse on 2015/2/16.
 */
public class MinK<T> extends FixedSizePriorityQueue<T> {

    public MinK(int k, Comparator<? super T> comparator) {
        super(k, comparator);
    }

    @Override
    protected Comparator<? super T> queueingComparator(Comparator<? super T> stdComparator) {
        return Collections.reverseOrder(stdComparator);
    }

    @Override
    protected Comparator<? super T> sortingComparator(Comparator<? super T> stdComparator) {
        return stdComparator;
    }

    public T greatestSmall() {
        return peek();
    }
}
