package adventofcode2020.day23;

import lombok.Getter;

public class CupList {
    private Cup head = null;
    private Cup tail = null;
    @Getter
    private int maxValue;

    public static class Cup {
        final Integer value;
        Cup next;

        public Cup(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public void add(int value) {
        Cup number = new Cup(value);
        if (head == null) {
            head = number;
        } else {
            tail.next = number;
        }
        tail = number;
        tail.next = head;

        if (maxValue < value)
            this.maxValue = value;
    }

    public Cup find(int value) {
        Cup currentCup = head;
        if (head == null) {
            return null;
        } else {
            do {
                if (currentCup.value == value) {
                    return currentCup;
                }
                currentCup = currentCup.next;
            } while (currentCup != head);
            return null;
        }
    }

    String printCupNumbers() {
        Cup currentCup = head.next;
        StringBuilder sb = new StringBuilder();
        while(currentCup != head) {
            sb.append(currentCup.value);
            currentCup = currentCup.next;
        }
        return sb.toString();
    }

    public Cup getHead() {
        return head;
    }

    public Cup moveAndGetHead() {
        Cup prevHead = this.head;
        this.head = prevHead.next;
        this.tail = prevHead;
        return this.head;
    }

    /**
     * Returns deleted cup
     */
    public Cup deleteNextAfter(Cup cup) {
        Cup nextCup = cup.next;
        if (this.maxValue == nextCup.value)
            this.maxValue--;
        cup.next = nextCup.next;
        return nextCup;
    }

    /**
     * Returns inserted cup
     */
    public Cup insertAfter(Cup cup, int value) {
        Cup newCup = new Cup(value);
        Cup nextCup = cup.next;
        cup.next = newCup;
        newCup.next = nextCup;
        if (this.maxValue < value)
            this.maxValue = value;
        return newCup;
    }

    @Override
    public String toString() {
        Cup currentCup = head;
        StringBuilder sb = new StringBuilder();
        if (head != null) {
            do {
                if (currentCup == head) {
                    sb.append("(" + currentCup.value + ") ");
                } else {
                    sb.append(currentCup.value + " ");
                }
                currentCup = currentCup.next;
            } while (currentCup != head);
        }
        return sb.toString();
    }
}
