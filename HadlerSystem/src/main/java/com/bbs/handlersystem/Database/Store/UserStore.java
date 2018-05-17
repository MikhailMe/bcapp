package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Database.UserProperties;

import java.sql.SQLException;

public interface UserStore extends Store<User> {

    String GET_USER_ID_QUERY = "SELECT id FROM users WHERE nickname = ?";
    String SET_USER_TOKEN_QUERY = "UPDATE users SET has_token = ? WHERE nickname = ?";
    String SET_USER_ORACLE_QUERY = "UPDATE users SET is_oracle = ? WHERE nickname = ?";
    String GET_NUMBER_BY_ID_QUERY = "SELECT mobile_number FROM users WHERE id = ?";
    String GET_USER_BY_ID_QUERY = "SELECT nickname, mobile_number FROM users WHERE id = ?";
    String ADD_USER_QUERY = "INSERT INTO users (nickname, has_token, is_oracle, mobile_number) VALUES (?, ?, ?, ?)";

    String getMobileNumber(long id) throws SQLException;

    void changeUserProperties(UserProperties type, String nickname, boolean value) throws SQLException;

}
