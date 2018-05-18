package com.bbs.handlersystem.Token;

import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;

public final class Token {

    @Setter
    private String token;

    public Token() {
        this.token = createToken();
    }

    public Token(@NonNull final String token) {
        this.token = token;
    }

    @NonNull
    public String getValue() {
        return token;
    }

    @NonNull
    private String createToken() {
        long token = 3;
        token = 31 * token * System.currentTimeMillis() - new Random().nextLong();
        token = 31 * token + System.currentTimeMillis() / new Random().nextLong();
        return String.valueOf(token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }

    @Override
    public String toString() {
        return token;
    }

}
