package adventofcode2020.day19;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MonsterMessages {

    static final Pattern stringPattern = Pattern.compile("[ab]+");
    static final Pattern rulePattern = Pattern.compile("(\\d+): ([\\d+\\|\\s\\w+\"]*)");

    public static String makeRules(Map<Integer, String> rules, Map<Integer, String> validRuleStrings, int ruleIdx) {
        if (validRuleStrings.containsKey(ruleIdx))
            return validRuleStrings.get(ruleIdx);

        String ruleStr = rules.get(ruleIdx);
        Matcher stringMatcher = stringPattern.matcher(ruleStr);
        if (stringMatcher.find())
            return ruleStr;

        String[] ruleParts = rules.get(ruleIdx).split("\\|");

        List<String> validStrings = new ArrayList<>();

        List<Integer> loop = null;
        for (String rulePart : ruleParts) {
            List<Integer> ruleRefs = Arrays.stream(rulePart.trim().split("\\s"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (ruleRefs.contains(ruleIdx) && !validRuleStrings.containsKey(ruleIdx)) {
                loop = ruleRefs;
                continue;
            }
                validStrings.add(ruleRefs.stream()
                        .map(ref -> makeRules(rules, validRuleStrings, ref))
                        .collect(Collectors.joining("")));
        }

        if (loop != null) {
            validStrings = new ArrayList<>();
            if (ruleIdx == 8) {
                String rule42 = makeRules(rules, validRuleStrings, 42);
                validStrings.add(rule42);
                validStrings.add("(?:" + rule42 + ")+");

            } else if (ruleIdx == 11) {
                String rule42 = makeRules(rules, validRuleStrings, 42);
                String rule31 = makeRules(rules, validRuleStrings, 31);
                for (int i = 1; i < 50; i++) {
                    validStrings.add("(?:" + rule42 + "{" + i + "}" + rule31 + "{" + i + "}" + ")");
                }
            }
        }
        String validString = "(?:" + String.join("|", validStrings) + ")";
        validRuleStrings.put(ruleIdx, validString);

        return validString;
    }

    public static long countValid(@NonNull String inputFileString) throws FileNotFoundException {
        Scanner scanner = new Scanner(
                Path.of(inputFileString).toFile());
        Map<Integer, String> rules = new HashMap<>();
        List<String> testStrings = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("\n\n"))
                break;
            Matcher ruleMatcher = rulePattern.matcher(line);
            if (ruleMatcher.find()) {
                rules.put(Integer.parseInt(ruleMatcher.group(1)), ruleMatcher.group(2).replaceAll("\"", ""));
            } else {
                testStrings.add(line);
            }
        }
        String validString = makeRules(rules, new HashMap<>(), 0);
        Pattern validPattern = Pattern.compile("^" + validString + "$");
        return testStrings.stream()
                .filter(s -> validPattern.matcher(s).find())
                .count();
    }
}