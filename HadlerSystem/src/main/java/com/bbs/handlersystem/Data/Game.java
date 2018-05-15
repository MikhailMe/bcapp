package com.bbs.handlersystem.Data;

import com.bbs.handlersystem.Utils.Pair;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

public final class Game {

    @Getter
    @Setter
    @NonNull
    private Pair<Team, Team> teams;

    @Getter
    @Setter
    @NonNull
    private Pair<Integer, Integer> goals;

    @Getter
    @Setter
    private LocalTime time;

    public Game(@NonNull final Pair<Team, Team> teams) {
        this.teams = teams;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final LocalTime time) {
        this.teams = teams;
        this.time = time;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Pair<Integer, Integer> goals) {
        this.teams = teams;
        this.goals = goals;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Pair<Integer, Integer> goals,
                @NonNull final LocalTime time) {
        this.teams = teams;
        this.goals = goals;
        this.time = time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teams, goals, time);
    }

    // FIXME
    @Override
    public String toString() {
        return teams.toString() +
                "\n" +
                goals.toString();
                /* +
                "\n" +
                times.toString()*/
    }

}
