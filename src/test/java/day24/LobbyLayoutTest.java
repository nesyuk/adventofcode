package day24;

import adventofcode2020.day24.LobbyLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class LobbyLayoutTest {

    @Test
    public void flipTilesTestInput() throws FileNotFoundException {
        assertThat(LobbyLayout.flipAndCountTiles("src/test/java/day24/test_input"), is(equalTo(10)));
    }

    @Test
    public void flipTiles() throws FileNotFoundException {
        assertThat(LobbyLayout.flipAndCountTiles("src/test/java/day24/input"), is(equalTo(282)));
    }

    @Test
    public void flipTilesForArtExhibitionTestInput() throws FileNotFoundException {
        assertThat(LobbyLayout.flipAndCountTilesForArtExhibition("src/test/java/day24/test_input"), is(equalTo(2208)));
    }

    @Test
    public void flipTilesForArtExhibition() throws FileNotFoundException {
        assertThat(LobbyLayout.flipAndCountTilesForArtExhibition("src/test/java/day24/input"), is(equalTo(3445)));
    }

}
