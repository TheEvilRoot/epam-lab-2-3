package com.theevilroot.epam.lab23.model.numbers;

public class Number {

    private Double value;

    public Number(Double value) {
        this.value = value;
    }

    public Number() {
        this(0d);
    }

    public Double getValue() {
        return value;
    }

    public Number setValue(Double newValue) {
        value = newValue;
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Number)
            return ((Number) o).getValue().equals(getValue());
        return false;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    /** Operations **/

    public Number add(Number number) {
        value += number.getValue();
        return this;
    }

    public Number subtract(Number number) {
        value -= number.getValue();
        return this;
    }

    public Number multiply(Number number) {
        value *= number.getValue();
        return this;
    }

    public Number divide(Number number) {
        value /= number.getValue();
        return this;
    }
}
