package day25;

import adventofcode2020.day25.ComboBreaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ComboBreakerTest {

    @Test
    public void testEncryptionKeyTestInput() {
        assertThat(ComboBreaker.findEncryptionKey(5764801, 17807724, 7),
                is(equalTo(BigInteger.valueOf(14897079))));
    }

    @Test
    public void testEncryptionKey() {
        assertThat(ComboBreaker.findEncryptionKey(335121, 363891, 7),
                is(equalTo(BigInteger.valueOf(9420461))));
    }
}
