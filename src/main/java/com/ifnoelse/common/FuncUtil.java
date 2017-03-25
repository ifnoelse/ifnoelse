package com.ifnoelse.common;

import java.util.*;

/**
 * Created by ifnoelse on 2015/8/22.
 */
public class FuncUtil {


    public interface Func$1<R, A> {
        public R todo(A a);
    }


    public static <E> Iterable<E> arrayToIterable(E[] arr) {
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

    public interface Func$2<R, A, B> {
        public R todo(A a, B b);
    }

    public interface Func$3<R, A, B, C> {
        public R todo(A a, B b, C c);
    }

    public static <R, E> R reduce(R init, Iterable<E> iterable, Func$2<R, R, E> func) {
        R r = init;

        for (E e : iterable)
            r = func.todo(r, e);

        return r;
    }

    public static <R, E> R reduce(R init, E[] arr, Func$2<R, R, E> func) {
        return reduce(init, arrayToIterable(arr), func);
    }

    public static <R, E> List<R> map(Iterable<E> iterable, Func$1<R, E> func) {
        return reduce(new ArrayList<R>(), iterable, (list, e) -> {
            list.add(func.todo(e));
            return list;
        });
    }

    public static <E> List<E> flatMap(Collection colls) {
        List<E> list = new ArrayList<>();
        for (Object obj : colls) {
            if (obj instanceof Collection) {
                list.addAll(flatMap((Collection) obj));
            } else {
                list.add((E) obj);
            }
        }
        return list;
    }


    /**
     * 将嵌套集合中的所有元素放入一个list中返回
     *
     * @param colls
     * @param func  处理每个元素的方法，元素被放入list之间会调用该方法，并将返回值放入list
     * @param <R>
     * @param <E>
     * @return
     */
    public static <R, E> List<R> flatMap(Collection colls, Func$1<R, E> func) {
        List<R> list = new ArrayList<>();
        for (Object obj : colls) {
            if (obj instanceof Collection) {
                list.addAll(flatMap((Collection) obj, func));
            } else {
                list.add(func.todo((E) obj));
            }
        }
        return list;
    }

    /**
     * 生成嵌套集合的代理迭代器，通过该迭代器可以遍历嵌套集合中的所有元素
     *
     * @param iterables
     * @param <E>
     * @return
     */
    public static <E> Iterable<E> flat(Iterable iterables) {

        return new Iterable<E>() {
            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {

                    E cur = null;
                    Iterator<E> subIterator = null;
                    Iterator<Iterable> iterator = iterables.iterator();

                    @Override
                    public boolean hasNext() {

                        if (cur != null) return true;

                        if (subIterator == null || !subIterator.hasNext()) {
                            while (iterator.hasNext()) {
                                Object obj = iterator.next();
                                if (obj instanceof Iterable) {
                                    subIterator = (Iterator<E>) flat((Iterable) obj).iterator();
                                    if (subIterator.hasNext())
                                        return true;
                                } else {
                                    cur = (E) obj;
                                    return true;
                                }
                            }
                        }

                        return cur != null || (subIterator != null && subIterator.hasNext());
                    }

                    @Override
                    public E next() {
                        if (!hasNext())
                            throw new NoSuchElementException();

                        if (cur != null) {
                            E res = cur;
                            cur = null;
                            return res;
                        } else {
                            return subIterator.next();
                        }
                    }
                };
            }
        };
    }

    public static <E> List<E> filter(Iterable<E> iterable, Func$1<Boolean, E> func) {
        return reduce(new ArrayList<E>(), iterable, (list, e) -> {
            if (func.todo(e)) {
                list.add(e);
            }
            return list;
        });
    }

    public static <E> List<E> filter(E[] arr, Func$1<Boolean, E> func) {
        return filter(arrayToIterable(arr), func);
    }


