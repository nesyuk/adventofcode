package adventofcode2020.day25;

import java.math.BigInteger;

public class ComboBreaker {

    static int calculateLoopSize(int publicKey, int subjectNumber) {
        int value = 1;
        int loopSize = 0;
        while(value != publicKey) {
            value *= subjectNumber;
            value %= 20201227;
            loopSize++;
        }

        return loopSize;
    }

    static BigInteger calculateEncryptionKey(int publicKey, int loopSize) {
        BigInteger value = BigInteger.ONE;
        BigInteger pk = BigInteger.valueOf(publicKey);
        for (int ls = 0; ls < loopSize; ls++) {
            value = value.multiply(pk);
            value = value.remainder(BigInteger.valueOf(20201227));
        }
        return value;
    }

    public static BigInteger findEncryptionKey(int cardPK, int doorPK, int subjectNumber) {
        int cardLoopSize = calculateLoopSize(cardPK, subjectNumber);
        int doorLoopSize = calculateLoopSize(doorPK, subjectNumber);
        BigInteger encryptionKey = calculateEncryptionKey(doorPK, cardLoopSize);
        assert(encryptionKey.equals(calculateEncryptionKey(cardPK, doorLoopSize)));
        return encryptionKey;
    }
}
