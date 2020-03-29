package com.theevilroot.epam.lab23.model.exceptions;

import com.theevilroot.epam.lab23.model.Human;
import com.theevilroot.epam.lab23.model.expression.NumericExpression;

public class TooDifficultException extends RuntimeException {

    public TooDifficultException(Human human, NumericExpression expr) {
        super("Expression with " + expr.getOperands().size() + " operands is too difficult for " + human.getName());
    }

}
