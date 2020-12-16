package day16;

import adventofcode2020.day16.TicketTranslation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class TicketTranslationTest {

    @Test
    public void testInvalidTickets() throws FileNotFoundException {
        assertThat(TicketTranslation.countInvalidTickets("src/test/java/day16/test_input"),
                is(equalTo(71L)));
        assertThat(TicketTranslation.countInvalidTickets("src/test/java/day16/input"),
                is(equalTo(21978L)));
    }

    @Test
    public void testTicketFields() throws FileNotFoundException {
        assertThat(TicketTranslation.findTicketFields("src/test/java/day16/test_input2", "seat"),
                is(equalTo(BigInteger.valueOf(13))));
        assertThat(TicketTranslation.findTicketFields("src/test/java/day16/input", "departure"),
                is(equalTo(BigInteger.valueOf(1053686852011L))));//1419864491
    }
}
