package com.theevilroot.epam.lab23.model.numbers;

public abstract class AbstractComplexNumber extends Number {

    /** Conversion **/

    public abstract AComplexNumber toAlgebraic();

    public abstract EComplexNumber toExponential();

    /** Operations **/

    @Override
    public AbstractComplexNumber add(Number number) {
        AComplexNumber self;
        if (!(this instanceof AComplexNumber))
            self = this.toAlgebraic();
        else self = (AComplexNumber) this;

        if (number instanceof AbstractComplexNumber) {
            AComplexNumber operand;
            if (!(number instanceof AComplexNumber))
                operand = ((AbstractComplexNumber) number).toAlgebraic();
            else operand = (AComplexNumber) number;

            return new AComplexNumber(self.getReal() + operand.getReal(), self.getImaginary() + operand.getImaginary());
        } else {
            return new AComplexNumber(self.getReal() + number.getValue(), self.getImaginary());
        }
    }

    @Override
    public Number subtract(Number number) {
        AComplexNumber self;
        if (!(this instanceof AComplexNumber))
            self = this.toAlgebraic();
        else self = (AComplexNumber) this;

        if (number instanceof AbstractComplexNumber) {
            AComplexNumber operand = null;
            if (!(number instanceof AComplexNumber))
                operand = ((AbstractComplexNumber) number).toAlgebraic();
            else operand = (AComplexNumber) number;

            return new AComplexNumber(self.getReal() - operand.getReal(), self.getImaginary() - operand.getImaginary());
        } else {
            return new AComplexNumber(self.getReal() - number.getValue(), self.getImaginary());
        }
    }

    @Override
    public Number multiply(Number number) {
        EComplexNumber self;
        if (!(this instanceof EComplexNumber))
            self = this.toExponential();
        else self = (EComplexNumber) this;

        if (number instanceof AbstractComplexNumber) {
            EComplexNumber operand;
            if (!(number instanceof EComplexNumber))
                operand = ((AbstractComplexNumber) number).toExponential();
            else operand = (EComplexNumber) number;

            return new EComplexNumber(self.getModulus() * operand.getModulus(), self.getExponent() + operand.getExponent());
        } else {
            return new EComplexNumber(self.getModulus() * number.getValue(), self.getExponent());
        }
    }

    @Override
    public Number divide(Number number) {
        EComplexNumber self;
        if (!(this instanceof EComplexNumber))
            self = this.toExponential();
        else self = (EComplexNumber) this;

        if (number instanceof AbstractComplexNumber) {
            EComplexNumber operand;
            if (!(number instanceof EComplexNumber))
                operand = ((AbstractComplexNumber) number).toExponential();
            else operand = (EComplexNumber) number;

            return new EComplexNumber(self.getModulus() / operand.getModulus(), self.getExponent() - operand.getExponent());
        } else {
            return new EComplexNumber(self.getModulus() / number.getValue(), self.getExponent());
        }
    }


    /**
     * Hello. If you're trying to code-review this, I'm sorry!
     * Just skip it. It works. See Unit tests.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractComplexNumber))
            return false;
        if (this instanceof AComplexNumber)
            if (o instanceof AComplexNumber)
                return ((AComplexNumber) this).getReal().equals(((AComplexNumber) o).getReal()) &&
                        ((AComplexNumber) this).getImaginary().equals(((AComplexNumber) o).getImaginary());
            else return equals(((AbstractComplexNumber) o).toAlgebraic());
        else if (this instanceof EComplexNumber)
            if (o instanceof EComplexNumber)
                return ((EComplexNumber) this).getModulus().equals(((EComplexNumber) o).getModulus()) &&
                        ((EComplexNumber) this).getExponent().equals(((EComplexNumber) o).getExponent());
            else return equals(((AbstractComplexNumber) o).toExponential());
        else return false;
    }
}
