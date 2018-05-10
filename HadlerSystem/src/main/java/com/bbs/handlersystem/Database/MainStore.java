package com.bbs.handlersystem.Database;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainStore implements Store {

    private static final String COLUMN_ID = "id";

    //region ========================= Users database fields =========================
    private static final String ADD_USER_QUERY = "INSERT INTO users (nickname, has_token, is_oracle, mobile_number) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_ID = "SELECT id FROM users WHERE nickname = ?";
    private static final String SET_USER_TOKEN = "UPDATE users SET has_token = ? WHERE nickname = ?";
    private static final String SET_USER_ORACLE = "UPDATE users SET is_oracle = ? WHERE nickname = ?";
    //endregion

    //region ========================= Wallets database fields =========================
    private static final String ADD_WALLET_QUERY = "INSERT INTO wallets (user_id, balance) VALUES (?, ?)";
    private static final String GET_WALLET_ID = "SELECT id FROM  wallets WHERE user_id = ?";
    private static final String CHANGE_BALANCE_QUERY = "UPDATE wallets SET balance = ? WHERE id = ?";
    //endregion

    //region ========================= Accounts database fields =========================
    private static final String ADD_ACCOUNT_QUERY = "INSERT INTO accounts (wallet_id, current_visit) VALUES (?, ?)";
    private static final String GET_ACCOUNT_QUERY = "SELECT id FROM accounts WHERE wallet_id = ?";
    //endregion

    //region ========================= Users database =========================

    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_USER_QUERY);
        preparedStatement.setString(1, user.getNickname());
        preparedStatement.setBoolean(2, user.isHasToken());
        preparedStatement.setBoolean(3, user.isOracle());
        preparedStatement.setString(4, user.getMobileNumber());
        preparedStatement.execute();
    }

    @Override
    public long getUserId(String nickname) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_USER_ID);
        preparedStatement.setString(1, nickname);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long userId = -1L;
        if (resultSet.next()) {
            userId = resultSet.getLong(COLUMN_ID);
        }
        return userId;
    }

    @Override
    public void changeUserProperties(UserProperties type, String nickname, boolean value) throws SQLException {
        boolean isToken = type.equals(UserProperties.TOKEN);
        PreparedStatement preparedStatement =
                StoreConnection.getConnection().prepareStatement(isToken ? SET_USER_TOKEN : SET_USER_ORACLE);
        preparedStatement.setBoolean(1, value);
        preparedStatement.setString(2, nickname);
        preparedStatement.execute();
    }

    //endregion

    //region ========================= Wallets database =========================

    @Override
    public void addWallet(Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_WALLET_QUERY);
        String userNickname = wallet.getUser().getNickname();
        preparedStatement.setLong(1, getUserId(userNickname));
        preparedStatement.setLong(2, wallet.getBalance());
        preparedStatement.execute();
    }

    @Override
    public long getWalletId(User user) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_WALLET_ID);
        preparedStatement.setLong(1, getUserId(user.getNickname()));
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

    //endregion

    //region ========================= Accounts database =========================

    @Override
    public void addAccount(Account account) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(ADD_ACCOUNT_QUERY);
        long walletId = getWalletId(account.getWallet().getUser());
        preparedStatement.setLong(1, walletId);
        preparedStatement.setDate(2, Date.valueOf(account.getCurrentVisit()));
        preparedStatement.execute();
    }

    @Override
    public long getAccountId(Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = StoreConnection.getConnection().prepareStatement(GET_ACCOUNT_QUERY);
        long walletId = getWalletId(wallet.getUser());
        preparedStatement.setLong(1, walletId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long accountId = -1L;
        if (resultSet.next()) {
            accountId = resultSet.getLong(COLUMN_ID);
        }
        return accountId;
    }

    //endregion


}
