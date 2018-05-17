package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.Wallet;

import java.sql.SQLException;

public interface WalletStore extends Store<Wallet> {

    String GET_BALANCE_QUERY = "SELECT balance FROM wallets WHERE user_id = ?";
    String GET_WALLET_ID_QUERY = "SELECT id FROM  wallets WHERE user_id = ?";
    String CHANGE_BALANCE_QUERY = "UPDATE wallets SET balance = ? WHERE id = ?";
    String GET_WALLET_BY_ID = "SELECT user_id, balance FROM wallets WHERE id = ?";
    String ADD_WALLET_QUERY = "INSERT INTO wallets (user_id, balance) VALUES (?, ?)";

    long getBalance(long user_id) throws SQLException;

    void changeBalance(long walletId, long income, boolean isAdd) throws SQLException;

}
