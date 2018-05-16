package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Client.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

public final class BetTransaction extends Transaction {

    @Getter
    @Setter
    protected int gameId;

    @Getter
    @Setter
    protected int betToCash;

    @Getter
    @Setter
    protected int coefficient;

    public BetTransaction(final int gameId,
                          final int betToCash,
                          final int coefficient,
                          @NonNull final User user,
                          @NonNull final Timestamp timestamp) {
        super(user, timestamp);
        this.gameId = gameId;
        this.betToCash = betToCash;
        this.coefficient = coefficient;
    }

}
