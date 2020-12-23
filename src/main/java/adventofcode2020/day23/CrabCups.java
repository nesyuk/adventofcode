package adventofcode2020.day23;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
public class CrabCups {

    static class Cup {
        @Getter
        final int value;
        @Getter @Setter
        Cup next;

        Cup(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + "->" + String.valueOf(next.value);
        }
    }

    static void print(Cup cup) {
        StringBuilder sb = new StringBuilder();
        Cup current = cup;
        sb.append(current.value);
        current = cup.next;
        while(current != cup) {
            System.out.println(current.value);
            sb.append(current.value);
            current = current.next;
        }
        System.out.println(sb.toString());
    }

    public static void playCrabCups(Map<Integer, Cup> cupsMap, Cup current, int maxValue, int numberOfRounds) {
        for (int i = 1; i < numberOfRounds + 1; i++) {
            Cup c1 = current.next;
            Cup c3 = c1.next.next;
            current.next = c3.next;
            cupsMap.put(current.value, current);

            int value = current.value - 1;
            if (value <= 0)
                value = maxValue;
            while (value == c1.value || value == c1.next.value || value == c1.next.next.value) {
                value--;
                if (value <= 0)
                    value = maxValue;
            }

            Cup destination = cupsMap.get(value);
            Cup d1 = destination.next;
            destination.next = c1;
            c1.next.next.next = d1;
            current = current.next;
            cupsMap.put(current.value, current);
            cupsMap.put(destination.value, destination);
            cupsMap.put(c1.next.next.value, c1.next.next);
        }
    }

    public static Pair<Map<Integer, Cup>, Cup> parseCups(List<Integer> numbers) {
        List<Cup> cups = new ArrayList<>();
        numbers.stream().map(Cup::new).forEach(cups::add);

        Map<Integer, Cup> cupsMap = new HashMap<>();
        Cup current = null;
        for (int i = 0; i < cups.size(); i++) {
            current = cups.get(i);
            int nextIdx = (i + 1) % cups.size();
            current.next = cups.get(nextIdx);
            cupsMap.put(current.value, current);
        }

        return Pair.of(cupsMap, current.next);
    }

    public static String play(List<Integer> numbers, int numberOfRounds) {
        Pair<Map<Integer, Cup>, Cup> cupMapPair = parseCups(numbers);
        Map<Integer, Cup> cupsMap = cupMapPair.getLeft();
        Cup current = cupMapPair.getRight();
        playCrabCups(cupsMap, current, numbers.size(), numberOfRounds);
        Cup nextCup = cupsMap.get(1).next;
        StringBuilder sb = new StringBuilder();
        while (nextCup.value != 1) {
            sb.append(nextCup.value);
            nextCup = nextCup.next;
        }
        return sb.toString();
    }

    public static String playMillion(List<Integer> numbers, int numberOfRounds) {
        Pair<Map<Integer, Cup>, Cup> cupMapPair = parseCups(numbers);
        Map<Integer, Cup> cupsMap = cupMapPair.getLeft();
        Cup current = cupMapPair.getRight();
        playCrabCups(cupsMap, current, numbers.size(), numberOfRounds);
        Cup two = cupsMap.get(1).next;
        Cup three = two.next;
        return BigInteger.valueOf(two.value).multiply(BigInteger.valueOf(three.value)).toString();
    }
}
