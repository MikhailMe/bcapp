package com.bbs.handlersystem.Database.StoreImpl;

import com.bbs.handlersystem.Database.Store.TransactionStore;
import com.bbs.handlersystem.Transaction.Transaction;

import java.sql.SQLException;

public class TransactionStoreImpl implements TransactionStore {

    private static long size = 0;

    @Override
    public long size() {
        return size;
    }

    @Override
    public void add(Transaction object) throws SQLException {

    }

    @Override
    public Transaction getById(long id) throws SQLException {
        return null;
    }

    @Override
    public long getId(String nickname) throws SQLException {
        return 0;
    }
}
