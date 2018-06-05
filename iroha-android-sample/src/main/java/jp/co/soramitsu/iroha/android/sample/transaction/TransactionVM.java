package jp.co.soramitsu.iroha.android.sample.transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionVM {

    public final long id;
    public final String prettyDate;
    public final String username;
    public final String prettyAmount;
}
