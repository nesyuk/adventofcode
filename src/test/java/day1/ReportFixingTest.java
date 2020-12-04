package day1;

import adventofcode2020.day1.ReportFixing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ReportFixingTest {

    @Test
    public void test() throws FileNotFoundException {
        final String path = "src/test/java/day1/input";
        assertThat(ReportFixing.productOfTwoNumbers(path),
                is(equalTo(918339)));
        assertThat(ReportFixing.productOfThreeNumbers(path),
                is(equalTo(23869440)));
    }
}
