package adventofcode2020.day2;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPhilosophy {
    static final Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

    static class ParsedEntry {
        int num1;
        int num2;
        char aChar;
        String aPassword;

        private ParsedEntry(int num1, int num2, char aChar, String aPassword) {
            this.num1 = num1;
            this.num2 = num2;
            this.aChar = aChar;
            this.aPassword = aPassword;
        }

        public static ParsedEntry fromString(String policyPasswordString) {
            Matcher matcher = pattern.matcher(policyPasswordString);
            if (matcher.find()) {
                return new ParsedEntry(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        matcher.group(3).charAt(0),
                        matcher.group(4));
            }
            return null;
        }

        public boolean confirmsOldPolicy() {
            long count = this.aPassword.chars().filter(c -> c == this.aChar).count();
            return count <= this.num2 && count >= this.num1;
        }

        public boolean confirmsNewPolicy() {
            return (this.aPassword.charAt(this.num1-1) == this.aChar) ^
                    (this.aPassword.charAt(this.num2-1) == this.aChar);
        }

        @Override
        public String toString() {
            return "ParsedEntry{" +
                    "num1=" + num1 +
                    ", num2=" + num2 +
                    ", aChar=" + aChar +
                    ", aPassword='" + aPassword + '\'' +
                    '}';
        }
    }

    public static int countValidOldPolicyRecords(@NotNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        int validPolicyCounter = 0;
        while(scanner.hasNextLine()) {
            ParsedEntry entry = ParsedEntry.fromString(scanner.nextLine());
            if (entry == null)
                throw new IllegalArgumentException();
            if (entry.confirmsOldPolicy())
                validPolicyCounter++;
        }

        return validPolicyCounter;
    }

    public static int countValidNewPolicyRecords(@NotNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        int validPolicyCounter = 0;
        while(scanner.hasNextLine()) {
            ParsedEntry entry = ParsedEntry.fromString(scanner.nextLine());
            if (entry == null)
                throw new IllegalArgumentException();
            if (entry.confirmsNewPolicy())
                validPolicyCounter++;
        }

        return validPolicyCounter;
    }
}
