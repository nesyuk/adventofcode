package adventofcode2020.day11;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SeatingSystem {

    static final Function<Integer, Integer> incr = (idx) -> idx+1;
    static final Function<Integer, Integer> decr = (idx) -> idx-1;
    static final Function<Integer, Integer> same = Function.identity();

    public static boolean isOccupied(Character seat) {
        return seat == '#';
    }

    public static boolean isFree(Character seat) {
        return seat == 'L';
    }

    public static boolean hasNeighbour(List<List<Character>> placesGrid,
                                       int i, Function<Integer, Integer> iFunc,
                                       int j, Function<Integer, Integer> jFunc,
                                       boolean checkOnlyAdjacent) {
        i = iFunc.apply(i);
        j = jFunc.apply(j);
        return (i == -1 || i == placesGrid.size() || j == -1 || j == placesGrid.get(0).size()) ?
                false
                : (isOccupied(placesGrid.get(i).get(j))
                ? true:
                isFree(placesGrid.get(i).get(j)) ? false :
                        checkOnlyAdjacent ? false:
                                hasNeighbour(placesGrid, i, iFunc, j, jFunc, checkOnlyAdjacent));
    }

    public static int firstNeighbourCount(List<List<Character>> placesGrid, int i, int j, boolean checkOnlyAdjacent) {
        int neighbourCount = 0;
        neighbourCount += hasNeighbour(placesGrid, i, decr,  j, same, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, decr, j, decr, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, decr, j, incr, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, same, j, incr, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, same, j, decr, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, incr,  j, same, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, incr, j, decr, checkOnlyAdjacent) ? 1 : 0;
        neighbourCount += hasNeighbour(placesGrid, i, incr, j, incr, checkOnlyAdjacent) ? 1 : 0;

        return neighbourCount;
    }

    public static long doRound(List<List<Character>> placesGrid, int roundCount, int seatTolerance, boolean checkOnlyAdjacent) {
        List<Pair<Integer, Integer>> flipSeats = new ArrayList<>();
        for (int i = 0; i < placesGrid.size(); i++) {
            for (int j = 0; j < placesGrid.get(0).size(); j++) {
                if (placesGrid.get(i).get(j) == 'L' && firstNeighbourCount(placesGrid, i, j, checkOnlyAdjacent) == 0)
                      flipSeats.add(Pair.of(i, j));
                else if (placesGrid.get(i).get(j) == '#' && firstNeighbourCount(placesGrid, i, j, checkOnlyAdjacent) >= seatTolerance)
                      flipSeats.add(Pair.of(i, j));
            }
        }
        if (flipSeats.isEmpty()) {
            return placesGrid.stream()
                    .flatMap(Collection::stream)
                    .filter(c -> c == '#').count();
        }
        else {
            for (Pair<Integer, Integer> seatCoords: flipSeats) {
                if (placesGrid.get(seatCoords.getLeft()).get(seatCoords.getRight()) == 'L')
                    placesGrid.get(seatCoords.getLeft()).set(seatCoords.getRight(), '#');
                else
                    placesGrid.get(seatCoords.getLeft()).set(seatCoords.getRight(), 'L');
            }
        }
        return doRound(placesGrid, roundCount + 1, seatTolerance, checkOnlyAdjacent);
    }

    public static List<List<Character>> readGrid(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<List<Character>> places = new ArrayList<>();
        while(scanner.hasNextLine())
            places.add(scanner.nextLine()
                    .chars()
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.toList()));

        return places;
    }

    public static long countSeats(@NonNull String inputFilePath, int seatTolerance, boolean checkOnlyAdjacent) throws FileNotFoundException {
        return doRound(readGrid(inputFilePath), 0, seatTolerance, checkOnlyAdjacent);
    }
}
