package day14;

import adventofcode2020.day14.DockingData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DockingDataTest {

    @Test
    public void testApplyValueMaskTestInput() throws FileNotFoundException {
        assertThat(DockingData.applyValueBinaryMask("src/test/java/day14/test_input"), is(equalTo(165L)));
    }

    @Test
    public void testApplyKeyMaskTestInput() throws FileNotFoundException {
        assertThat(DockingData.applyKeyBinaryMask("src/test/java/day14/test_input2"), is(equalTo(208L)));
    }

    @Test
    public void testApplyValueBinaryMask() throws FileNotFoundException {
        assertThat(DockingData.applyValueBinaryMask("src/test/java/day14/input"), is(equalTo(10885823581193L)));
    }

    @Test
    public void testApplyKeyBinaryMask() throws FileNotFoundException {
        assertThat(DockingData.applyKeyBinaryMask("src/test/java/day14/input"), is(equalTo(3816594901962L)));
    }
}
