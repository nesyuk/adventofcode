package day13;

import adventofcode2020.day13.ShuttleSearch;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ShuttleSearchTest {

    @Test
    public void findClosestBusTestInput() throws FileNotFoundException {
        assertThat(ShuttleSearch.findClosestBus("src/test/java/day13/test_input"), is(equalTo(295)));
    }

    @Test
    public void findClosestBus() throws FileNotFoundException {
        assertThat(ShuttleSearch.findClosestBus("src/test/java/day13/input"), is(equalTo(3606)));
    }

    @Test
    public void findBusSequenceTestInput() throws FileNotFoundException {
        assertThat(ShuttleSearch.findBusSequence("src/test/java/day13/test_input"), is(equalTo(BigInteger.valueOf(1068781))));
    }

    @Test
    public void findBusSequence() throws FileNotFoundException {
        BigInteger res = BigInteger.valueOf(37978635).multiply(BigInteger.valueOf(10000000)).add(BigInteger.valueOf(8533423));
        assertThat(ShuttleSearch.findBusSequence("src/test/java/day13/input"), is(equalTo(res)));
    }
}
