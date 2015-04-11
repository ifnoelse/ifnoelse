package com.ifnoelse.collect;

import com.ifnoelse.common.Assert;

import java.util.*;

/**
 * Created by ifnoelse on 2015/2/16.
 */
abstract class FixedSizePriorityQueue<T> {

    private final int k;
    private final Comparator<? super T> queueingComparator;
    private final Comparator<? super T> sortingComparator;
    private final Queue<T> queue;

    FixedSizePriorityQueue(int k, Comparator<? super T> comparator) {
        Assert.isTrue(k > 0);
        this.k = k;
        Assert.notNull(comparator);
        this.queueingComparator = queueingComparator(comparator);
        this.sortingComparator = sortingComparator(comparator);
        this.queue = new PriorityQueue<T>(k + 1, queueingComparator);
    }

    abstract Comparator<? super T> queueingComparator(Comparator<? super T> stdComparator);
    abstract Comparator<? super T> sortingComparator(Comparator<? super T> stdComparator);

    public void offer(T item) {
        if (queue.size() < k) {
            queue.add(item);
        } else if (queueingComparator.compare(item, queue.peek()) > 0) {
            queue.add(item);
            queue.poll();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public List<T> retrieve() {
        List<T> topItems = new ArrayList(queue);
        Collections.sort(topItems, sortingComparator);
        return topItems;
    }

    protected T peek() {
        return queue.peek();
    }
}