package day20;

import adventofcode2020.day20.JurassicJigsaw;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class JurassicJigsawTest {

    @Test
    public void testCornerTiles() {
        assertThat(JurassicJigsaw.findCornerTiles("src/test/java/day20/input"), equalTo(new BigInteger("14129524957217")));
    }

    @Test
    public void testCornerTilesTestInput() {
        assertThat(JurassicJigsaw.findCornerTiles("src/test/java/day20/test_input"), equalTo(new BigInteger("20899048083289")));
    }

    @Test
    public void testMonsterTiles() {
        assertThat(JurassicJigsaw.findMonsterTiles("src/test/java/day20/input"), equalTo(1649));
    }

    @Test
    public void testMonsterTilesTestInput() {
        assertThat(JurassicJigsaw.findMonsterTiles("src/test/java/day20/test_input"), equalTo(273));
    }
}
