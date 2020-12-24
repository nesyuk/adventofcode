package adventofcode2020.day24;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbyLayout {
    final static Pattern pattern = Pattern.compile("(se|sw|ne|nw|e|w)");

    public static List<String> parseDirections(String command) {
        Matcher patternMatcher = pattern.matcher(command);
        List<String> directions = new ArrayList<>();
        while(patternMatcher.find()) {
            directions.add(patternMatcher.group(1));
        }
        return directions;
    }

    public static Integer[][] layoutFloor(List<String> commands) {
        // 358 tiles (see input file) will form a square 18*18;
        // in 100 days each side can grow max 1 tile each side;
        // so, the max floor size in 100 days is 180*180
        int size = 180;
        Integer[][] floor = new Integer[size][];
        for (int i = 0; i < size; i++) {
            floor[i] = new Integer[size];
            Arrays.fill(floor[i], 0);
        }

        int iMin = size-1;
        int iMax = 0;
        int jMin = size-1;
        int jMax = 0;

        for (String command: commands) {
            int i = size/2;
            int j = size/2;
            List<String> directions = parseDirections(command);
            for (String direction: directions) {
                //            nw            ne
                //          (-1, 0)     (-1, +1)
                //
                //       w           .           e
                //    (0, -1)     (0, 0)      (0, +1)
                //
                //            sw           se
                //         (+1, -1)     (+1, 0)

                switch (direction) {
                    case "se":
                         i++; break;
                    case "sw":
                         i++; j--; break;
                    case "ne":
                         i--; j++; break;
                    case "nw":
                         i--; break;
                    case "e":
                         j++; break;
                    case "w":
                         j--; break;
                    default:
                        throw new IllegalStateException(direction);
                }
            }
            if (iMin > i)
                iMin = i;
            if (iMax < i)
                iMax = i;
            if (jMin > j)
                jMin = j;
            if (jMax < j)
                jMax = j;
            floor[i][j] = (floor[i][j] == 1 ? 0 : 1);
        }
        return floor;
    }

    public static int flipAndCountTiles(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<String> commands = new ArrayList<>();
        while(scanner.hasNextLine()) {
            commands.add(scanner.nextLine());
        }
        return Arrays.stream(layoutFloor(commands))
                .flatMap(Arrays::stream)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static Integer[][] flipTiles(Integer[][] floor) {
        List<Pair<Integer, Integer>> flipTiles = new ArrayList<>();
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[0].length; j++) {
                int countBlack = 0;
                try {
                    countBlack += floor[i-1][j] == 1 ? 1 : 0;
                    countBlack += floor[i-1][j+1] == 1 ? 1 : 0;
                    countBlack += floor[i][j-1] == 1 ? 1 : 0;
                    countBlack += floor[i][j+1] == 1 ? 1 : 0;
                    countBlack += floor[i+1][j-1] == 1 ? 1 : 0;
                    countBlack += floor[i+1][j] == 1 ? 1 : 0;
                } catch (IndexOutOfBoundsException e) {
                    //lazy hack
                }

                if ((floor[i][j] == 0 && countBlack == 2) ||
                        (floor[i][j] == 1 && (countBlack == 0 || countBlack > 2)))
                    flipTiles.add(Pair.of(i, j));
            }
        }
        for (Pair<Integer, Integer> coords: flipTiles) {
            floor[coords.getLeft()][coords.getRight()] =
                    (floor[coords.getLeft()][coords.getRight()] == 0 ? 1 : 0);
        }
        return floor;
    }

    public static int flipAndCountTilesForArtExhibition(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<String> commands = new ArrayList<>();
        while(scanner.hasNextLine()) {
            commands.add(scanner.nextLine());
        }
        Integer[][] floor = layoutFloor(commands);
        for (int i = 0; i < 100; i++) {
            floor = flipTiles(floor);
        }

        return Arrays.stream(floor)
                .flatMap(Arrays::stream)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
