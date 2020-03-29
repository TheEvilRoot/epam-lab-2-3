package com.theevilroot.epam.lab23.model.expression;

import com.theevilroot.epam.lab23.api.Observable;
import com.theevilroot.epam.lab23.api.ObservableArrayList;
import com.theevilroot.epam.lab23.api.ObservableValue;
import com.theevilroot.epam.lab23.model.numbers.Number;

public class NumericExpression {

    private ObservableValue<ExpressionOperator> operator;
    private ObservableArrayList<Number> operands = new ObservableArrayList<>();

    public NumericExpression(ExpressionOperator operator) {
        this.operator = new ObservableValue<>(operator);
    }

    public NumericExpression addOperands(Number ...numbers) {
        getOperands().addAll(numbers);
        return this;
    }

    public NumericExpression removeOperand(int index) {
        getOperands().remove(index);
        return this;
    }

    public NumericExpression clear() {
        getOperands().clear();
        return this;
    }

    public ObservableArrayList<Number> getOperands() {
        return operands;
    }

    public NumericExpression setOperator(ExpressionOperator op) {
        operator.setValue(op);
        return this;
    }

    public Observable<ExpressionOperator> getOperator() {
        return operator;
    }

    public Number calculateResult() {
        if (operands.size() == 0) {
            return new Number();
        }

        Number result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Number operand = operands.get(i);

            switch (getOperator().getValue()) {
                case ADD:
                    result = result.add(operand);
                    break;
                case SUBTRACT:
                    result = result.subtract(operand);
                    break;
                case MULTIPLY:
                    result = result.multiply(operand);
                    break;
                case DIVIDE:
                    result = result.divide(operand);
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return getOperands().stream().map(Number::toString).reduce((p, q) ->
                String.format("%s %s %s", p, getOperator().getValue().value, q)).orElse("[No expression]");
    }

}