    public static <E> Iterable<E> filter$(Iterable<E> iterable, Func$1<Boolean, E> func) {
        Iterator<E> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (!func.todo(e))
                iterator.remove();
        }
        return iterable;
    }


    public static <K, V> Map<K, V> filter(Map<K, V> map, Func$2<Boolean, K, V> func) {
        return reduce(new HashMap<K, V>(), map.entrySet(), (m, e) -> {
            if (func.todo(e.getKey(), e.getValue())) {
                m.put(e.getKey(), e.getValue());
            }
            return m;
        });
    }


    public static <E> Set<E> filter(Set<E> set, Func$1<Boolean, E> func) {
        return reduce(new HashSet<E>(), set, (s, e) -> {
            if (func.todo(e))
                s.add(e);
            return s;
        });
    }


    public static <R, E> Map<R, Integer> countBy(Iterable<E> iterable, Func$1<R, E> func) {
        return reduce(new HashMap<R, Integer>(), iterable, (m, e) -> {
            R key = func.todo(e);
            m.put(key, m.getOrDefault(key, 0) + 1);
            return m;
        });

    }

    public static <E> Map<E, Integer> count(Iterable<E> iterable) {
        return countBy(iterable, e -> e);
    }

    public static <E> Map<E, Integer> count(E[] arr) {
        return countBy(arrayToIterable(arr), e -> e);
    }

    public static <E> Map countIn(Iterable<E> iterable, Func$1<Object[], E> func) {
        return reduce(new HashMap(), iterable, (m, e) -> {
            Object[] keys = func.todo(e);
            Map pre = m;
            for (int i = 0; i < keys.length; i++) {
                Object key = keys[i];
                if (i == keys.length - 1) {
                    pre.put(key, (Integer) pre.getOrDefault(key, 0) + 1);
                } else {
                    Map cur = (Map) pre.getOrDefault(key, new HashMap<>());
                    pre.put(key, cur);
                    pre = cur;
                }
            }
            return m;
        });

    }

    public static <E> Map countIn(E[] arr, Func$1<Object[], E> func) {
        return countIn(arrayToIterable(arr), func);

    }


    public static <R, E> Map<R, Integer> countBy(E[] arr, Func$1<R, E> func) {
        return reduce(new HashMap<R, Integer>(), arrayToIterable(arr), (m, e) -> {
            R key = func.todo(e);
            m.put(key, m.getOrDefault(key, 0) + 1);
            return m;
        });

    }

    public static <R, E> Map<R, List<E>> groupBy(Iterable<E> iterable, Func$1<R, E> func) {
        return groupBy(iterable, func, x -> {
            return x;
        });
    }


    public static <R, E, T> Map<R, List<T>> groupBy(Iterable<E> iterable, Func$1<R, E> keyMapper, Func$1<T, E> convertor) {
        Map<R, List<T>> res = new HashMap<>();
        reduce(res, iterable, (m, e) -> {
            R r = keyMapper.todo(e);
            List<T> list = m.getOrDefault(r, new ArrayList<T>());
            list.add(convertor.todo(e));
            m.put(r, list);
            return m;
        });
        return res;
    }

    public static <R, E> Map groupIn(Iterable<E> iterable, Func$1<Object[], E> keyMapper, Func$1<R, E> convertor) {
        return reduce(new HashMap(), iterable, (m, e) -> {
            Object[] keys = keyMapper.todo(e);
            Map pre = m;
            for (int i = 0; i < keys.length; i++) {
                Object key = keys[i];
                if (i == keys.length - 1) {
                    List<R> list = (List<R>) pre.getOrDefault(key, new ArrayList<R>());
                    list.add(convertor.todo(e));
                    pre.put(key, list);
                } else {
                    Map cur = (Map) pre.getOrDefault(key, new HashMap<>());
                    pre.put(key, cur);
                    pre = cur;
                }
            }
            return m;
        });
    }


    public static <R, E> Map groupIn(E[] arr, Func$1<Object[], E> keyMapper, Func$1<R, E> convertor) {
        return groupIn(arrayToIterable(arr), keyMapper, convertor);
    }

    public static <E> Map groupIn(Iterable<E> iterable, Func$1<Object[], E> keyMapper) {
        return groupIn(iterable, keyMapper, x -> {
            return x;
        });
    }

    public static <E> Map groupIn(E[] arr, Func$1<Object[], E> func) {
        return groupIn(arrayToIterable(arr), func);
    }

    public static <R, E, T> Map<R, List<T>> groupBy(E[] arr, Func$1<R, E> keymapper, Func$1<T, E> convertor) {
        return groupBy(arrayToIterable(arr), keymapper, convertor);
    }

    public static <R, E> Map<R, List<E>> groupBy(E[] arr, Func$1<R, E> keymapper) {
        return groupBy(arrayToIterable(arr), keymapper);
    }

}
