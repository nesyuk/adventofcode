package day4;

import adventofcode2020.day4.PassportProcessing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(JUnit4.class)
public class PassportProcessingTest {

    @Test
    public void testBirthYear() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.byr;
        assertThat(dataField.isValid("1920"), is(true));
        assertThat(dataField.isValid("2000"), is(true));
        assertThat(dataField.isValid("2002"), is(true));

        assertThat(dataField.isValid("02002"), is(false));
        assertThat(dataField.isValid("20020"), is(false));
        assertThat(dataField.isValid("931"), is(false));
        assertThat(dataField.isValid("2020"), is(false));
        assertThat(dataField.isValid("abc2002"), is(false));
        assertThat(dataField.isValid("2002abc"), is(false));
        assertThat(dataField.isValid(""), is(false));
    }

    @Test
    public void testIssueYear() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.iyr;
        assertThat(dataField.isValid("2010"), is(true));
        assertThat(dataField.isValid("2015"), is(true));
        assertThat(dataField.isValid("2020"), is(true));

        assertThat(dataField.isValid("20200"), is(false));
        assertThat(dataField.isValid("02020"), is(false));
        assertThat(dataField.isValid("2030"), is(false));
        assertThat(dataField.isValid("2000"), is(false));
        assertThat(dataField.isValid("abc2020"), is(false));
        assertThat(dataField.isValid("2020abc"), is(false));
        assertThat(dataField.isValid(""), is(false));
    }

    @Test
    public void testExpirationYear() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.eyr;
        assertThat(dataField.isValid("2020"), is(true));
        assertThat(dataField.isValid("2025"), is(true));
        assertThat(dataField.isValid("2030"), is(true));

        assertThat(dataField.isValid("02030"), is(false));
        assertThat(dataField.isValid("20300"), is(false));
        assertThat(dataField.isValid("2000"), is(false));
        assertThat(dataField.isValid("abc2020"), is(false));
        assertThat(dataField.isValid("2020abc"), is(false));
        assertThat(dataField.isValid(""), is(false));
    }

    @Test
    public void testHeight() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.hgt;
        assertThat(dataField.isValid("150cm"), is(true));
        assertThat(dataField.isValid("165cm"), is(true));
        assertThat(dataField.isValid("193cm"), is(true));

        assertThat(dataField.isValid("69cm"), is(false));
        assertThat(dataField.isValid("76cm"), is(false));
        assertThat(dataField.isValid("200cm"), is(false));
        assertThat(dataField.isValid("cm"), is(false));

        assertThat(dataField.isValid("59in"), is(true));
        assertThat(dataField.isValid("65in"), is(true));
        assertThat(dataField.isValid("76in"), is(true));

        assertThat(dataField.isValid("5in"), is(false));
        assertThat(dataField.isValid("500in"), is(false));
        assertThat(dataField.isValid("in"), is(false));

        assertThat(dataField.isValid("m"), is(false));
        assertThat(dataField.isValid("150m"), is(false));
        assertThat(dataField.isValid("150c"), is(false));
        assertThat(dataField.isValid("59i"), is(false));
    }

    @Test
    public void testHairColor() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.hcl;
        assertThat(dataField.isValid("#123456"), is(true));
        assertThat(dataField.isValid("#abcdef"), is(true));
        assertThat(dataField.isValid("#123abc"), is(true));
        assertThat(dataField.isValid("#abc123"), is(true));
        assertThat(dataField.isValid("#a1b2c3"), is(true));
        assertThat(dataField.isValid("#1a2b3c"), is(true));

        assertThat(dataField.isValid("#"), is(false));
        assertThat(dataField.isValid("123456"), is(false));
        assertThat(dataField.isValid("abcdef"), is(false));
        assertThat(dataField.isValid("#1a2b3g"), is(false));
        assertThat(dataField.isValid("#g23456"), is(false));
        assertThat(dataField.isValid("#123g56"), is(false));
        assertThat(dataField.isValid("#12345"), is(false));
        assertThat(dataField.isValid("#1234567"), is(false));
    }

    @Test
    public void testEyeColor() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.ecl;
        List<String> validValues = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        validValues.forEach(v -> assertThat(dataField.isValid(v), is(true)));

        validValues.forEach(v -> assertThat(dataField.isValid(v + "z"), is(false)));
        validValues.forEach(v -> assertThat(dataField.isValid("z" + v), is(false)));
        validValues.forEach(v -> assertThat(dataField.isValid("z" + v + "w"), is(false)));
    }

    @Test
    public void testPassportId() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.pid;
        assertThat(dataField.isValid("000000000"), is(true));
        assertThat(dataField.isValid("123456789"), is(true));
        assertThat(dataField.isValid("000006789"), is(true));

        assertThat(dataField.isValid("00000000"), is(false));
        assertThat(dataField.isValid("0000000000"), is(false));
        assertThat(dataField.isValid("abcdefghi"), is(false));
    }

    @Test
    public void testCountryId() {
        PassportProcessing.DataField dataField = PassportProcessing.DataField.cid;
        assertThat(dataField.isValid(""), is(true));
        assertThat(dataField.isValid("abracatabra"), is(true));
        assertThat(dataField.isValid("123456789"), is(true));
        assertThat(dataField.isValid(null), is(true));
    }

    @Test
    public void testValidRecordsCount() throws FileNotFoundException {
        assertThat(PassportProcessing.countValidRecords("src/test/java/day4/valid"),
                is(equalTo(4)));
    }

    @Test
    public void testInvalidRecordsCount() throws FileNotFoundException {
        assertThat(PassportProcessing.countValidRecords("src/test/java/day4/invalid"),
                is(equalTo(0)));
    }

    @Test
    public void testPassportProcessing() throws FileNotFoundException {
        assertThat(PassportProcessing.countValidRecords("src/test/java/day4/input"),
                is(equalTo(140)));
    }
}
