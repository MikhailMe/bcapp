package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Client.Bet;
import com.bbs.handlersystem.Database.Store.BetStore;

import java.sql.SQLException;

public final class BetStoreImpl implements BetStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(Bet object) throws SQLException {

    }

    @Override
    public Bet getById(long id) throws SQLException {
        return null;
    }

    @Override
    public long getId(String nickname) throws SQLException {
        return 0;
    }
}
