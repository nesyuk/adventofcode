package adventofcode2020.day22;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CrabCombat {

    public static Deque<Integer> parseDeck(String inputStr) {
        return Arrays.stream(inputStr.split("\n"))
                .skip(1)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public static Deque<Integer> combat(Deque<Integer> deck1, Deque<Integer> deck2) {
        while(!(deck1.isEmpty() || deck2.isEmpty())) {
            Integer top1 = deck1.removeFirst();
            Integer top2 = deck2.removeFirst();
            if (top1 > top2) {
                deck1.addLast(top1);
                deck1.addLast(top2);
            } else {
                deck2.addLast(top2);
                deck2.addLast(top1);
            }
        }

        return deck1.isEmpty() ? deck2 : deck1;
    }

    public static Deque<Integer> createSubDeck(Deque<Integer> deck, int size) {
        Deque<Integer> subDeck = new ArrayDeque<>(deck);
        while(subDeck.size() != size) {
            subDeck.removeLast();
        }
        return subDeck;
    }

    public static Pair<Integer, Deque<Integer>> recursiveCombat(Deque<Integer> deck1, Deque<Integer> deck2) {
        Set<Pair<List<Integer>, List<Integer>>> deckHistory = new HashSet<>();
        while(!(deck1.isEmpty() || deck2.isEmpty())) {
            Pair<List<Integer>, List<Integer>> decks = Pair.of(new ArrayList<>(deck1), new ArrayList<>(deck2));
            if (!deckHistory.contains(decks))
                deckHistory.add(decks);
            else
                return Pair.of(1, deck1);
            Integer top1 = deck1.removeFirst();
            Integer top2 = deck2.removeFirst();
            Integer winner = top1 > top2 ? 1 : 2;
            if (deck1.size() >= top1 && deck2.size() >= top2) {
                Pair<Integer, Deque<Integer>> winnerOfSubGame = recursiveCombat(
                        createSubDeck(deck1, top1), createSubDeck(deck2, top2));
                winner = winnerOfSubGame.getLeft() == 1 ? 1 : 2;
            }
            if (winner == 1) {
                deck1.addLast(top1);
                deck1.addLast(top2);
            } else {
                deck2.addLast(top2);
                deck2.addLast(top1);
            }
        }
        return deck1.isEmpty() ? Pair.of(2, deck2) : Pair.of(1, deck1);
    }

    public static int play(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        Deque<Integer> deck1 = parseDeck(scanner.next());
        Deque<Integer> deck2 = parseDeck(scanner.next());
        Deque<Integer> winner = combat(deck1, deck2);
        int i = 1;
        int res = 0;
        while(!winner.isEmpty()) {
            res += winner.removeLast() * i++;
        }
        return res;
    }

    public static int playRecursively(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile()).useDelimiter("\n\n");
        Deque<Integer> deck1 = parseDeck(scanner.next());
        Deque<Integer> deck2 = parseDeck(scanner.next());
        Pair<Integer, Deque<Integer>> winner = recursiveCombat(deck1, deck2);
        int i = 1;
        int res = 0;
        Deque<Integer> winnerDeck = winner.getLeft() == 1 ? deck1 : deck2;
        while(!winnerDeck.isEmpty()) {
            res += winnerDeck.removeLast() * i++;
        }
        return res;
    }
}
