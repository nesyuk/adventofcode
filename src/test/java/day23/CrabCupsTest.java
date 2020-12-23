package day23;

import adventofcode2020.day23.CrabCups;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class CrabCupsTest {
    List<Integer> testNumbers = List.of(3,8,9,1,2,5,4,6,7);
    List<Integer> numbers = List.of(5,9,8,1,6,2,7,3,4);

    @Test
    public void testPlayTestInput() {
        assertThat(CrabCups.play(testNumbers, 10), is(equalTo("92658374")));
        assertThat(CrabCups.play(testNumbers, 100), is(equalTo("67384529")));
    }

    @Test
    public void testPlay() {
        assertThat(CrabCups.play(numbers, 10), is(equalTo("24876359")));
        assertThat(CrabCups.play(numbers, 100), is(equalTo("32658947")));
    }

    @Test
    public void testPlayMillionTestInput() {
        List<Integer> cups = new ArrayList<>();
        cups.addAll(testNumbers);
        IntStream.range(10, (int) (Math.pow(10, 6) + 1)).forEach(cups::add);
        assertThat(CrabCups.playMillion(cups, (int) Math.pow(10, 7)),
                is(equalTo("149245887792")));
    }

    @Test
    public void testPlayMillion() {
        List<Integer> cups = new ArrayList<>();
        cups.addAll(numbers);
        IntStream.range(10, (int) (Math.pow(10, 6) + 1)).forEach(cups::add);
        assertThat(CrabCups.playMillion(cups, (int) Math.pow(10, 7)),
                is(equalTo("683486010900")));
    }

}
