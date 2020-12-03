package adventofcode2020.day3;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TobogganTrajectory {
    public static boolean hasTreeOnPosition(String path, int index) {
        return path.charAt(index) == '#';
    }

    public static long countTrees(List<String> paths, int rightStepSize, int downStepSize) {
        int length = paths.get(0).length();
        long treeCount = 0;
        int position = rightStepSize;
        for (int i = downStepSize; i < paths.size(); i += downStepSize) {
            if (hasTreeOnPosition(paths.get(i), position))
                treeCount++;
            position = (position + rightStepSize) % length;
        }
        return treeCount;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Path path = Path.of("src/adventofcode2020/day3/input");
        Scanner scanner = new Scanner(path.toFile());
        List<String> paths = new ArrayList<>();
        while(scanner.hasNextLine())
            paths.add(scanner.nextLine());
        System.out.println(countTrees(paths, 1, 1) *
                countTrees(paths, 3, 1) *
                countTrees(paths, 5, 1) *
                countTrees(paths, 7, 1) *
                countTrees(paths, 1, 2));
    }
}
