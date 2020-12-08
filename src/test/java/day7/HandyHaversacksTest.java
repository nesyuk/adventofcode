package day7;

import adventofcode2020.day7.HandyHaversacks;
import adventofcode2020.day7.HandyHaversacks.Bags;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class HandyHaversacksTest {

    @Test
    public void testLuggageProcessingRuleForTwoBags() {
        Pair<Bags, Set<Bags>> luggageProcessingRule = HandyHaversacks.parseLuggageProcessingRule(
                "light red bags contain 1 bright white bag, 2 muted yellow bags.");

        assertThat(luggageProcessingRule.getLeft().getColor(), is(equalTo("light red")));
        assertThat(luggageProcessingRule.getRight().size(), is(equalTo(2)));

        luggageProcessingRule.getRight().stream().forEach(contentBag -> {
            assertThat(contentBag.getColor(), anyOf(equalTo("bright white"), equalTo("muted yellow")));
            if (contentBag.getColor().equals("bright white"))
                assertThat(contentBag.getCount(), is(equalTo(1)));
            else
                assertThat(contentBag.getCount(), is(equalTo(2)));
        });
    }

    @Test
    public void testLuggageProcessingRuleForOneBag() {
        Pair<Bags, Set<Bags>> luggageProcessingRule = HandyHaversacks.parseLuggageProcessingRule(
                "bright white bags contain 1 shiny gold bag.");

        assertThat(luggageProcessingRule.getLeft().getColor(), is(equalTo("bright white")));
        assertThat(luggageProcessingRule.getRight().size(), is(equalTo(1)));

        luggageProcessingRule.getRight().stream().forEach(contentBag -> {
            assertThat(contentBag.getColor(), is(equalTo("shiny gold")));
            assertThat(contentBag.getCount(), is(equalTo(1)));
        });
    }

    @Test
    public void testLuggageProcessingRuleForEmptyBag() {
        Pair<Bags, Set<Bags>> luggageProcessingRule = HandyHaversacks.parseLuggageProcessingRule(
                "faded blue bags contain no other bags.");

        assertThat(luggageProcessingRule.getLeft().getColor(), is(equalTo("faded blue")));
        assertThat(luggageProcessingRule.getRight().size(), is(equalTo(0)));
    }

    @Test
    public void testCountParentBagsTestInput() throws FileNotFoundException {
        assertThat(HandyHaversacks.countParentBags("src/test/java/day7/test_input" ,"shiny gold"),
                is(equalTo(4)));
    }

    @Test
    public void testCountParentBags() throws FileNotFoundException {
        assertThat(HandyHaversacks.countParentBags("src/test/java/day7/input" ,"shiny gold"),
                is(equalTo(265)));
    }

    @Test
    public void testCountChildBagsTestInput() throws FileNotFoundException {
        assertThat(HandyHaversacks.countChildBags("src/test/java/day7/test_input" ,"shiny gold"),
                is(equalTo(32)));
    }

    @Test
    public void testCountChildBagsTestInput2() throws FileNotFoundException {
        assertThat(HandyHaversacks.countChildBags("src/test/java/day7/test_input2" ,"shiny gold"),
                is(equalTo(126)));
    }

    @Test
    public void testCountChildBags() throws FileNotFoundException {
        assertThat(HandyHaversacks.countChildBags("src/test/java/day7/input" ,"shiny gold"),
                is(equalTo(14177)));
    }
}
