package adventofcode2020.day12;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainRisk {

    static final Pattern commandPattern = Pattern.compile("^(\\w)(\\d+)$");

    static List<Pair<Character, Integer>> parseCommands(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<Pair<Character, Integer>> commands = new ArrayList<>();
        while(scanner.hasNext()) {
            Matcher matcher = commandPattern.matcher(scanner.next());
            if (matcher.find()) {
                commands.add(Pair.of(matcher.group(1).charAt(0), Integer.parseInt(matcher.group(2))));
            }
        }
        return commands;
    }

    public static long moveShip(@NonNull String inputFilePath) throws FileNotFoundException {
        int x = 0;
        int y = 0;
        int dir = 0;
        for (Pair<Character, Integer> command: parseCommands(inputFilePath)) {
            switch (command.getLeft()) {
                case 'E':
                    x += command.getRight(); break;
                case 'W':
                    x -= command.getRight(); break;
                case 'N':
                    y += command.getRight(); break;
                case 'S':
                    y -= command.getRight(); break;
                case 'L':
                    dir = (dir + command.getRight()) % 360;
                    break;
                case 'R':
                    dir = (360 + (dir - command.getRight())) % 360; // 0 - 90 (90), 270 - 180 = 90
                    break;
                case 'F':
                    switch (dir) {
                        case 360:
                        case 0:
                            x += command.getRight(); break;
                        case 90:
                            y += command.getRight(); break;
                        case 180:
                            x -= command.getRight(); break;
                        case 270:
                            y -= command.getRight(); break;
                        default:
                            throw new IllegalArgumentException();
                    } break;
                default:
                    throw new IllegalArgumentException(command.getLeft().toString());
            }
        }
        return manhattanDistance(x, y);
    }

    static long manhattanDistance(int x, int y) {
        return Math.abs(x) + Math.abs(y);
    }

    public static long moveWayPoint(@NonNull String inputFilePath) throws FileNotFoundException {
        int wx = 10;
        int wy = 1;
        int sx = 0;
        int sy = 0;
        int degree;

        for (Pair<Character, Integer> command: parseCommands(inputFilePath)) {
            switch (command.getLeft()) {
                case 'E':
                    wx += command.getRight(); break;
                case 'W':
                    wx -= command.getRight(); break;
                case 'N':
                    wy += command.getRight(); break;
                case 'S':
                    wy -= command.getRight(); break;
                case 'L':
                    degree = command.getRight() % 360;
                    while(degree > 0) {
                        int temp = wy;
                        wy = wx;
                        wx = -temp;
                        degree -=90;
                    }
                    break;
                case 'R':
                    degree = command.getRight() % 360;
                    while(degree > 0) {
                        int temp = wx;
                        wx = wy;
                        wy = -temp;
                        degree -=90;
                    }
                    break;
                case 'F':
                    sx += wx * command.getRight();
                    sy += wy * command.getRight();
                    break;
                default:
                    throw new IllegalArgumentException(command.getLeft().toString());
            }
        }
        return manhattanDistance(sx, sy);
    }
}
