package com.theevilroot.epam.lab23.gui;

public enum NumberType {


    ALGEBRAIC(NumberType.ACN),
    EXPONENTIAL(NumberType.ECN),
    COMMON(NumberType.N);

    private static final String ACN = "Algebraic Complex Number";
    private static final String ECN = "Exponential Complex Number";
    private static final String N = "Number";

    public final String value;

    NumberType(String str){
        value = str;
    }

    public static NumberType fromValue(String val) {
        switch (val) {
            case NumberType.ACN: return ALGEBRAIC;
            case NumberType.ECN: return EXPONENTIAL;
            default: return COMMON;
        }
    }

}
