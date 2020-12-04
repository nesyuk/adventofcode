package test.java.day3;

import adventofcode2020.day3.TobogganTrajectory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class TobogganTrajectoryTest {

    @Test
    public void test() throws FileNotFoundException {
        final String path = "src/test/java/day3/input";
        assertThat(TobogganTrajectory.treesCount(path, Pair.of(3, 1)), is(equalTo(274L)));
        assertThat(TobogganTrajectory.productOfTreesCount(path, List.of(Pair.of(1, 1), Pair.of(3, 1), Pair.of(5, 1), Pair.of(7, 1), Pair.of(1, 2))),
                is(equalTo(6050183040L)));
    }
}
