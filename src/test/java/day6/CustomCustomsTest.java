package day6;

import adventofcode2020.day6.CustomCustoms;
import adventofcode2020.day6.CustomCustoms.GroupAnswers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class CustomCustomsTest {

    @Test
    public void testCountAnswersTestInput() throws FileNotFoundException {
        assertThat(CustomCustoms.countUnionAnswersSum("src/test/java/day6/test_input"),
                is(equalTo(11L)));

        assertThat(CustomCustoms.countIntersectionAnswersSum("src/test/java/day6/test_input"),
                is(equalTo(6L)));
    }

    @Test
    public void testCountAnswers() throws FileNotFoundException {
        assertThat(CustomCustoms.countUnionAnswersSum("src/test/java/day6/input"),
                is(equalTo(6416L)));

        assertThat(CustomCustoms.countIntersectionAnswersSum("src/test/java/day6/input"),
                is(equalTo(3050L)));
    }

    @Test
    public void testGroupAnswersCount() {
        GroupAnswers groupAnswers = new GroupAnswers("abc");
        assertThat(groupAnswers.unitAnswersCount(), is(equalTo(3)));
        assertThat(groupAnswers.intersectionAnswersCount(), is(equalTo(3)));

        groupAnswers = new GroupAnswers("a\n" + "b\n" + "c\n");
        assertThat(groupAnswers.unitAnswersCount(), is(equalTo(3)));
        assertThat(groupAnswers.intersectionAnswersCount(), is(equalTo(0)));

        groupAnswers = new GroupAnswers("ab\n" + "ac");

        assertThat(groupAnswers.unitAnswersCount(), is(equalTo(3)));
        assertThat(groupAnswers.intersectionAnswersCount(), is(equalTo(1)));

        groupAnswers = new GroupAnswers("a\n" + "a\n" + "a\n" + "a");
        assertThat(groupAnswers.unitAnswersCount(), is(equalTo(1)));
        assertThat(groupAnswers.intersectionAnswersCount(), is(equalTo(1)));

        groupAnswers = new GroupAnswers("b\n");
        assertThat(groupAnswers.unitAnswersCount(), is(equalTo(1)));
        assertThat(groupAnswers.intersectionAnswersCount(), is(equalTo(1)));
    }
}
