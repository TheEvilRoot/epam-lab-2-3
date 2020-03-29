import com.theevilroot.epam.lab23.model.numbers.AComplexNumber;
import com.theevilroot.epam.lab23.model.numbers.AbstractComplexNumber;
import com.theevilroot.epam.lab23.model.numbers.EComplexNumber;
import org.junit.Assert;
import org.junit.Test;

public class CNTests {

    @Test
    public void testConversion() {
        AComplexNumber acn = new AComplexNumber(2d, -3d);
        EComplexNumber ecn = acn.toExponential();
        AComplexNumber aacn = ecn.toAlgebraic();

        Assert.assertEquals(aacn.getReal(), acn.getReal(), 0.05);
        Assert.assertEquals(aacn.getImaginary(), acn.getImaginary(), 0.05);
    }

    @Test
    public void testEquals() {
        AbstractComplexNumber acn = new AComplexNumber(2d, -3d);
        AbstractComplexNumber ecn = acn.toExponential();
        AbstractComplexNumber aacn = ecn.toAlgebraic();

        AbstractComplexNumber excn = new EComplexNumber(16d, 0.3d);
        AbstractComplexNumber aexcn = excn.toAlgebraic();
        AbstractComplexNumber aaexcn = aexcn.toAlgebraic();

        Assert.assertEquals(acn, aacn);
        Assert.assertEquals(aaexcn, excn);
    }

}
