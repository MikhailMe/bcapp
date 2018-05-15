package com.bbs.handlersystem.Utils;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static Team createTeam() {
        return new Team(RandomGenerator.randomString(10));
    }

    private static Pair<Team, Team> createPairTeam() {
        return new Pair<>(createTeam(), createTeam());
    }

    private static Game createGame() {
        Pair<Team, Team> teams = createPairTeam();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Game(teams, timestamp);
    }

    public static List<Game> getListOfGames() {
        List<Game> list = new ArrayList<>(10);
        list.forEach(game -> game = createGame());
        return list;
    }
}
