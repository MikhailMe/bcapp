package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Validator.Validator;

public abstract class Transaction {

    public boolean isValid() {
        return Validator.isValid(this);
    }
}
