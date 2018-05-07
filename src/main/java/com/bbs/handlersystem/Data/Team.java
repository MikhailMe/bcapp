package com.bbs.handlersystem.Data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Team {

    @Getter
    private int points;

    @Getter
    @Setter
    private String name;

    private int amountOfScoredGoals;
    private int amountOfMissedGoals;

    public Team(@NonNull String name) {
        this.name = name;
        this.amountOfScoredGoals = 0;
        this.amountOfMissedGoals = 0;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setGoals(int amountOfScoredGoals, int amountOfMissedGoals) {
        this.amountOfScoredGoals = amountOfScoredGoals;
        this.amountOfMissedGoals = amountOfMissedGoals;
    }

    public int getDifference() {
        return amountOfScoredGoals - amountOfMissedGoals;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + points;
        hash = 19 * hash + name.hashCode();
        hash = 19 * hash + amountOfScoredGoals;
        hash = 19 * hash + amountOfMissedGoals;
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
