package jp.co.soramitsu.iroha.android.sample.transaction;

import java.util.Date;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Transaction {

    public final long id;
    public final Date date;
    public final String username;
    public final long amount;
}
