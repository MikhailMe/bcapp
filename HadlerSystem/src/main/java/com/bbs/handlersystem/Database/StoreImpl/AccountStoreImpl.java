package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Database.Store.AccountStore;
import com.bbs.handlersystem.Database.StoreConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountStoreImpl implements AccountStore {

    private static long size = 0;
    private static final String ADD_ACCOUNT_QUERY = "INSERT INTO accounts (wallet_id, current_visit) VALUES (?, ?)";
    private static final String GET_ACCOUNT_QUERY = "SELECT id FROM accounts WHERE wallet_id = ?";

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(Account account) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_ACCOUNT_QUERY);
        long walletId = new WalletStoreImpl().getId(account.getWallet().getUser().getNickname());
        preparedStatement.setLong(1, walletId);
        preparedStatement.setDate(2, Date.valueOf(account.getCurrentVisit()));
        preparedStatement.execute();
        size++;
    }

    @Override
    public long getId(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_ACCOUNT_QUERY);
        long walletId = new WalletStoreImpl().getId(nickname);
        preparedStatement.setLong(1, walletId);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }
}
