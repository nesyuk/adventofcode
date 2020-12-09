package day9;

import adventofcode2020.day9.EncodingError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class EncodingErrorTest {

    @Test
    public void testPreambleSum() {
        List<Long> preamble = LongStream.rangeClosed(1L, 25L).boxed().collect(Collectors.toList());

        assertThat(EncodingError.hasSum(preamble, 49L), is(true));
        assertThat(EncodingError.hasSum(preamble, 26L), is(true));
        assertThat(EncodingError.hasSum(preamble, 50L), is(false));
        assertThat(EncodingError.hasSum(preamble, 100L), is(false));

        preamble.set(19, 45L);

        assertThat(EncodingError.hasSum(preamble, 26L), is(true));
        assertThat(EncodingError.hasSum(preamble, 65L), is(false));
        assertThat(EncodingError.hasSum(preamble, 64L), is(true));
        assertThat(EncodingError.hasSum(preamble, 66L), is(true));
    }

    @Test
    public void testFindErrorTestInputPreamble5() throws FileNotFoundException {
        assertThat(EncodingError.findError("src/test/java/day9/test_input_5", 5),
                is(equalTo(127L)));
    }

    @Test
    public void testFindSlidingWindowSumInputPreamble5() throws FileNotFoundException {
        assertThat(EncodingError.findSum("src/test/java/day9/test_input_5", 5),
                is(equalTo(62L)));
    }

    @Test
    public void testFindError() throws FileNotFoundException {
        assertThat(EncodingError.findError("src/test/java/day9/input", 25),
                is(equalTo(248131121L)));
    }

    @Test
    public void testFindSlidingWindowSum() throws FileNotFoundException {
        assertThat(EncodingError.findSum("src/test/java/day9/input", 25),
                is(equalTo(31580383L)));
    }
}
