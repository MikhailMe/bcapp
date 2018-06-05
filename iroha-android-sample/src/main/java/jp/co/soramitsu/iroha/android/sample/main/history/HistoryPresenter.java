package jp.co.soramitsu.iroha.android.sample.main.history;

import android.text.format.DateUtils;
import android.arch.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;
import lombok.Setter;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountTransactionsInteractor;
import jp.co.soramitsu.iroha.android.sample.transaction.Transaction;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionVM;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionsViewModel;

public class HistoryPresenter {

    @Setter
    private HistoryFragment mHistoryFragment;

    private TransactionsViewModel mTransactionsViewModel;

    private final GetAccountTransactionsInteractor mGetAccountTransactionsInteractor;

    private static final String TODAY = "Today";
    private static final String JUST_NOW = "just now";
    private static final String YESTERDAY = "Yesterday";
    private static final String MINUTES_AGO = "minutes ago";
    private static final String HOURS_DATE_FORMAT = "HH:mm";
    private static final String HEADER_DATE_FORMAT = "MMMM, dd";

    @Inject
    public HistoryPresenter(@NonNull GetAccountTransactionsInteractor getAccountTransactionsInteractor) {
        this.mGetAccountTransactionsInteractor = getAccountTransactionsInteractor;
    }

    void onCreateView() {
        mTransactionsViewModel = ViewModelProviders.of(mHistoryFragment).get(TransactionsViewModel.class);
    }

    void getTransactions() {
        mGetAccountTransactionsInteractor.execute(
                transactions -> {
                    Collections.sort(transactions, (o1, o2) -> o2.date.compareTo(o1.date));
                    mTransactionsViewModel.getMTransactions().postValue(transformTransactions(transactions));
                    mHistoryFragment.finishRefresh();
                },
                throwable -> mHistoryFragment.didError(throwable));
    }

    private List transformTransactions(@NonNull List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }
        List listItems = new ArrayList();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();

        c.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.HOUR, -1);
        Date oneHourBefore = c.getTime();

        SimpleDateFormat headerDateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat hoursDateFormat = new SimpleDateFormat(HOURS_DATE_FORMAT, Locale.getDefault());

        String currentPrettyDate = getHeader(transactions.get(0).date, headerDateFormat,
                today, yesterday);

        listItems.add(currentPrettyDate);

        for (Transaction transaction : transactions) {
            if (!getHeader(transaction.date, headerDateFormat, today, yesterday)
                    .equals(currentPrettyDate)) {
                currentPrettyDate = getHeader(transaction.date, headerDateFormat,
                        today, yesterday);
                listItems.add(currentPrettyDate);
            }

            BigDecimal amount = new BigDecimal(transaction.amount);
            String prettyAmount = amount.toString();

            String prettyDate;
            if (currentPrettyDate.equals(TODAY) && transaction.date.after(oneHourBefore)) {
                long duration = new Date().getTime() - transaction.date.getTime();
                long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                if (diffInMinutes == 0) {
                    prettyDate = JUST_NOW;
                } else {
                    prettyDate = diffInMinutes + " " + MINUTES_AGO;
                }
            } else {
                prettyDate = hoursDateFormat.format(transaction.date);
            }

            TransactionVM vm = new TransactionVM(transaction.id, prettyDate, transaction.username, prettyAmount);

            listItems.add(vm);
        }
        return listItems;
    }

    private String getHeader(@NonNull Date date,
                             @NonNull SimpleDateFormat dateFormat,
                             @NonNull Date today,
                             @NonNull Date yesterday) {
        if (DateUtils.isToday(date.getTime())) {
            return TODAY;
        } else if (date.before(today) && date.after(yesterday)) {
            return YESTERDAY;
        } else {
            return dateFormat.format(date);
        }
    }

    void onStop() {
        mHistoryFragment = null;
        mGetAccountTransactionsInteractor.unsubscribe();
    }
}
