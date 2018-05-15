package com.bbs.handlersystem.Utils;

import lombok.NonNull;

import java.util.Locale;
import java.util.Random;

public final class RandomGenerator {

    private static final double DELIMITER = 0.5d;
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static int randInt() {
        return new Random().nextInt(10) + 1;
    }

    @NonNull
    public static String randomString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            if (new Random().nextDouble() > DELIMITER) {
                sb.append(upper.charAt(randInt()));
            } else {
                sb.append(lower.charAt(randInt()));
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String randomDigitsString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randInt());
        }
        return sb.toString();
    }
}