package jp.co.soramitsu.iroha.android.sample.transaction;

import lombok.Getter;
import lombok.Setter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;

public class TransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Getter
    @Setter
    private List mTransactions;

    public TransactionsAdapter() {
        this.mTransactions = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_item, parent, false);
            return new TransactionItem(v);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_header_item, parent, false);
            return new TransactionHeaderItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TransactionItem) {
            TransactionVM transaction = (TransactionVM) mTransactions.get(position);
            TransactionItem transactionItem = (TransactionItem) holder;
            transactionItem.username.setText(transaction.username);
            if (transaction.prettyAmount.contains("-")) {
                transactionItem.amount.setText("- " + transaction.prettyAmount.substring(1, transaction.prettyAmount.length()));
                transactionItem.amount.setTextColor(SampleApplication.instance.getResources().getColor(R.color.main_background_color));
            } else {
                transactionItem.amount.setText("+ " + transaction.prettyAmount);
                transactionItem.amount.setTextColor(SampleApplication.instance.getResources().getColor(R.color.positiveAmount));
            }
            transactionItem.date.setText(transaction.prettyDate);
        } else if (holder instanceof TransactionHeaderItem) {
            TransactionHeaderItem headerItem = (TransactionHeaderItem) holder;
            headerItem.date.setText(mTransactions.get(position).toString());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTransactions.get(position) instanceof TransactionVM) {
            return TYPE_ITEM;
        }
        return TYPE_HEADER;
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    static class TransactionItem extends RecyclerView.ViewHolder {

        TextView username;
        TextView date;
        TextView amount;

        TransactionItem(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
        }
    }

    static class TransactionHeaderItem extends RecyclerView.ViewHolder {

        TextView date;

        TransactionHeaderItem(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
        }
    }
}
