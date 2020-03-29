package com.theevilroot.epam.lab23.model.numbers;

public class EComplexNumber extends AbstractComplexNumber {

    private Double modulus;
    private Double exponent;

    public EComplexNumber(Double modulus, Double exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }

    public EComplexNumber() {
        this(0d, 0d);
    }

    public Double getModulus() {
        return modulus;
    }

    public Double getExponent() {
        return exponent;
    }

    @Override
    public AComplexNumber toAlgebraic() {
        return new AComplexNumber(Math.cos(getExponent()) * getModulus(), Math.sin(getExponent()) * getModulus());
    }

    @Override
    public EComplexNumber toExponential() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%f * e^(%f)i", getModulus(), getExponent());
    }
}
