package com.bbs.handlersystem.Token;

import lombok.Getter;

import java.util.Random;

public class Token implements Tokenable {

    @Getter
    private String token;

    public Token() {
        this.token = String.valueOf(makeToken());
    }

    @Override
    public long makeToken() {
        long token = 3;
        token = 31 * token * System.currentTimeMillis() - new Random().nextLong();
        token = 31 * token + System.currentTimeMillis() / new Random().nextLong();
        return token;
    }
}
