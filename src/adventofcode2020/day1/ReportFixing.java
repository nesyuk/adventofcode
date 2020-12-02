package adventofcode2020.day1;

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

    public static void main(String[] args) throws FileNotFoundException {
        Path path = Path.of("src/adventofcode2020/day1/input");
        Scanner scanner = new Scanner(path.toFile());
        List<Integer> inputNumbers = new ArrayList<>();
        while(scanner.hasNextInt())
            inputNumbers.add(scanner.nextInt());
        System.out.println(ReportFixing.findComplementary(inputNumbers, 0, 2020));
        System.out.println(ReportFixing.find3Numbers(inputNumbers, 2020));
    }
}
