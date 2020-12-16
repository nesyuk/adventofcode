package adventofcode2020.day16;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketTranslation {

    static final Pattern rulePattern = Pattern.compile("([\\w+\\s]*): (\\d+)-(\\d+) or (\\d+)-(\\d+)");
    static final Pattern ticketPattern = Pattern.compile("([\\d+\\,]+)");

    public static long countInvalidTickets(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());

        Set<Integer> rules = new HashSet<>();
        List<Integer> invalidNumbers = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String str = scanner.nextLine();
            Matcher rulesMatcher = rulePattern.matcher(str);
            Matcher ticketMatcher = ticketPattern.matcher(str);
            if (rulesMatcher.find()) {
                rules.addAll(IntStream.range(Integer.parseInt(rulesMatcher.group(2)),
                        Integer.parseInt(rulesMatcher.group(3))+1).boxed().collect(Collectors.toSet()));
                rules.addAll(IntStream.range(Integer.parseInt(rulesMatcher.group(4)),
                        Integer.parseInt(rulesMatcher.group(5))+1).boxed().collect(Collectors.toSet()));
            } else if (ticketMatcher.find()) {
                Set<Integer> ticketNumbers = Arrays.stream(str.split(","))
                        .map(Integer::parseInt).collect(Collectors.toSet());
                ticketNumbers.removeAll(rules);
                invalidNumbers.addAll(ticketNumbers);
            }
        }
        return invalidNumbers.stream().reduce(Integer::sum).get();
    }

    public static BigInteger findTicketFields(@NonNull String inputFilePath, String fieldOfInterest) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());

        Map<String, Set<Integer>> rulesMap = new HashMap<>();
        List<Integer> ticket;
        List<Set<Integer>> nearbyTickets = new ArrayList<>();

        String str = scanner.nextLine();
        Matcher rulesMatcher = rulePattern.matcher(str);

        while(rulesMatcher.find()) {
            Set<Integer> rules = new HashSet<>();
            rules.addAll(IntStream.range(Integer.parseInt(rulesMatcher.group(2)),
                    Integer.parseInt(rulesMatcher.group(3))+1).boxed().collect(Collectors.toSet()));
            rules.addAll(IntStream.range(Integer.parseInt(rulesMatcher.group(4)),
                    Integer.parseInt(rulesMatcher.group(5))+1).boxed().collect(Collectors.toSet()));
            rulesMap.put(rulesMatcher.group(1), rules);

            str = scanner.nextLine();
            rulesMatcher = rulePattern.matcher(str);
        }

        while(!str.equals("your ticket:")) {
            str = scanner.nextLine();
        }
        str = scanner.nextLine();
        ticket = Arrays.stream(str.split(","))
                .map(Integer::parseInt).collect(Collectors.toList());

        while(!str.equals("nearby tickets:")) {
            str = scanner.nextLine();
        }

        Set<Integer> validValues = rulesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        while (scanner.hasNextLine()) {
            String ticketStr = scanner.nextLine();
            List<Integer> nearbyTicket = Arrays.stream(ticketStr.split(","))
                    .map(Integer::parseInt).collect(Collectors.toList());
            if (validValues.containsAll(nearbyTicket)) {
                for (int i = 0; i <nearbyTicket.size(); i++) {
                    if (nearbyTickets.size() < i+1)
                        nearbyTickets.add(new HashSet<>());
                    nearbyTickets.get(i).add(nearbyTicket.get(i));
                }
            }
        }

        Map<String, Set<Integer>> fieldMapper = new LinkedHashMap<>();

        for (int i = 0; i < nearbyTickets.size(); i++) {
            for (Map.Entry<String, Set<Integer>> rule: rulesMap.entrySet()) {
                if (rule.getValue().containsAll(nearbyTickets.get(i))) {
                    fieldMapper.putIfAbsent(rule.getKey(), new HashSet<>());
                    fieldMapper.get(rule.getKey()).add(i);
                }
            }
        }

        Map<String, Set<Integer>> sortedByAmbiguityMap = fieldMapper.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> null,
                        LinkedHashMap::new));

        Set<Integer> identifiedFields = new HashSet<>();
        for (Map.Entry<String, Set<Integer>> rule: sortedByAmbiguityMap.entrySet()) {
            if (rule.getValue().size() == 1) {
                identifiedFields.addAll(rule.getValue());
            } else {
                rule.getValue().removeAll(identifiedFields);
                if (rule.getValue().size() == 1) {
                    identifiedFields.addAll(rule.getValue());
                }
            }
        }
        return sortedByAmbiguityMap.entrySet().stream()
                .filter(e -> e.getKey().contains(fieldOfInterest))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(ticket::get)
                .map(BigInteger::valueOf)
                .reduce(BigInteger::multiply).get();
    }
}
