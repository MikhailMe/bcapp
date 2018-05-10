package com.bbs.handlersystem.Database;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;

import java.sql.SQLException;

public interface Store {

    //region ========================= methods for users table =========================
    void addUser(User user) throws SQLException;

    long getUserId(String nickname) throws SQLException;

    void changeUserProperties(UserProperties type, String nickname, boolean value) throws SQLException;
    //endregion

    //region ========================= methods for wallets table =========================
    void addWallet(Wallet wallet) throws SQLException;

    long getWalletId(User user) throws SQLException;

    void changeBalance(long walletId, long income, boolean isAdd) throws SQLException;
    //endregion

    //region ========================= methods for accounts table =========================
    void addAccount(Account account) throws SQLException;

    long getAccountId(Wallet wallet) throws SQLException;
    //endregion

}
