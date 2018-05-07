package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

// TODO: всё запихать в базку

public class Wallet {

    private static final String NO_PERMISSION = "No permission";

    @Getter
    @Setter
    @NonNull
    private User user;

    @Getter
    @Setter
    @NonNull
    private long balance;

    Wallet(@NonNull final User user) {
        this.user = user;
        this.balance = 0;
    }

    public void increaseBy(@NonNull final long income) {
        balance += income;
    }

    public void decreaseBy(@NonNull final long expense) {
        balance -= expense;
    }

    @NonNull
    public boolean isPositive() {
        return balance > 0;
    }


    @Override
    public int hashCode() {
        return (int) (balance ^ (balance >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
