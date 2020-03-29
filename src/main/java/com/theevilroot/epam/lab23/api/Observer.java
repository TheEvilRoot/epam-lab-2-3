package com.theevilroot.epam.lab23.api;

public interface Observer<T> {

    void onValueChanged(T newValue);

}
