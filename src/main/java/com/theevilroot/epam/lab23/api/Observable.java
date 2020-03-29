package com.theevilroot.epam.lab23.api;

public interface Observable<T> {

    void subscribe(Observer<? super T> observer);

    void unsubscribe(Observer<? super T> observer);

    T getValue();

}
