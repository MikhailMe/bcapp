package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Database.Store.GameStore;
import com.bbs.handlersystem.Database.StoreConnection;
import com.bbs.handlersystem.Utils.Pair;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class GameStoreImpl implements GameStore {

    // TODO : need fix WHERE = ? ??????
    private static long size = 0;
    private static final String GET_GAME_ID_QUERY = "SELECT id FROM  games WHERE games.team1 = ? AND games.team2 = ?";
    private static final String ADD_GAME_QUERY = "INSERT INTO games (team1, team2, game_date) VALUES  (?, ?, ?)";

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(Game game) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_GAME_QUERY);
        Pair<Team, Team> teams = game.getTeams();
        preparedStatement.setString(1, teams.getFirst().toString());
        preparedStatement.setString(2, teams.getSecond().toString());
        preparedStatement.setTime(3, Time.valueOf(game.getTimes().getFirst()));
        preparedStatement.execute();
        size++;
    }

    // pattern for @param nickname: "team1 team2"
    @Override
    public long getId(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_GAME_ID_QUERY);
        String[] teams = nickname.split(" ");
        preparedStatement.setString(1, teams[0]);
        preparedStatement.setString(2, teams[1]);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

}
