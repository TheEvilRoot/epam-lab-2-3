package com.theevilroot.epam.lab23.api;

public interface MutableObservable<T> extends Observable<T> {

    void setValue(T newValue);

}
