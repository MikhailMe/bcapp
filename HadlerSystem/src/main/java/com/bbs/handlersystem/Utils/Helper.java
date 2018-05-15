package com.bbs.handlersystem.Utils;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Network.Wrappers.GameMessage;
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
    public static List<GameMessage> getListGameMessages(List<Game> games) {
        List<GameMessage> gameMessages = new ArrayList<>(games.size());
        games.forEach(game ->  {
            String team1 = game.getTeams().getFirst().toString();
            String team2 = game.getTeams().getSecond().toString();
            Timestamp timestamp = game.getTimestamp();
            gameMessages.add(new GameMessage(team1, team2, timestamp));
        });
        return gameMessages;
    }
}
