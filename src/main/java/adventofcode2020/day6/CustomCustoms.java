package adventofcode2020.day6;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class CustomCustoms {

    public static class GroupAnswers {
        Set<Integer> unionAnswers = new HashSet();
        Set<Integer> intersectionAnswers;

        public GroupAnswers(@NonNull String inputStr) {
            Pattern pattern = Pattern.compile("(\\w+)");
            Matcher patternMatcher = pattern.matcher(inputStr);
            while(patternMatcher.find()) {
                String personInput = patternMatcher.group(1);
                Set<Integer> personAnswers = personInput.chars().boxed().collect(toSet());
                unionAnswers.addAll(personAnswers);
                if (intersectionAnswers == null)
                    intersectionAnswers = personAnswers;
                else {
                    intersectionAnswers.retainAll(personAnswers);
                }
            }
        }

        private Set<Character> intToChar(Set<Integer> setOfInts) {
            return setOfInts.stream()
                    .map(i -> Character.forDigit(i, 10))
                    .collect(Collectors.toSet());
        }

        public Set<Character> unitAnswers() {
            return intToChar(unionAnswers);
        }

        public int unitAnswersCount() {
            return unionAnswers.size();
        }

        public Set<Character> intersectionAnswers() {
            return intToChar(intersectionAnswers);
        }

        public int intersectionAnswersCount() {
            return intersectionAnswers.size();
        }
    }

    public static long countUnionAnswersSum(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        long sumOfAnswers = 0;
        while (scanner.hasNext())
            sumOfAnswers += new GroupAnswers(scanner.next()).unitAnswersCount();

        return sumOfAnswers;
    }

    public static long countIntersectionAnswersSum(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        long sumOfAnswers = 0;
        while (scanner.hasNext())
            sumOfAnswers += new GroupAnswers(scanner.next()).intersectionAnswersCount();

        return sumOfAnswers;
    }
}
