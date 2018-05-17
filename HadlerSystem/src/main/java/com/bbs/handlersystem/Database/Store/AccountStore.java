package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.Account;

public interface AccountStore extends Store<Account> {

    String GET_ACCOUNT_QUERY = "SELECT id FROM accounts WHERE wallet_id = ?";
    String GET_ACCOUNT_BY_ID_QUERY = "SELECT wallet_id FROM accounts WHERE id = ?";
    String ADD_ACCOUNT_QUERY = "INSERT INTO accounts (wallet_id, current_visit) VALUES (?, ?)";

}
