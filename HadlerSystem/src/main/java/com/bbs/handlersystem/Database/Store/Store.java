package com.bbs.handlersystem.Database.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Store<T> {

    String COLUMN_ID = "id";

    long size();

    void add(T object) throws SQLException;

    long getId(String nickname) throws SQLException;

    default long getIdFromResultSet(ResultSet resultSet) throws SQLException {
        long id = -1L;
        if (resultSet.next()) {
            id = resultSet.getLong(COLUMN_ID);
        }
        return id;
    }

}
