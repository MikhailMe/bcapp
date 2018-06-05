package jp.co.soramitsu.iroha.android.sample.core;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

public final class Team {

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

    public void addPoints(final int points) {
        this.points += points;
    }

    public void setGoals(final int amountOfScoredGoals,
                         final int amountOfMissedGoals) {
        this.amountOfScoredGoals = amountOfScoredGoals;
        this.amountOfMissedGoals = amountOfMissedGoals;
    }

    public int getDifference() {
        return amountOfScoredGoals - amountOfMissedGoals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points, name, amountOfScoredGoals, amountOfMissedGoals);
    }

    @Override
    public String toString() {
        return this.name;
    }

}