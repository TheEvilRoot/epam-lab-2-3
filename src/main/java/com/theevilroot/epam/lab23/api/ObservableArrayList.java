package com.theevilroot.epam.lab23.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ObservableArrayList<T> implements ListObservable<T> {

    private ArrayList<T> values = new ArrayList<>();
    private Set<ListObserver<T>> observerSet = new HashSet<>();

    @Override
    public T get(int index) {
        return values.get(index);
    }

    @Override
    public void add(T t) {
        values.add(t);
        observerSet.forEach(e -> {
            e.onAdded(values.size() - 1, t);
            e.onValueChanged(values);
        });
    }

    @Override
    public void addAll(T... ts) {
        for (T t : ts)
            add(t);
    }

    @Override
    public void remove(T t) {
        int index = values.indexOf(t);
        if (index >= 0) {
           remove(index);
        }
    }

    @Override
    public void remove(int index) {
        values.remove(index);
        observerSet.forEach(e -> {
            e.onRemoved(index);
            e.onValueChanged(values);
        });
    }

    @Override
    public void clear() {
        values.clear();
        observerSet.forEach(e -> {
            e.onCleared();
            e.onValueChanged(values);
        });
    }

    @Override
    public Stream<T> stream() {
        return values.stream();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public void subscribe(ListObserver<T> observer) {
        observerSet.add(observer);
        observer.onValueChanged(values);
    }

    @Override
    public void unsubscribe(ListObserver<T> observer) {
        observerSet.remove(observer);
    }

}
