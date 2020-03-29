package com.theevilroot.epam.lab23.api;

import java.util.HashSet;
import java.util.Set;

public class ObservableValue<T> implements MutableObservable<T> {

    private T value;
    private Set<Observer<? super T>> observerSet = new HashSet<>();

    public ObservableValue(T initialValue) {
        this.value = initialValue;
    }

    public ObservableValue() {
        this(null);
    }

    @Override
    public void setValue(T newValue) {
        value = newValue;
        notifyObservers(newValue);
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        observerSet.add(observer);
        observer.onValueChanged(value);
    }

    public void notifyObservers(T newValue) {
        observerSet.forEach(o -> o.onValueChanged(newValue));
    }

    @Override
    public void unsubscribe(Observer<? super T> observer) {
        observerSet.remove(observer);
    }

    @Override
    public T getValue() {
        return value;
    }
}
