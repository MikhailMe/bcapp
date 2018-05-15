package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Database.Store.UserStore;
import com.bbs.handlersystem.Database.StoreConnection;
import com.bbs.handlersystem.Database.UserProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStoreImpl implements UserStore {

    private static long size = 0;
    private static final String GET_USER_ID = "SELECT id FROM users WHERE nickname = ?";
    private static final String SET_USER_TOKEN = "UPDATE users SET has_token = ? WHERE nickname = ?";
    private static final String SET_USER_ORACLE = "UPDATE users SET is_oracle = ? WHERE nickname = ?";
    private static final String GET_NUMBER_BY_ID_QUERY = "SELECT mobile_number FROM users WHERE id = ?";
    private static final String GET_USER_BY_ID = "SELECT nickname, mobile_number FROM users WHERE id = ?";
    private static final String ADD_USER_QUERY = "INSERT INTO users (nickname, has_token, is_oracle, mobile_number) VALUES (?, ?, ?, ?)";

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_USER_QUERY);
        preparedStatement.setString(1, user.getNickname());
        preparedStatement.setBoolean(2, user.isHasToken());
        preparedStatement.setBoolean(3, user.isOracle());
        preparedStatement.setString(4, user.getMobileNumber());
        preparedStatement.execute();
        size++;
    }

    @Override
    public User getById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_USER_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        String name = "";
        String mobileNumber = "";
        if (resultSet.next()) {
            name = resultSet.getString(COLUMN_NICKNAME);
            mobileNumber = resultSet.getString(COLUMN_NICKNAME);
        }
        return new User(name, mobileNumber);
    }

    @Override
    public long getId(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_USER_ID);
        preparedStatement.setString(1, nickname);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public String getMobileNumber(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_NUMBER_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        String mobileNumber = "";
        if (resultSet.next()) {
            mobileNumber = resultSet.getString(COLUMN_MOBILE_NUM);
        }
        return mobileNumber;
    }

    @Override
    public void changeUserProperties(UserProperties type, String nickname, boolean value) throws SQLException {
        boolean isToken = type.equals(UserProperties.TOKEN);
        PreparedStatement preparedStatement =
                StoreConnection.getConnection().prepareStatement(isToken ? SET_USER_TOKEN : SET_USER_ORACLE);
        preparedStatement.setBoolean(1, value);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }
}
