package day2;

import adventofcode2020.day2.PasswordPhilosophy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class PasswordPhilosophyTest {

    @Test
    public void test() throws FileNotFoundException {
        final String path = "src/test/java/day2/input";
        assertThat(PasswordPhilosophy.countValidOldPolicyRecords(path),
                is(equalTo(418)));
        assertThat(PasswordPhilosophy.countValidNewPolicyRecords(path),
                is(equalTo(616)));
    }
}
