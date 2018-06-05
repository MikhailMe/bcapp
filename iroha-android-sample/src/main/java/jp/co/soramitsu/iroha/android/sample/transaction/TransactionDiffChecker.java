package jp.co.soramitsu.iroha.android.sample.transaction;

import android.support.v7.util.DiffUtil;

import java.util.List;

import lombok.NonNull;

public class TransactionDiffChecker extends DiffUtil.Callback {

    private final List mOldTransactions;
    private final List mNewTransactions;

    public TransactionDiffChecker(@NonNull List oldTransactions,
                           @NonNull List newTransactions) {
        this.mOldTransactions = oldTransactions;
        this.mNewTransactions = newTransactions;
    }

    @Override
    public int getOldListSize() {
        return mOldTransactions.size();
    }

    @Override
    public int getNewListSize() {
        return mNewTransactions.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition,
                                   final int newItemPosition) {
        if (mOldTransactions.get(oldItemPosition) instanceof TransactionVM) {
            TransactionVM oldItem = (TransactionVM) mOldTransactions.get(oldItemPosition);
            TransactionVM newItem = (TransactionVM) mNewTransactions.get(newItemPosition);
            return oldItem.id == newItem.id;
        } else {
            return mOldTransactions.get(oldItemPosition).equals(mNewTransactions.get(newItemPosition));
        }
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition,
                                      final int newItemPosition) {
        if (mOldTransactions.get(oldItemPosition) instanceof TransactionVM) {
            TransactionVM oldItem = (TransactionVM) mOldTransactions.get(oldItemPosition);
            TransactionVM newItem = (TransactionVM) mNewTransactions.get(newItemPosition);
            return oldItem.prettyAmount.equals(newItem.prettyAmount)
                    && oldItem.prettyDate.equals(newItem.prettyDate)
                    && oldItem.username.equals(newItem.username);
        } else {
            return mOldTransactions.get(oldItemPosition).equals(mNewTransactions.get(newItemPosition));
        }
    }
}
