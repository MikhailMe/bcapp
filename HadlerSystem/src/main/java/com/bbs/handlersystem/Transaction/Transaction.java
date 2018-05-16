package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Client.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

public abstract class Transaction {

    @Getter
    @Setter
    @NonNull
    protected User user;

    @Getter
    @Setter
    @NonNull
    protected Timestamp timestamp;

    public Transaction(@NonNull final User user,
                       @NonNull final Timestamp timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

}
