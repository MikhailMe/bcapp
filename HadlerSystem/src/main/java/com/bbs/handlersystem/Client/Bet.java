package com.bbs.handlersystem.Client;

import com.bbs.handlersystem.Coefficients.Coefficient;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

public final class Bet {

    @Getter
    @Setter
    @NonNull
    private User user;

    @Getter
    @Setter
    private int gameId;

    @Getter
    @Setter
    private int cashToBet;

    @Getter
    @Setter
    @NonNull
    private Timestamp timestamp;

    @Getter
    @Setter
    @NonNull
    private Coefficient coefficient;

    public Bet(final int gameId,
               final int cashToBet,
               @NonNull final User user,
               @NonNull final Timestamp timestamp,
               @NonNull final Coefficient coefficient) {
        this.user = user;
        this.gameId = gameId;
        this.cashToBet = cashToBet;
        this.timestamp = timestamp;
        this.coefficient = coefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, gameId, cashToBet, coefficient, timestamp);
    }

    @Override
    public String toString() {
        return "user = " +
                user.toString() +
                "\ngameId = " +
                gameId +
                "\ncashToBet = " +
                cashToBet +
                "\ncoefficient" +
                coefficient.toString() +
                "\ntimestamp = " +
                timestamp.toString();
    }
}
