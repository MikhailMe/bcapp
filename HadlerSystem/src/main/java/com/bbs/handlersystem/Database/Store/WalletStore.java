package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.Wallet;

import java.sql.SQLException;

public interface WalletStore extends Store<Wallet> {

    void changeBalance(long walletId, long income, boolean isAdd) throws SQLException;

}
