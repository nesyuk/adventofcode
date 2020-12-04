package adventofcode2020.day1;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportFixing {

    public static int findComplementary(List<Integer> numbers, Integer startIndex, Integer sum) {
        for(int i = startIndex; i < numbers.size()/2 + 1; i++) {
            if (numbers.contains(sum - numbers.get(i)))
                return numbers.get(i) * (sum - numbers.get(i));
        }
        return -1;
    }

    public static int find3Numbers(List<Integer> numbers, Integer sum) {
        for(int i = 0; i < numbers.size()/2 + 1; i++) {
            int result = findComplementary(numbers, i + 1, sum - numbers.get(i));
            if (result != -1)
                return result * numbers.get(i);
        }
        return -1;
    }

    private static List<Integer> parseInputNumbers(String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<Integer> inputNumbers = new ArrayList<>();
        while(scanner.hasNextInt())
            inputNumbers.add(scanner.nextInt());
        return inputNumbers;
    }

    public static int productOfTwoNumbers(@NotNull String inputFilePath) throws FileNotFoundException {
        return ReportFixing.findComplementary(parseInputNumbers(inputFilePath), 0, 2020);
    }

    public static int productOfThreeNumbers(@NotNull String inputFilePath) throws FileNotFoundException {
        return ReportFixing.find3Numbers(parseInputNumbers(inputFilePath), 2020);
    }
}
