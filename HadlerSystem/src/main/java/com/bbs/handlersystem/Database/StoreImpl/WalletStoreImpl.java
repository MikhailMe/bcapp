package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Database.Store.WalletStore;
import com.bbs.handlersystem.Database.StoreConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletStoreImpl implements WalletStore {

    private static final String ADD_WALLET_QUERY = "INSERT INTO wallets (user_id, balance) VALUES (?, ?)";
    private static final String GET_WALLET_ID = "SELECT id FROM  wallets WHERE user_id = ?";
    private static final String CHANGE_BALANCE_QUERY = "UPDATE wallets SET balance = ? WHERE id = ?";

    @Override
    public void add(Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_WALLET_QUERY);
        String userNickname = wallet.getUser().getNickname();
        preparedStatement.setLong(1, new UserStoreImpl().getId(userNickname));
        preparedStatement.setLong(2, wallet.getBalance());
        preparedStatement.execute();
    }

    @Override
    public long getId(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_WALLET_ID);
        preparedStatement.setLong(1, new UserStoreImpl().getId(nickname));
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long walletId = -1L;
        if (resultSet.next()) {
            walletId = resultSet.getLong(COLUMN_ID);
        }
        return walletId;
    }

    @Override
    public void changeBalance(long walletId, long value, boolean isAdd) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(CHANGE_BALANCE_QUERY);
        preparedStatement.setLong(1, isAdd ? value : -value);
        preparedStatement.execute();
    }
}
