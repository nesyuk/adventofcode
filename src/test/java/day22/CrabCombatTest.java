package day22;

import adventofcode2020.day22.CrabCombat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class CrabCombatTest {

    @Test
    public void testPlayTestInput() throws FileNotFoundException {
        assertThat(CrabCombat.play("src/test/java/day22/test_input"), is(equalTo(306)));
    }

    @Test
    public void testRecursivePlayTestInput() throws FileNotFoundException {
        assertThat(CrabCombat.playRecursively("src/test/java/day22/test_input"), is(equalTo(291)));
    }

    @Test
    public void testRecursivePlay() throws FileNotFoundException {
        assertThat(CrabCombat.playRecursively("src/test/java/day22/input"), is(equalTo(30498)));
    }

    @Test
    public void testPlay() throws FileNotFoundException {
        assertThat(CrabCombat.play("src/test/java/day22/input"), is(equalTo(32179)));
    }
}
