package adventofcode2020.day4;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PassportProcessing {

    public enum DataField {

        byr("Birth Year", true),
        iyr("Issue Year", true),
        eyr("Expiration Year", true),
        hgt("Height", true),
        hcl("Hair Color", true),
        ecl("Eye Color", true),
        pid("Passport Id", true),
        cid("Country Id", false);

        private String fieldName;
        private boolean isMandatory;

        private static final Pattern yearPattern = Pattern.compile("^([0-9]{4})$");
        private static final Pattern hairColorPattern = Pattern.compile("^#[0-9a-f]{6}$");
        private static final Pattern pidPattern = Pattern.compile("^([0-9]{9})$");
        private static final List<String> eyeColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

        DataField(String fieldName, boolean isMandatory) {
            this.fieldName = fieldName;
            this.isMandatory = isMandatory;
        }

        private Integer parseInt(String intValue) {
            try {
                return Integer.parseInt(intValue);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        private boolean isWithinInterval(Integer value, int min, int max) {
            return value != null && (value >= min && value <= max);
        }

        public boolean isValid(String value) {
            if ((value == null || value.isEmpty()) && this != cid)
                return false;

            switch (this) {
                case byr:
                    return yearPattern.matcher(value).find() &&
                            isWithinInterval(parseInt(value), 1920, 2002);
                case iyr:
                    return yearPattern.matcher(value).find() &&
                            isWithinInterval(parseInt(value), 2010, 2020);
                case eyr:
                    return yearPattern.matcher(value).find() &&
                            isWithinInterval(parseInt(value), 2020, 2030);
                case hgt:
                    if (value.endsWith("cm"))
                        return isWithinInterval(
                                parseInt(value.replace("cm", "")),
                                150, 193);
                    else if (value.endsWith("in"))
                        return isWithinInterval(
                                parseInt(value.replace("in", "")),
                                59, 76);
                    return false;
                case hcl:
                    return hairColorPattern.matcher(value).find();
                case ecl:
                    return eyeColors.stream().anyMatch(color -> color.equals(value));
                case pid:
                    return pidPattern.matcher(value).find();
                case cid:
                    return true;
                default:
                    return false;
            }
        }
    }

    static class PassportData {
        Map<DataField, String> data = Arrays.stream(DataField.values())
                .collect(Collectors.toMap(Function.identity(), (v) -> ""));
        final static Pattern keyValuePattern = Pattern.compile("(\\w+):([^\\s]*)");

        PassportData(String dataString) {
            Matcher matcher = keyValuePattern.matcher(dataString);
            while (matcher.find())
                data.put(DataField.valueOf(matcher.group(1)), matcher.group(2));
        }

        boolean isValid() {
            return data.entrySet().stream()
                    .filter(e -> (e.getKey().isMandatory && !e.getKey().isValid(e.getValue())))
                    .findAny()
                    .isEmpty();
        }
    }

    public static int countValidRecords(@NotNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        int validRecords = 0;
        while (scanner.hasNext())
            if (new PassportData(scanner.next()).isValid())
                validRecords++;

        return validRecords;
    }
}
