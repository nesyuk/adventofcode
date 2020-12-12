package day12;

import adventofcode2020.day12.RainRisk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class RainRiskTest {

    @Test
    public void testManhattanDistanceTestInput() throws FileNotFoundException {
        assertThat(RainRisk.moveShip("src/test/java/day12/test_input"), is(equalTo(25L)));
    }

    @Test
    public void testManhattanDistance() throws FileNotFoundException {
        assertThat(RainRisk.moveShip("src/test/java/day12/input"), is(equalTo(521L)));
    }

    @Test
    public void testManhattanDistanceWithWayPointTestInput() throws FileNotFoundException {
        assertThat(RainRisk.moveWayPoint("src/test/java/day12/test_input"), is(equalTo(286L)));
    }

    @Test
    public void testManhattanWithWayPointDistance() throws FileNotFoundException {
        assertThat(RainRisk.moveWayPoint("src/test/java/day12/input"), is(equalTo(22848L)));
    }
}
