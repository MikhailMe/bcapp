package jp.co.soramitsu.iroha.android.sample.bet;

public interface BetView {

    void didSendSuccess();

    void didSendError(Throwable error);

}
