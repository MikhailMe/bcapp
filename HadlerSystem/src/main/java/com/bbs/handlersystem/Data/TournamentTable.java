package com.bbs.handlersystem.Data;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TournamentTable {

    @NonNull
    private List<Team> teams;
    @NonNull
    private List<Integer> places;
    @NonNull
    private List<Integer> points;
    @NonNull
    private List<Integer> amountOfGames;

    public TournamentTable() {
        this.teams = new ArrayList<>();
        this.places = new ArrayList<>();
        this.points = new ArrayList<>();
        this.amountOfGames = new ArrayList<>();
    }

    public TournamentTable(@NonNull final List<Team> teams,
                           @NonNull final List<Integer> places,
                           @NonNull final List<Integer> points,
                           @NonNull final List<Integer> amountOfGames) {
        this.teams = teams;
        this.places = places;
        this.points = points;
        this.amountOfGames = amountOfGames;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teams, places, points, amountOfGames);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%-6s %-15s %-10s %-10s",
                "место",
                "команда",
                "игры",
                "очки")).append("\n");
        for (int i = 0; i < teams.size(); i++) {
            result.append(
                    String.format("%-6d  %-15s %-10d %-10d",
                            places.get(i),
                            teams.get(i),
                            amountOfGames.get(i),
                            points.get(i)))
                    .append("\n");
        }
        return result.toString();
    }
}
