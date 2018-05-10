package com.bbs.handlersystem.Database;

import com.bbs.handlersystem.Client.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainStore implements Store {

    private static final String ADD_USER_QUERY = "INSERT INTO users (nickname, has_token, is_oracle, mobile_number) VALUES (?, ?, ?, ?)";
    private static final String SET_USER_TOKEN = "UPDATE users SET has_token = ? WHERE nickname = ?";
    private static final String SET_USER_ORACLE = "UPDATE users SET is_oracle = ? WHERE nickname = ?";

    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_USER_QUERY);
        preparedStatement.setString(1, user.getNickname());
        preparedStatement.setBoolean(2, user.isHasToken());
        preparedStatement.setBoolean(3, user.isOracle());
        preparedStatement.setString(4, user.getMobileNumber());
        preparedStatement.execute();
    }

    @Override
    public void changeBooleanField(UserProperties type, String userNickname, boolean value) throws SQLException {
        PreparedStatement preparedStatement;
        if (type.equals(UserProperties.TOKEN)) {
            preparedStatement = StoreConnection.getConnection().prepareStatement(SET_USER_TOKEN);
        } else {
            preparedStatement = StoreConnection.getConnection().prepareStatement(SET_USER_ORACLE);
        }
        preparedStatement.setBoolean(1, value);
        preparedStatement.setString(2, userNickname);
        preparedStatement.execute();
    }

    @Override
    public void requestClientInformation() throws SQLException {

    }

    @Override
    public void responseClientInformation() throws SQLException {

    }

}
