package com.theevilroot.epam.lab23.api;

import java.util.List;
import java.util.stream.Stream;

public interface ListObservable<T> {

    T get(int index);

    void add(T t);

    void addAll(T ...ts);

    void remove(T t);

    void remove(int index);

    int size();

    void clear();

    Stream<T> stream();

    void subscribe(ListObserver<T> observer);

    void unsubscribe(ListObserver<T> observer);

}
