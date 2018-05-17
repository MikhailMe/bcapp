package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.Bet;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Coefficients.Coefficient;
import com.bbs.handlersystem.Database.Store.BetStore;
import com.bbs.handlersystem.Database.StoreConnection;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class BetStoreImpl implements BetStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(Bet bet) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_BET_QUERY);
        preparedStatement.setInt(1, bet.getGameId());
        preparedStatement.setInt(2, bet.getCashToBet());
        long userId = MainStore.userStore.getId(bet.getUser().getNickname());
        preparedStatement.setLong(3, userId);
        preparedStatement.setTimestamp(4, bet.getTimestamp());
        preparedStatement.setFloat(5, bet.getCoefficient().getCoef());
        preparedStatement.execute();
        size++;
    }

    @Override
    public Bet getById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_BET_ID_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        int gameId = -1;
        int cashToBet = -1;
        long userId = -1;
        Timestamp timestamp = null;
        Coefficient coefficient = new Coefficient();
        if (resultSet.next()) {
            gameId = resultSet.getInt(COLUMN_GAME_ID);
            cashToBet = resultSet.getInt(COLUMN_CASH_TO_BET);
            userId = resultSet.getLong(COLUMN_USER_ID);
            timestamp = resultSet.getTimestamp(COLUMN_BET_DATE);
            coefficient.setCoef(resultSet.getFloat(COLUMN_COEF));
        }
        User user = MainStore.userStore.getById(userId);
        return new Bet(gameId, cashToBet, user, timestamp, coefficient);
    }

    @Override
    public long getId(@NonNull final String string) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_BET_BY_ID_QUERY);
        int gameId = Integer.parseInt(string);
        preparedStatement.setInt(1, gameId);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }
}
