package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Wallet {

    @Getter
    @Setter
    @NonNull
    private User user;

    @Getter
    @Setter
    @NonNull
    private long balance;

    public Wallet(@NonNull final User user) {
        this.user = user;
        this.balance = 0;
    }

    public void changeBalance(@NonNull final long value,
                              @NonNull final boolean isAdd) {
        balance = isAdd ? (balance += value) : (balance -= value);
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
