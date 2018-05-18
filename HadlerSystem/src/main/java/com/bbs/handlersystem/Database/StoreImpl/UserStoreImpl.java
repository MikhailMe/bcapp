package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Database.Store.UserStore;
import com.bbs.handlersystem.Database.StoreConnection;
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
    public long add(@NonNull final User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_USER_QUERY);
        preparedStatement.setString(1, user.getNickname());
        preparedStatement.setLong(2, user.getTokenId());
        preparedStatement.setBoolean(3, user.isOracle());
        preparedStatement.setString(4, user.getMobileNumber());
        preparedStatement.execute();
        size++;
        return size;
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
    public void updateNickname(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(UPDATE_NICKNAME_QUERY);
        preparedStatement.setString(1, nickname);
        preparedStatement.setLong(2, getId(nickname));
        preparedStatement.execute();
    }

    @Override
    public void updateMobileNumber(String mobileNumber, String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(UPDATE_MOBILE_NUMBER_QUERY);
        preparedStatement.setString(1, mobileNumber);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }

    @Override
    public void updateToken(long tokenId, String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(UPDATE_TOKEN_QUERY);
        preparedStatement.setLong(1, tokenId);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }

    @Override
    public void updateOracle(boolean isOracle, String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(UPDATE_ORACLE_QUERY);
        preparedStatement.setBoolean(1, isOracle);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }

    @Override
    public void getTokenById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_TOKEN_BY_ID_QUERY);

    }
}
