package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.Wallet;

import java.sql.SQLException;

public interface WalletStore extends Store<Wallet> {

    long getBalance(long user_id) throws SQLException;

    void changeBalance(long walletId, long income, boolean isAdd) throws SQLException;

}
