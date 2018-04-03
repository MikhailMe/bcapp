package Data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TournamentTable {

    @NotNull
    private List<Team> teams;
    @NotNull
    private List<Integer> places;
    @NotNull
    private List<Integer> wins;
    @NotNull
    private List<Integer> draws;
    @NotNull
    private List<Integer> looses;
    @NotNull
    private List<Integer> points;
    @NotNull
    private List<Integer> amountOfGames;

    public TournamentTable() {
        this.teams = new ArrayList<>();
        this.places = new ArrayList<>();
        this.wins = new ArrayList<>();
        this.draws = new ArrayList<>();
        this.looses = new ArrayList<>();
        this.points = new ArrayList<>();
        this.amountOfGames = new ArrayList<>();
    }

    public TournamentTable(@NotNull final List<Team> teams,
                           @NotNull final List<Integer> places,
                           @NotNull final List<Integer> wins,
                           @NotNull final List<Integer> draws,
                           @NotNull final List<Integer> looses,
                           @NotNull final List<Integer> points,
                           @NotNull final List<Integer> amountOfGames) {
        this.teams = teams;
        this.places = places;
        this.wins = wins;
        this.draws = draws;
        this.looses = looses;
        this.points = points;
        this.amountOfGames = amountOfGames;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < teams.size(); i++) {
            result.append(
                    String.format("%-2d  %-15s %-10d %-10d %-10d %-10d %-10d", places.get(i),
                            teams.get(i),
                            amountOfGames.get(i),
                            wins.get(i),
                            draws.get(i),
                            looses.get(i),
                            points.get(i)))
                    .append("\n");
        }
        return result.toString();
    }
}
