package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class Wallet {

    @Getter
    @Setter
    @NonNull
    private User user;

    @Getter
    @Setter
    private long balance;

    public Wallet(@NonNull final User user) {
        this.user = user;
        this.balance = 0;
    }

    public void changeBalance(final long value,
                              final boolean isAdd) {
        balance = isAdd ? (balance += value) : (balance -= value);
    }

    @NonNull
    public boolean isPositive() {
        return balance > 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, balance);
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
