package adventofcode2020.day17;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static adventofcode2020.day17.Utils.allNeighbours;

public class ConwayCubes {

    public static void runCircle(List<List<Integer>> grid) {
        Set<List<Integer>> deactivate = new HashSet<>();
        Set<List<Integer>> activate = new HashSet<>();
        Set<List<Integer>> seen = new HashSet<>();

        for (List<Integer> coords : grid) {
            Set<List<Integer>> neighbours = allNeighbours(coords);
            neighbours.retainAll(grid);

            if (!(neighbours.size() == 4 || neighbours.size() == 3)) {
                deactivate.add(coords);
            }
            if (neighbours.size() >= 2) {
                Set<List<Integer>> emptyPoints = allNeighbours(coords);
                emptyPoints.removeAll(grid);
                emptyPoints.removeAll(seen);
                for (List<Integer> emptyPoint: emptyPoints) {
                    Set<List<Integer>> pointNeighbours = allNeighbours(emptyPoint);
                    pointNeighbours.retainAll(grid);
                    if (pointNeighbours.size() == 3) {
                        activate.add(emptyPoint);
                    }
                }
                seen.addAll(emptyPoints);
            }
        }

        grid.removeAll(deactivate);
        grid.addAll(activate);
    }

    public static int runSixCircles(@NonNull String inputFilePath, int numOfDimensions) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());

        List<String> lines = new ArrayList<>();
        while(scanner.hasNextLine())
            lines.add(scanner.nextLine());

        List<List<Integer>> grid = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(lines.size() - y - 1);
            int x = 0;
            while(line.indexOf("#", x) != -1) {
                List<Integer> point = new ArrayList<>();
                point.addAll(List.of(line.indexOf("#", x), y));
                int j = 2;
                while(numOfDimensions - j++ > 0)
                    point.add(0);
                grid.add(point);
                x = line.indexOf("#", x) + 1;
            }
        }

        for (int circle = 0; circle < 6; circle++) {
            runCircle(grid);
        }
        return grid.size();
    }
}
