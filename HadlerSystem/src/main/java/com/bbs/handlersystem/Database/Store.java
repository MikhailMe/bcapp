package com.bbs.handlersystem.Database;

import com.bbs.handlersystem.Client.User;

import java.sql.SQLException;

public interface Store {

    void addUser(User user) throws SQLException;
    void changeBooleanField(UserProperties type, String userNickname, boolean value) throws SQLException;

    // Are we need this methods ?
    void requestClientInformation() throws SQLException;
    void responseClientInformation() throws SQLException;


}
