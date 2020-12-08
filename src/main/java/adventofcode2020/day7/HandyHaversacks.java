package adventofcode2020.day7;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandyHaversacks {

    public static class Bags {
        private String color;
        private int count;

        protected Bags(@NonNull String color, int count) {
            this.color = color;
            this.count = count;
        }

        public String getColor() {
            return color;
        }

        public int getCount() {
            return count;
        }

        public static Bags parse(@NonNull String bagStr) {
            Pattern pattern = Pattern.compile("^[\\s]?([\\d+]*)[\\s]?([\\w\\s]*) bag[s]?$");
            Matcher matcher = pattern.matcher(bagStr);
            if (matcher.find()) {
                if (matcher.group(2).equals("no other"))
                    return null;
                int count = Integer.parseInt(matcher.group(1).isEmpty() ? "1" : matcher.group(1));
                return new Bags(matcher.group(2), count);
            }
            throw new IllegalArgumentException("Failed to parse content bag: " + bagStr);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bags bag = (Bags) o;
            return color.equals(bag.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color);
        }

        @Override
        public String toString() {
            return "Bag: " + this.color + " (" + this.count + ")";
        }
    }

    public static Pair<Bags, Set<Bags>> parseLuggageProcessingRule(@NonNull String ruleStr) {
        Pattern pattern = Pattern.compile("^([\\w\\s]*) contain ([\\w\\s\\d,]*)\\.$");
        Matcher matcher = pattern.matcher(ruleStr);
        if (matcher.find()) {
            Bags bag = Bags.parse(matcher.group(1));
            if (bag == null)
                throw new IllegalArgumentException("Failed to parse bag: " + ruleStr);
            Set<Bags> bagContents = Arrays.stream(matcher.group(2)
                    .split(","))
                    .map(Bags::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            return Pair.of(bag, bagContents);
        } else {
            throw new IllegalArgumentException("Failed to parse rule: " + ruleStr);
        }
    }

    public static Set<Bags> getParentBagsSet(Map<Bags, Set<Bags>> map, Set<Bags> set, Bags bag) {
        if (!map.containsKey(bag))
            return set;
        set.addAll(map.get(bag));
        map.get(bag).forEach(b -> getParentBagsSet(map, set, b));
        return set;
    }

    public static int countParentBags(@NonNull String inputFilePath, String bagColor) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<Bags, Set<Bags>> reverseLuggageRulesMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            Pair<Bags, Set<Bags>> bagRule = parseLuggageProcessingRule(scanner.nextLine());
            bagRule.getRight().forEach(contentBag -> {
                Set<Bags> containingBags = reverseLuggageRulesMap.getOrDefault(contentBag, new HashSet<>());
                containingBags.add(bagRule.getLeft());
                reverseLuggageRulesMap.put(contentBag, containingBags);
            });
        }
        return getParentBagsSet(reverseLuggageRulesMap, new HashSet(), new Bags(bagColor, 1)).size();
    }

    public static int countContainingBags(Map<Bags, Set<Bags>> map, Bags bag) {
        return map.get(bag).stream()
                .map(b -> b.getCount() * (1 + countContainingBags(map, b)))
                .reduce(Integer::sum).orElse(0);
    }

    public static int countChildBags(@NonNull String inputFilePath, String bagColor) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<Bags, Set<Bags>> luggageRulesMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            Pair<Bags, Set<Bags>> bagRule = parseLuggageProcessingRule(scanner.nextLine());
            luggageRulesMap.put(bagRule.getLeft(), bagRule.getRight());
        }
        return countContainingBags(luggageRulesMap, new Bags(bagColor, 1));
    }
}
