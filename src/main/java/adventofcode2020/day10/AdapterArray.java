package adventofcode2020.day10;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

public class AdapterArray {

    public static Map<Integer, Integer> voltageDiffs(@NonNull List<Integer> adapters) {
        Map<Integer, Integer> diffs = new HashMap<>();
        Iterator<Integer> iter = adapters.iterator();
        Integer prev = iter.next();
        while(iter.hasNext()) {
            Integer curr = iter.next();
            diffs.putIfAbsent(curr - prev, 0);
            diffs.put(curr - prev, diffs.get(curr - prev) + 1);
            prev = curr;
        }
        return diffs;
    }

    private static List<Integer> slice(List<Integer> voltages, int i) {
        List<Integer> copy = new ArrayList<>();
        copy.addAll(voltages.subList(0, i));
        copy.addAll(voltages.subList(i+1, voltages.size()));
        return copy;
    }

    public static long percolate(List<Integer> voltages, int idx, Map<List<Integer>, Long> memo) {
        if (idx == voltages.size())
            return 1;
        if (memo.containsKey(voltages.subList(0, idx)))
            return memo.get(voltages.subList(0, idx));
        long count = 0;
        if (voltages.get(idx) - voltages.get(idx - 2) <= 3)
            count += percolate(slice(voltages, idx-1), idx, memo);
        count += percolate(voltages.subList(1, voltages.size()), idx, memo);
        memo.putIfAbsent(voltages.subList(0, idx), count);
        return count;
    }

    public static long distinctConnectionWays(@NonNull String inputFilePath) throws FileNotFoundException {
        List<Integer> adapters = sortedAdapters(inputFilePath);
        return percolate(adapters, 2, new HashMap<>());
    }

    public static int productOfMinMaxVoltageDiff(@NonNull String inputFilePath) throws FileNotFoundException {
        Map<Integer, Integer> diffs = voltageDiffs(sortedAdapters(inputFilePath));
        return diffs.get(1) * diffs.get(3);
    }

    public static List<Integer> sortedAdapters(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<Integer> voltages = new ArrayList<>();
        while(scanner.hasNextInt())
            voltages.add(scanner.nextInt());
        Integer deviceVoltage = voltages.stream().max(Integer::compareTo).get() + 3;
        voltages.add(deviceVoltage);
        voltages.add(0);
        voltages.sort(Integer::compareTo);
        return voltages;
    }
}