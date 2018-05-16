package com.bbs.handlersystem.Network.ContentMessage;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

public class ContentOfTransactionMessage {

    @Getter
    @Setter
    private int gameId;

    @Getter
    @Setter
    private int cashToBet;

    @Getter
    @Setter
    private int coefficient;

    @Getter
    @Setter
    @NonNull
    private Timestamp timestamp;

    public ContentOfTransactionMessage(final int gameId,
                                       final int cashToBet,
                                       final int coefficient,
                                       @NonNull final Timestamp timestamp) {
        this.gameId = gameId;
        this.cashToBet = cashToBet;
        this.timestamp = timestamp;
        this.coefficient = coefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gameId, cashToBet, coefficient, timestamp);
    }

    @Override
    public String toString() {
        return "gameId = " +
                gameId +
                "\n" +
                "cashToBet = " +
                cashToBet +
                "\n" +
                "coefficient" +
                coefficient +
                "\n" +
                "timestamp = " +
                timestamp.toString();
    }

}
