package day5;

import adventofcode2020.day5.BinaryBoarding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class BinaryBoardingTest {

    String path = "src/test/java/day5/input";

    @Test
    public void testSeatId() {
        assertThat(BinaryBoarding.seatId("FBFBBFFRLR", 128, 8),
                is(equalTo(357)));

        assertThat(BinaryBoarding.seatId("BFFFBBFRRR", 128, 8),
                is(equalTo(567)));

        assertThat(BinaryBoarding.seatId("FFFBBBFRRR", 128, 8),
                is(equalTo(119)));

        assertThat(BinaryBoarding.seatId("BBFFBBFRLL", 128, 8),
                is(equalTo(820)));
    }

    @Test
    public void testHighestId() throws FileNotFoundException {
        assertThat(BinaryBoarding.highestSeatId(path, 128, 8),
                is(equalTo(922)));
    }

    @Test
    public void testMissingId() throws FileNotFoundException {
        assertThat(BinaryBoarding.missingSeatId(path, 128, 8),
                is(equalTo(747)));
    }
}
