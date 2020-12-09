package adventofcode2020.day9;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EncodingError {

    public static boolean hasSum(final List<Long> preamble, final Long sum) {
        for (Long num : preamble) {
            if (preamble.contains(sum-num) && (num != sum/2))
                return true;
        }
        return false;
    }

    static Long findSumError(List<Long> encodedInts, int startIdx, int endIdx) {
        while (hasSum(encodedInts.subList(startIdx, endIdx), encodedInts.get(endIdx))) {
            startIdx++;
            endIdx++;
        }
        return encodedInts.get(endIdx);
    }

    private static List<Long> scanNums(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<Long> encodedInts = new ArrayList<>();
        while(scanner.hasNextLong())
            encodedInts.add(scanner.nextLong());
        return encodedInts;
    }

    private static List<Long> slidingWindowSum(List<Long> numbers, Long sum, int windowSize) {
        Long currentSum = numbers.subList(0, windowSize).stream().reduce(Long::sum)
                .orElseThrow(IllegalArgumentException::new);
        int currentIndex = windowSize;
        while(currentIndex != numbers.size()) {
            currentSum = currentSum + numbers.get(currentIndex) - numbers.get(currentIndex - windowSize);
            if (currentSum.equals(sum))
                return numbers.subList(currentIndex - windowSize+1, currentIndex+1);
            currentIndex++;
        }
        return null;
    }

    public static Long findSum(@NonNull String inputFilePath, int preambleSize) throws FileNotFoundException {
        List<Long> encodedInts = scanNums(inputFilePath);
        long sum = findSumError(encodedInts, 0, preambleSize);
        int windowSize = 2;
        List<Long> windowNums = slidingWindowSum(encodedInts, sum, windowSize);
        while(windowNums == null) {
            windowNums = slidingWindowSum(encodedInts, sum, ++windowSize);
        }
        windowNums.sort(Long::compareTo);
        return windowNums.get(0) + windowNums.get(windowNums.size()-1);
    }

    public static long findError(@NonNull String inputFilePath, int preambleSize) throws FileNotFoundException {
        return findSumError(scanNums(inputFilePath), 0, preambleSize);
    }
}