package adventofcode2020.day13;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ShuttleSearch {

    public static int nextClosestArrival(int bus, int arrivalTime) {
        if(arrivalTime % bus == 0)
            return arrivalTime;
        return bus * (arrivalTime/bus + 1) - arrivalTime;
    }

    public static int findClosestBus(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        int arrivalTime = Integer.parseInt(scanner.nextLine());
        Set<Integer> buses = Arrays.stream(scanner.nextLine().split(","))
                .filter(ch -> !ch.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        Pair<Integer, Integer> busWaitingTime = buses.stream()
                .map(bus -> Pair.of(bus, nextClosestArrival(bus, arrivalTime)))
                .min(Comparator.comparingInt(Pair::getRight)).get();
        return busWaitingTime.getLeft() * busWaitingTime.getRight();
    }

    public static BigInteger findSequence(List<Pair<Integer, Integer>> schedule) {
        // Chinese remainder theorem

        BigInteger M = schedule.stream().map(Pair::getLeft)
                .map(b -> BigInteger.valueOf(b))
                .reduce(BigInteger::multiply).get();

        return schedule.stream().map(pair -> {
            BigInteger a_i = BigInteger.valueOf(pair.getLeft());
            BigInteger r_i = BigInteger.valueOf(pair.getRight());
            BigInteger M_i = M.divide(a_i);
            BigInteger M_i1 = M_i.modPow(BigInteger.valueOf(-1), a_i);
            return r_i.multiply(M_i).multiply(M_i1);
        }).reduce(BigInteger::add).get().mod(M);
    }

    public static BigInteger findBusSequence(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        scanner.nextLine();
        List<Integer> buses = Arrays.stream(scanner.nextLine().split(","))
                .map(ch -> ch.equals("x") ? null : Integer.parseInt(ch))
                .collect(Collectors.toList());
        List<Pair<Integer, Integer>> busesGaps = new ArrayList<>();
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i) != null) {
                busesGaps.add(Pair.of(buses.get(i), buses.get(i) - i));
            }
        }
        return findSequence(busesGaps);
    }
}
