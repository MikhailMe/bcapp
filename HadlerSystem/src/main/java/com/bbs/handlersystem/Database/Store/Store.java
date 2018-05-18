package com.bbs.handlersystem.Database.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Store<T> {

    String COLUMN_ID = "id";
    String COLUMN_TEAM1 = "team1";
    String COLUMN_TEAM2 = "team2";
    String COLUMN_TOKEN = "token";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_BALANCE = "balance";
    String COLUMN_GAME_ID = "game_id";
    String COLUMN_COEF = "coefficient";
    String COLUMN_BET_DATE = "bet_date";
    String COLUMN_NICKNAME = "nickname";
    String COLUMN_WALLET_ID = "wallet_id";
    String COLUMN_GAME_DATE = "game_date";
    String COLUMN_CASH_TO_BET = "cash_to_bet";
    String COLUMN_GOALS_TEAM1 = "goals_team1";
    String COLUMN_GOALS_TEAM2 = "goals_team2";
    String COLUMN_MOBILE_NUM = "mobile_number";

    long size();

    long add(T object) throws SQLException;

    T getById(long id) throws SQLException;

    long getId(String string) throws SQLException;

    default long getIdFromResultSet(ResultSet resultSet) throws SQLException {
        long id = -1L;
        if (resultSet.next()) {
            id = resultSet.getLong(COLUMN_ID);
        }
        return id;
    }

}
