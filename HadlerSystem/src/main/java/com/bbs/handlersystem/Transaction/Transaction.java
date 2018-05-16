package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Validator.Validator;
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

    protected boolean isValid() {
        return Validator.isValid(this);
    }
}
