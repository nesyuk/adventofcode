package adventofcode2020.day21;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AllergenAssessment {
    static final Pattern pattern = Pattern.compile("([\\w\\s]+)\\s\\(contains\\s([\\w\\s,]+)\\)");

    public static String findIngredientsWithAllergens(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<String, Set<String>> allergyIngredientMap = new HashMap<>();
        List<String> allIngredients = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()) {
                Set<String> ingredients = Arrays.stream(matcher.group(1)
                        .split(" "))
                        .collect(Collectors.toSet());

                allIngredients.addAll(ingredients);

                Set<String> allergens = Arrays.stream(matcher.group(2)
                        .split(", ")).collect(Collectors.toSet());

                for (String allergen : allergens) {
                    if (allergyIngredientMap.containsKey(allergen)) {
                        HashSet allergySet = new HashSet(allergyIngredientMap.get(allergen));
                        allergySet.retainAll(ingredients);
                        if (allergySet.size() == 1) {
                            allergyIngredientMap.entrySet().stream()
                                    .filter(e -> !e.getKey().equals(allergen))
                                    .forEach(e -> e.getValue().removeAll(allergySet));
                        }
                        allergyIngredientMap.put(allergen, allergySet);
                    } else {
                        allergyIngredientMap.put(allergen, ingredients);
                    }
                }
            }
        }

        Set<String> ingredientsWithAllergens = allergyIngredientMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return allergyIngredientMap.entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue().stream().findFirst()))
                .sorted((pair1, pair2) -> (pair1.getLeft()).compareTo(pair2.getLeft()))
                .map(pair -> pair.getRight().get())
        .collect(Collectors.joining(","));
        //return ingredientsWithAllergens.stream().sorted((s1, s2) -> s2.compareTo(s1)).collect(Collectors.joining(","));
    }

    public static int findIngredients(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<String, Set<String>> allergyIngredientMap = new HashMap<>();
        List<String> allIngredients = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()) {
                Set<String> ingredients = Arrays.stream(matcher.group(1)
                        .split(" "))
                        .collect(Collectors.toSet());

                allIngredients.addAll(ingredients);

                Set<String> allergens = Arrays.stream(matcher.group(2)
                        .split(", ")).collect(Collectors.toSet());

                for (String allergen : allergens) {
                    if (allergyIngredientMap.containsKey(allergen)) {
                        HashSet allergySet = new HashSet(allergyIngredientMap.get(allergen));
                        allergySet.retainAll(ingredients);
                        if (allergySet.size() == 1) {
                            allergyIngredientMap.entrySet().stream()
                                    .filter(e -> !e.getKey().equals(allergen))
                                    .forEach(e -> e.getValue().removeAll(allergySet));
                        }
                        allergyIngredientMap.put(allergen, allergySet);
                    } else {
                        allergyIngredientMap.put(allergen, ingredients);
                    }
                }
            }
        }

        Set<String> ingredientsWithAllergens = allergyIngredientMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        System.out.println(allIngredients);
        allIngredients.removeAll(ingredientsWithAllergens);
        System.out.println(allIngredients);
        return allIngredients.size();
    }
}
