package com.bbs.handlersystem.Database.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Store<T> {

    String COLUMN_ID = "id";
    String COLUMN_TEAM1 = "team1";
    String COLUMN_TEAM2 = "team2";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_BALANCE = "balance";
    String COLUMN_NICKNAME = "nickname";
    String COLUMN_WALLET_ID = "wallet_id";
    String COLUMN_GAME_DATE = "game_date";
    String COLUMN_MOBILE_NUM = "mobile_number";

    long size();

    void add(T object) throws SQLException;

    T getById(long id) throws SQLException;

    long getId(String nickname) throws SQLException;

    default long getIdFromResultSet(ResultSet resultSet) throws SQLException {
        long id = -1L;
        if (resultSet.next()) {
            id = resultSet.getLong(COLUMN_ID);
        }
        return id;
    }

}
