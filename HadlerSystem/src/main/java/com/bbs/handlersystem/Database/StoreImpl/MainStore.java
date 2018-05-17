package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Database.Store.*;

public final class MainStore {

    public static BetStore betStore;
    public static GameStore gameStore;
    public static UserStore userStore;
    public static WalletStore walletStore;
    public static AccountStore accountStore;
    public static TransactionStore transactionStore;

    static {
        betStore = new BetStoreImpl();
        gameStore = new GameStoreImpl();
        userStore = new UserStoreImpl();
        walletStore = new WalletStoreImpl();
        accountStore = new AccountStoreImpl();
        transactionStore = new TransactionStoreImpl();
    }

}
