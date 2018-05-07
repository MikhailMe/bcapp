package com.bbs.handlersystem.Client;

import com.bbs.handlersystem.Bet.Bet;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: всё запихать в базку

public class Account {

    @Getter
    @NonNull
    private User user;

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

    public Account() {
        this.user = null;
        this.wallet = null;
        this.currentVisit = null;
    }

    public Account(@NonNull User user) {
        this.user = user;
        this.wallet = new Wallet(user);
        this.currentVisit = LocalDate.now();
        this.listOfVisitDates.add(currentVisit);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + user.hashCode();
        hash = 31 * hash + wallet.hashCode();
        hash = 31 * hash + currentVisit.hashCode();
        hash = 31 * hash + listOfUserBets.hashCode();
        hash = 31 * hash + listOfVisitDates.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return user.toString() + currentVisit.toString();
    }

}
