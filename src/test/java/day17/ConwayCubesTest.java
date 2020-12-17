package day17;

import adventofcode2020.day17.ConwayCubes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ConwayCubesTest {

    @Test
    public void test3D() throws FileNotFoundException {
        assertThat(ConwayCubes.runSixCircles("src/test/java/day17/test_input", 3),
                is(equalTo(112)));
        assertThat(ConwayCubes.runSixCircles("src/test/java/day17/input", 3),
                is(equalTo(298)));
    }

    @Test
    public void test4DTestInput() throws FileNotFoundException {
        assertThat(ConwayCubes.runSixCircles("src/test/java/day17/test_input", 4),
                is(equalTo(848)));
    }

    @Test
    public void test4D() throws FileNotFoundException {
        assertThat(ConwayCubes.runSixCircles("src/test/java/day17/input", 4),
                is(equalTo(1792)));
    }
}
