package adventofcode2020.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CrabCups2 {

    public static List<Integer> remove(List<Integer> cups, int fromIdx) {
        int i = (fromIdx+1) % cups.size();
        int j = (i + 1) % cups.size();
        while(j != fromIdx && cups.get(j) != null) {
            cups.set(i, cups.get(j));
            cups.set(j, null);
            i = (i + 1) % cups.size();
            j = (i + 1) % cups.size();
        }
        return cups;
    }

    public static List<Integer> add(List<Integer> cups, int fromIdx, Integer cup) {
        int i = (fromIdx+1) % cups.size();
        int j = i;
        while(cups.get(j) != null) {
            j = (j + 1) % cups.size();
        }
        while(j != i) {
            int k = (cups.size() + j - 1) % cups.size();
            cups.set(j, cups.get(k));
            j = k;
        }
        cups.set(i, cup);
        return cups;
    }

    public static List<Integer> playCrabCups(List<Integer> cups, int currentCupIdx, int round, int totalRounds) {
        if (round > totalRounds)
            return cups;
        System.out.println("round " + round);
        System.out.println("cups: " + cups);

        int currentCup = cups.get(currentCupIdx);
        List<Integer> pickedUp = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            int pickUpIdx = (currentCupIdx+i) % cups.size();
            pickedUp.add(cups.get(pickUpIdx));
        }
        System.out.println("pickup: " + pickedUp);

        remove(cups, currentCupIdx);
        remove(cups, currentCupIdx);
        remove(cups, currentCupIdx);

        int next = currentCup - 1;
        if (next <= 0)
            next = cups.stream().filter(Objects::nonNull).max(Integer::compareTo).get();
        while(!cups.contains(next)) {
            next -= 1;
            if (next <= 0)
                next = cups.stream()
                        .filter(Objects::nonNull)
                        .max(Integer::compareTo).get();
        }
        System.out.println("destination: " + next);
        for (int i = 0; i < 3; i++) {
            add(cups, (cups.indexOf(next) + i) % cups.size(), pickedUp.get(i));
        }
        return playCrabCups(cups, (currentCupIdx + 1) % cups.size(), round+1, totalRounds);
    }

    public static String play(int startSequence, int numOfRounds) {
        List<Integer> numbers = String.valueOf(startSequence).chars()
                .map(i -> Integer.parseInt(String.valueOf((char) i)))
                .boxed()
                .collect(Collectors.toList());
        List<Integer> cups = playCrabCups(numbers, 0, 1, numOfRounds);
        cups.indexOf(1);
        StringBuilder resultStr = new StringBuilder();
        for (int idx = (cups.indexOf(1) + 1)% cups.size(); idx != cups.indexOf(1); idx = (idx + 1) % cups.size()) {
            resultStr.append(cups.get(idx));
        }
        return resultStr.toString();
    }

    public static String playMillion(int startSequence, int numOfRounds) {
        List<Integer> numbers = String.valueOf(startSequence).chars()
                .map(i -> Integer.parseInt(String.valueOf((char) i)))
                .boxed()
                .collect(Collectors.toList());
        CupList numberList = new CupList();
        numbers.forEach(numberList::add);
        System.out.println(numberList);
        return "";
    }
}
