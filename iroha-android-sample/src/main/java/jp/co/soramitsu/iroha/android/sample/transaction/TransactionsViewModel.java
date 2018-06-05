package jp.co.soramitsu.iroha.android.sample.transaction;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NonNull;

public class TransactionsViewModel extends ViewModel {

    @Getter
    @NonNull
    private MutableLiveData<List> mTransactions;

    public TransactionsViewModel() {
        mTransactions = new MutableLiveData<>();
        mTransactions.setValue(new ArrayList());
    }
}
