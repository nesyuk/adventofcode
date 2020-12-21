package day21;

import adventofcode2020.day21.AllergenAssessment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class AllergenAssessmentTest {

    @Test
    public void testCountIngredientsWithoutAllergensTestInput() throws FileNotFoundException {
        assertThat(AllergenAssessment.findIngredients("src/test/java/day21/test_input"), is(equalTo(5)));
    }

    @Test
    public void testCountIngredientsWithAllergensTestInput() throws FileNotFoundException {
        assertThat(AllergenAssessment.findIngredientsWithAllergens("src/test/java/day21/test_input"), is(equalTo("mxmxvkd,sqjhc,fvjkl")));
    }

    @Test
    public void testCountIngredientsWithoutAllergens() throws FileNotFoundException {
        assertThat(AllergenAssessment.findIngredients("src/test/java/day21/input"), is(equalTo(2724)));
    }

    @Test
    public void testCountIngredientsWithAllergens() throws FileNotFoundException {
        assertThat(AllergenAssessment.findIngredientsWithAllergens("src/test/java/day21/input"), is(equalTo("mxmxvkd,sqjhc,fvjkl")));
    }
}
