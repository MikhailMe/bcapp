package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Validator.Validator;

import java.time.LocalTime;

public abstract class Transaction {

    private long id;
    private LocalTime time;

    public boolean isValid() {
        return Validator.isValid(this);
    }
}
