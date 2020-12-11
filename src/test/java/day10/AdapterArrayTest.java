package day10;

import adventofcode2020.day10.AdapterArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class AdapterArrayTest {

    @Test
    public void testProductOfDiffsInput1() throws FileNotFoundException {
        assertThat(AdapterArray.productOfMinMaxVoltageDiff("src/test/java/day10/test_input"),
                is(equalTo(35)));
    }

    @Test
    public void testProductOfDiffsInput2() throws FileNotFoundException {
        assertThat(AdapterArray.productOfMinMaxVoltageDiff("src/test/java/day10/test_input2"),
                is(equalTo(220)));
    }

    @Test
    public void testProductOfDiffsInput() throws FileNotFoundException {
        assertThat(AdapterArray.productOfMinMaxVoltageDiff("src/test/java/day10/input"),
                is(equalTo(2414)));
    }

    @Test
    public void testConnectionWaysTestInput() throws FileNotFoundException {
        assertThat(AdapterArray.distinctConnectionWays("src/test/java/day10/test_input"),
                is(equalTo(8L)));
    }

    @Test
    public void testConnectionWaysTestInput2() throws FileNotFoundException {
        assertThat(AdapterArray.distinctConnectionWays("src/test/java/day10/test_input2"),
                is(equalTo(19208L)));
    }

    @Test
    public void testConnectionWaysInput() throws FileNotFoundException {
        assertThat(AdapterArray.distinctConnectionWays("src/test/java/day10/input"),
                is(equalTo(21156911906816L)));
    }
}