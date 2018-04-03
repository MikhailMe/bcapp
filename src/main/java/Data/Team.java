package Data;

import java.util.List;

public class Team {

    private int points;
    private String name;
    private int difference;
    private List<Player> players;
    private int amountOfScoredGoals;
    private int amountOfMissedGoals;

    public Team(String name) {
        this.name = name;
        this.amountOfScoredGoals = 0;
        this.amountOfMissedGoals = 0;
    }

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
        this.amountOfScoredGoals = 0;
        this.amountOfMissedGoals = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setGoals(int amountOfScoredGoals, int amountOfMissedGoals) {
        this.amountOfScoredGoals = amountOfScoredGoals;
        this.amountOfMissedGoals = amountOfMissedGoals;
    }

    private int getDifference() {
        return amountOfScoredGoals - amountOfMissedGoals;
    }


    @Override
    public String toString() {
        return this.name;
    }

}
