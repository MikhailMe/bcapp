package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Database.Store.AccountStore;
import com.bbs.handlersystem.Database.Store.GameStore;
import com.bbs.handlersystem.Database.Store.UserStore;
import com.bbs.handlersystem.Database.Store.WalletStore;

public class MainStore {

    public static GameStore gameStore;
    public static UserStore userStore;
    public static WalletStore walletStore;
    public static AccountStore accountStore;

    static {
        gameStore = new GameStoreImpl();
        userStore = new UserStoreImpl();
        walletStore = new WalletStoreImpl();
        accountStore = new AccountStoreImpl();
    }

}
