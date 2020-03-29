package com.theevilroot.epam.lab23.model.expression;

public enum ExpressionOperator {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    public final String value;

    ExpressionOperator(String str) {
        this.value = str;
    }

    public static ExpressionOperator fromValue(String val) {
        switch (val) {
            case "-": return SUBTRACT;
            case "*": return MULTIPLY;
            case "/": return DIVIDE;
            default: return ADD;
        }
    }

}
