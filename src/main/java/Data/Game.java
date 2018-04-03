package Data;

import Utils.Pair;
import org.jetbrains.annotations.NotNull;

public class Game {

    @NotNull
    private Pair<Team, Team> teams;
    @NotNull
    private Pair<Integer, Integer> goals;

    public Game(@NotNull Pair<Team, Team> teams,
                @NotNull Pair<Integer, Integer> goals) {
        this.teams = teams;
        this.goals = goals;
    }

    @NotNull
    public Pair<Team, Team> getTeams() {
        return teams;
    }

    public void setTeams(@NotNull Pair<Team, Team> teams) {
        this.teams = teams;
    }

    @NotNull
    public Pair<Integer, Integer> getGoals() {
        return goals;
    }

    public void setGoals(@NotNull Pair<Integer, Integer> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return teams.toString() +
                "\n" +
                goals.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + teams.hashCode();
        hash = 19 * hash + goals.hashCode();
        return hash;
    }

}
