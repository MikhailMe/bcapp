package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Database.Store.WalletStore;
import com.bbs.handlersystem.Database.StoreConnection;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class WalletStoreImpl implements WalletStore {

    private static long size = 0;

    private static final String GET_BALANCE_QUERY = "SELECT balance FROM wallets WHERE user_id = ?";
    private static final String GET_WALLET_ID_QUERY = "SELECT id FROM  wallets WHERE user_id = ?";
    private static final String CHANGE_BALANCE_QUERY = "UPDATE wallets SET balance = ? WHERE id = ?";
    private static final String GET_WALLET_BY_ID = "SELECT user_id, balance FROM wallets WHERE id = ?";
    private static final String ADD_WALLET_QUERY = "INSERT INTO wallets (user_id, balance) VALUES (?, ?)";

    @Override
    public long size() {
        return size;
    }

    @Override
    public long getBalance(final long user_id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_BALANCE_QUERY);
        preparedStatement.setLong(1, user_id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long balance = -1;
        if (resultSet.next()) {
            balance = resultSet.getLong(COLUMN_BALANCE);
        }
        return balance;
    }

    @Override
    public void add(@NonNull final Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_WALLET_QUERY);
        String userNickname = wallet.getUser().getNickname();
        preparedStatement.setLong(1, new UserStoreImpl().getId(userNickname));
        preparedStatement.setLong(2, wallet.getBalance());
        preparedStatement.execute();
        size++;
    }

    @Override
    public Wallet getById(final long id) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_WALLET_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long userId = -1L;
        long balance = -1L;
        if (resultSet.next()) {
            userId = resultSet.getLong("user_id");
            balance = resultSet.getLong("balance");
        }
        User user = new UserStoreImpl().getById(userId);
        Wallet wallet = new Wallet(user);
        wallet.setBalance(balance);
        return wallet;
    }

    @Override
    public long getId(@NonNull final String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_WALLET_ID_QUERY);
        preparedStatement.setLong(1, new UserStoreImpl().getId(nickname));
        preparedStatement.execute();
        return getIdFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public void changeBalance(final long walletId,
                              final long value,
                              final boolean isAdd) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(CHANGE_BALANCE_QUERY);
        preparedStatement.setLong(1, isAdd ? value : -value);
        preparedStatement.execute();
    }
}
