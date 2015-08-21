package com.ifnoelse.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

    public static <R, E> List<R> map(Iterable<E> iterable, Func1<R, E> func) {
        return reduce(new ArrayList<R>(), iterable, (list, e) -> {
            list.add(func.todo(e));
            return list;
        });
    }

    public static <E> List<E> flatMap(Collection colls){
        List<E> list = new ArrayList<>();
        for (Object obj:colls){
            if(obj instanceof Collection){
                list.addAll(flatMap((Collection) obj));
            }else {
                list.add((E) obj);
            }
        }
        return list;
    }

    public static <E> List<E> filter(Iterable<E> iterable, Func1<Boolean, E> func) {
        return reduce(new ArrayList<E>(), iterable, (list, e) -> {
            if (func.todo(e)) {
                list.add(e);
            }
            return list;
        });
    }

    public static <E> Iterable<E> filter$(Iterable<E> iterable, Func1<Boolean, E> func) {
        Iterator<E> iterator = iterable.iterator();
        while (iterator.hasNext()){
            E e = iterator.next();
            if(!func.todo(e))
                iterator.remove();
        }
        return iterable;
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, Func2<Boolean, K, V> func) {
        return reduce(new HashMap<K, V>(), map.entrySet(), (m, e) -> {
            if (func.todo(e.getKey(), e.getValue())) {
                m.put(e.getKey(), e.getValue());
            }
            return m;
        });
    }


    public static <E> Set<E> filter(Set<E> set, Func1<Boolean, E> func) {
        return reduce(new HashSet<E>(), set, (s, e) -> {
            if (func.todo(e))
                s.add(e);
            return s;
        });
    }


    public static <R, E> Map<R, List<E>> groupBy(Iterable<E> iterable, Func1<R, E> func) {
        return groupBy(iterable, func, x -> {
            return x;
        });
    }


    public static <R, E, T> Map<R, List<T>> groupBy(Iterable<E> iterable, Func1<R, E> keymapper, Func1<T, E> convertor) {
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

    public static <R, E, T> Map<R, List<T>> groupBy(E[] arr, Func1<R, E> keymapper, Func1<T, E> convertor) {
        return groupBy(Array2Iterable.convert(arr), keymapper, convertor);
    }

    public static <R, E> Map<R, List<E>> groupBy(E[] arr, Func1<R, E> keymapper) {

        return groupBy(Array2Iterable.convert(arr), keymapper);
    }

    public static void main(String[] args) {

    }
}
