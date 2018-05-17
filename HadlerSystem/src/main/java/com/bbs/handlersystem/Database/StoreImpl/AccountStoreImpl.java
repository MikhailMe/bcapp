package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Database.Store.AccountStore;
import com.bbs.handlersystem.Database.StoreConnection;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class AccountStoreImpl implements AccountStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(@NonNull final Account account) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_ACCOUNT_QUERY);
        String nickname = account.getWallet().getUser().getNickname();
        long walletId = new WalletStoreImpl().getId(nickname);
        preparedStatement.setLong(1, walletId);
        preparedStatement.setTimestamp(2, account.getCurrentVisit());
        preparedStatement.execute();
        size++;
    }

    @Override
    public Account getById(final long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_ACCOUNT_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long wallet_id = -1L;
        if (resultSet.next()) {
            wallet_id = resultSet.getLong(COLUMN_WALLET_ID);
        }
        Wallet wallet = new WalletStoreImpl().getById(wallet_id);
        return new Account(wallet);
    }

    @Override
    public long getId(@NonNull final String string) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_ACCOUNT_QUERY);
        long walletId = new WalletStoreImpl().getId(string);
        preparedStatement.setLong(1, walletId);
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }
}
