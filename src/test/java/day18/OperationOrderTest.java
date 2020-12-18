package day18;

import adventofcode2020.day18.OperationOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class OperationOrderTest {

    @Test
    public void scanExpressionRules1TestInput() throws FileNotFoundException {
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input"),
                is(equalTo(BigInteger.valueOf(71))));
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input2"),
                is(equalTo(BigInteger.valueOf(51))));
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input3"),
                is(equalTo(BigInteger.valueOf(26))));
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input4"),
                is(equalTo(BigInteger.valueOf(437))));
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input5"),
                is(equalTo(BigInteger.valueOf(12240))));
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/test_input6"),
                is(equalTo(BigInteger.valueOf(13632))));
    }

    @Test
    public void scanExpressionTestInputRules2() throws FileNotFoundException {
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input"),
                is(equalTo(BigInteger.valueOf(231))));
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input2"),
                is(equalTo(BigInteger.valueOf(51))));
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input3"),
                is(equalTo(BigInteger.valueOf(46))));
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input4"),
                is(equalTo(BigInteger.valueOf(1445))));
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input5"),
                is(equalTo(BigInteger.valueOf(669060))));
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/test_input6"),
                is(equalTo(BigInteger.valueOf(23340))));
    }

    @Test
    public void scanExpression() throws FileNotFoundException {
        assertThat(OperationOrder.scanExpressionsRule1("src/test/java/day18/input"),
                is(equalTo(BigInteger.valueOf(650217205)
                        .multiply(BigInteger.valueOf(1000))
                        .add(BigInteger.valueOf(854)))));
    }

    @Test
    public void scanExpressionRule2() throws FileNotFoundException {
        assertThat(OperationOrder.scanExpressionsRule2("src/test/java/day18/input"),
                is(equalTo(new BigInteger("20394514442037"))));
    }
}
