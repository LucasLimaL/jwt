package jwt.application.service;

import java.math.BigInteger;

public final class Utils {
    public static boolean textContainsAnyNumber(String text) {
        return text.matches(".*\\d.*");
    }

    public static boolean isNumberNotPrime(Long number) {
        final var numberAsBigInt = BigInteger.valueOf(number);

        return !numberAsBigInt.isProbablePrime(100);
    }
}
