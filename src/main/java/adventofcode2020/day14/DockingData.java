package adventofcode2020.day14;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DockingData {
    static final Pattern maskPattern = Pattern.compile("mask = ([10X]{36})");
    static final Pattern mapValuePattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

    public static Long applyValueBinaryMask(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<Long, Long> maskedValues = new HashMap<>();
        Long zeroMask = 0L;
        Long oneMask = 0L;
        Matcher maskMatcher;
        Matcher mapValueMatcher;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            maskMatcher = maskPattern.matcher(line);

            if (maskMatcher.find()) {
                String mask = maskMatcher.group(1);
                zeroMask = Long.parseLong(mask.replaceAll("X", "0"), 2);
                oneMask = Long.parseLong(mask.replaceAll("X", "1"), 2);
            }

            mapValueMatcher = mapValuePattern.matcher(line);

            if (mapValueMatcher.find()) {
                Long key = Long.parseLong(mapValueMatcher.group(1));
                long value =  Long.parseLong(mapValueMatcher.group(2));
                maskedValues.putIfAbsent(key, 0L);
                value = value | zeroMask;
                value = value & oneMask;
                maskedValues.put(key, value);
            }
        }
        return maskedValues.values().stream().reduce(Long::sum).get();
    }

    public static Long applyKeyBinaryMask(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        Map<Long, Long> maskedKeys = new HashMap<>();
        List<Long> masks = new ArrayList<>();
        Long zeroMask = 0L;
        Matcher maskMatcher;
        Matcher mapValueMatcher;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            maskMatcher = maskPattern.matcher(line);

            if (maskMatcher.find()) {
                String maskStr = maskMatcher.group(1);
                zeroMask = Long.parseLong(maskStr.replaceAll("X", "0"), 2);

                masks = new ArrayList<>();
                int fromIdx = 0;
                while (maskStr.indexOf("X", fromIdx) != -1) {
                    Long mask = 1L << (35 - maskStr.indexOf("X", fromIdx));
                    masks.add(mask); //000000001000000 (or) // and (111111101111111)
                    fromIdx = maskStr.indexOf("X", fromIdx) + 1;
                }
            }

            mapValueMatcher = mapValuePattern.matcher(line);

            if (mapValueMatcher.find()) {
                Long key = Long.parseLong(mapValueMatcher.group(1));
                long value =  Long.parseLong(mapValueMatcher.group(2));
                key = key | zeroMask;

                Set<Long> appliedMasks = applyMasks(masks, 0, key, new HashSet<>());

                for (Long maskedKey : appliedMasks) {
                    maskedKeys.putIfAbsent(maskedKey, 0L);
                    maskedKeys.put(maskedKey, value);
                }
            }
        }
        return maskedKeys.values().stream().reduce(Long::sum).get();
    }

    public static Set<Long> applyMasks(List<Long> masks, int fromIdx, Long toMask, Set<Long> results) {
        if (fromIdx == masks.size()) {
            results.add(toMask);
            return results;
        }
        Set<Long> ones = applyMasks(masks, fromIdx+1, toMask | masks.get(fromIdx), results);
        Set<Long> zeros = applyMasks(masks, fromIdx+1, toMask & ~masks.get(fromIdx), results);
        ones.addAll(zeros);
        return ones;
    }
}
