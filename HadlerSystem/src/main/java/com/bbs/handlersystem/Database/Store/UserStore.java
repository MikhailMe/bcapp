package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Database.UserProperties;

import java.sql.SQLException;

public interface UserStore extends Store<User> {

    String getMobileNumber(long id) throws SQLException;

    void changeUserProperties(UserProperties type, String nickname, boolean value) throws SQLException;

}
