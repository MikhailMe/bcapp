package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.User;

import java.sql.SQLException;

public interface UserStore extends Store<User> {

    String GET_USER_ID_QUERY = "SELECT id FROM users WHERE nickname = ?";
    String GET_NUMBER_BY_ID_QUERY = "SELECT mobile_number FROM users WHERE id = ?";
    String GET_USER_BY_ID_QUERY = "SELECT nickname, mobile_number FROM users WHERE id = ?";
    String ADD_USER_QUERY = "INSERT INTO users (nickname, token_id, is_oracle, mobile_number) VALUES (?, ?, ?, ?)";

    String UPDATE_NICKNAME_QUERY = "UPDATE users SET nickname = ? where id = ?";
    String UPDATE_TOKEN_QUERY = "UPDATE users SET token_id = ? where nickname = ?";
    String UPDATE_ORACLE_QUERY = "UPDATE users SET is_oracle = ? where nickname = ?";
    String UPDATE_MOBILE_NUMBER_QUERY = "UPDATE users SET mobile_number = ? where nickname = ?";

    String GET_TOKEN_BY_ID_QUERY = "SELECT token FROM tokens WHERE id = ?";

    String getMobileNumber(long id) throws SQLException;

    void updateNickname(String nickname) throws SQLException;

    void updateMobileNumber(String mobileNumber, String nickname) throws SQLException;

    void updateToken(long tokenId, String nickname) throws SQLException;

    void updateOracle(boolean isOracle, String nickname) throws SQLException;

    void getTokenById(long id) throws SQLException;

}
