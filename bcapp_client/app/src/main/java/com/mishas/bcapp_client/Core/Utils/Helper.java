package com.mishas.bcapp_client.Core.Utils;

import com.mishas.bcapp_client.Core.ContentMessage.ContentOfGameMessage;
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

    @NonNull
    public static List<ContentOfGameMessage> getListGameMessages(List<Game> games) {
        List<ContentOfGameMessage> gameMessages = new ArrayList<>(games.size());
        for (Game game : games) {
            String team1 = game.getTeams().getFirst().toString();
            String team2 = game.getTeams().getSecond().toString();
            Timestamp timestamp = game.getTimestamp();
            gameMessages.add(new ContentOfGameMessage(team1, team2, timestamp));
        }
        return gameMessages;
    }
}
