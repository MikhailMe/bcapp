package com.bbs.handlersystem.Network.Wrappers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class ClientInfoWrapper {

    private static final String HAVE_A = " have a ";

    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    private long balance;

    public ClientInfoWrapper(@NonNull final String name,
                             final long balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, balance);
    }

    @Override
    public String toString() {
        return name + HAVE_A + balance;
    }
}
