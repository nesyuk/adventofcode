package adventofcode2020.day17;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Utils {

    public static List<Pair<Integer, Integer>> gridSizes(List<List<Integer>> grid) {
        List<Pair<Integer, Integer>> size = new ArrayList<>();
        for (int i = 0; i < grid.get(0).size(); i++) {
            int dimension = i; // i must be effectively final
            int min_i = grid.stream().map(l -> l.get(dimension)).min(Integer::compareTo).get();
            int max_i = grid.stream().map(l -> l.get(dimension)).max(Integer::compareTo).get();
            size.add(Pair.of(min_i, max_i));
        }
        return size; // {x: <min, max>, y: <min, max>, z: <min, max>, w: <min, max>}
    }

    public static void print3D(List<List<Integer>> grid) {
        List<Pair<Integer, Integer>> size = gridSizes(grid);

        for (int z = size.get(2).getLeft(); z <= size.get(2).getRight(); z++) {
            System.out.println("z = " + z);
            for (int y = size.get(1).getRight(); y >= size.get(1).getLeft(); y--) {
                for (int x = size.get(0).getLeft(); x <= size.get(0).getRight(); x++) {
                    if (grid.contains(List.of(x, y, z))) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    static Set<List<Integer>> allNeighbours(int idx, List<Integer> point,
                                            Set<List<Integer>> allCoords,
                                            List<Integer> coords) {
        if (idx == point.size()) {
            allCoords.add(coords);
            return allCoords;
        }
        for (int i = -1; i <= 1; i++) {
            List<Integer> updated = new ArrayList<>(coords);
            updated.add(point.get(idx) + i);
            allNeighbours(idx + 1, point, allCoords, updated);
        }
        return allCoords;
    }

    public static Map<List<Integer>, Set<List<Integer>>> seenNeighbours = new HashMap<>();

    public static Set<List<Integer>> allNeighbours(List<Integer> point) {
        seenNeighbours.putIfAbsent(point, allNeighbours(0, point, new HashSet<>(), new ArrayList<>()));
        return new HashSet<>(seenNeighbours.get(point));
    }

    public static Set<List<Integer>> allPossibleNeighbours3D(List<Integer> point) {
        Set<List<Integer>> neighbours = new HashSet<>();
        int coordX = point.get(0);
        int coordY = point.get(1);
        int coordZ = point.get(2);

        for (int z = coordZ - 1; z <= coordZ + 1; z++) {
            for (int y = coordY - 1; y <= coordY + 1; y++) {
                for (int x = coordX - 1; x <= coordX + 1; x++) {
                    neighbours.add(List.of(x, y, z));
                }
            }
        }
        return neighbours;
    }

    public static Set<List<Integer>> allPossibleNeighbours4D(List<Integer> point) {
        Set<List<Integer>> neighbours = new HashSet<>();
        int coordX = point.get(0);
        int coordY = point.get(1);
        int coordZ = point.get(2);
        int coordW = point.get(3);

        for (int w = coordW - 1; w <= coordW + 1; w++) {
            for (int z = coordZ - 1; z <= coordZ + 1; z++) {
                for (int y = coordY - 1; y <= coordY + 1; y++) {
                    for (int x = coordX - 1; x <= coordX + 1; x++) {
                        neighbours.add(List.of(x, y, z, w));
                    }
                }
            }
        }
        return neighbours;
    }
}
