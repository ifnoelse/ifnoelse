package com.ifnoelse.collect;


import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ifnoelse on 2015/2/16.
 */
public class TopK<T> extends FixedSizePriorityQueue<T> {

    public TopK(int k, Comparator<? super T> comparator) {
        super(k, comparator);
    }

    @Override
    protected Comparator<? super T> queueingComparator(Comparator<? super T> stdComparator) {
        return stdComparator;
    }

    @Override
    protected Comparator<? super T> sortingComparator(Comparator<? super T> stdComparator) {
        return Collections.reverseOrder(stdComparator);
    }

    public T smallestGreat() {
        return peek();
    }
}