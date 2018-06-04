package jp.co.soramitsu.iroha.android.sample.data;

import iroha.protocol.Responses;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Account {

    @Setter
    @Getter
    @NonNull
    private Responses.Account irohaAccount;

    @Setter
    @Getter
    private long balance;

    public Account(@NonNull Responses.Account account,
                   final long balance) {
        this.irohaAccount = account;
        this.balance = balance;
    }
}
