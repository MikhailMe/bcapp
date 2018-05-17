package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Coefficients.Coefficient;
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
    protected Coefficient coefficient;

    public BetTransaction(final int gameId,
                          final int betToCash,
                          @NonNull final User user,
                          @NonNull final Timestamp timestamp,
                          @NonNull final Coefficient coefficient) {
        super(user, timestamp);
        this.gameId = gameId;
        this.betToCash = betToCash;
        this.coefficient = coefficient;
    }

}
