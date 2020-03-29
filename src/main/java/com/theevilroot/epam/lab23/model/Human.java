package com.theevilroot.epam.lab23.model;

import com.theevilroot.epam.lab23.model.exceptions.TooDifficultException;
import com.theevilroot.epam.lab23.model.expression.NumericExpression;

public class Human {

    private String name;
    private int maxDifficulty;

    public Human(String name, int maxDifficulty) {
        this.name = name;
        this.maxDifficulty = maxDifficulty;
    }

    public Human(String name) {
        this(name, -1);
    }

    public String getName() {
        return name;
    }

    public int getMaxDifficulty() {
        return maxDifficulty;
    }

    public String evaluateExpression(NumericExpression expression) {
        if (expression.getOperands().size() > maxDifficulty) {
            throw new TooDifficultException(this, expression);
        }

        return expression.calculateResult().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Human)
            return ((Human) o).getName().equals(this.getName()) &&
                    ((Human) o).getMaxDifficulty() == this.getMaxDifficulty();
        return false;
    }
}
