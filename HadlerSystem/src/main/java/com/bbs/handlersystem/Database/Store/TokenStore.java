package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Token.Token;

import java.sql.SQLException;

public interface TokenStore extends Store<Token> {

    String GET_TOKEN_ID_QUERY = "SELECT id FROM tokens WHERE token = ?";
    String GET_TOKEN_BY_ID_QUERY = "SELECT token FROM tokens WHERE id = ?";
    String ADD_TOKEN_QUERY = "INSERT INTO tokens (token) VALUES (?)";
    String REMOVE_TOKEN_BY_ID_QUERY = "DELETE from tokens WHERE id = ?";
    String UPDATE_ID_BY_TOKEN_QUERY = "UPDATE tokens SET id = ? WHERE token = ?";

    void removeTokenById(long id) throws SQLException;

    void updateTokenById(long id) throws SQLException;
}
