package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Database.Store.*;

public final class MainStore {

    public static BetStore betStore;
    public static GameStore gameStore;
    public static UserStore userStore;
    public static TokenStore tokenStore;
    public static WalletStore walletStore;
    public static AccountStore accountStore;

    static {
        betStore = new BetStoreImpl();
        gameStore = new GameStoreImpl();
        userStore = new UserStoreImpl();
        tokenStore = new TokenStoreImpl();
        walletStore = new WalletStoreImpl();
        accountStore = new AccountStoreImpl();
    }

}
