package com.ifnoelse.common;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.util.*;

/**
 * Created by ifnoelse on 2015/8/22.
 */
public class FuncUtil {
    public interface Func1<R, A> {
        public R todo(A a);
    }

    private static class Array2Iterable {
        public static <E> Iterable<E> convert(E[] arr) {
            return new Iterable<E>() {
                @Override
                public Iterator<E> iterator() {
                    return new Iterator<E>() {

                        int index = 0;

                        @Override
                        public boolean hasNext() {
                            return index < arr.length;
                        }

                        @Override
                        public E next() {
                            return arr[index++];
                        }
                    };
                }
            };
        }
    }

    public interface Func2<R, A, B> {
        public R todo(A a, B b);
    }

    public static <R, E> R reduce(R init, Iterable<E> iterable, Func2<R, R, E> func) {
        R r = init;

        for (E e : iterable)
            r = func.todo(r, e);

        return r;
    }

    public static <R, E> R reduce(R init, E[] arr, Func2<R, R, E> func) {
        return reduce(init, Array2Iterable.convert(arr), func);
    }

    public static <R, E> Map<R, List<E>> group(Iterable<E> iterable, Func1<R, E> func) {
        return group(iterable, func, x -> {
            return x;
        });
    }

    public static <R, E, T> Map<R, List<T>> group(Iterable<E> iterable, Func1<R, E> keymapper, Func1<T, E> convertor) {
        Map<R, List<T>> res = new HashMap<>();
        reduce(res, iterable, (m, e) -> {
            R r = keymapper.todo(e);
            List<T> list = m.getOrDefault(r, new ArrayList<T>());
            list.add(convertor.todo(e));
            m.put(r, list);
            return m;
        });
        return res;
    }

    public static <R, E, T> Map<R, List<T>> group(E[] arr, Func1<R, E> func, Func1<T, E> convertor) {
        return group(Array2Iterable.convert(arr), func, convertor);
    }

    public static <R, E> Map<R, List<E>> group(E[] arr, Func1<R, E> func) {

        return group(Array2Iterable.convert(arr), func);
    }

    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }

        System.out.println(reduce(0, new Integer[]{1, 23}, (init, e) -> {
            return init + e;
        }));
        System.out.println(group("a b c".split(" "), in -> {
            return in;
        }, x -> {
            return x.toUpperCase();
        }));
    }
}
