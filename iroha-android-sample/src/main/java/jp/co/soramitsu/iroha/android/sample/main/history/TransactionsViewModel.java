package jp.co.soramitsu.iroha.android.sample.main.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;


public class TransactionsViewModel extends ViewModel {

    @Getter
    @NonNull
    private MutableLiveData<List> transactions;

    public TransactionsViewModel() {
        transactions = new MutableLiveData<>();
        transactions.setValue(new ArrayList());
    }
}
