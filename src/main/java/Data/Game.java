package Data;

import Utils.Pair;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class Game {

    @Getter
    @Setter
    @NotNull
    private Pair<Team, Team> teams;

    @Getter
    @Setter
    @NotNull
    private Pair<Integer, Integer> goals;

    @Getter
    @Setter
    private Pair<LocalTime, LocalTime> times;

    public Game(@NotNull Pair<Team, Team> teams,
                @NotNull Pair<Integer, Integer> goals) {
        this.teams = teams;
        this.goals = goals;
        this.times = null;

    }

    public Game(@NotNull Pair<Team, Team> teams,
                @NotNull Pair<Integer, Integer> goals,
                @NotNull Pair<LocalTime, LocalTime> times) {
        this.teams = teams;
        this.goals = goals;
        this.times = times;
    }

    @Override
    public String toString() {
        return teams.toString() +
                "\n" +
                goals.toString();
                /* +
                "\n" +
                times.toString()*/
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + teams.hashCode();
        hash = 19 * hash + goals.hashCode();
        hash = 19 * hash + times.hashCode();
        return hash;
    }

}
