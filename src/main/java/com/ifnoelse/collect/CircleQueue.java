package com.ifnoelse.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircleQueue<T> implements Iterable<T> {

    private Object[] data = null;
    private int index = -1;
    private int last = 0;
    private int size = 0;

    public CircleQueue(int size) {
        data = new Object[size];
    }

    public static void main(String[] args) {
        CircleQueue<Integer> queue = new CircleQueue<>(120);
        for (int i = 0; i < 13; i++) {
            queue.add(i);
        }
        for (Integer e : queue) {
            System.out.println(e);
            queue.clear();
        }
        for (Integer e : queue) {
            System.out.println(e);
            queue.clear();
        }
    }

    public int size() {
        return size;
    }

    public synchronized void add(T e) {
        if (++index < data.length)
            data[index] = e;
        else {
            index = index % data.length;
            data[index] = e;
        }
        last = index;
        if (size < data.length)
            size++;
    }

    public synchronized void clear() {
        index = -1;
        size = 0;
    }

    public T head() {
        return (T) data[last];
    }

    public int getCapacity() {
        return data.length;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]).append(",");
            if (i >= 20) {
                sb.append(",,后边还有");
                break;
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator(index, size, data);
    }

    public Iterator<T> iterator(int num) {
        if (num <= 0 || num > data.length)
            throw new IllegalArgumentException("iterator args error !");
        return new QueueIterator(index, size, data, 0, num);
    }

    public Iterator<T> iterator(int start, int num) {
        if (num <= 0 || start < 0 || start > data.length || num > data.length)
            throw new IllegalArgumentException("iterator args error !");
        return new QueueIterator(index, size, data, start, num);
    }

    public void foreach(int num, ForEach<T> forEach, boolean sync) {
        foreach(0, num, forEach, sync);
    }

    public void foreach(int num, ForEach<T> forEach) {
        foreach(0, num, forEach, false);
    }

    public void foreach(int start, int num, ForEach<T> forEach) {
        foreach(start, num, forEach, false);
    }

    /**
     * @param start   倒排遍历开始的索引，倒数第几个
     * @param num     遍历的数据
     * @param forEach 处理函数
     * @param sync    是否保持同步
     */

    public void foreach(int start, int num, ForEach<T> forEach, boolean sync) {
        int curIndex = index;
        Object[] curData = data;
        if (num <= 0 || start < 0 || start > curData.length) return;
        if (sync)
            synchronized (this) {
                curIndex = index;
                curData = Arrays.copyOf(data, num);
            }
        int maxLen = curData.length - start;
        maxLen = Math.min(maxLen, size - start);
        num = Math.min(maxLen, num);
        int cur = curIndex - start;
        int count = start;
        while (num-- > 0) {
            if (cur < 0) {
                cur = curData.length + cur;
            }
            if (!forEach.process(count++, (T) curData[cur--])) return;
        }
    }

    public interface ForEach<E> {
        public boolean process(int i, E e);
    }

    private static class QueueIterator<T> implements Iterator<T> {
        private int num = 0;
        private int index = -1;
        private int size = 0;
        private Object[] data = null;

        public QueueIterator(int index, int size, T[] data) {
            this(index, size, data, 0, size);
        }

        public QueueIterator(int index, int size, T[] data, int start, int num) {
            this.index = index - start;
            this.data = data;
            this.size = size;
            int maxLen = Math.min(data.length - start, size - start);
            this.num = Math.min(maxLen, num);

        }

        @Override
        public boolean hasNext() {
            return num > 0;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            num--;
            if (index < 0) {
                index = data.length + index;
                return (T) data[index--];
            } else
                return (T) data[index--];

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
