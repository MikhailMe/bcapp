package Data;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Team {

    @Getter
    private int points;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @NotNull
    private List<Player> players;

    private int difference;
    private int amountOfScoredGoals;
    private int amountOfMissedGoals;

    public Team(String name) {
        this.name = name;
        this.amountOfScoredGoals = 0;
        this.amountOfMissedGoals = 0;
        this.players = new ArrayList<>();
    }

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
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

    private int getDifference() {
        return amountOfScoredGoals - amountOfMissedGoals;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + points;
        hash = 19 * hash + name.hashCode();
        hash = 19 * hash + players.hashCode();
        hash = 19 * hash + amountOfScoredGoals;
        hash = 19 * hash + amountOfMissedGoals;
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
