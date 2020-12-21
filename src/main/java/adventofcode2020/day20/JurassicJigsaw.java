package adventofcode2020.day20;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JurassicJigsaw {

    final static Pattern tilePattern = Pattern.compile("Tile (\\d+):");

    public static Map<String, Long> findMatches(List<Tile> tiles) {
        return tiles.stream()
                .map(Tile::getSides)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static Set<Tile> findCornerTiles(List<Tile> tiles) {
        Map<String, Long> matches = findMatches(tiles);

        return tiles.stream()
                .filter(tile -> {
                    List<String> matchedSides = new ArrayList();
                    for (String side : tile.getSides()) {
                        if (matches.get(side) == 2L) {
                            if (!matchedSides.contains(new StringBuilder(side).reverse().toString()))
                                matchedSides.add(side);
                        }
                    }
                    return matchedSides.size() == 2L;
                })
                .collect(Collectors.toSet());
    }

    public static class Tile {
        final int id;
        final List<String> rows;

        int getId() {
            return this.id;
        }

        Tile(int id, List<String> rows) {
            this.id = id;
            this.rows = rows;
        }

        Tile copy() {
            return new Tile(this.id, this.rows);
        }

        int height() {
            return this.rows.size();
        }

        public Tile(String[] rawRows) {
            Matcher idMatcher = tilePattern.matcher(rawRows[0]);
            idMatcher.find();
            this.id = Integer.parseInt(idMatcher.group(1));
            this.rows = Arrays.stream(rawRows).skip(1).collect(Collectors.toList());
        }

        public String getRow(int i) {
            if (i < 0 || i >= this.rows.size())
                throw new IllegalArgumentException(String.valueOf(i));
            return rows.get(i);
        }

        public String getColumn(int i) {
            if (i < 0 || i >= this.rows.get(0).length())
                throw new IllegalArgumentException(String.valueOf(i));
            return rows.stream()
                    .map(s -> String.valueOf(s.charAt(i)))
                    .collect(Collectors.joining());
        }

        public String getColumnWithoutBorders(int i) {
            if (i <= 0 || i >= this.rows.get(0).length() - 1)
                throw new IllegalArgumentException(String.valueOf(i));
            List<String> trimmedRows = rows.subList(1, rows.size() - 1);
            return trimmedRows.stream()
                    .map(s -> String.valueOf(s.charAt(i)))
                    .collect(Collectors.joining());
        }

        public Tile flipHorizontally() {
            List<String> rotatedRows = new ArrayList<>();
            for (int i = 0; i < this.rows.size(); i++) {
                rotatedRows.add(reverse(this.getRow(i)));
            }
            return new Tile(this.id, rotatedRows);
        }

        public Tile flipVertically() {
            List<String> rotatedRows = new ArrayList<>();
            for (int i = this.rows.size() - 1; i >= 0; i--) {
                rotatedRows.add(this.getRow(i));
            }
            return new Tile(this.id, rotatedRows);
        }

        public Tile rotateLeft() {
            List<String> rotatedRows = new ArrayList<>();
            for (int j = rows.size() - 1; j >= 0; j--) {
                rotatedRows.add(this.getColumn(j));
            }
            return new Tile(this.id, rotatedRows);
        }

        public Tile rotateRight() {
            List<String> rotatedRows = new ArrayList<>();
            for (int j = 0; j < rows.size(); j++) {
                rotatedRows.add(reverse(this.getColumn(j)));
            }
            return new Tile(this.id, rotatedRows);
        }

        public Tile positionTop(String side) {
            if (this.topSide().equals(side))  //top
                return this.copy();
            else if (reverse(this.topSide()).equals(side))
                return this.flipHorizontally();
            else if (this.rightSide().equals(side)) //right
                return this.rotateLeft();
            else if (reverse(this.rightSide()).equals(side))
                return this.rotateLeft().flipHorizontally();
            else if (this.leftSide().equals(side)) //left
                return this.rotateRight();
            else if (reverse(this.leftSide()).equals(side))
                return this.rotateRight().flipHorizontally();
            else if (this.bottomSide().equals(side)) //bottom
                return this.flipVertically();
            else if (reverse(this.bottomSide()).equals(side))
                return this.flipVertically().flipHorizontally();
            else
                throw new IllegalStateException();
        }

        public Tile positionLeft(String side) {
            if (this.leftSide().equals(side))  //left
                return this.copy();
            else if (reverse(this.leftSide()).equals(side))
                return this.flipVertically();
            else if (this.rightSide().equals(side)) //right
                return this.flipHorizontally();
            else if (reverse(this.rightSide()).equals(side))
                return this.flipVertically().flipHorizontally();
            else if (this.topSide().equals(side)) //top
                return this.rotateLeft().flipVertically();
            else if (reverse(this.topSide()).equals(side))
                return this.rotateLeft();
            else if (this.bottomSide().equals(side)) //bottom
                return this.rotateRight();
            else if (reverse(this.bottomSide()).equals(side))
                return this.rotateRight().flipVertically();
            else
                throw new IllegalStateException();
        }

        public static String reverse(String string) {
            return new StringBuilder(string).reverse().toString();
        }

        public boolean hasRightSideMatch(Tile other) {
            return other.getSides().contains(this.rightSide());
        }

        public boolean hasBottomSideMatch(Tile other) {
            return other.getSides().contains(this.bottomSide());
        }

        public String topSide() {
            return this.getRow(0);
        }

        public String leftSide() {
            return this.getColumn(0);
        }

        public String rightSide() {
            return this.getColumn(rows.get(0).length() - 1);
        }

        public String bottomSide() {
            return this.getRow(rows.size() - 1);
        }

        public List<String> getSides() {
            List<String> sides = new ArrayList<>();
            String top = rows.get(0);
            sides.add(top);
            sides.add(reverse(top));
            String bottom = rows.get(rows.size() - 1);
            sides.add(bottom);
            sides.add(reverse(bottom));
            String left = rows.stream()
                    .map(s -> String.valueOf(s.charAt(0)))
                    .collect(Collectors.joining());
            sides.add(left);
            sides.add(reverse(left));
            String right = rows.stream()
                    .map(s -> String.valueOf(s.charAt(s.length() - 1)))
                    .collect(Collectors.joining());
            sides.add(right);
            sides.add(reverse(right));

            return sides;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.id);
            sb.append(":\n");
            for (int i = 0; i < rows.size(); i++) {
                sb.append(rows.get(i));
                sb.append("\n");
            }
            sb.append("\n");
            return sb.toString();
        }
    }

    static List<Tile> parseTiles(String inputFilePath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        } catch (FileNotFoundException e) {
            //
        }
        List<Tile> tiles = new ArrayList<>();
        while (scanner.hasNext())
            tiles.add(new Tile(scanner.next().split("\n")));

        return tiles;
    }

    public static BigInteger findCornerTiles(@NonNull String inputFilePath) {
        return findCornerTiles(parseTiles(inputFilePath)).stream()
                .map(Tile::getId)
                .map(BigInteger::valueOf)
                .reduce(BigInteger::multiply).get();
    }

    public static int findMonsterTiles(@NonNull String inputFilePath) {
        List<Tile> tiles = parseTiles(inputFilePath);
        List<Tile> edgeTiles = findCornerTiles(tiles).stream()
                .filter(tile -> {
                    int matchCount = 0;
                    for (Tile other : tiles) {
                        if (other.getSides().contains(tile.bottomSide()) && other.id != tile.id)
                            matchCount++;
                        if (other.getSides().contains(tile.rightSide()) && other.id != tile.id)
                            matchCount++;
                    }
                    return matchCount == 2;
                }).collect(Collectors.toList());

        Tile edgeTile = edgeTiles.get(0);
        List<List<Tile>> grid = new ArrayList<>();
        grid.add(new ArrayList<>());
        grid.get(0).add(edgeTile.copy());
        tiles.remove(edgeTile);

        while (true) {
            while (true) {
                List<Tile> row = grid.get(grid.size() - 1);
                Tile rightSideTile = tiles.stream()
                        .filter(tile -> row.get(row.size() - 1)
                                .hasRightSideMatch(tile))
                        .findFirst().orElse(null);

                if (rightSideTile == null)
                    break;
                Tile rotatedRightTile = rightSideTile.positionLeft(row.get(row.size() - 1).rightSide());
                tiles.remove(rightSideTile);
                grid.get(grid.size() - 1).add(rotatedRightTile);
            }

            Tile topTile = grid.get(grid.size() - 1).get(0);
            Tile bottomTile = tiles.stream().filter(topTile::hasBottomSideMatch).findFirst()
                    .orElse(null);
            if (bottomTile == null)
                break;
            tiles.remove(bottomTile);
            Tile alignedBottomTile = bottomTile.positionTop(topTile.bottomSide());
            grid.add(new ArrayList<>());
            grid.get(grid.size() - 1).add(alignedBottomTile);
        }
        List<List<Tile>> flippedGrid = new ArrayList<>();
        for (int i = grid.size() - 1; i >= 0; i--) {
            flippedGrid.add(grid.get(i).stream().map(Tile::rotateRight).collect(Collectors.toList()));
        }
        List<String> gridStrings = new ArrayList<>();
        for (List<Tile> rowTiles : flippedGrid) {
            for (int i = 1; i < rowTiles.get(0).height() - 1; i++) {
                int idx = i;
                StringBuilder sb = new StringBuilder();
                rowTiles.forEach(tile -> sb.append(tile.getColumnWithoutBorders(idx)));
                gridStrings.add(sb.toString());
            }
        }
        Tile gridTile = new Tile(0, gridStrings);
        int i = 0;
        while (i++ < 2) {
            gridTile = gridTile.rotateLeft();
            gridTile = findMonsters(gridTile);
            gridTile = findMonsters(gridTile.flipHorizontally());
            gridTile = findMonsters(gridTile.flipHorizontally().flipVertically());
        }
        int count = 0;
        for (String row: gridTile.rows) {
            count += row.chars().map(d -> (char)d).boxed().filter(ch -> ch == '#').count();
        }
        return count;
    }

    static Pattern createPattern(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int spaceCount = 0;
        int hashCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                spaceCount++;
                if (hashCount > 0) {
                    sb.append("#".repeat(hashCount));
                    hashCount = 0;
                }
                if (i == str.length() - 1) {
                    sb.append("[.#]*");
                }
            } else if (str.charAt(i) == '#') {
                hashCount++;
                if (spaceCount > 0) {
                    sb.append("[.#]{");
                    sb.append(spaceCount);
                    sb.append("}");
                    spaceCount = 0;
                }
                if (i == str.length() - 1) {
                    sb.append("#".repeat(hashCount));
                }
            } else
                throw new IllegalStateException(String.valueOf(str.charAt(i)));
        }
        sb.append(")");
        return Pattern.compile(sb.toString());
    }

    final static String[] monster = {"                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   "};
    final static Pattern monsterRow1 = createPattern(monster[0]);
    final static Pattern monsterRow2 = createPattern(monster[1]);
    final static Pattern monsterRow3 = createPattern(monster[2]);
    final static int monsterWidth = 20;

    static Tile findMonsters(Tile gridTile) {
        List<String> seaRows = gridTile.rows;
        for (int i = 0; i < seaRows.size() - 2; i++) {
            for (int idx = 0; idx < seaRows.get(i).length() - monsterWidth; idx++) {
                List<Matcher> matchers = List.of(
                        monsterRow1.matcher(
                                seaRows.get(i).substring(idx, idx + monsterWidth)),
                        monsterRow2.matcher(
                                seaRows.get(i + 1).substring(idx, idx + monsterWidth)),
                        monsterRow3.matcher(
                                seaRows.get(i + 2).substring(idx, idx + monsterWidth))
                );
                if (matchers.get(0).find()) {
                    if (matchers.get(1).find()) {
                        if (matchers.get(2).find()) {
                            for (int rowIdx = 0; rowIdx < 3; rowIdx++) {
                                char[] match = matchers.get(rowIdx).group(1).toCharArray();
                                String monsterRow = monster[rowIdx];
                                int fromIdx = 0;
                                while (monsterRow.indexOf('#', fromIdx) != -1) {
                                    int hashIdx = monsterRow.indexOf('#', fromIdx);
                                    assert (match[hashIdx] == '#');
                                    match[hashIdx] = 'O';
                                    fromIdx = hashIdx + 1;
                                }
                                seaRows.set(i + rowIdx, seaRows.get(i + rowIdx).replace(matchers.get(rowIdx).group(1), new String(match)));
                            }
                        }
                    }
                }
            }
        }
        return gridTile;
    }
}
