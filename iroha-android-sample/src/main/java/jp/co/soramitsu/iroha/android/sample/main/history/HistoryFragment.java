package jp.co.soramitsu.iroha.android.sample.main.history;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.databinding.DataBindingUtil;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;

import java.util.Objects;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionsAdapter;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionsViewModel;
import jp.co.soramitsu.iroha.android.sample.databinding.FragmentHistoryBinding;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionDiffChecker;

public class HistoryFragment extends Fragment implements HistoryView {

    @Inject
    HistoryPresenter mHistoryPresenter;

    private FragmentHistoryBinding binding;

    private TransactionsAdapter mTransactionsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        SampleApplication.instance.getApplicationComponent().inject(this);

        mHistoryPresenter.setMHistoryFragment(this);
        mHistoryPresenter.onCreateView();
        mHistoryPresenter.getTransactions();

        TransactionsViewModel transactionsViewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);
        transactionsViewModel.getMTransactions().observe(this, transactions -> {
            DiffUtil.Callback transactionDiffChecker =
                    new TransactionDiffChecker(mTransactionsAdapter.getMTransactions(), transactions);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(transactionDiffChecker);
            mTransactionsAdapter.setMTransactions(transactions);
            diffResult.dispatchUpdatesTo(mTransactionsAdapter);
        });

        configureRecycler();
        binding.refresh.setOnRefreshListener(() -> mHistoryPresenter.getTransactions());
        return binding.getRoot();
    }

    private void configureRecycler() {
        binding.transactions.setHasFixedSize(true);
        binding.transactions.setLayoutManager(new LinearLayoutManager(getContext()));
        mTransactionsAdapter = new TransactionsAdapter();
        binding.transactions.setAdapter(mTransactionsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHistoryPresenter.setMHistoryFragment(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHistoryPresenter.onStop();
    }

    @Override
    public void finishRefresh() {
        binding.refresh.setRefreshing(false);
    }

    @Override
    public void didError(Throwable error) {
        binding.refresh.setRefreshing(false);
        ((MainActivity) Objects.requireNonNull(getActivity())).showError(error);
    }
}
