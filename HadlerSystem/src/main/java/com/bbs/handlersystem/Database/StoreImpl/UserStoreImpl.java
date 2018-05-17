package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Database.Store.UserStore;
import com.bbs.handlersystem.Database.StoreConnection;
import com.bbs.handlersystem.Database.UserProperties;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserStoreImpl implements UserStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(@NonNull final User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_USER_QUERY);
        preparedStatement.setString(1, user.getNickname());
        preparedStatement.setBoolean(2, user.isHasToken());
        preparedStatement.setBoolean(3, user.isOracle());
        preparedStatement.setString(4, user.getMobileNumber());
        preparedStatement.execute();
        size++;
    }

    @Override
    public User getById(final long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_USER_BY_ID_QUERY);
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
    public long getId(@NonNull final String string) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_USER_ID_QUERY);
        preparedStatement.setString(1, string);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public String getMobileNumber(final long id) throws SQLException {
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
    public void changeUserProperties(@NonNull final UserProperties type,
                                     @NonNull final String nickname,
                                     final boolean value) throws SQLException {
        boolean isToken = type.equals(UserProperties.TOKEN);
        PreparedStatement preparedStatement =
                StoreConnection.getConnection().prepareStatement(isToken ? SET_USER_TOKEN_QUERY : SET_USER_ORACLE_QUERY);
        preparedStatement.setBoolean(1, value);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }
}
