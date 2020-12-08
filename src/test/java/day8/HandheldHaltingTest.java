package day8;

import adventofcode2020.day8.HandheldHalting;
import adventofcode2020.day8.HandheldHalting.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class HandheldHaltingTest {

    @Test
    public void testCommandParsing() {
        Command command = Command.parse("nop +0");
        assertThat(command.getType(), is(equalTo(Command.CommandType.nop)));
        assertThat(command.getArg(), is(equalTo(0)));
        assertThat(command.isExecuted(), is(equalTo(false)));

        command = Command.parse("acc +1");
        assertThat(command.getType(), is(equalTo(Command.CommandType.acc)));
        assertThat(command.getArg(), is(equalTo(1)));
        assertThat(command.isExecuted(), is(equalTo(false)));

        command = Command.parse("jmp +4");
        assertThat(command.getType(), is(equalTo(Command.CommandType.jmp)));
        assertThat(command.getArg(), is(equalTo(4)));
        assertThat(command.isExecuted(), is(equalTo(false)));

        command = Command.parse("jmp -3");
        assertThat(command.getType(), is(equalTo(Command.CommandType.jmp)));
        assertThat(command.getArg(), is(equalTo(-3)));
        assertThat(command.isExecuted(), is(equalTo(false)));
    }

    @Test
    public void testRunBootCodeTestInput() throws FileNotFoundException {
        assertThat(HandheldHalting.runBootCode("src/test/java/day8/test_input"), is(equalTo(5)));
    }

    @Test
    public void testFixAndRunBootCodeTestInput() throws FileNotFoundException {
        assertThat(HandheldHalting.fixAndRunBootCode("src/test/java/day8/test_input"), is(equalTo(8)));
    }

    @Test
    public void testRunBootCode() throws FileNotFoundException {
        assertThat(HandheldHalting.runBootCode("src/test/java/day8/input"), is(equalTo(1594)));
    }

    @Test
    public void testFixAndRunBootCode() throws FileNotFoundException {
        assertThat(HandheldHalting.fixAndRunBootCode("src/test/java/day8/input"), is(equalTo(758)));
    }
}
