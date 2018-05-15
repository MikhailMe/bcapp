package com.bbs.handlersystem.Data;

import com.bbs.handlersystem.Utils.Pair;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
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
    private Timestamp timestamp;

    public Game(@NonNull final Pair<Team, Team> teams) {
        this.teams = teams;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Timestamp timestamp) {
        this.teams = teams;
        this.timestamp = timestamp;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Pair<Integer, Integer> goals) {
        this.teams = teams;
        this.goals = goals;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Pair<Integer, Integer> goals,
                @NonNull final Timestamp timestamp) {
        this.teams = teams;
        this.goals = goals;
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teams, goals, timestamp);
    }

    @Override
    public String toString() {
        return teams.toString() +
                "\n" +
                goals.toString() +
                "\n" +
                timestamp.toString();
    }

}
