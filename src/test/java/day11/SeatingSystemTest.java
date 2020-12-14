package day11;

import adventofcode2020.day11.SeatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class SeatingSystemTest {

    @Test
    public void testCountSeatsTestInput() throws FileNotFoundException {
        assertThat(SeatingSystem
                .countSeats("src/test/java/day11/test_input", 4, true),
                is(equalTo(37L)));
    }

    @Test
    public void testCountSeats() throws FileNotFoundException {
        assertThat(SeatingSystem
                        .countSeats("src/test/java/day11/input", 4, true),
                is(equalTo(2261L)));
    }

    @Test
    public void testCountSeatsFirstSeenTestInput() throws FileNotFoundException {
        assertThat(SeatingSystem
                        .countSeats("src/test/java/day11/test_input_2", 5, false),
                is(equalTo(26L)));
    }

    @Test
    public void testCountSeatsFirstSeen() throws FileNotFoundException {
        assertThat(SeatingSystem
                        .countSeats("src/test/java/day11/input", 5, false),
                is(equalTo(2039L)));
    }

    @Test
    public void testCountFirstSeen() throws FileNotFoundException {
        assertThat(SeatingSystem.firstNeighbourCount(
                SeatingSystem.readGrid("src/test/java/day11/input_seen_0"), 3, 3, false),
                is(equalTo(0)));

        assertThat(SeatingSystem.firstNeighbourCount(
                SeatingSystem.readGrid("src/test/java/day11/input_seen_1"), 1, 1, false),
                is(equalTo(0)));

        assertThat(SeatingSystem.firstNeighbourCount(
                SeatingSystem.readGrid("src/test/java/day11/input_seen_8"), 4, 3, false),
                is(equalTo(8)));
    }
}
