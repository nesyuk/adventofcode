package day15;

import adventofcode2020.day15.RambunctiousRecitation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class RambunctiousRecitationTest {

    @Test
    public void testPlayMemoGame() throws FileNotFoundException {
        //18,8,0,5,4,1,20
        assertThat(RambunctiousRecitation.playMemoGame(List.of(0,3,6), 2020), equalTo(436));
        assertThat(RambunctiousRecitation.playMemoGame(List.of(18,8,0,5,4,1,20), 2020), equalTo(253));
        assertThat(RambunctiousRecitation.playMemoGame(List.of(18,8,0,5,4,1,20), 30000000), equalTo(13710));
    }
}
