package com.bbs.handlersystem.Database.Store;

import java.sql.SQLException;

public interface Store<T> {

    String COLUMN_ID = "id";

    void add(T object) throws SQLException;

    long getId(String nickname) throws SQLException;

}
