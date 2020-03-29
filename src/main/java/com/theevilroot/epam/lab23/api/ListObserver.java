package com.theevilroot.epam.lab23.api;

import java.util.List;

public interface ListObserver<T> extends Observer<List<T>>{

    void onAdded(int index, T newValue);

    void onRemoved(int index);

    void onCleared();

}
