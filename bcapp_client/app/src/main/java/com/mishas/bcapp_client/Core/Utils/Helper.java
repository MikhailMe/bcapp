package com.mishas.bcapp_client.Core.Utils;

import com.mishas.bcapp_client.Core.Data.Game;
import com.mishas.bcapp_client.Core.Data.Team;

import lombok.NonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class Helper {

    @NonNull
    public static Team createTeam() {
        return new Team(RandomGenerator.randomString(10));
    }

    @NonNull
    public static Pair<Team, Team> createPairTeam() {
        return new Pair<>(createTeam(), createTeam());
    }

    @NonNull
    public static Game createGame() {
        Pair<Team, Team> teams = createPairTeam();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Game(teams, timestamp);
    }

    @NonNull
    public static List<Game> createListOfGames(final int capacity) {
        List<Game> list = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            list.add(createGame());
        }
        return list;
    }
}
