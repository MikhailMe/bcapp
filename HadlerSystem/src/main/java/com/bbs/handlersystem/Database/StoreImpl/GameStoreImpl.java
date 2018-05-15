package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Database.Store.GameStore;
import com.bbs.handlersystem.Database.StoreConnection;
import com.bbs.handlersystem.Utils.Pair;
import lombok.NonNull;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public final class GameStoreImpl implements GameStore {

    private static long size = 0;
    private static final String GET_GAME_BY_ID = "SELECT team1, team2, game_date FROM games WHERE id = ?";
    private static final String ADD_GAME_QUERY = "INSERT INTO games (team1, team2, game_date) VALUES  (?, ?, ?)";
    private static final String GET_GAME_ID_QUERY = "SELECT id FROM  games WHERE games.team1 = ? AND games.team2 = ?";

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(@NonNull final Game game) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_GAME_QUERY);
        Pair<Team, Team> teams = game.getTeams();
        preparedStatement.setString(1, teams.getFirst().toString());
        preparedStatement.setString(2, teams.getSecond().toString());
        preparedStatement.setTimestamp(3, game.getTimestamp());
        preparedStatement.execute();
        size++;
    }

    @Override
    public Game getById(final long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_GAME_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        Team team1 = null;
        Team team2 = null;
        Timestamp timestamp = null;
        if (resultSet.next()) {
            team1 = new Team(resultSet.getString(COLUMN_TEAM1));
            team2 = new Team(resultSet.getString(COLUMN_TEAM2));
            timestamp = resultSet.getTimestamp(COLUMN_GAME_DATE);
        }
        Pair<Team, Team> teams = new Pair<>(Objects.requireNonNull(team1), Objects.requireNonNull(team2));
        Game game = new Game(teams);
        game.setTimestamp(timestamp);
        return game;
    }

    // pattern for @param nickname: "team1 team2"
    @Override
    public long getId(@NonNull final String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_GAME_ID_QUERY);
        String[] teams = nickname.split(" ");
        preparedStatement.setString(1, teams[0]);
        preparedStatement.setString(2, teams[1]);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

}
