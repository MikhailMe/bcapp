package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Database.Store.TokenStore;
import com.bbs.handlersystem.Database.StoreConnection;
import com.bbs.handlersystem.Token.Token;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenStoreImpl implements TokenStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public long add(Token token) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_TOKEN_QUERY);
        preparedStatement.setString(1, token.getValue());
        preparedStatement.execute();
        size++;
        return size;
    }

    @Override
    public Token getById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_TOKEN_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        String token = "";
        if (resultSet.next()) {
            token = resultSet.getString(COLUMN_TOKEN);
        }
        return new Token(token);
    }

    @Override
    public long getId(String string) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_TOKEN_ID_QUERY);
        preparedStatement.setString(1, string);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public void removeTokenById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(REMOVE_TOKEN_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
    }

    @Override
    public void updateTokenById(long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(UPDATE_ID_BY_TOKEN_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, getById(id).toString());
        preparedStatement.execute();
    }
}
