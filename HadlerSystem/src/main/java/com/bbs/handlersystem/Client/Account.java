package com.bbs.handlersystem.Client;

import com.bbs.handlersystem.Bet.Bet;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Account {

    @Getter
    @NonNull
    private Wallet wallet;

    @Getter
    @NonNull
    private LocalDate currentVisit;

    @Getter
    private List<Bet> listOfUserBets;

    @Getter
    private List<LocalDate> listOfVisitDates;

    {
        this.listOfUserBets = new ArrayList<>();
        this.listOfVisitDates = new ArrayList<>();
    }

    public Account(@NonNull final Wallet wallet) {
        this.wallet = wallet;
        this.currentVisit = LocalDate.now();
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

}
