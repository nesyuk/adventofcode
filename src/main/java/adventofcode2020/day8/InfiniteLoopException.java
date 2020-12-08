package adventofcode2020.day8;

import lombok.Getter;

public class InfiniteLoopException extends Exception {
    @Getter
    final int arg;

    public InfiniteLoopException(int arg) {
        this.arg = arg;
    }
}
