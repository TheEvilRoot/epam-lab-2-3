package com.theevilroot.epam.lab23.model.numbers;

public class AComplexNumber extends AbstractComplexNumber {

    private Double real;
    private Double imaginary;

    public AComplexNumber(Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public AComplexNumber() {
        this(0d, 0d);
    }

    public Double getReal() {
        return real;
    }

    public Double getImaginary() {
        return imaginary;
    }

    @Override
    public AComplexNumber toAlgebraic() {
        return this;
    }

    @Override
    public EComplexNumber toExponential() {
        Double r = Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
        Double a = Math.atan(getImaginary() / getReal());
        return new EComplexNumber(r, a);
    }

    @Override
    public String toString() {
        return String.format("%f %c %fi", getReal(), getImaginary() < 0 ? '-' : '+', Math.abs(getImaginary()));
    }
}
