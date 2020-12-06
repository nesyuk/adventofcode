package adventofcode2020.day5;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class BinaryBoarding {

    private static int rowCodeLength(int rows) {
        return (int) (Math.log(rows) / (Math.log(2)));
    }

    private static boolean isInFirstHalf(String codes, int idx) {
        return codes.charAt(idx) == 'F' || codes.charAt(idx) == 'L';
    }

    private static boolean isInSecondHalf(String codes, int idx) {
        return codes.charAt(idx) == 'B' || codes.charAt(idx) == 'R';
    }

    private static int search(String codes, int idx, int startIdx, int endIdx) {
        int midIdx = (endIdx + startIdx) / 2;

        if (idx == (codes.length() - 1))
            return isInFirstHalf(codes, idx) ? startIdx : endIdx;

        if (isInFirstHalf(codes, idx)) {
            return search(codes, idx + 1, startIdx, midIdx);
        } else if (isInSecondHalf(codes, idx)) {
            return search(codes,idx + 1, midIdx + 1, endIdx);
        }
        return -1;
    }

    public static int seatId(String ticketCode, int rows, int cols) {
        String rowCode = ticketCode.substring(0, rowCodeLength(rows));
        String columnCode = ticketCode.substring(rowCodeLength(rows));
        int rowId = search(rowCode, 0, 0, rows - 1);
        int colId = search(columnCode, 0, 0, cols - 1);
        return rowId * 8 + colId;
    }

    public static int highestSeatId(@NonNull String inputFilePath, int rows, int cols) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        int maxId = -1;
        while (scanner.hasNextLine())
            maxId = Math.max(seatId(scanner.nextLine(), rows, cols), maxId);
        return maxId;
    }

    public static int missingSeatId(@NonNull String inputFilePath, int rows, int cols) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        int seats[] = new int[(rows - 1) * 8 + cols -1];
        int minSeatId = rows;
        int maxSeatId = -1;
        Arrays.fill(seats, 0);
        while (scanner.hasNextLine()) {
            int seatId = seatId(scanner.nextLine(), rows, cols);
            seats[seatId] = 1;
            minSeatId = Math.min(seatId, minSeatId);
            maxSeatId = Math.max(maxSeatId, seatId);
        }
        for (int seatId = minSeatId; seatId <= maxSeatId; seatId++) {
            if (seats[seatId] == 0)
                return seatId;
        }
        return -1;
    }
}
