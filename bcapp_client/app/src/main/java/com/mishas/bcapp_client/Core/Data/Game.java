package com.mishas.bcapp_client.Core.Data;

import com.mishas.bcapp_client.Core.Utils.Pair;

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
    private Timestamp timestamp;

    public Game(@NonNull final Pair<Team, Team> teams) {
        this.teams = teams;
    }

    public Game(@NonNull final Pair<Team, Team> teams,
                @NonNull final Timestamp timestamp) {
        this.teams = teams;
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teams, timestamp);
    }

    @Override
    public String toString() {
        return teams.toString() +
                "\n" +
                timestamp.toString();
    }

}

