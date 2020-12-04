package adventofcode2020.day3;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TobogganTrajectory {
    public static boolean hasTreeOnPosition(String path, int index) {
        return path.charAt(index) == '#';
    }

    static long countTrees(List<String> paths, int rightStepSize, int downStepSize) {
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

    public static Long treesCount(@NotNull String inputFilePath, Pair<Integer, Integer> rightDownSteps) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<String> paths = new ArrayList<>();
        while(scanner.hasNextLine())
            paths.add(scanner.nextLine());
        return countTrees(paths, rightDownSteps.getLeft(), rightDownSteps.getRight());
    }

    public static Long productOfTreesCount(@NotNull String inputFilePath, List<Pair<Integer, Integer>> rightDownStepsList) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<String> paths = new ArrayList<>();
        while(scanner.hasNextLine())
            paths.add(scanner.nextLine());
        return rightDownStepsList.stream()
                .map(pair -> countTrees(paths, pair.getLeft(), pair.getRight()))
                .reduce((x, y) -> x*y)
                .get();
    }
}
