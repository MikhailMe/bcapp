package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Account {

    @Getter
    @NonNull
    private Wallet wallet;

    @Getter
    @NonNull
    private Timestamp currentVisit;

    @Getter
    private List<Bet> listOfUserBets;

    @Getter
    private List<Timestamp> listOfVisitDates;

    {
        this.listOfUserBets = new ArrayList<>();
        this.listOfVisitDates = new ArrayList<>();
    }

    public Account(@NonNull final Wallet wallet) {
        this.wallet = wallet;
        this.currentVisit = new Timestamp(System.currentTimeMillis());
        this.listOfVisitDates.add(currentVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wallet, currentVisit, listOfUserBets, listOfVisitDates);
    }

    @Override
    public String toString() {
        return wallet.getUser().toString() + currentVisit.toString();
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
    }

}
