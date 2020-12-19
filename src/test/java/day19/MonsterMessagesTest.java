package day19;

import adventofcode2020.day19.MonsterMessages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MonsterMessagesTest {

    @Test
    public void testNoLoop() throws FileNotFoundException {
        assertThat(MonsterMessages.countValid("src/test/java/day19/input"), is(182L));
    }

    @Test
    public void testLoop() throws FileNotFoundException {
        assertThat(MonsterMessages.countValid("src/test/java/day19/input2"), is(334L));
    }
}
