package adventofcode2020.day15;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RambunctiousRecitation {

    public static int playMemoGame(List<Integer> fromNumbers, int stop) throws FileNotFoundException {

        Map<Integer, List<Integer>> numberRoundMap = new HashMap<>();

        int lastNumber = -1;
        for (int i = 0; i < fromNumbers.size(); i++) {
            List<Integer> occurences = new ArrayList<>();
            occurences.add(i+1);
            lastNumber = fromNumbers.get(i);
            numberRoundMap.put(fromNumbers.get(i), occurences);
        }

        int round = fromNumbers.size() + 1;

        while(round <= stop) {
            List<Integer> occurences = numberRoundMap.get(lastNumber);
            if (occurences.size() == 1) {
                lastNumber = 0;
            } else {
                lastNumber = occurences.get(occurences.size()-1) - occurences.get(occurences.size()-2);
            }
            numberRoundMap.putIfAbsent(lastNumber, new ArrayList<>());
            numberRoundMap.get(lastNumber).add(round);
            round++;
        }

        return lastNumber;
    }
}
